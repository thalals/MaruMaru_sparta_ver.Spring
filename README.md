### 🐶강만다(강아지를 만나다) - 스파르타 내일배움캠프 3차 프로젝트

<br>


## 🏠 소개

+ 반려견을 위한 반려견의 의한 반려 동반자의 커뮤니티 사이트
+ 홈페이지 : http://www.hminpage.shop/
+ 개발기간 : [3차] 2021년 11월 19일 ~ 2021년 12월 09일 (21일)

<br>

<img src = "https://user-images.githubusercontent.com/42319300/161721735-e80e07f1-108b-4d85-bbda-77599c4729ff.png" width ="400" /> <img src = "https://user-images.githubusercontent.com/42319300/161721726-8e424d92-72a1-44e4-95b1-aca64d0df5fd.png" width ="400" /> </br>

<Br><Br>
  
## 🕸 시스템 구성도
![image](https://user-images.githubusercontent.com/42319300/162265770-338562cc-62b9-4197-83b5-8ba3222cae9a.png)

<br><br>
  
<br/>

## ✨️ 주요 기능
 
#### 1) 반려인들을 위한 소통 
  1. 게시글 작성, 댓글, 좋아요
  2. 검색, 정렬
  
#### 2) 만남을 위한 이벤트 개설 
  1. 모임을 위한 날짜
  2. 장소 위치
  3. 장소 근처 반려동물을 위한 장소 추천
  
#### 3) 반려인의 반려동물 뽑내기
  1. 반려동물 등록
  2. 사용자들의 반려동물 목록
  3. 프로필 좋아요
  
<br/>

## 📗 트러블 슈팅 [Wiki](https://github.com/thalals/MaruMaru_sparta_ver.Spring/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)
#### 1. Pagenation기능 구현 - Page To Dto
 - JPA를 이용해서 Pageable 인터페이스 객체로 값을 불러오는데, 사용자에게 보내는 정보를 Entity 그대로 보내고 싶지않다.
#### 2. JPA 외부참조 엔티티의 size로 정렬 - JPA 서브쿼리 사용
 - JPA 페이징 정렬을 사용할 때, 참조중인 엔티티의 개수로 정렬을 하고싶다!
#### 3. OneToMany N+1 문제 해결하기
 - JPA를 더 효율적으로 사용하고싶다! 주르륵 쿼리 보기싫다!
#### 4. JPA 페이징 API 성능 개선
 - 더 빠른 성능을 이끌어내고싶다! 오프셋 보다는 커서페이징을! 카운트 쿼리의 최적화를!
#### 5.AWS S3를 사용한 파일 업로더 구현
  - 이미지 파일은 Entity, Ajax, 서버 어떻게 관리하면 좋을까?

<br/>

## 👍 API 설계 [Wiki](https://github.com/thalals/MaruMaru_sparta_ver.Spring/wiki/API-%EB%AC%B8%EC%84%9C)


<br/>


## 📌 Tech Stack


<p align='center'>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/></a>
<img src="https://img.shields.io/badge/-Jquery-D3D3D3?style=flat&logo=jquery&logoColor=white"/></a><br>
<img src="https://img.shields.io/badge/JAVA-5483B1?style=flat-square&logo=JAVA&logoColor=white"/></a>
<img src="https://img.shields.io/badge/SPRING-232F3E?style=flat-square&logo=SPRING&logoColor=white"/></a>
<img src="https://img.shields.io/badge/SpringDataJPA-72C850?style=flat&logo=jpa&logoColor=white"/></a>
<img src="https://img.shields.io/badge/-SpringSecurity-98FB98?style=flat&logo=springsecurity&logoColor=white"/></a> <br>
<img src="https://img.shields.io/badge/-Swagger-B6D72C?style=flat&logo=swagger&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Mysql-47A248?style=flat-square&logo=Mysql&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Amazon AWS-BD8B13?style=flat-square&logo=Amazon%20AWS&logoColor=white"/></a>
</p>

##### 프레임워크/ DB 
  ```Spring Boot(JPA)``` , ```MySQL```

#### 라이브러리 
  ```Spring Security```, ```JWT```

#### 인프라 

```AWS S3```, ```EB(ElasticBeanstalk)```, ```ECR(Elastic Container Registry)```, <br> 
```Github Action```, ```Docker```
    
### Open API
  ```Daum API(도로명 주소)```, ```Jquery DatePicker(날짜 입력)```, <br>
  ```KaKao Map```, ```KaKao Local (장소 검색)```

#### 프로젝트 관리
   ```Github Project + issues```



<br/>


## 🧙 맴버

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


