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
>> RestAPI 구현 
>> Category, Product 조회시 DB를 통한 데이터가 조회가 아닌 CategoryCache, ProductCache에서 조회
  
+ UseCase 2. Data Loading and Reloading
  * InitializingBean을 통해서 초기 기동시 DB로부터 CategoryCache, ProductCache에 각각 적재
  * 데이터 조회 API가 호출시 Cache Miss가 발생하였을 경우 DB로부터 해당 데이터를 Cache에 다시 적재
  
+ UseCase 3. Cache Eviction Policy
  * CategoryCacheSize : 10, ProductCacheSize : 1000 사이즈 제한 (초기 DB에 저장되어있는 개수만큼)
  * Cache Miss가 발생하였을 경우 Cache Eviction 실행
  * 가장 최근에 사용되지 않은 데이터를 우선적으로 캐시에서 배제하기 위한 정책 설정 (LRU 알고리즘)
  
+ UseCase 4. Cache Optimize
  * Cache에 적재되어 있는 데이터를 갱신하기 위해서 Scheduling 구현
  * 매 10분마다 Cache의 Key(CategoryNo, ProductNo)값을 기준으로 DB로부터 해당 데이터를 다시 적재
  
+ UseCase 5. DB 데이터 ADD/DELETE/UPDATE
  * RestAPI 구현
  * 유효성 검사 구현

## RestAPI
|Method|Category|Product|설명
|------|---|---|---|
|GET|http://localhost:8080/api/category/list||조회|
|GET|http://localhost:8080/api/category/{categoryNo}|http://localhost:8080/api/product/{productNo}|조회|
|POST|http://localhost:8080/api/category|http://localhost:8080/api/product|등록|
|PUT|http://localhost:8080/api/category/{categoryNo}|http://localhost:8080/api/product/{productNo}|수정|
|DELETE|http://localhost:8080/api/category/{categoryNo}|http://localhost:8080/api/product/{productNo}|삭제|

## RestAPI 요청, 응답 샘플링
+ Category 등록  
curl -d '{"categoryName":"등록테스트", "parentNo":"1", "depth": "2"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/category
```
{
  "result": "true"
}
```
+ Category 수정  
curl -d "categoryName=수정테스트&parentNo=1&depth=2" -H "Content-Type: application/x-www-form-urlencoded" -X PUT http://localhost:8080/api/category/9
```
{
  "result": "true"
}
```
+ Category 삭제  
curl -H "Content-Type: application/x-www-form-urlencoded" -X DELETE http://localhost:8080/api/category/2
```
{
  "result": "true"
}
```

