// sychronized method
package com.eomcs.concurrent.ex5;

public class Exam0530 {
  public static void main(String[] args) {
    Job job1 = new Job();
    Job job2 = new Job();
    
    // 서로 다른 인스턴스를 사용한다.
    Worker w1 = new Worker("홍길동", job1);
    Worker w2 = new Worker("임꺽정", job2);
    
    w1.start();
    w2.start();
  }
  
  static class Job {
    // 서로 다른 인스턴스는 어차피 critical section 이 없다. 
    synchronized void play(String threadName) throws Exception {
      System.out.println(threadName);
      Thread.sleep(10000);
    }
  }
  
  static class Worker extends Thread {
    Job job;
    
    public Worker(String name, Job job) {
      super(name);
      this.job = job;
    }
    
    @Override
    public void run() {
      try {
        job.play(getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    

  }

}
