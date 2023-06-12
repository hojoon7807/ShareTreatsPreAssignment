## 쉐어트리츠 사전과제

-----------------------

### 개발 환경

------------

Java 17 

### 사전과제 

### 상품교환 서비스 

------------

**동작 설명**

초기화면에서 exchangegoods 명령어를 통해 상품교환 서비스를 실행 할 수 있습니다.

이후 help(서비스 이용방법 안내), check(상품코드의 유효 판단), claim(상품교환) 명령어로 작동합니다.

Goods는 상품코드와 상품교환 상태를 가지고 있는 클래스입니다.

Store는 교환한 상품코드 목록을 가지고 있는 클래스입니다.

서비스에 들어오는 입력은 `ExchangeGoodsInputValidator`를 통해 초기 입력에 문제가 없는지 판단합니다.

입력에 문제가 없다면 명령어에 따라서 해당 서비스가 작동되고 명령어에 따라 작동될 서비스는 커맨드 패턴을 사용해서 처리했습니다.

`ExchangeGoodsCommandManager`는 실행될 커맨드들을 관리하는 클래스고 해당 클래스를 통해 커맨드가 실행되어 결과값을 반환합니다.

**테스트 케이스**

명령어 입력이 비어있는 경우 InvalidInputException 발생 아무런 입력이 없습니다. 원하는 요청을 입력해주세요 메세지 출력

제공되어있지 않는 코드로 check 할 경우 InvalidGoodsCodeException 예외 발생

제공되어있지 않는 코드로 claim 할 경우 InvalidGoodsCodeException 예외 발생

이미 교환된 상품 코드로 check 할 경우 교환되었다는 메세지 출력

이미 교환된 상품 코드로 claim 할 경우 교환되었다는 메세지 출력
