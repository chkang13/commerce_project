# 예약구매 프로젝트
##  프로젝트 소개

### 주요 기능
1. 기능1 : 
2. 기능2 : 
## 개발 환경
* Spring Boot :  3.2.4
* Java 버전 : 17
* Gradle : 8.7
## 실행 방법
데이터 저장소, 메세지 큐 docker 컨테이너 환경에서 구성
> docker-compose up -d 
## 기술 스택
* Spring Boot
* Spring Security
* Spring Cloud
* JPA / Hibernate
* MySQL
* Redis
* Docker
* Kafka
## ERD
![image](https://github.com/chkang13/commerce_project/assets/34392347/a839ef3e-6378-4ca4-a505-ceb81a9200e2)
## API 명세서
https://documenter.getpostman.com/view/20766712/2sA3JT3e2t
## 아키텍쳐
![commerce drawio](https://github.com/chkang13/commerce_project/assets/34392347/5b11c266-6833-42d0-922c-6251402a1934)
## 트러블 슈팅
* **분산락을 사용한 재고 동시성 이슈 해결**
  * **문제**
    동시성 테스트 과정에서 재고 감소가 정상적으로 반영되지 않음
  * **문제 원인**
    하나의 트랜잭션이 끝나서 재고에 반영되기 전에 동시에 요청이 들어올 경우에 발생
  * **해결 방법**
    Redisson의 분산락 적용
* **메세지큐를 이용한 데이터 일관성 보장**
