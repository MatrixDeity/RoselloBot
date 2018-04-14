package matrixdeity.rosello.util;

public class Timer {

    private long interval;
    private Thread thread;
    private Runnable lambda;

    public Timer(long interval, Runnable lambda) {
        this.interval = interval;
        this.lambda = lambda;
        thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(interval);
                    lambda.run();
                }
            } catch (InterruptedException e) {
            }
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

}
