// 프로퍼티 호출 - 의존 객체 주입할 때 즉시 객체 생성하기
package com.eomcs.spring.ioc.ex04.e;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eomcs.spring.ioc.ex04.Car;
import com.eomcs.spring.ioc.ex04.Engine;

public class Exam01 {

  public static void main(String[] args) {
    ApplicationContext iocContainer = new ClassPathXmlApplicationContext(//
        "com/eomcs/spring/ioc/ex04/e/application-context.xml");

    Car c1 = (Car) iocContainer.getBean("c1");
    Car c2 = (Car) iocContainer.getBean("c2");

    System.out.println(c1.getEngine() == c2.getEngine());
    
    // 프로퍼티 주입 중에 만드는 객체는 컨테이너에 따로 보관하지 않는다.getBean() 불가
    // 따라서 그 객체를 선언할때는 id 를 지정할 필요가 없다.
    Engine e1 = (Engine) iocContainer.getBean("e1");
  }

}


