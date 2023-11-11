# hongshop

* commit1 : settings (23/06/27)
* commit2 : login setting (23/07/10)
  - 로그인 기본 세팅
  - 유저 등록 생성
* commit3 : get login user info (23/07/10)
* commit4 : filter, wrapper test -> for xss (23/07/10)
* commit5 : xss 공격 대비 -> common-io, text implement (23/07/10)
* commit6 : xss 공격 대비 -> owasp, jsoup (23/07/13)
* commit7 : hong-user table -> address field 추가 (23/07/14)
* commit8 : filter chain 코드 수정 (23/07/14)
* commit9 : hong user 주석 (23/07/17)
* commit10 : 작성자, 수정자, 작성일, 수정일 공통 매핑 처리
             작성일 및 수정일에 대해서는 'EnableJpaAuditing' 활용 (23/07/17)
* commit11 : swagger 설정  (23/07/17)
* commit12 : 로그인 전에 index 페이지 접근 시 로그인 페이지로 전환 (23/07/17)
* commit13,14 : HongUserController 이름 HongUserRestController로 바꾸고, swagger 어노테이션 붙이기 (23/07/17)
* commit15 : Address 클래스 위치 변경 (23/07/17)
* commit16 : post 테이블 추가 (23/07/17)
* commit17 : answer 테이블 추가 (23/07/17)
* commit18 : 단건 게시글 조회 시, 답변 리스트 함께 조회 (23/07/17)
* commit19 (23/07/17)
    - 응답 형식 ResponseEntity에서 Response 형태로 변환
    - 응답 코드값 enum으로 선언
    - IllegalArgumentException에 대한 handler 지정 -> BAD_REQUEST 반환
    - validation 처리
* commit20 : category 테이블 추가 (23/07/17)
* commit21 : 게시글 조회수 증가 -> using cookie (23/07/17)
* commit22 : 파일 업로드 & 다운로드 로직 구현 (23/07/18)
* commit23 : Rest 컨트롤러 정보 swagger에 등록 (23/07/18)
* commit24 : product 테이블 추가 (23/07/18)
* commit25 : 설명 주석 추가 (23/07/18)
* commit26 : 카테고리(상위)에서 물품들(하위) 함께 조회하는 api 추가 (23/07/18)
* commit27 : product 테이블에 재고 컬럼 추가 (23/07/18)
* commit28 : order & order-detail 테이블 추가 (23/07/18)
      - 주문에 따라 product 테이블의 재고 컬럼도 변한다.
      - 만약 재고가 없다면, throw와 함께 주문이 안된다.
* commit29 : commit12에 대해 바꿈. redirect가 아니라 권한 처리로 login page로 전환 (23/07/18)
* commit30 : fileGroupId 부분 하드 코딩 수정
* commit31 : fileGroup & file 부분 오류 수정 및 동작 기능 변경
      - fileServiceImpl에서 진행하던 fileGroup 관련 save, findById를 fileGroupServiceImpl 단으로 이동시킴
* commit32 : swagger tag name 수정
* commit33 : cart 테이블 생성 (23/07/18)
* commit34 : 게시글 저장 -> file-group-id 유무로 나누어 저장
    - 만일, fileGroupId가 있다면 해당 fileGroupId를 갖는 파일들의 fileState SAVED로 변경
* commit35 : post 엔티티 builder 수정 (23/07/18)
* commit36 : 주문 상태 값 변경 (23/07/18)
* commit37 : 배송 테이블 생성 (23/07/19)
    - 1개 배송 = 1개의 주문
* commit38 : 코드 수정 (23/07/19)
* commit39 : 기본적인 css, js 파일 추가 (23/07/19)
* commit40 : layout 구현 및 기본적인 login 화면 구현 (23/07/19)
* commit41 : 로그인 및 회원가입 로직 구현 (23/07/19)
* commit42 : 기본 레이아웃 수정 및 로그아웃 처리 (23/07/20)
* commit43 : html에서의 validation 처리 및 회원정보 수정 기능 추가 (23/07/28)
* commit44 : 장바구니 화면 세팅 및 spring security를 thymeleaf에서 사용하도록 gradle 추가 (23/07/31)
* commit45 : 장바구니 화면 구현 (now: 장바구니 삭제 로직까지 구현) (23/08/08)
* commit46 : 게시글들에 대해 게시글을 포함하고 있는 '게시판' 테이블 생성 (23/08/08)
* commit47 : qna 게시판 기본 세팅 (게시글+파일 불러오기까지 완료) (23/08/08)
* commit48 : qna 게시판 데이터 뿌려주기 및 버튼 클릭 이벤트 제어 (23/08/09)
* commit49 : qna 게시판 각각 게시글 조회 및 ckeditor 추가 (23/08/09)
* commit50 : ckeditor 파일 업로드 url 생성 (23/08/09)
* commit51 : 로그인시, 아이디 저장 로직 구현 (23/08/09)
* commit52 : 파일 다운로드 front쪽 로직 구현 (23/08/09)
* commit53 : 게시글 업데이트 로직 구현 (23/08/09)
    - 아직 ckeditor에서 사진 업로드는 구현 안됨
