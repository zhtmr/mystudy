package bitcamp.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool implements Pooling<WorkerThread> {
  List<WorkerThread> list = new ArrayList<>();

  public ThreadPool(int initSize) {
    if (initSize <= 0 || initSize > 100) {
      return;
    }
    for (int i = 0; i < initSize; i++) {
      list.add(create());
    }
  }

  @Override
  public WorkerThread get() {
    if (!list.isEmpty()) {
      WorkerThread t = list.removeFirst();
      System.out.printf("기존 스레드를 꺼냄!(%s)\n", t.getName());
      return t;
    }
    return create();
  }

  private WorkerThread create() {
    WorkerThread thread = new WorkerThread(this);
    thread.start();  // wait() 호출
    try {
      Thread.sleep(500);
    } catch (Exception e) {
    }
    System.out.printf("새 스레드 생성!(%s)\n", thread.getName());
    return thread;
  }

  @Override
  public void revert(WorkerThread thread) {
    list.add(thread);
  }


}
