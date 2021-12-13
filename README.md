## 스파르타 내일배움캠프 3차 프로젝트
### 🐶강만다(강아지를 만나다) - 스파르타 내일배움캠프 3차 프로젝트

## 🏠 소개

+ 반려견을 위한 반려견의 의한 반려 동반자의 커뮤니티 사이트


## 🐶Project Directory

```
.
├── gradle/wrapper
├── src                         # 메인 파일
│   ├── main/java/com.example.marumaru_sparta_verspring
│   │    ├──common              # 예외처리
│   │    ├──configuration
│   │    ├──controller
│   │    ├──domain              # Entity 작성 (테이블)
│   │    ├──dto
│   │    ├──repository
│   │    ├──security            # 인증/인가 설정
│   │    ├──service             # 비즈니스로직
│   │    └──util                # jwt
│   │
│   └──recources
│           ├──static                       # JS/CSS/IMG 파일 + index.html
│           ├──templates                    # HTML 파일
│           ├──application.properties       # 설정파일
│           └──application.aws.yml          # aws 설정(삭제 예정)
└──build.gradle
```

<br/>

## 👍 API 설계

### POST 게시글 

|기능         |Method|URL|Request|Response|
|---------------|------|-----------|---------|---------|
|게시글 조회 |GET|  /post-list        |          | Meet 게시글 List |
|게시글 작성 |POST|  /posts           |게시글 입력값(FormData) |      |
|상세 조회  |GET|  /posts/detail     |           id(QueryString)    | 게시글 |
|게시글 권한 확인 |GET|  /posts/check      | 게시글 입력값(FormData) |  |
|게시글 수정 |PUT|  /posts/detail     | 게시글 입력값(FormData) |  |
|게시글 삭제 |DELETE| /posts/detai    | id(QueryString)      |         |
|댓글 작성 |POST|  /posts/comment    |  userId , id         |         |
|댓글 삭제 |DELETE|  /posts/comment | userId , id |         |
|댓글 수정 |PUT| /posts/comment  | 수정 값, id |           |



### profile 기능

|기능         |Method|URL|Request|Response|
|---------------|------|-----------|---------|---------|
|프로필 조회 |GET|  /profile        |                |          |
|프로필 작성 |POST|  /profile         |              |          |
|프로필 상세 조회  |GET|  /profile/detail     |               |         |
|프로필 수정 |PUT|  /profile/detail      |              |         |
|프로필 권한 확인 |GET|  /profile/check     |               |       |
|프로필 삭제 |DELETE| /profile/detail    |             |         |




### MEET 기능

|기능         |Method|URL|Request|Response|
|---------------|------|-----------|---------|---------|
|Meet 게시글 조회 |GET|  /api/meets           |      | Meet 게시글 List |
|Meet 게시글 작성 |POST|  /api/meets          |게시글 입력값(FormData) | Meet 게시글 List |
|Meet 상세 조회  |GET|  /api/meet/{id}        |                      | Meet 게시글 |
|Meet 게시글 수정 |PUT|  /api/meet/{id}       | 게시글 입력값(FormData) | 수정 결과 |
|Meet 게시글 삭제 |DELETE|  /api/meet/{id}    |      |           |
|Meet 댓글 작성 |POST|  /api/meet/comment    | Meet_idx, userId |           |
|Meet 댓글 삭제 |DELETE|  /api/meet/comment/{id} | userId |           |
|Meet 댓글 수정 |PUT|  /api/meet/comment    | 수정 값 |           |


### User 기능

|기능         |Method|URL|Request|Response|
|---------------|------|-----------|---------|---------|
|로그인        |POST|  /login         |      |        |
|카카오 로그인 |POST|  /login/kakao      |         |        |
|회원가입       |POST|  /signup        |                      |       |
|아이디 중복 확인 |POST|  /signup-check-dup       |         |       |
|회원 탈퇴       |DELETE|  /withdrawak/{username}  |            |           |
|유저 프로필 수정 |PUT|  /userprofile    |                   |           |
|유저 정보 가져오기 |GET|  /userprofile/{username} |  |           |
|유저의 강아지 프로필 |GET|  /user/dogprofile      |  |           |
|내가 쓴 게시글 확인 |GET|  /user/posts              |  |           |



<br/>


## 🧙 맴버구성

<table>
    <tr>
        <td align="center" width="130px" height="160px">
            <a href="https://github.com/thalals"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/u/42319300?s=460&u=feb753590ea1a1d094b08573bb11f15e801e63cc&v=4" /></a>
          <br />
            <a href="https://github.com/thalals">박형민(팀장)</a>
      </td>
      <td align="center" width="130px" height="160px">
                  <a href="https://github.com/sendkite1"><img height="100px" width="100px" src="https://user-images.githubusercontent.com/42319300/135604950-2cf4e5fd-8cf4-4941-8a00-77e0cd982751.jpg" /></a>
                <br />
                  <a href="https://github.com/sendkite">전송연</a>
            </td>
  </tr>
  <tr>
        <td align="center" width="130px" height="160px">
            <a href="https://github.com/carina9231"><img height="100px" width="100px" src="https://user-images.githubusercontent.com/42319300/135605305-2b71e4a7-c01d-4349-a1d8-dc8132584d99.jpg" /></a>
          <br />
            <a href="https://github.com/carina9231">배소영</a>
      </td>
      <td align="center" width="130px" height="160px">
                  <a href="https://github.com/jenny0325"><img height="100px" width="100px" src="https://user-images.githubusercontent.com/42319300/135706447-06ba949f-ec19-462b-81c6-c5b297bbfc45.jpg" /></a>
                <br />
                  <a href="https://github.com/jenny0325">김재은</a>
            </td>
  </tr>

</table>

<br/>


## 📌 기술 선택 이유!


<p align='center'>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JAVA-5483B1?style=flat-square&logo=JAVA&logoColor=white"/></a>
<img src="https://img.shields.io/badge/SPRING-232F3E?style=flat-square&logo=SPRING&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Mysql-47A248?style=flat-square&logo=Mysql&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Amazon AWS-BD8B13?style=flat-square&logo=Amazon%20AWS&logoColor=white"/></a>
</p>


## 📌 사용 라이브러리, API

1. JPA : DB ORM
2. AWS S3: 사진 업로드
3. jwt : 로그인 암호화
4. validation : 예외처리
5. security : 인증/인가 관리
6. 다음 주소 api : 도로명 주소
7. jquery datepicker : 날짜 입력


<br/>



### 1차 발표영상
[![오지조 1차 발표](http://img.youtube.com/vi/4BzMYLfXwS0/0.jpg)](https://www.youtube.com/watch?v=4BzMYLfXwS0)

### 2차 발표영상
[![오지조 2차 발표](http://img.youtube.com/vi/aSasz08EP7U/0.jpg)](https://www.youtube.com/watch?v=aSasz08EP7U)

