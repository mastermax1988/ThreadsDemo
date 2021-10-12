package nq2.sep;

import java.util.Random;

public class Demo3WaitNotify {

  private volatile boolean run;
  private volatile int apples;
  private volatile int appleJuices;
  final ThreadLocal<Random> rnd = ThreadLocal.withInitial(() -> new Random());

  public void demo() {
    run = true;
    apples = 0;
    appleJuices = 0;
    Thread picker1 = new Thread(() -> pickerWork(1));
    Thread picker2 = new Thread(() -> pickerWork(2));
    Thread juice = new Thread(this::juiceFactoryWork);
    picker1.start();
    picker2.start();
    juice.start();
    try {
      juice.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void pickerWork(int nr) {
    while (run) {
      try {
        Thread.sleep(rnd.get().nextInt(1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (this) { // nur ein Thread darf auf die Variable apples zugreifen
        apples+=rnd.get().nextInt(1,4);
        System.out.println("[" + nr + "] Apples: " + apples);
        notifyAll(); // alle schlafenden threads wecken
      }
    }
  }

  private synchronized void
      juiceFactoryWork() { // die ganze methode ist synchronized; wenn sie sich mit wait schlafen
                           // legt, dann können die anderen threads arbeiten
    while (run) {
      while (apples < 10) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      apples -= 10;
      appleJuices++;
      System.out.println("Juices: " + appleJuices + ", " + apples + " apples left.");
      if (appleJuices > 5) {
        run = false;
      }
    }
  }
}
