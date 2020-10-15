# 인메모리캐시(in-memory cache) 구현

## 요구사항 구현
  + 카테고리, 상품 조회,등록,수정,삭제 RestAPI를 구현
  + 초기 기동시 데이터를 인메모리 캐시에 Loading
  + Cache Eviction 처리 (LRU 알고리즘)
  + Cache Optimize (Scheduling으로 Cache Miss 최소화) 