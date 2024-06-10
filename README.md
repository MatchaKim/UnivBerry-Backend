# 유니브베리 백엔드 레파지토리

## 서비스 개요
해당 서비스는 개발자들에게 이메일 인증을 용이하게 해주는 서비스로, 원하는 대학의 이메일 소유 여부를 확인시켜줍니다.

## 용어 정리

### 유니브베리 제공자
- **정의**: 유니브베리의 개발자, 김준식.
- **역할**: 유니브베리 플랫폼을 개발하고 유지보수함.

### 유니브베리 사용자
- **정의**: 유니브베리를 사용하여 자체 서비스를 구축하고자 하는 개발자.
- **역할**: 유니브베리 플랫폼을 이용하여 다양한 어플리케이션과 서비스를 개발함.

### 제품 회원
- **정의**: 유니브베리 사용자가 제작한 서비스를 이용하는 최종 사용자.
- **역할**: 유니브베리 기반의 서비스를 사용하고 그 기능을 활용함.

### 인증 그룹
- **정의**: 제품 회원들을 특정 기준에 따라 분류하는 단위.
- **목적**: 제품 회원들의 인증 및 관리를 용이하게 하기 위함.

## 개발 도구 및 환경설정
- **프론트엔드**: Vue3 Nuxt 기반의 프레임워크
- **백엔드**: 스프링 프레임워크
- **배포된 서버 주소**: [univberry.site](http://univberry.site)

## 상세 설계서

### 4.1 백엔드 서버
백엔드 서버 자바 패키지 구성은 다음과 같이 정의합니다:
- dto
- controller
- service
- repository
- entity

#### 4.1.1 백엔드 서버 파일 구조
```plaintext
src
└── main
    └── java
        └── com
            └── bezkoder
                └── springjwt
                    ├── auth
                    │   ├── controllers
                    │   ├── models
                    │   ├── payload
                    │   ├── repository
                    │   ├── security
                    │   └── services
                    ├── product
                    │   ├── controller
                    │   ├── dto
                    │   ├── entity
                    │   ├── repository
                    │   └── service
                    └── SpringBootSecurityJwtApplication.java
```

### 4.2 기능 단위 설명

#### 4.2.1 개발자 회원관리

**회원가입**
- **HTTP Method**: POST
- **경로**: /api/auth/signup
- **받을 파라미터**: username, email, password
- **DTO Class**: SignupRequest
- **Controller Class**: AuthController
- **Controller Method**: registerUser
- **Service Class**: UserDetailsServiceImpl
- **Repository Class**: UserRepository
- **Entity Class**: User
- **설명**: 회원가입 요청을 받아 사용자 정보를 데이터베이스에 저장합니다.

**로그인**
- **HTTP Method**: POST
- **경로**: /api/auth/signin
- **받을 파라미터**: username, password
- **DTO Class**: LoginRequest
- **Controller Class**: AuthController
- **Controller Method**: authenticateUser
- **Service Class**: UserDetailsServiceImpl
- **Repository Class**: UserRepository
- **Entity Class**: User
- **설명**: 로그인 요청을 받아 JWT 토큰을 생성합니다.

#### 4.2.2 인증그룹 관리

**인증그룹 생성**
- **HTTP Method**: POST
- **경로**: /api/certification-group
- **받을 파라미터**: forceEmail
- **DTO Class**: CertificationGroupRequest
- **Controller Class**: CertificationGroupController
- **Controller Method**: createCertificationGroup
- **Service Class**: CertificationGroupService
- **Repository Class**: CertificationGroupRepository
- **Entity Class**: CertificationGroup
- **설명**: 새로운 인증그룹을 생성합니다.

**인증그룹 전체 조회**
- **HTTP Method**: GET
- **경로**: /api/certification-group/user-groups
- **Controller Class**: CertificationGroupController
- **Controller Method**: getCertificationGroupsByUser
- **Service Class**: CertificationGroupService
- **Repository Class**: CertificationGroupRepository
- **설명**: 모든 인증그룹의 목록을 조회합니다.

**인증그룹 상세 사용자 조회**
- **HTTP Method**: GET
- **경로**: /api/certification-group/{groupId}/product-users
- **받을 파라미터**: groupId
- **Controller Class**: CertificationGroupController
- **Controller Method**: getAllProductUsers
- **Service Class**: CertificationGroupService
- **Repository Class**: ProductUserRepository
- **설명**: 특정 인증그룹의 사용자 상세 정보를 조회합니다.

**사용자 인증**
- **HTTP Method**: GET
- **경로**: /product-user/verify/product/{productUserId}
- **받을 파라미터**: productUserId
- **Controller Class**: ProductUserController
- **Controller Method**: verify
- **Repository Class**: ProductUserRepository
- **설명**: 사용자를 인증합니다.

#### 4.2.3 대학 관리

**대학리스트 받아오기**
- **HTTP Method**: GET
- **경로**: /api/universities/all
- **Controller Class**: UniversityController
- **Controller Method**: getAllUniversities
- **Repository Class**: UniversityRepository
- **설명**: 모든 대학의 리스트를 조회합니다.

**대학 검색**
- **HTTP Method**: GET
- **경로**: /api/universities/search
- **받을 파라미터**: name
- **Controller Class**: UniversityController
- **Controller Method**: searchUniversitiesByName
- **Repository Class**: UniversityRepository
- **설명**: 키워드로 대학을 검색합니다.

### 4.3 프론트엔드 화면

#### 4.3.1 회원가입 화면
- **경로**: /member/create
- **사용 변수**: email, password, passwordConfirm
- **로직**: 회원가입 버튼 클릭 시 API 요청으로 email, password, passwordConfirm을 백엔드 서버의 /api/auth/signup으로 전송합니다.

#### 4.3.2 로그인 화면
- **경로**: /member/login
- **사용 변수**: email, password
- **로직**: 로그인 버튼 클릭 시 API 요청으로 email, password를 백엔드 서버의 /api/auth/signin으로 전송합니다.

#### 4.3.3 강제하는 대학교 설정 화면
- **경로**: /univ/list
- **사용 변수**: 없음
- **로직**:
  - /api/universities/all을 활용하여 대학교 리스트를 받아옵니다.
  - 받아온 대학교와 header의 토큰을 백엔드 서버의 /api/certification-group으로 전송하여 인증그룹을 생성합니다.

#### 4.3.4 인증그룹 전체 조회 리스트
- **경로**: /univ/certification-group
- **사용 변수**: 없음
- **로직**: /api/certification-group/user-groups를 활용하여 사용자 리스트를 받아옵니다.

## 4.2 기능 단위 설명 (표)


| 기능단위           | 기능               | HTTP Method | 경로                                             | 받을 파라미터              | DTO Class               | Controller Class            | Controller Method            | Service Class              | Repository Class            | 설명                                      |
|--------------------|--------------------|-------------|--------------------------------------------------|---------------------------|-------------------------|-----------------------------|-------------------------------|-----------------------------|-----------------------------|-------------------------------------------|
| 개발자 회원관리    | 회원가입           | POST        | /api/auth/signup                                 | username, email, password | SignupRequest           | AuthController              | registerUser                  | UserDetailsServiceImpl      | UserRepository               | 사용자 정보를 데이터베이스에 저장합니다.  |
| 개발자 회원관리    | 로그인             | POST        | /api/auth/signin                                 | username, password        | LoginRequest            | AuthController              | authenticateUser              | UserDetailsServiceImpl      | UserRepository               | 로그인 요청을 받아 JWT 토큰을 생성합니다.|
| 인증그룹           | 인증그룹 생성      | POST        | /api/certification-group                         | forceEmail                | CertificationGroupRequest | CertificationGroupController | createCertificationGroup      | CertificationGroupService    | CertificationGroupRepository | 새로운 인증그룹을 생성합니다.            |
| 인증그룹           | 인증그룹 전체 조회 | GET         | /api/certification-group/user-groups             | 없음                      | 없음                    | CertificationGroupController | getCertificationGroupsByUser  | CertificationGroupService    | CertificationGroupRepository | 모든 인증그룹의 목록을 조회합니다.      |
| 인증그룹           | 인증그룹 상세 조회 | GET         | /api/certification-group/{groupId}/product-users | groupId                   | 없음                    | CertificationGroupController | getAllProductUsers            | CertificationGroupService    | ProductUserRepository        | 특정 인증그룹의 사용자 상세 정보를 조회합니다. |
| 인증               | 사용자 인증        | GET         | /product-user/verify/product/{productUserId}     | productUserId             | 없음                    | ProductUserController       | verify                        | 없음                         | ProductUserRepository        | 사용자를 인증합니다.                    |
| 강제 이메일 리스트 | 대학리스트 받아오기| GET         | /api/universities/all                            | 없음                      | 없음                    | UniversityController        | getAllUniversities            | 없음                         | UniversityRepository         | 모든 대학의 리스트를 조회합니다.        |
| 강제 이메일 리스트 | 대학 검색          | GET         | /api/universities/search                         | name                      | 없음                    | UniversityController        | searchUniversitiesByName      | 없음                         | UniversityRepository         | 키워드로 대학을 검색합니다.              |
