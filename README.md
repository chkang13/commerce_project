# 🔖 상품구매 커머스 프로젝트
##  📓 프로젝트 소개
이 프로젝트는 이커머스 서비스로 사용자가 편리하게 상품을 관리하고 주문할 수 있는 통합 서비스입니다.
장바구니 기능을 통해 원하는 상품을 등록할 수 있고, 주문과 결제 시스템을 통해 사용자는 편리하게 상품을 구매할 수 있습니다.

## 🔎 기술 스택
* Spring Boot : 3.2.4
* Java 버전 : 17
* Gradle : 8.7
* MySQL
* Redis
* JPA / Hibernate
* Spring Security
* Spring Cloud
* Docker
* Kafka
## 📋 실행 방법
데이터 저장소, 메세지 큐를 docker 컨테이너 환경에서 구성
> docker-compose up -d 
## 🗄️ERD
![image](https://github.com/chkang13/commerce_project/assets/34392347/a839ef3e-6378-4ca4-a505-ceb81a9200e2)
## 💻 아키텍쳐
![commerce drawio (1)](https://github.com/chkang13/commerce_project/assets/34392347/bac2ea41-a6f8-4768-a82b-30342c5a1fe7)
## 📄 API 명세서
https://documenter.getpostman.com/view/20766712/2sA3JT3e2t
## 📈 트러블 슈팅
* **재고 수량 동시성 문제 발생**
  
  * **문제** : 동시성 테스트 과정에서 재고 감소가 정상적으로 반영되지 않는 문제 발생
    
  * **문제 원인** : 하나의 트랜잭션이 끝나고 재고가 반영되기 전에 들어온 요청의 경우에 재고 불일치
    
  * **해결 방법** : Redisson의 분산락 적용으로 재고 일관성 보장
* **주문과 재고 사이의 데이터 일관성 문제**
  
  * **문제** : 재고가 없을 경우 주문이 무효화 되어야하지만 서비스가 분리되어 있어서 일관성을 보장하기 어려움
    
  * **문제 원인** : 동기 방식의 경우 서비스에 부하 및 속도 저하 우려
    
  * **해결 방법** : 메세지큐를 이용하여 비동기 방식으로 데이터 일관성 유지 및 성능 개선
