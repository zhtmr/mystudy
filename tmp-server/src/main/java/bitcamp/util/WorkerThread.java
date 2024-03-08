//package bitcamp.util;
//
//public class WorkerThread extends Thread {
//  Pooling<WorkerThread> pool;
//  Worker worker;
//
//  public WorkerThread(Pooling<WorkerThread> Pool) {
//    this.pool = Pool;
//  }
//
//  synchronized public void setWorker(Worker worker) {
//    this.worker = worker;
//    this.notify();
//  }
//
//  @Override
//  public void run() {
//    try {
//      while (true) {
//        synchronized (this) {
//          this.wait();
//        }
//        try {
//          worker.play();
//        } catch (Exception e) {
//          System.out.println("클라이언트 요청 처리 중 오류 발생!");
//          e.printStackTrace();
//        }
//        pool.revert(this);
//      }
//    } catch (Exception e) {
//      System.out.println("스레드 예외 발생!");
//      e.printStackTrace();
//    }
//  }
//}
