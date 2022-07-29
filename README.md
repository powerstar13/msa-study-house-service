# 🏠 부동산 중개플랫폼

`스테이션3 과제`

---

# ℹ️ 실행 방법

`out 폴더 경로의 각 jar 실행 명령을 입력 바랍니다.`

### java가 설치되어 있을 경우

1. API Gateway 실행
    ```
    java -jar ./out/stattion3-assignment-gateway.jar
    ```
2. 회원 서비스 실행
    ```
    java -jar ./out/stattion3-assignment-member.jar
    ```
3. 방 서비스 실행
    ```
    java -jar ./out/stattion3-assignment-house.jar
    ```

### intelliJ를 사용할 경우
1. `./out/` 폴더 내의 jar 파일들을 우클릭 후 Run하여 모두 실행
   1. API Gateway port: 8080
   2. 회원 서비스 port: 8081
   3. 방 서비스 port: 8082


## [API 문서](http://localhost:8080/docs/index.html)

`jar 파일들을 모두 실행시킨 후 API 문서를 열어주시기 바랍니다.`

## [API Test](./http-test/api.http)

`API를 실행할 수 있는 http test 파일입니다.`

---

# 💡 마이크로서비스 모델링

![마이크로서비스 모델링](./public/station3-assignment-domain-model.png)

# 💠 ERD 설계

![ERD 설계](./public/station3-assignment-erd.png)

# 🏭 시스템 아키텍처

![시스템 아키텍처](./public/station3-assignment-system-architecture.png)

---
