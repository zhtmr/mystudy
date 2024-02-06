package com.eomcs.reflect.ex06.a;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Exam0121 {
  public static void main(String[] args) {
    
//    InvocationHandler handler = new InvocationHandler() {
//      @Override
//      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println(method.getName());
//        return null;
//      }
//    };
    
    Object obj = Proxy.newProxyInstance(Exam0121.class.getClassLoader(),
        new Class<?>[] {A.class, B.class, C.class},
        (proxy, method, params) -> { System.out.println(method.getName()); return null; });
    
    ((A) obj).m1();
    ((B) obj).m2();
    ((C) obj).m3();
    
  }
}
