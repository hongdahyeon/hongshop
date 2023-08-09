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
