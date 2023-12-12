package bitcamp.myapp.repository;

import bitcamp.myapp.vo.Member;

public class MemberRepository {

  private Member[] members = new Member[3];
  private int length = 0;

  public void add(Member member) {
    if (this.length == this.members.length) {
      int oldSize = this.members.length;
      int newSize = oldSize + (oldSize >> 1);

      Member[] arr = new Member[newSize];
      for (int i = 0; i < this.length; i++) {
        arr[i] = this.members[i];
      }
      this.members = arr;
    }
    this.members[this.length++] = member;
  }

  public Member get(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    return this.members[index];
  }

  public Member[] toArray() {
    Member[] arr = new Member[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.members[i];
    }
    return arr;
  }

  public Member remove(int index) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    Member old = this.members[index];
    for (int i = index; i < this.length - 1; i++) {
      this.members[i] = this.members[i + 1];
    }

    this.members[--this.length] = null;
    return old;
  }

  public Member set(int index, Member member) {
    if (index < 0 || index >= this.length) {
      return null;
    }
    Member old = this.members[index];
    members[index] = member;
    return old;
  }
}
