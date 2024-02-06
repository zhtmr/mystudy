// stateless 방식에서 클라이언트를 구분하고 작업 결과를 유지하는 방법
package com.eomcs.net.ex04.stateless2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CalcServer3 {
  
  // 특정 코드 실행을 main() 메소드 호출과 분리해서 별도로 실행시키기
  static class Worker extends Thread {

    Socket socket;
    
    public Worker(Socket socket) {
      this.socket = socket;
    }
    
    @Override
    public void run() {
      try (Socket socket2 = socket;
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

        long clientId = in.readLong();

        // 연산자와 값을 입력 받는다.
        String op = in.readUTF();
        int value = in.readInt();

        // 클라이언트를 위한 기존 값 꺼내기
        Integer obj = resultMap.get(clientId);
        int result = 0;

        if (obj != null) {
          System.out.printf("%d 기존 고객 요청 처리!\n", clientId);
          result = obj; // auto-unboxing
        } else {
          clientId = System.currentTimeMillis();
          System.out.printf("%d 신규 고객 요청 처리!\n", clientId);
        }

        String message = null;
        switch (op) {
          case "+":
            result += value;
            break;
          case "-":
            result -= value;
            break;
          case "*":
            result *= value;
            break;
          case "/":
            Thread.sleep(30000);
            result /= value;
            break;
          default:
            message = "해당 연산을 지원하지 않습니다.";
        }

        // 계산 결과를 resultMap에 보관한다.
        resultMap.put(clientId, result);

        // 클라이언트로 응답할 때 항상 클라이언트 아이디와 결과를 출력한다.
        // => 클라이언트 아이디 출력
        out.writeLong(clientId);

        // => 계산 결과 출력
        if (message == null) {
          message = String.format("계산 결과: %d", result);
        }
        out.writeUTF(message);
        out.flush();

      } catch (Exception e) {
        System.out.println("클라이언트 요청 처리 중 오류 발생!");
      }
    }
    
  }

  // 각 클라이언트의 작업 결과를 보관할 맵 객체
  // => Map<clientID, result>
  static Map<Long, Integer> resultMap = new HashMap<>();

  public static void main(String[] args) throws Exception {
    System.out.println("서버 실행 중...");

    ServerSocket ss = new ServerSocket(8888);

    while (true) {
      Socket socket = ss.accept();
      System.out.println("클라이언트 요청 처리!");
      
      Worker 클라이언트요청처리 = new Worker(socket);
      클라이언트요청처리.start(); 
      // main() 메소드 실행과 별도로 run() 메소드는 따로 실행시키고 즉시 리턴한다.    
    }
    // ss.close();
  }

}


