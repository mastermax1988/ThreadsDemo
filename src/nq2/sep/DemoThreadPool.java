package nq2.sep;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DemoThreadPool {

  private volatile boolean running;
  public void demo() {
    int anz = 10;
    running = true;
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(anz);
    for (int i = 0; i < anz; i++) {
      int finalI = i;
      executor.execute(() -> spam(finalI));
    }

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    running = false;
    executor.shutdownNow();//alle Threads beenden
  }

  private void spam(int i) {
    while (running) {
      System.out.println(i);
    }
  }
}
