package nq2.sep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo4ThreadPool {

  private volatile boolean running;

  public void demo() {
    int anz = 10;
    running = true;
    // executors ist ein threadpool, der maximal 10 threads zeitgleich laufen lässt. beim servercode
    // ist dies z.b. eine elegante möglichkeit, die eingehenden verbindungen zu limitieren.
    ExecutorService executor =  Executors.newFixedThreadPool(anz);
    for (int i = 0; i < anz; i++) {
      // workaround, da ich die zählvariable nicht übergeben darf; hier erstmal ignorieren.
      // wenn man selbst drauf stößt, wird man von intellij auf diesen workaround
      // hingewiesen.
      int finalI = i;
      executor.submit(() -> spam(finalI));
    }

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    running = false;
    // Threadpool beenden, da ansonsten das Programm nicht ordentlich beendet wird. die einzelnen
    // threads machen zwar nichts mehr, existieren aber noch.
    executor.shutdownNow();
    System.out.println("shutdown");
  }

  private void spam(int i) {
    while (running) {
      System.out.println(i);
    }
    System.out.println("running is false");
  }
}
