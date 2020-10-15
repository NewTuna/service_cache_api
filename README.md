# 인메모리캐시(in-memory cache) 구현

# 요구사항 구현
  1. 카테고리, 상품 조회,등록,수정,삭제 RestAPI를 구현
  2. 초기 기동시 데이터를 인메모리 캐시에 Loading
  3. Cache Eviction (LRU 알고리즘)
  4. Cache Optimize (Scheduling으로 Cache Miss 최소화) 
