
https://github.com/scarfunk/nest-school

프로젝트를 코틀린 버전으로 개발하는 토이프로젝트

# 준비

1. docker, gradle 설치
2. gradle build 로 프로젝트 빌드
3. docker-compose up -d 로 mysql 실행
4. docker compose -f docker-compose-kafka.yml up -d 로 카프카 설치
5. http://localhost:8080/ui/clusters/local/all-topics 에서 school-feed 이라는 토픽 생성

# 실행

1. gradle bootRun 으로 실행

2. boot run 이후 스웨거 주소. http://localhost:8081/swagger-ui/index.html#


## TODO

- [x] 코틀린으로 전환
- [x] JPA로 변경
- [x] 테스트 코드 작성
- [x] 카프카 큐 도입
- [x] 계층형 아키텍쳐 탈피 
