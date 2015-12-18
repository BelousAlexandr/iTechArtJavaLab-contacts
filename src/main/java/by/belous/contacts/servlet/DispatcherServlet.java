package by.belous.contacts.servlet;

import by.belous.contacts.BuilderParameterImpl;
import by.belous.contacts.controller.Controller;
import by.belous.contacts.controller.RequestMapping;
import by.belous.contacts.controller.model.ViewModel;
import by.belous.contacts.utils.RegexMap;
import by.belous.contacts.utils.ScanPackageUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
    private RegexMap map;


    @Override
    public void init() {
        map = ScanPackageUtils.getPathOfAnnotation("by.belous.contacts.controller", Controller.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer path = getPath(request);
        Method method = (Method) map.get(path);
        if (method != null) {
            List<String> matches = getMethodParameterMatches(request, path, method);
            ViewModel viewModel;
            Map<String, List<ParameterValue>> params = null;
            try {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (isMultipart) {
                    ServletFileUpload upload = createUploader();
                    List<FileItem> parameters = upload.parseRequest(request);
                    if (CollectionUtils.isNotEmpty(parameters)) {
                        params = new HashMap<>();
                        for (FileItem parameter : parameters) {
                            String fieldName = parameter.getFieldName();
                            List<ParameterValue> paramValues = params.get(fieldName);
                            if (paramValues == null) {
                                paramValues = new ArrayList<>();
                                params.put(fieldName, paramValues);
                            }
                            if (!parameter.isFormField()) {
                                paramValues.add(new ParameterValue(parameter));
                            } else {
                                paramValues.add(new ParameterValue(parameter.getString("UTF-8")));
                            }
                        }
                    }
                } else {
                    params = getRequestParameters(request);
                }
                Object[] parameters = new BuilderParameterImpl().build(request, response, method, matches, params);
                viewModel = getViewModel(method, parameters);
                if (viewModel != null) {
                    forward(request, response, viewModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendError(404);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ViewModel getViewModel(Method method, Object... parameters) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Class<?> clazz = method.getDeclaringClass();
        Object obj = clazz.newInstance();
        return invoke(method, obj, parameters);
    }

    private Map<String, List<ParameterValue>> getRequestParameters(HttpServletRequest request) {
        Map<String, List<ParameterValue>> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            ArrayList<ParameterValue> values = new ArrayList<>();
            for (String value : request.getParameterValues(paramName)) {
                values.add(new ParameterValue(value));
            }
            parameters.put(paramName, values);
        }
        return parameters;
    }


    private List<String> getMethodParameterMatches(HttpServletRequest request, StringBuffer path, Method method) {
        if (method != null) {
            if (request.getQueryString() == null) {
                String methodPath = method.getAnnotation(RequestMapping.class).path();
                Matcher matcher = Pattern.compile(methodPath).matcher(path);
                ArrayList<String> matches = new ArrayList<>();
                matcher.find();
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String group = matcher.group(i);
                    matches.add(group);
                }
                return matches;
            }
        }
        return new ArrayList<>();
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, ViewModel viewModel)
            throws ServletException, IOException {
        Map<String, Object> viewModelMap = viewModel.getModel();
        if (viewModelMap != null) {
            for (String str : viewModelMap.keySet()) {
                request.setAttribute(str, viewModelMap.get(str));
            }
        }

        String redirectPage = viewModel.getForward();
        if (redirectPage.startsWith("redirect:")) {
            redirectPage = redirectPage.substring(9);

            response.sendRedirect(redirectPage);
        } else if (redirectPage.startsWith("forward:")) {
            redirectPage = redirectPage.substring(8);
            request.getRequestDispatcher(redirectPage).forward(request, response);
        }
    }

    private ViewModel invoke(Method method, Object obj, Object... parameters)
            throws IllegalAccessException, InvocationTargetException {
        ViewModel viewModel = null;
        if (method.getReturnType().equals(ViewModel.class)) {
            viewModel = (ViewModel) method.invoke(obj, parameters);
        } else {
            method.invoke(obj, parameters);
        }
        return viewModel;
    }

    private StringBuffer getPath(HttpServletRequest request) {
        StringBuffer path = new StringBuffer(request.getRequestURI());
        path.append('@');
        path.append(request.getMethod().toUpperCase());
        return path;
    }

    private ServletFileUpload createUploader() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        return upload;
    }

}
