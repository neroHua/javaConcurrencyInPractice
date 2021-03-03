package chapter15.case05;

import chapter08.case05.Node;
import common.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class ConcurrentLinkedQueue<E> {

    private final Node<E> dummy = new Node<E>(null, null);

    private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);

    /**
     * there is only two step on put blockly
     * 阻塞时的插入仅有两步
     *
     * @param item
     */
    public synchronized void putBlockly(E item) {
        Node newTailNode = new Node(item, null);
        // step one
        // 步骤1
        tail.get().next.set(newTailNode);
        // step two
        // 步骤2
        tail.set(newTailNode);
    }

    /**
     * there is only two state on put no-blockly
     * 非阻塞时的插入仅有两种情况
     *
     * @param item
     */
    public boolean put(E item) {
        Node<E> newTailNode = new Node<E>(item, null);
        while (true) {
            Node<E> oldTailNode = tail.get();
            Node<E> oldTailNodeNextNode = oldTailNode.next.get();

            if (oldTailNode == tail.get()) {
                // middle state
                // 中间状态
                // if condition is true : this queue in a middle state
                // 如果条件为真：队列将在中间状态
                // other thread just finish step 1, so just finish step two for other thread
                // 别的线程仅仅完成了步骤1， 所以只要帮助他完成步骤2就可以了
                if (oldTailNodeNextNode != null) {
                    tail.compareAndSet(oldTailNode, oldTailNodeNextNode);
                }
                // final state
                // 终态
                // if condition is false : this queue in a final state
                // 如果条件为假： 队列将在终态
                else {
                    // step one
                    // 步骤1
                    if (oldTailNode.next.compareAndSet(null, newTailNode)) {
                        // step two
                        // 步骤2
                        // if this step failed : this queue will in a middle state
                        // 如果步骤2失败了： 队列将会进入中间态
                        // a middle state will be turn to final state by other thread, or be took from this queue
                        // 中间状态最终会在别的线程或者被取出队列变成终态
                        tail.compareAndSet(oldTailNode, newTailNode);
                        return true;
                    }
                }
            }
        }
    }

    private static class Node<E> {

        E item;
        AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<Node<E>>(next);
        }

    }

}
