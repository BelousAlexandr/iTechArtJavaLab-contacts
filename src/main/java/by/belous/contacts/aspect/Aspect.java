package by.belous.contacts.aspect;

import java.lang.reflect.Method;

public interface Aspect {

    boolean isApplied(Object target, Method method, Object[] args) throws Exception;

    public void before(Object target, Method method, Object[] args) throws Exception;

    public void after(Object target, Method method, Object[] args) throws Exception;

    public void exception(Exception e, Object target, Method method, Object[] args) throws Exception;
}
