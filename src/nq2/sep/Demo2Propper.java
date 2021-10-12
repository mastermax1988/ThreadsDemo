package nq2.sep;

public class Demo2Propper {

  private volatile int counter;

  public void demo() {
    for (int i = 0; i < 10; i++) {
      int anz = 10000;
      counter = 0;
      Thread t1 = new Thread(() -> inc(anz));
      Thread t2 = new Thread(() -> dec(anz));
      t1.start();
      t2.start();
      try {
        t1.join();
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(counter); //ausgabe immer 0, da synchronized
    }
  }

  private synchronized void inc(int anz) {
    for (int i = 0; i < anz; i++) {
      counter = counter + 1;
    }
  }

  private synchronized void dec(int anz) {
    for (int i = 0; i < anz; i++) {
      counter = counter - 1;
    }
  }
}
