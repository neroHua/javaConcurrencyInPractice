package chapter02;

/**
 * thread safe means multiple thread program run as we expected
 * 线程安全意味着，多线程程序按照我们的期望运行
 *
 * thread safety is most import thing in concurrent programming
 * 在编写高并发程序中最重要的是线程安全
 *
 * the core for writing thread safe code is about managing access to state
 * 编写线程安全的代码的关键是管理状态
 *
 * an object's state is its data, stored in state variables such as instance or static fields
 * 一个对象的状态就是他的数据，通常装载在它的状态变量中。状态变量可以是：实例或静态字段（属性）
 *
 * three ways to fix thread safe problem
 * 三种修复线程安全问题的办法
 *  1. Don't share the state variable across threads;
 *  1. 不要多线程共享变量
 *  2. Make the state variable immutable;
 *  2. 状态变量不可变
 *  3. Use synchronization whenever accessing the state variable
 *  3. 只要访问状态变量就一定得适当同步
 *
 */
public class ThreadSafe {
}
