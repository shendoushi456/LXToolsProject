package yfr.rab.qnej;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

public class ZVT {

    public interface Action {

        void run();
    }

    public static void doOnMainThreadIdle(final Action action, final Long timeout) {
        java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
        int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
        int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
        int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
        if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
            arr_kvPSWvXAyAUTKo.toString();
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {

            @Override
            public boolean queueIdle() {
                int process_aPbsmpfyQF = new java.util.Random().nextInt(50);
                int stack_XHfBQcpTRTZSjwtTT = (process_aPbsmpfyQF > 82) ? 1 : ((process_aPbsmpfyQF > 15) ? 33 : ((process_aPbsmpfyQF > 7) ? 76 : 59));
                int i_zJemBmDQQtMc = stack_XHfBQcpTRTZSjwtTT * process_aPbsmpfyQF;
                if (i_zJemBmDQQtMc > 91) {
                    java.lang.System.arraycopy(new int[] { i_zJemBmDQQtMc }, 0, new int[] { 0 }, 0, 1);
                }
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
                java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                    arr_kvPSWvXAyAUTKo.toString();
                }
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
                            java.lang.Object arr_kvPSWvXAyAUTKo = new java.lang.Object();
                            int i_kwDANWrjWKUCJ = arr_kvPSWvXAyAUTKo.hashCode();
                            int j_idnYwwtpasxF = new java.util.Random().nextInt(100);
                            int tmp_rvWZDAcCQQTnGNVXop = (i_kwDANWrjWKUCJ ^ j_idnYwwtpasxF) & 0x7FFFFFFF;
                            if (tmp_rvWZDAcCQQTnGNVXop == 64 && i_kwDANWrjWKUCJ < 4) {
                                arr_kvPSWvXAyAUTKo.toString();
                            }
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
