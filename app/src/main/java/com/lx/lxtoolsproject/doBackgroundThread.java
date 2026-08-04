package com.lx.lxtoolsproject;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

public class doBackgroundThread {

    public interface Action {
        void run();
    }

    public static void doOnMainThreadIdle(final Action action, final Long timeout) {
        final Handler handler = new Handler(Looper.getMainLooper());

        final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                handler.removeCallbacksAndMessages(null);
                try {
                    action.run();
                } catch (Exception ignored) {
                }
                return false;
            }
        };

        // 相当于 Kotlin 的内部函数
        Runnable setupIdleHandler = new Runnable() {
            @Override
            public void run() {
                MessageQueue queue;

                if (Looper.getMainLooper() == Looper.myLooper()) {
                    queue = Looper.myQueue();
                } else {
                    queue = Looper.getMainLooper().getQueue();
                }

                if (timeout != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            queue.removeIdleHandler(idleHandler);
                            try {
                                action.run();
                            } catch (Exception ignored) {
                            }
                        }
                    }, timeout);
                }

                queue.addIdleHandler(idleHandler);
            }
        };

        setupIdleHandler.run();
    }
}