package hongshop.hongshop.global.handler;

public enum FailureException {

    UsernameNotFoundException("등록되지 않은 사용자입니다. \n 회원가입을 먼저 해주시기 바랍니다."),
    DisabledException("계정이 비활성화 되었습니다. \n 관리자에게 문의 바랍니다."),
    CredentialsExpiredException("비밀번호가 만료되었습니다. \n 비밀번호를 변경해주세요."),
    AccountExpiredException("최근 로그인일로부터 1년이 지나 휴먼 계정이 되었습니다. \n 이메일 인증을 해주세요."),
    InternalAuthenticationServiceException("내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. \n 관리자에게 문의해주세요."),
    LockedException("비밀번호 5회 오류로 계정이 잠겼습니다. \n 관리자에게 문의해주세요.");
    String message;

    FailureException(String message) {
        this.message = message;
    }
}
