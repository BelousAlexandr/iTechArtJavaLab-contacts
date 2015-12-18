package by.belous.contacts;

import by.belous.contacts.controller.Body;
import by.belous.contacts.controller.FileUpload;
import by.belous.contacts.controller.PathParam;
import by.belous.contacts.controller.QueryParam;
import by.belous.contacts.entity.Paging;
import by.belous.contacts.servlet.ParameterValue;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderParameterImpl implements BuilderParameter {


    @Override
    public Object[] build(HttpServletRequest request, HttpServletResponse response, Method method,
                          List<String> matches, Map<String, List<ParameterValue>> params) throws IOException,
            InstantiationException, ParseException, IllegalAccessException, java.text.ParseException {
        if (method != null) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Object[] parameterValues = new Object[parameterAnnotations.length];

            for (int i = 0; i < parameterAnnotations.length; i++) {
                Class<?> paramType = method.getParameterTypes()[i];
                PathParam pathParamAnnotation = (PathParam) findAnnotation(parameterAnnotations[i], PathParam.class);
                Body bodyParamAnnotation = (Body) findAnnotation(parameterAnnotations[i], Body.class);
                FileUpload fileUploadAnnotation = (FileUpload) findAnnotation(parameterAnnotations[i], FileUpload.class);
                QueryParam queryParamAnnotation = (QueryParam) findAnnotation(parameterAnnotations[i], QueryParam.class);

                if (pathParamAnnotation != null) {
                    parameterValues[i] = buildPathParameterValue(matches, paramType, pathParamAnnotation);
                } else if (bodyParamAnnotation != null) {
                    parameterValues[i] = buildBodyParameterValue(params, paramType);
                } else if (fileUploadAnnotation != null) {
                    parameterValues[i] = buildFileUploadParameterValue(params, paramType, fileUploadAnnotation);
                } else if (queryParamAnnotation != null) {
                    parameterValues[i] = buildQueryParameterValue(request, paramType);
                } else if (paramType.isAssignableFrom(HttpServletResponse.class)) {
                    parameterValues[i] = response;
                } else if (paramType.isAssignableFrom(HttpServletRequest.class)) {
                    parameterValues[i] = request;
                }
            }
            return parameterValues;
        }
        return new Object[0];
    }

    private Object buildPathParameterValue(List<String> matches, Class<?> paramType, PathParam pathParamAnnotation) {
        int groupNumber = pathParamAnnotation.value();
        String s = matches.get(groupNumber);
        return convert(paramType, s);
    }

    private Object buildQueryParameterValue(HttpServletRequest request, Class<?> paramType)
            throws IllegalAccessException, InstantiationException, UnsupportedEncodingException, java.text.ParseException {

        Object object = paramType.newInstance();
        if (request.getQueryString() != null) {
            String[] queryParams = URLDecoder.decode(request.getQueryString(), "UTF-8").split("&");
            Map<String, String> parameters = getQueryParameters(queryParams);
            Field[] fields = paramType.getDeclaredFields();
            setQueryParameters(object, parameters, fields);
        }
        if (object.getClass() == Paging.class && request.getQueryString() == null) {
            Paging paging = new Paging();
            paging.setPage(1);
            paging.setPageSize(10);
            object = paging;
        }
        return object;
    }

    private void setQueryParameters(Object object, Map<String, String> parameters, Field[] fields)
            throws IllegalAccessException, java.text.ParseException {
        if (MapUtils.isNotEmpty(parameters)) {
            for (Field field : fields) {
                String value = parameters.get(field.getName());
                if (value != null) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (!Modifier.isFinal(field.getModifiers()) && type.equals(Date.class)) {
                        field.set(object, DateUtils.parseDate(value, "yyyy-MM-dd"));
                    } else if (type.isEnum()) {
                        field.set(object, Enum.valueOf((Class<Enum>) type, value));
                    } else {
                        field.set(object, convert(type, value));
                    }
                    field.setAccessible(false);
                }
            }
        }
    }

    private Map<String, String> getQueryParameters(String[] queryParams) {
        Map<String, String> parameters = new HashMap<>();
        for (String queryParam : queryParams) {
            int indexOf = queryParam.indexOf('=');
            if (indexOf != -1) {
                String key = queryParam.substring(0, indexOf);
                String value = queryParam.substring(indexOf + 1);
                parameters.put(key, value);
            }
        }
        return parameters;
    }

    private Object buildFileUploadParameterValue(Map<String, List<ParameterValue>> params,
                                                 Class<?> paramType, FileUpload fileUploadAnnotation) throws IOException {
        String expectedParameterName = fileUploadAnnotation.value();
        Map<Long, ParameterValue> fileItems = Maps.newHashMap();
        for (Map.Entry<String, List<ParameterValue>> paramEntry : params.entrySet()) {
            if (StringUtils.startsWith(paramEntry.getKey(), expectedParameterName)) {
                String draftIndex = StringUtils.substringAfter(paramEntry.getKey(), expectedParameterName);
                if (StringUtils.isNotEmpty(draftIndex)) {
                    long index = Long.parseLong(draftIndex.substring(1, draftIndex.length() - 1));
                    fileItems.put(index, paramEntry.getValue().get(0));
                } else {
                    fileItems.put(0L, paramEntry.getValue().get(0));
                }
            }
        }

        if (MapUtils.isNotEmpty(fileItems)) {
            boolean isMap = paramType.isAssignableFrom(Map.class);
            if (isMap) {
                return Maps.transformEntries(fileItems, toInputStream());
            } else return toInputStream().transformEntry(0L, fileItems.get(0L));
        }
        return null;
    }

    private Maps.EntryTransformer<Long, ParameterValue, InputStream> toInputStream() {
        return new Maps.EntryTransformer<Long, ParameterValue, InputStream>() {
            @Override
            public InputStream transformEntry(Long key, ParameterValue parameterValue) {
                try {
                    return ((FileItem) parameterValue.get()).getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to open input stream");
                }
            }
        };
    }

    private Object buildBodyParameterValue(Map<String, List<ParameterValue>> params, Class<?> paramType)
            throws InstantiationException, IllegalAccessException, IOException, ParseException {
        String fieldName = paramType.getSimpleName().toLowerCase();
        List<ParameterValue> parameterValues = params.get(fieldName);
        Object obj = null;
        if (!paramType.isArray()) {
            obj = paramType.newInstance();
            if (parameterValues.size() == 1) {
                ParameterValue parameterValue = parameterValues.get(0);
                ObjectMapper mapper = new ObjectMapper();
                byte[] bytes = ((String) parameterValue.get()).getBytes("UTF-8");
                obj = mapper.readValue(bytes, obj.getClass());
            }
        } else {
            if (parameterValues != null) {
                obj = Array.newInstance(paramType.getComponentType(), parameterValues.size());
                for (int i = 0; i < parameterValues.size(); i++) {
                    Array.set(obj, i, convert(paramType.getComponentType(), (String) parameterValues.get(i).get()));
                }
            }
        }
        return obj;
    }

    private Object convert(Class<?> targetType, String text) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }

    private Annotation findAnnotation(Annotation[] parameterAnnotations, Class annotationClass) {
        for (Annotation annotation : parameterAnnotations) {
            if (annotation.annotationType().isAssignableFrom(annotationClass)) {
                return annotation;
            }
        }
        return null;
    }
}
