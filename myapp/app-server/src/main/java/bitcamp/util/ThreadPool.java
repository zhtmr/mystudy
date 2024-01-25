package bitcamp.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool implements Pooling<WorkerThread> {
  List<WorkerThread> list = new ArrayList<>();

  @Override
  public WorkerThread get() {
    if (!list.isEmpty()) {
      return list.removeFirst();
    }
    WorkerThread thread = new WorkerThread(this);
    thread.start();
    return thread;
  }

  @Override
  public void revert(WorkerThread thread) {
    list.add(thread);
  }
}
