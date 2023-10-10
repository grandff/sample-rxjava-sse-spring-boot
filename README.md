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
- [ ] get lis of books (rxjava)
    " 책 목록 조회 (get)
- [ ] get a book's details (rxjava)
    " 책 상세 조회 (get)
- [ ] delete a book (rxjava)
    " 책 삭제 (delete)
- [X] common response entity
- [ ] file upload
- [ ] file download
- [ ] jwt login
- [ ] refresh token
- [ ] SSE (Server Sent Event) - 카운트 알림 받기
- [ ] CRUD Basic API
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

## 💻 Tech Stack

**Main Framework** - [Spring Boot](https://nextjs.org/blog/next-13/)  
**Component** - [RxJava](https://reactjs.org/)  
**Test** - [jUnit](https://vercel.com/)  
**Docs** - [Swagger](https://vercel.com/)  

## 👨🏻‍💻 Running Locally

- db : h2
- swagger : <http://localhost:8080/swagger-ui.html>

```bash

```

## 📜 License

Licensed under the MIT License, Copyright © 2023

---

Made by kjm 👨🏻‍💻