* commit54 : ckeditor 사진 업로드 구현 (안된 이유 : @Configuration를 안적음)
* commit55 :  layout:decorate="layouts/default" -> layout:decorate="~{layouts/default}" (23/09/12)
* commit56 : 게시글 새글 작성 로직 구현 (23/10/19)
* commit57 (23/10/19)
      - 게시글 댓글 기능 추가
      - 유저 권한 추가 : ROLE_SUPER -> 모든 게시글에 대한 댓글 작성 가능 및 다른 사람의 댓글 삭제 및 수정 가능
* commit58 : 테이블 tr, td style 적용 (23/10/19)
* commit59 : 공지사항 cru 적용 (23/10/19) -> 삭제(d)에 대해서는 qna와 함께 기능 추가 필요
* commit60 : 공지사항, qna 삭제 기능 적용 (23/10/19)
* commit61 : 답변개수 -> 답변 (23/10/19)
* commit62 : window.onload -> $(document).ready(function(e) (23/10/19)
* commit63 : FAQ CRUD 기능 추가 (23/10/19)
* commit64 : home 화면 이동 (23/10/19)
* commit65 : admin, super 권한은 정보관리 메뉴 보임 (23/10/19)
* commit66 : 비번, 비번확인 validation (23/10/26)
* commit67 : 상품 카테고리 관리 화면 추가(수정중) (23/10/26)
* commit68 : product, category table 'deleteYn' 컬럼 추가 (23/10/27)
* commit69 : 카테고리 및 상품 관리 화면 구현 (23/10/27)
  - 상품 주문한 사용자 버튼 이동 화면 구현 필요
  - 상품 가격 변경에 대한 대책 필요
* commit70 : nav-width 70% (23/10/27)
* commit71 : product-user-list-view (23/10/30)
* commit72 : 주문관리 화면 구현 (23/10/30)
* commit73 : 상품 구경하기 헤더 db 데이터로 뿌리기 (23/10/30)
* commit74 : 카테고리 테이블 ORDER_NUM 컬럼추가 (23/10/30)
* commit75 :  사용자 관리 화면 구현 (23/10/30)
* commit76 (23/10/30)
   - 유저 테이블 이름, 이메일 컬럼 추가
   - spring-email-service 구현(세팅만)
* commit77 : 이메일 중복 확인 로직 구현 (23/10/31)
* commit78 : dto, vo 패키지 생성 및 이동 (23/10/31)
* commit79  (23/10/31)
  - '/front/**'로그인 전 시점에 동작 가능
  - 아이디찾기, 비번 초기화 -> 메일 발송 구현
* commit80 : properties 암호화 (23/10/31)
* commit81 : 파일 다운로드/업로드 기록 조회 화면 구현 (23/10/31)
* commit82 : 소셜 로그인 구현 (카카오톡) (23/11/01)
* commit83 : 파일 다운로드 로그 조회 화면 -> 사용자ID 추가 (23/11/01)
* commit84 : 네이버 로그인 구현 (23/11/02)
* commit85 : 구글 로그인 구현 (23/11/02)
* commit86 : 로그인 실패 핸들러 구현 (23/11/03)
  - 원래는 아이디 틀린 경우, 비번 틀린 경우를 나누어 구현하려고 하였으나 보안적인 측면에서 나누는 것은 좋지 않다고 하여 합쳐둠
* commit87 : 사용하지 않는 Controller 제거 및 HongPostTypeController에서 HongPostController로 변경 및 위치 변경 (23/11/02)
   - postType html 페이지 추가
* commit88 : 소셜 로그인의 경우 비번 변경 불가능 (23/11/02)
* commit89 : 게시판 유형 관리 페이지 구현 (23/11/03)
    - postType테이블 : 사용여부, 삭제여부, 정렬, URL 컬럼 추가
    - postType테이블 : 사용여부 'Y'일 경우 자동 URL 생성
    - postType테이블 : CRUD 적영
* commit90 : manage 폴더 -> manager 변경 (23/11/03)
* commit91 : 404, 403 페이지 생성 (23/11/03)
* commit92 : 로그인 실패, 성공 핸들러 구현 (23/11/03)
  - 비번 실패 시 5번의 카운트 -> 계정 잠김
* commit93 : 이미지 폴더 위치 변경 및 index 페이지 살짝 구현 (23/11/03)
* commit94 : 상품 테이블 사진그룹Id 추가 (23/11/06)
    - file.js 추가를 통한 파일 업로드,삭제 공통처리
    - 상품 테이블에 이미지 Id 추가 -> 후에 상품 주문 화면에서 해당 이미지 보여주기 위함
* commit95 (23/11/06)
   - 게시판 -> file.js 적용
   - 파일 삭제 시, deleteYn 'Y'로 변경
   - 파일 조회 시, deleteYn & fileState 조건절로 적용
* commit96 (23/11/06)
   - 상품 이미지 등록의 경우 이미지 파일만 가능하도록 처리
   - file.js script.html로 이동
   - listwithDeleteYnAndFileState 메소드 deleteYn, FileStat값 하드코딩에서 직접 받아오도록 처리
* commit97 (23/11/06)
   - 상품테이블 newProductYn 컬럼 추가 -> 해당 상품들은 최산상품으로 구분
   - 상품 메뉴 dropdwon -> button으로 변경
* commit98 (23/11/06)
   - 상품 주문 모달창 생성 -> 후에 모달창 안에 값들 변경 필요
* commit99 (23/11/07)
   - body conatainer 변경
   - 상품 화면 container css -> 따로 빼기  
* commit100 : 관리자 화면에서 사용자 계정 정지 풀기 구현 (23/11/07)
* commit101 (23/11/07)
  - 주문하기 모달창 validation 추가 및 모달화면 구성
  - 장바구니 화면 버튼으로 상품 구매 화면 이동
* commit102 : 500에러 화면 구현 (23/11/07)
* commit103 (23/11/08)
   - 400 에러 화면 구현
   - 장바구니 테이블 deleteYn 추가
   - 장바구니에서 삭제 구현, 주문 화면 넘어가기 구현
* commit104 (23/11/08)
   - 장바구니에서 상품 주문 하기
   - order, order-detail, deliver insert
   - cart deletYn to Y, product stock update
* commit105 : 배송 주문 관리 화면 구현 (23/11/08)
* commit106 : 주문시, 꼭 사용자의 주소 정보가 아닌 다른 주소 정보로도 주문 가능 (23/11/08)
* commit107 : 사용자 입장에서 자신이 주문한 주문정보 및 배송 정보 보기 (23/11/08)
* commit108 : 소셜로그인한 사용자의 경우 로그인 성공시 만약 주소 정보가 비어있다면 회원정보 수정 페이지로 redirect (23/11/08)
    - 주소의 경우 폼으로 회원가입시 필수사항이지만 소셜 로그인의 경우 정보를 얻지 못한다. 하지만 회원정보중 필수 값이기 때문에 해당 정보가 비어있다면 로그인 성공시 주소 정보를 확인하고 메인 혹은 회원정보 변경 화면으로 redirect
* commit109  : 주문 및 배송정보 화면에서 배송상태가 AWAIT일 경우, 배송지 변경 구현 (23/11/09)
* commit110 : url 정리 및 콘솔 정리 (23/11/09)
* commit111 : serviceImpl 주석 설명 달기 (23/11/09)      
* commit112 : 상품 조회 화면에서 주문 및 장바구니 담기 로직 구현 (23/11/09)
* commit113
     - 장바구니 화면에서 담긴 개수 변경 로직 구현
     - 장바구니 화면에서 현재 담긴 개수보다 재고가 적거나 없으면 알림창 띄우기  (해당 상품들은 결제화면으로 못넘어가도록 처리)
     - 상품 조회 화면에서 재고가 없으면 text 보이기
* commit114 : 게시판 테이블 text-center 한번에 style로 처리 (23/11/09)
* commit115 : 주문건의 주문 상태가 CHARGED, DELIVER_ING 인게 있다면 해당 상품 삭제 못하도록 처리 (23/11/10)
* commit116 : error 화면용 css 생성 및 적용 (23/11/10)
* commit117 (23/11/10)
     - 리뷰 테이블 생성 (주문 테이블과 1:1)
     - 주문상태값에 따라 배달 상태값 변경
     - 배달상태값에 따라 주문 상태값 변경
     - (사용자입장) 배달 상태가 'DELIVERED'일 경우, 주소 변경 버튼 대신에 리뷰작성 버튼 생성
* commit118 (23/11/10)
     - 리뷰 작성 폼 구현 및 로직 구현
     - 1사용자는 1주문건에 대해 1리뷰만 작성 가능
     - 리뷰 작성했는지 확인 후, 리뷰 작성할 수 있도록 처리
* commit119 : 리뷰가 작성된 주문건의 경우 관리자 화면에서 주문/배송 상태 변경 불가능 (23/11/10)
* commit120 (23/11/11)
   - 리뷰 테이블 order-detail 테이블이랑 OneToOne
   - 1 주문상세 상품건이 1리뷰를 갖는다.
   - 1주문에 대해 상세 주문건중 1개라도 리뷰가 달려있으면 주문 상태 및 배달 상태값 변경 불가능
* commit121 : 리뷰 리스트 및 리뷰 수정, 리뷰 삭제 로직 구현 (23/11/12)
