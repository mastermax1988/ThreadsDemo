package nq2.sep;

/** Wenn 2 Threads auf die gleiche Variable zugreifen, kann es zu Problemen kommen.*/
public class Demo2Buggy {

  private int counter; //beide threads greifen zeitgleich auf diese variable zu

  public void demo() {
    for (int i = 0; i < 10; i++) {
      int anz = 10000;
      counter = 0;
      Thread t1 = new Thread(() -> inc(anz)); //t1 erhöht counter
      Thread t2 = new Thread(() -> dec(anz)); //t2 verringert counter
      t1.start();
      t2.start();
      try {
        t1.join();
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(
          counter); // die ausgabe sollte immer 0 sein, ist sie aber nicht, weil die threads sich in
                    // die quere kommen
    }
  }

  private void inc(int anz) {
    for (int i = 0; i < anz; i++) {
      counter = counter + 1;
    }
  }

  private void dec(int anz) {
    for (int i = 0; i < anz; i++) {
      counter = counter - 1;
    }
  }
}
