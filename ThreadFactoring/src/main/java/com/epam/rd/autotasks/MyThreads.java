package com.epam.rd.autotasks;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreads implements ThreadUnion {
    private static final String THREAD_NAME = "-worker-";
    private final String groupName;

    private final AtomicInteger createdCount;
    private final AtomicBoolean isShutdown;

    private final ConcurrentLinkedQueue<Thread> activeThreads;
    private final Map<Thread, FinishedThreadResult> finishedThreads;

    public MyThreads(String name) {
        groupName = name;
        createdCount = new AtomicInteger(0);
        isShutdown = new AtomicBoolean(false);
        finishedThreads = new ConcurrentHashMap<>();
        activeThreads = new ConcurrentLinkedQueue<>();
    }

    @Override
    public int totalSize() {
        return createdCount.get();
    }

    @Override
    public int activeSize() {
        int activeCount = 0;

        for (Thread thread : activeThreads) {
            if (thread.isAlive()) {
                activeCount++;
            }
        }

        return activeCount;
    }

    @Override
    public void shutdown() {
        for (Thread thread : activeThreads) {
            thread.interrupt();
        }

        while (true) {
            if (activeSize() == 0) {
                break;
            }
        }

        isShutdown.getAndSet(true);
    }

    @Override
    public boolean isShutdown() {
        return isShutdown.get();
    }

    @Override
    public synchronized void awaitTermination() {
        shutdown();
        while (true) {
            if (isShutdown() && createdCount.get() == finishedThreads.size()) {
                break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return activeSize() == 0 && isShutdown();
    }

    @Override
    public List<FinishedThreadResult> results() {
        return new ArrayList<>(finishedThreads.values());
    }

    @Override
    public synchronized Thread newThread(Runnable runnable) {
        Thread newThread;

        if (isShutdown.get()) {
            throw new IllegalStateException();
        } else {
            newThread = new Thread(runnable);
            newThread.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> {
                activeThreads.remove(thread);
                finishedThreads.put(thread, new FinishedThreadResult(thread.getName(), throwable));
            });

            newThread.setName(groupName + THREAD_NAME + createdCount.get());
            activeThreads.add(newThread);
            addFinishedThreads();

            createdCount.getAndAdd(1);
            return newThread;
        }
    }

    private void addFinishedThreads() {
        new Thread(() -> {
            while (!activeThreads.isEmpty()) {
                for (Thread thread : activeThreads) {
                    if (thread.getState() == Thread.State.TERMINATED || thread.isInterrupted()) {
                        activeThreads.remove(thread);
                        finishedThreads.put(thread, new FinishedThreadResult(thread.getName()));
                    }
                }
            }
        }).start();
    }
}
