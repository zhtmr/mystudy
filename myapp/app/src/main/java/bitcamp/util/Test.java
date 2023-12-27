package bitcamp.util;

public class Test {

  public static void main(String[] args) {
    LinkedList list = new LinkedList();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    list.add("ddd");

    // 맨 앞에 추가하기
//    list.add(0, "xxx");
//
//    // 맨 뒤에 추가하기
//    list.add(5, "yyy");
//
//    // 기존 값 자리에 추가하기
//    list.add(1, "mmm");
//    list.add(3, "ttt");
//    list.add(7, "ppp"); // xxx, mmm, aaa, ttt, bbb, ccc, ddd, ppp, yyy,

//    list.remove(0);
//    list.remove(0);
    list.remove(3);
    list.remove(2);
    list.remove(1);
    list.remove(0);

    Object[] array = list.toArray();
    for (Object val : array) {
      System.out.printf("%s, ", val);
    }
    System.out.println();

//    System.out.println(list.get(0));
//    System.out.println(list.get(1));
//    System.out.println(list.get(2));
//    System.out.println(list.get(3));
//    System.out.println(list.get(4));
  }

}
