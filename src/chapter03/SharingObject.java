package chapter03;

/**
 * sharing object can bring thread safe problem
 * 共享对象可能会带来线程安全问题
 *
 * here is a way to share object without thread safe problem
 * 这里有一个共享对象同时又避免线程安全问题的办法
 * guard: using lock ，wherever and whenever you want to access share object
 * 守护: 无论何时何地你想要的访问共对象都必须使用锁
 *
 * here are some ways for not share object
 * 这里是一些不共享对象的办法
 * thread confined: stack confined, threadLocal, aliveInAThread
 * 线程限制： 栈限制，线程本地，生命周期在某个线程中
 *
 * here is common way for both
 * 这里是个通用办法
 * immutable object
 * 不可变对象
 *
 */
public class SharingObject {

}
