package nq2.sep;

/** Jetzt erfolgt der Zugriff auf counter threadsave. */
public class Demo2Propper {

  private volatile int
      counter; // volatile signalisiert, dass die variable von mehreren threads geändert wird. Jede
  // Änderung wird direkt an den Hauptspeicher weitergegeben und der CPU Cache wird
  // nicht benutzt

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
      System.out.println(counter); // ausgabe immer 0, da synchronized
    }
  }

  /**
   * nur ein synchronized codeblock darf zeitgleich ausgeführt werden. also wird entweder inc oder
   * dec ausgeführt
   */
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
