# Spring - DB Transaction Study project

## Project Spec
- 프로젝트 선택
    - Project: Gradle Project
    - Spring Boot: 2.7.4
    - Language: Java
    - Packaging: Jar
    - Java: 11
- Project Metadata
    - Group: com.example
    - Artifact: springtx
    - Name: spring-db-transaction
    - Package name: com.example.springtx
- Dependencies: **Spring Web**, **Thymeleaf**, **Lombok**, **Spring Data JPA**, **H2 Database**
- DB : H2 database

## Package Design
```
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.springtx
    │   │               ├── order
    │   │               │     ├── domain
    │   │               │     │      └── Order(C)
    │   │               │     ├── enums
    │   │               │     │      ├── OrderPayStatusType(E)
    │   │               │     │      └── OrderUsernameType(E)
    │   │               │     ├── exception
    │   │               │     │      └── NotEnoughMoneyException(C)
    │   │               │     ├── repository
    │   │               │     │      └── OrderRepository(C)
    │   │               │     └── service
    │   │               │            └── OrderService(C) 
    │   │               └── SpringDbTransactionApplication(C)
    │   └── resource
    │       ├── static
    │       │     └── index.html
    │       └── application.properties
    ├── test
    │   ├── java
    │   │     └── com.example.springtx
    │   │                        ├── apply
    │   │                        │     ├── InitTxTest(C)
    │   │                        │     ├── InternalCallV1Test(C)
    │   │                        │     ├── InternalCallV2Test(C)
    │   │                        │     ├── TransactionApplyBasicTest(C)
    │   │                        │     └── TransactionLevelTest(C)
    │   │                        ├── exception
    │   │                        │     └── RollbackTest(C)
    │   │                        ├── order
    │   │                        │     └── OrderServiceTest(C)
    │   │                        ├── rollback
    │   │                        │     └── MyExceptionRollbackTest(C)
    │   │                        └── SpringDbTransactionApplicationTests(C)
```


## Content
- 테스트코드를 통한 트랜잭션 적용 확인 : TransactionApplyBasicTest.java
- 테스트코드를 통한 트랜젝션 적용위치 확인 : TransactionLevelTest.java
- 테스트코드를 통한 트랜젝션 프록시 내부 호출 확인 : InternalCallV1Test.java, InternalCallV2Test.java
- 테스트코드를 통한 트랜젝션 초기화 시점 적용확인 : InitTxTest.java

- 테스트코드를 통한 스프링 트랜잭션의 전파 : BasicTxTest.java
  - case 1 : 커밋 : commitTest() 
  - case 2 : 롤백 : rollbackTest()
  - case 3 : 트랜잭션 두 번 커밋-커밋 : doubleCommitTest()
  - case 4 : 트랜잭션 두 번 커밋-롤백 : doubleCommitRollbackTest()
  - case 5 : 트랜잭션 전파 - 외부 트랜젝션(내부 트랜잭션 커밋) 커밋 : innerCommitTest()
  - case 6 : 트랜잭션 전파 - 외부 트랜젝션(내부 트랜잭션 커밋) 롤백 : outerRollbackTest()
  - case 7 : 트랜잭션 전파 - 외부 트랜젝션(내부 트랜잭션 롤백) 커밋 : innerRollbackTest()

- 예제 1 : 예외와 트랜잭션 커밋, 롤백처리
  - OrderServiceTest.java
    - 정상승인에 대한 테스트
    - Runtime(unchecked) Exception에 대한 테스트
    - 비지니스(checked) Exception에 대한 테스트
- 예제 2 : 스프링 트랜잭션의 전파
  - MemberServiceTest.java
    - 외부트랜젝션이 없이 성공에 대한 케이스
    - 