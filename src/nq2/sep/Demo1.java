package nq2.sep;

/** Demo, wie grundsätzlich ein Thread gestartet wird.*/
public class Demo1 {

  public void demo(){
    Thread t1 = new Thread(this::workload);
    Thread t2 = new Thread(() -> workloadWithParam("Thread mit Parameter"));
    t1.start();//t1 läuft jetzt parallel
    t2.start();//t2 ab jetzt auch
    System.out.println("Threads gestartet");
    try {
      t1.join(); //warte, bis t1 beendet wurde
      t2.join(); //t2 muss ebenfalls beendet sein. es ist egal, ob dieser bereits zuvor fertig war
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Threads beendet");
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

  private void workloadWithParam(String s){
       try {
      Thread.sleep(250);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("[THREAD] Thread 2: " + s);
  }

}
