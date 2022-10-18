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
    │   │     ├── com.example.springtx
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
- 테스트코드를 통한 예외와 트랜잭션 커밋, 롤백처리 :  OrderServiceTest.java
- 테스트코드를 통한 스프링 트랜잭션의 전파 : BasicTxTest
  - 커밋 : commitTest() 
  - 롤백 : rollbackTest()
  - 트랜잭션 두 번 커밋-커밋 : doubleCommitTest()
  - 트랜잭션 두 번 커밋-롤백 : doubleCommitRollbackTest()
  - 트랜잭션 전파 - 외부 트랜젝션(내부 트랜잭션 커밋) 커밋 : innerCommitTest()
  - 트랜잭션 전파 - 외부 트랜젝션(내부 트랜잭션 커밋) 롤백 : outerRollbackTest()