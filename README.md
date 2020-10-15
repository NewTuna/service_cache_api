# 인메모리캐시(in-memory cache) 구현

## 요구사항 구현
+ 카테고리, 상품 조회,등록,수정,삭제 RestAPI를 구현
+ 초기 기동시 데이터를 인메모리 캐시에 Loading
+ Cache Eviction 처리 (LRU 알고리즘)
+ Cache Optimize (Scheduling으로 Cache Miss 최소화) 

## 개발환경
+ Java8
+ Spring Boot 2.3.4.RELEASE
+ Mybatis
+ H2

## 실행
```
mvn clean install -DskipTests
java -jar ./target/service_cache_api-0.0.1-SNAPSHOT.jar
```

## 개발 노트
+ UseCase 1. Query
  * RestAPI 구현 
  * Category, Product 조회시 DataBase를 통한 데이터가 조회가 아닌 CategoryCache, ProductCache에서 조회
  
+ UseCase 2. Data Loading and Reloading
  * InitializingBean을 통해서 초기 기동시 DataBase로부터 CategoryCache, ProductCache에 각각 적재
  * 데이터 조회 API가 호출되었을 때 Cache Miss가 발생하였을 경우 DataBase로부터 해당 데이터를 Cache에 재적재 
    (LRU 알고리즘)
