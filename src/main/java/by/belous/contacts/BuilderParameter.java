package by.belous.contacts;

import by.belous.contacts.servlet.ParameterValue;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public interface BuilderParameter {
    Object[] build(HttpServletRequest request, HttpServletResponse response, Method method,
                   List<String> matches, Map<String, List<ParameterValue>> params) throws IOException,
            InstantiationException, ParseException, IllegalAccessException, java.text.ParseException;
}
