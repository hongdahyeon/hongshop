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
