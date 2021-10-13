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
      // nur ein thread darf auf apples zugreifen; da beide picker zeitgleich schlafen dürfen, aber
      // nur genau ein thread zeitgleich auf apples zugreifen darf, wird hier nur der zugriff
      // synchronized markiert.
      synchronized (this) {
        apples += rnd.get().nextInt(1, 4);
        System.out.println("[" + nr + "] Apples: " + apples);
        notifyAll(); // alle schlafenden threads wecken
        // notify(); würde genau einen zufällig ausgewählten schlafenden thread wecken; ginge hier
        // auch
      }
    }
  }

  /** die ganze methode ist synchronized */
  private synchronized void juiceFactoryWork() {
    while (run) {
      while (apples < 10) {
        try {
          wait(); // dieser thread legt sich schlafen, bis er mit notify aufgeweckt wird und wacht
          // genau hier wieder auf. Das ist kein busy waiting, der thread wird suspended.
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      apples -= 10; //mache aus 10 äpfeln
      appleJuices++; // einen apfelsaft
      System.out.println("Juices: " + appleJuices + ", " + apples + " apples left.");
      if (appleJuices > 5) {
        run = false;
      }
    }
  }
}
