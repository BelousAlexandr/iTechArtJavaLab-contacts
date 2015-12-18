package by.belous.contacts.aspect;

import by.belous.contacts.ThreadContext;
import by.belous.contacts.connection.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;

public class TransactionAspect implements Aspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean isApplied(Object target, Method method, Object[] args) throws Exception {
        return method.isAnnotationPresent(Transactional.class);
    }

    @Override
    public void before(Object target, Method method, Object[] args) throws Exception {
        log.debug("start transaction");
        Connection conn = DBConnectionManager.getInstance().getConnection();
        conn.setAutoCommit(false);
        ThreadContext.setCurrentConnection(conn);
    }

    @Override
    public void after(Object target, Method method, Object[] args) throws Exception {
        log.debug("commit transaction");
        ThreadContext.getCurrentConnection().commit();
        log.debug("connection close");
        ThreadContext.getCurrentConnection().close();
        log.debug("remove current connection");
        ThreadContext.removeCurrentConnection();
    }

    @Override
    public void exception(Exception e, Object target, Method method, Object[] args) throws Exception {
        log.debug("exception transaction");
        ThreadContext.getCurrentConnection().rollback();
        ThreadContext.getCurrentConnection().close();
        ThreadContext.removeCurrentConnection();
    }
}
