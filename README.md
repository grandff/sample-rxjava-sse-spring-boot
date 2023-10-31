# Sample Spring Boot REST API with RxJava and SSE

RxJava와 SSE 통신 기능을 적용시킨 Spring Boot REST API 샘플입니다.

비동기 통신과 더불이 기본적인 CRUD API도 추가됐습니다. swagger를 추가했습니다.

DB는 h2를 사용했습니다.

## 🔥 Features

- [X] add an author (rxjava)
    " author 추가 (post)
- [X] add a book (rxjava)
    " 책 정보 추가 (post)
    " authro와 book은 1:N 관계
- [X] update a book (rxjava)
    " 책 정보 수정 (patch)
- [X] get lis of books (rxjava)
    " 책 목록 조회 (get)
- [X] get a book's details (rxjava)
    " 책 상세 조회 (get)
- [X] delete a book (rxjava)
    " 책 삭제 (delete)
- [X] common response entity
- [ ] file upload
- [ ] file download
- [ ] jwt login
- [ ] refresh token
- [X] SSE (Server Sent Event)
- [ ] CRUD Basic API
- [X] Spring Security
- [X] swagger
- [X] unit test
    " 모든 API 요청에 대해 추가

## 🚀 Project Structure

```bash
/
├── src/
│   ├── main/
│   │   ├── java/com/kjm/sample/rxjava/rxjavarestapi/
│   │   │   ├── auth/
│   │   │   ├── author/
│   │   │   │   ├── [id]/
│   │   │   ├── book/
│   │   │   ├── common/
│   │   │   ├── config/
│   │   │   ├── file/
│   │   │   ├── memo/
│   │   │   ├── notice/
│   │   │   └── RxjavarestapiApplication.java
│   │   ├── resources/
│   │   │   ├── application-local.yml
│   │   │   └── application.yml
│   ├── test/java/com/kjm/sample/rxjava/rxjavarestapi/
│   │   ├── author/
│   └───├── book/
│       └── RxjavarestapiApplicationTests.java
└── pom.xml
```

## ⛺️ References

[Reactive REST API](https://axella-gerald.medium.com/reactive-rest-api-using-spring-boot-rxjava-4efb620c69ac)

[RxJava에 대한 이해 1](https://onlyfor-me-blog.tistory.com/326)

[스케줄러](https://4z7l.github.io/2020/12/14/rxjava-5.html)

[Spring Security 최신 버전 문법 확인](https://sennieworld.tistory.com/109)

[h2 console true인 경우 Security 적용 시 빌드 에러](https://forwe.tistory.com/m/66)

[SSE 예제 1](https://dkswnkk.tistory.com/702)

[SSE 예제 2](https://tecoble.techcourse.co.kr/post/2022-10-11-server-sent-events/)

[JPA 쿼리 차이점](https://velog.io/@jehpark/Spring-Data-JPA-%EC%BF%BC%EB%A6%AC-like-containing%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90)

## 💻 Tech Stack

**Main Framework** - [Spring Boot](https://nextjs.org/blog/next-13/)  
**Component** - [RxJava](https://reactjs.org/)  
**Test** - [jUnit](https://vercel.com/)  
**Docs** - [Swagger](https://vercel.com/)  

## 👨🏻‍💻 Running Locally

- database : h2
- docs : <http://localhost:8080/swagger-ui/index.html>

```bash

```

## 📜 License

Licensed under the MIT License, Copyright © 2023

---

Made by kjm 👨🏻‍💻
