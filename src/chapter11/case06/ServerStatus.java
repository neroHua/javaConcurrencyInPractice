package chapter11.case06;

import common.GuardedBy;
import common.ThreadSafe;

import java.util.Set;

@ThreadSafe
public class ServerStatus {

    @GuardedBy("users")
    public final Set<String> users;
    @GuardedBy("queries")
    public final Set<String> queries;

    public ServerStatus(Set<String> users, Set<String> queries) {
        this.users = users;
        this.queries = queries;
    }

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQuery(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }

}
