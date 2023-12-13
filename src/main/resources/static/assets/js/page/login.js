const changePwd = $("#changePwd")
const changePwdChk = $("#changePwdChk")

$(document).ready(function(){

    const params = new URLSearchParams(location.search);
    if (params.has("error")) {

        const errorMessage = decodeURIComponent(params.get("error"));
        $("#pwdError").html(errorMessage.replace("\n", "<br>"));
        history.replaceState({}, null, '/login');

    } else if(params.has("enable")) {

        const userId = decodeURIComponent(params.get("userId"));
        const errorMessage = decodeURIComponent(params.get("enable"));
        $("#pwdError").html(errorMessage.replace("\n", "<br>"));
        history.replaceState({}, null, '/login');

        Http.get(`/front/api/user-enable/${userId}`).then((res) => {
            const data = res.message
            Util.alert(`현자 사용자의 계정은 비활성화 되었습니다. <br/> 사유: ${data['enableMsg']} <br/> 관리자에게 문의 바랍니다.`, 'w')
        })

    } else if(params.has("expired")) {

        const userId = decodeURIComponent(params.get("userId"));
        const errorMessage = decodeURIComponent(params.get("expired"));
        $("#pwdError").html(errorMessage.replace("\n", "<br>"));
        history.replaceState({}, null, '/login');

        Util.alert("비밀번호 변경일로부터 90일이 지나 비밀번호가 만료되었습니다. \n 비밀번호를 변경해주세요.", 'w').then(() => {
            $("#changePwdModal-loginUserId").val(userId)
            $("#changePwdModal").modal('show')
        })

    } else if(params.has("account")) {

        const userId = decodeURIComponent(params.get("userId"));
        const errorMessage = decodeURIComponent(params.get("account"));
        $("#pwdError").html(errorMessage.replace("\n", "<br>"));
        history.replaceState({}, null, '/login');

        Util.alert("최근 로그인일로부터 1년이 지나 휴먼 계정이 되었습니다. \n 이메일 인증을 해주세요.", 'w').then(() => {
            $("#validateEmailModal-loginUserId").val(userId)
            $("#validateEmailModal").modal('show')
        });
    }

    const idItemKey = 'HONG_SHOP_ID'

    /* 로그인 아이디 기억하기 -> localStorage에서 id가져오기 */
    document.getElementById('form').addEventListener('submit', () => {
        $('#remember').is(":checked") && localStorage.setItem(idItemKey, $('#userId').val())
        return true
    })

    /* 로그인 아이디 기억하기 */
    const rememberId = localStorage.getItem(idItemKey);
    if (rememberId) {
        const checkbox = $("#remember");
        checkbox.prop("checked", !checkbox.prop("checked"));
        $('#userId').val(rememberId)
    }

    /* 회원가입 */
    $("#goto-new-login").on('click', function(e) {
        window.location.href = '/front/newLogin'
    })

    /* 카카오 로그인 */
    $("#kakao-login-btn").on("click", function(e){
        window.location.href = '/oauth2/authorization/kakao'
    })

    /* 네이버 로그인 */
    $("#naver-login-btn").on("click", function(e){
        window.location.href = '/oauth2/authorization/naver'
    })

    /* 구글 로그인 */
    $("#google-login-btn").on("click", function(e){
        window.location.href = '/oauth2/authorization/google'
    })

    /* 아이디/비번 찾기 */
    $("#find-user-password-userId").on("click", function(e){
        window.location.href = '/front/initialPassword'
    })

    /* 로그인 -> 비번 보이기/숨기기 */
    $("#show-pwd").click(() => {
        const passwordInput = $('#password');
        const type = passwordInput.attr('type') === 'password' ? 'text' : 'password';
        const text = passwordInput.attr('type') === 'password' ? 'hide' : 'show';
        passwordInput.attr('type', type);
        $("#show-pwd").text(text)
    })

    /* [비밀번호 만료] 90일더 연장하기 */
    $("#more-days").on('click', function(e) {
        const userId = $("#changePwdModal-loginUserId").val()
        Http.put('/front/api/changePwdEndDate', {'userId': userId}).then(() => {
            Util.alert("비밀번호 만료일을 90일 연장하였습니다.").then(() => window.location.href = '/login')
        })
    })

    /* [비밀번호 만료] 비밀번호 변경하기 */
    $("#change-pwd").on('click', function(e) {
        const userId = $("#changePwdModal-loginUserId").val()
        const password = $("#changePwd").val()

        const form = document.getElementById("change-pwd-form");
        if (form.checkValidity() === false) form.classList.add("was-validated");
        else {
            Http.put('/front/api/changePwdEndDate', {'userId': userId, 'password': password}).then(() => {
                Util.alert("비밀번호가 변경되었습니다.").then(() => window.location.href = '/login')
            })
        }
    })

    /* [계정 만료] 이메일로 인증번호 발송 */
    $("#email-validate").on('click', function(e) {
        const form = document.getElementById("validate-email-form");
        if (form.checkValidity() === false) form.classList.add("was-validated");
        else {
            const userId = $("#validateEmailModal-loginUserId").val()
            const userEmail = $("#chkEmail").val()
            Http.get(`/front/api/sendEmail?userId=${userId}&userEmail=${userEmail}`).then((res) => {
                if(res.message.result === 1) Util.alert(`${res.message.message}`, 'w')                  // 해당 사용자 찾기 실패
                else {                                                                                  // 이메일 전송 성공
                    Util.alert(`${res.message.message}`).then(() => {
                        $("#email-validate-chk").prop('disabled', false)
                        $("#email-validate").prop('disabled', true)
                    })
                }
            })
        }
    })

    /* [계정 만료] 이메일로 인증번호 발송 */
    $("#email-validate-chk").on('click', function(e) {
        const userId = $("#validateEmailModal-loginUserId").val()
        const userEmail = $("#chkEmail").val()
        const validateNum = $("#validate-num").val()

        if(!validateNum.length) Util.alert("이메일로 받으신 인증번호를 입력해주세요.", 'w')
        else {
            Http.get(`/front/api/check-verify?verifyCode=${validateNum}&userEmail=${userEmail}`).then((res) => {
                if(res.message.result === 0) Util.alert(`${res.message.message}`, 'w')
                else Util.alert(`${res.message.message}`).then(() => window.location.href = '/login')
            })
        }
    })

    changePwd.on('input', chckValidate)
    changePwdChk.on('input', chckValidate)
})

/* [비밀번호 만료] -> 비번,비번확인 일치 validation */
function chckValidate(){
    const changePwdVal = changePwd.val()
    const changePwdChkVal = changePwdChk.val()

    if(changePwdVal && changePwdChkVal) {
        if(changePwdVal === changePwdChkVal) changePwdChk.removeClass('is-invalid')
        else changePwdChk.addClass('is-invalid')
    }
}