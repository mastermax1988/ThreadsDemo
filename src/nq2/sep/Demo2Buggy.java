package nq2.sep;

public class Demo2Buggy {

  private int counter;

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
