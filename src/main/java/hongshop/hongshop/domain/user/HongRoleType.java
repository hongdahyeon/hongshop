package hongshop.hongshop.domain.user;

/**
 * @fileName HongRoleType
 * @author dahyeon
 * @version 1.0.0
 * @date 2023-07-17
 * @summary  사용자가 갖는 role type에 대해 enum으로 선언하였다.
 * - ROLE_SUPER : 슈퍼 권한 -> 자신 게시글 아닌 게시글에 댓글 작성, 자신이 작성하지 않은 댓글 수정 및 삭제
 * - ROLE_ADMIN : 어드민 권한 -> 자신 게시글 아닌 게시글에 댓글 작성, 자신이 작성한 댓글만 수정 및 삭제
 * - ROLE_USER : 일반 유저 -> 자신이 작성한 게시글만 조회 가능 (댓글 작성은 불가능) (게시글 작성은 가능)
 **/
public enum HongRoleType {
    ROLE_ADMIN, ROLE_USER, ROLE_SUPER;
}
