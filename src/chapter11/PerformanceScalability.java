package chapter11;

/**
 * Costs Introduced by Threads
 * 使用多线程的代价
 *
 * 1. Context Switching
 * 1. 上下文切换
 * 2. Memory Synchronization
 * 2. 内存同步
 * 3. Blocking
 * 3. 阻塞
 *
 * Reducing Lock Contention
 * 减少锁的竞争
 * 1. narrowing lock scope
 * 1. 减小锁的范围
 * 2. reduce lock granularity: lock splitting, lock striping
 * 2. 减小锁的粒度: 锁分拆, 锁平行（分拆的一种，分拆后的state是一样的）
 * 3. alternatives to exclusive locks: read-write lock, immutable object, atomic variables
 * 3. 替换独占锁：读写锁, 不可变对象, 原子变量
 */
public class PerformanceScalability {
}
