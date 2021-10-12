package nq2.sep;

public class Demo1 {

  public void demo(){
    Thread t1 = new Thread(this::workload);
    t1.start();
    System.out.println("Thread 1 gestartet");
    try {
      t1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Thread 1 beendet.");
  }

  /** Das läuft jetzt parallel zum Hauptthread*/
  private void workload() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("[THREAD] Thread 1 work done");
  }

}
