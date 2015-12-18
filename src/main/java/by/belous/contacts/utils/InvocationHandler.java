package by.belous.contacts.utils;

import by.belous.contacts.aspect.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class InvocationHandler implements java.lang.reflect.InvocationHandler {

    private Logger log = LoggerFactory.getLogger(InvocationHandler.class);
    private Object target;
    private Aspect aspect;

    public InvocationHandler(Object target, Aspect aspect) {
        this.aspect = aspect;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        if (isApplied(method, args, aspect, target)) {
            before(method, args, aspect, target);
            try {
                result = method.invoke(target, args);
            } catch (Exception e) {
                exception(method, args, e, aspect, target);
                throw e;
            }
            after(method, args, aspect, target);
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }

    private void after(Method method, Object[] args, Aspect aspect,
                       Object target) throws Exception {
        aspect.after(target, method, args);
    }

    private void exception(Method method, Object[] args, Exception e, Aspect aspect,
                           Object target) throws Exception {
        aspect.exception(e, target, method, args);
    }

    private void before(Method method, Object[] args, Aspect aspect,
                        Object target) throws Exception {
        aspect.before(target, method, args);
    }

    private boolean isApplied(Method method, Object[] args, Aspect aspect, Object target) throws Exception {
        return aspect.isApplied(target, method, args);
    }

}
