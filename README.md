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
