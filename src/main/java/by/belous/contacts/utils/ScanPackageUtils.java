package by.belous.contacts.utils;

import by.belous.contacts.controller.RequestMapping;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class ScanPackageUtils {
    private static Set<Class<?>> annotated;

    public static RegexMap getPathOfAnnotation(String packageName, Class<? extends Annotation> annotationName) {
        final Reflections reflections = new Reflections(packageName);
        annotated = reflections.getTypesAnnotatedWith(annotationName);
        RegexMap map = new RegexMap();
        for (Class<?> controller : annotated) {
            final Method[] methods = controller.getDeclaredMethods();
            for (Method method : methods) {
                RequestMapping request = method.getAnnotation(RequestMapping.class);
                if (request != null) {
                    map.put(request.path() + "@" + request.method(), method);
                }
            }
        }
        return map;
    }
}
