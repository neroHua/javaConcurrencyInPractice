package chapter04.case02;

import common.GuardedBy;
import common.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class PersonSet {

    @GuardedBy("this")
    private final Set<Person> personSet = new HashSet<Person>();

    public synchronized void addPerson(Person person) {
        personSet.add(person);
    }

    public synchronized boolean containPerson(Person person) {
        return personSet.contains(person);
    }

    private class Person {
    }

}
