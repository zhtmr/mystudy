// stateful 방식의 이점 활용 - 계산기 서버 만들기
package com.eomcs.net.ex04.stateful2;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServer2 {
  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");

    ServerSocket ss = new ServerSocket(8888);

    while (true) {
      Socket socket = ss.accept();
      Worker 클라이언트요청을독립적으로실행 = new Worker(socket);
      클라이언트요청을독립적으로실행.start();
      System.out.println("현재의 실행에서 분리하여 run() 메소드는 따로 실행");
    }
    // ss.close();
  }

  static class Worker extends Thread {
    Socket socket;

    public Worker(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try (Socket socket2 = socket;
          DataInputStream in = new DataInputStream(socket.getInputStream());
          PrintStream out = new PrintStream(socket.getOutputStream());) {

        // 작업 결과를 유지할 변수
        int result = 0;

        loop: while (true) {
          String op = in.readUTF();
          int a = in.readInt();

          switch (op) {
            case "+":
              result += a;
              break;
            case "-":
              result -= a;
              break;
            case "*":
              result *= a;
              break;
            case "/":
              result /= a;
              break;
            case "quit":
              break loop;
            default:
              out.println("해당 연산을 지원하지 않습니다.");
              continue;
          }

          out.printf("계산 결과: %d\n", result);
        }
        out.println("Goodbye!");
      } catch (Exception e) {
        System.out.println("클라이언트 요청 처리 중 오류 발생!");
      }
    }
  }
}


