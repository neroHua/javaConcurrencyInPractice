package chapter03.case09;

/**
 * ThreadLocal
 * 本地线程
 *
 * typical using： holding the transaction context
 * 典型用法： 存放事务的上下文
 *
 */
public class ConnectionHolder {

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    public static Connection getConnection() {
        connectionHolder.set(new Connection());
        return connectionHolder.get();
    }

    public static class Connection {

    }

}
