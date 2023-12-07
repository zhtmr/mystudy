# mystudy

## static field vs instance field

- static field
  - 클래스 로딩 시 같이 Method Area 에 만들어짐(클래스 당 1개)
    - 공유 자원의 의미
  - 앱이 실행되고 클래스가 로딩되는 시점에 만들기 때문에 사용하지 않아도 Method Area 에 만들어진다.
    - 무분별하게 static 을 사용할 경우 메모리 낭비, 성능저하가 심해진다.
  - 인스턴스 데이터를 다루지 않는, 주로 앱 전반에 사용할 유틸리티 함수를 static 으로 만든다.

- instance field
  - 인스턴스 생성 시 Heap 영역에 만들어짐(인스턴스 마다 가지고 있는 독립적인 데이터)
    - 개별 자원의 의미
      - ex) 배럭에서 마린을 뽑으면 new Marine() 으로 각각의 마린 인스턴스가 만들어지고 그 마린이 갖는 체력 데이터도 인스턴스에 종속된 데이터다.
      
