package by.belous.contacts;

import java.sql.Connection;

public class ThreadContext {
    private static final ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

    public static Connection getCurrentConnection() {
        return threadConnection.get();
    }

    public static void setCurrentConnection(Connection connection) {
        threadConnection.set(connection);
    }

    public static void removeCurrentConnection() {
        threadConnection.remove();
    }

}
