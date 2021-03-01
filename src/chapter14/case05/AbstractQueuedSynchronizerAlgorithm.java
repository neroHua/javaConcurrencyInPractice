package chapter14.case05;

public class AbstractQueuedSynchronizerAlgorithm {

    /**
     * get lock
     * 获取锁
     *
     * @return
     * @throws InterruptedException
     */
    boolean acquire() throws InterruptedException {
        // self-spin
        // 自旋
        // polled
        // 轮询
        // blocked
        // 阻塞自己
        while (stateDoesNotPermitAcquire()) {
            if (blockingAcquisitionRequested()) {
                enqueueCurrentThreadIfNotAlreadyQueued();
                blockCurrentThread();
            }
            else {
                return false;
            }
        }

        // get lock
        possiblyUpdateSynchronizationState();

        // unblocked
        dequeueThreadIfItWasQueued();

        return true;
    }

    /**
     * release lock
     * 释放锁
     */
    void release() {
        updateSynchronizationState();
        if (newStateMayPermitABlockedThreadToAcquire()) {
            unblockOneOrMoreQueuedThreads();
        }
    }

    /**
     * 通知其他线程获取锁
     */
    private void unblockOneOrMoreQueuedThreads() {
    }

    /**
     * 新的状态是否允许阻塞状态下的线程获取锁
     *
     * @return
     */
    private boolean newStateMayPermitABlockedThreadToAcquire() {
        return false;
    }

    /**
     * 同步修改状态
     */
    private void updateSynchronizationState() {
    }

    /**
     * 出队，如果线程在队列中
     */
    private void dequeueThreadIfItWasQueued() {
    }

    /**
     * 很有可能同步修改状态
     */
    private void possiblyUpdateSynchronizationState() {
    }

    /**
     * 阻塞当前线程
     */
    private void blockCurrentThread() {
    }

    /**
     * 入队，当前线程，如果没有入队
     */
    private void enqueueCurrentThreadIfNotAlreadyQueued() {
    }

    /**
     * 是否需要被阻塞当获取锁时
     *
     * @return
     */
    private boolean blockingAcquisitionRequested() {
        return false;
    }

    /**
     * 状态是否允许获取锁
     *
     * @return
     */
    private boolean stateDoesNotPermitAcquire() {
        return false;
    }

}
