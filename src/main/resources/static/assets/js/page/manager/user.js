$(document).ready(function(e){

    $(".user-is-unlocked").on("click", function(e){
        const userId = $(this).attr("data-num")
        $("#locked-userId").val(userId)
        $("#alertModal").modal('show')
    })

    $("#change-userNonLocked").on("click", function(e) {
        const userId = $("#locked-userId").val();
        Http.put(`/api/reset/userNonLocked/${userId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                Util.alert(`${res.message}`).then(() => {
                    window.location.href='/manager/user'
                })
            }else {
                Util.alert("해당 사용자의 계정 정지 해제에 실패했습니다.", 'w', 'w')
            }
        })
    })

    /* alertModal 모달창 닫기 */
    $("#close-alertModal").on("click", function(e){
        $("#alertModal").modal('hide')
    })

    /* 사용자 권한 바꾸기 이벤트 */
    $(".userRole-select").on("change", function(e){
        const id = $(this).attr("data-num")
        const userId = $(this).attr("data-userId")
        const userRole = $(this).attr("data-role")
        const chanageUserRole = $(this).val()

        Util.confirm(`${userId} 사용자의 권한 정보를 바꾸시겠습니까?`).then((isOk) => {
            if(!isOk) $(`#userRole-${id}`).val(userRole)
            else {
                const obj = {'role': chanageUserRole}
                Http.put(`/api/user/${id}/role`, obj).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert(res.message).then(() => {
                            window.location.href='/manager/user'
                        })
                    }else {
                        Util.alert("해당 사용자의 권한 정보 변경에 실패하였습니다.").then(() => {
                            $(`#userRole-${id}`).val(userRole)
                        })
                    }
                })
            }
        })
    })
})