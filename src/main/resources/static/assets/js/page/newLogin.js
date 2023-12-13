let IdChk = false;
let EmailChk = false;

const newPassword = $("#newPassword")
const newPasswordChk = $("#newPasswordChk")

$(document).ready(function() {

    /* 로그인 화면으로 이동하기 */
    $("#goto-login").on("click", function(e) {
        window.location.href = '/login'
    })

    /* 회원가입 -> 이메일 중복 확인 */
    $("#check-Email").click(() => {
        const newUserEmail = $("#userEmail").val()

        if(!newUserEmail.length) Util.alert("이메일은 빈값일 수 없습니다.", 'w', 'w')
        else {

            Http.get(`/front/api/checkEmail?userEmail=${newUserEmail}`).then((res) => {
                if(res.message === 1) $("#EmailChkMessage").css('color', 'red').html("중복되는 이메일입니다.")
                else if(res.message === 2) $("#EmailChkMessage").css('color', 'red').html("유효한 이메일 형식이 아닙니다.")
                else {
                    $("#EmailChkMessage").css('color', 'green').html("사용 가능한 이메일입니다.")
                    $("#userEmail").prop('disabled', true)
                    $("#check-Email").prop('disabled', true)
                    EmailChk = true
                }
            })
        }
    })

    /* 회원가입 -> 아이디 중복 확인 */
    $("#check-Id").click(() => {
        const newUserId = $("#newUserId").val();

        if(!newUserId.length) Util.alert("아이디는 빈값일 수 없습니다.", 'w', 'w')
        else {

            Http.get(`/front/api/checkId?userId=${newUserId}`).then((res) => {
                if (!res.message) $("#IdChkMessage").css('color', 'red').html("중복되는 아이디입니다.")
                else {
                    $("#IdChkMessage").css('color', 'green').html("사용 가능한 아이디입니다.")
                    $("#newUserId").prop('disabled', true)
                    $("#check-Id").prop('disabled', true)
                    IdChk = true
                }
            })
        }
    })

    /* 회원가입 */
    window.addEventListener("load", function(event) {
        event.preventDefault(); event.stopPropagation();
        const form = document.getElementById("new-login-form");
        form.addEventListener("submit", function(e){
            if(form.checkValidity() === false){
                e.preventDefault(); e.stopPropagation();
                form.classList.add("was-validated")
            }else{
                e.preventDefault(); e.stopPropagation();
                const pwd = newPassword.val()
                const pwdChk = newPasswordChk.val()

                if(!IdChk || !EmailChk) Util.alert("아이디 및 이메일 중복 여부를 확인해주세요.", 'w', 'w')
                else if(pwd.length === 0 || pwd !== pwdChk) Util.alert("입력하신 비밀번호가 다릅니다.", 'w', 'w')
                else {
                    FormDataToObj.getParameter("new-login-form").then(obj => {
                        obj['userId'] = $("#newUserId").val()
                        obj['userEmail'] = $("#userEmail").val()
                        obj['role'] = 'ROLE_USER'

                        Http.post(`/front/api/user`, obj).then((res) => {
                            if(res['httpStatus'] === 200) {
                                Util.alert("회원가입을 성공했습니다.").then(() => {
                                    window.location.href = '/login'
                                })
                            }else {
                                Util.alert("회원가입을 실패했습니다.", 'w', 'w')
                            }
                        })
                    })
                }
            }
        })
    })

    newPassword.on('input', chckValidate)
    newPasswordChk.on('input', chckValidate)

})

/* 회원가입 -> 비번,비번확인 일치 validation */
function chckValidate(){
    const passwordVal = newPassword.val()
    const passwordChkVal = newPasswordChk.val()

    if(passwordVal && passwordChkVal) {
        if(passwordVal === passwordChkVal) newPasswordChk.removeClass('is-invalid')
        else newPasswordChk.addClass('is-invalid')
    }
}