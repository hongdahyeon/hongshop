$(document).ready(function(e){

    table
        .get("/api/users")
        .headerBottom()
        .usePagination()
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("userId").title("사용자Id").width("10%").left())
        .add(new Column("userNonLocked").title(`계정정지<br/> 여부`).width("5%").center().formatter(function(cell) {
            const rowData = cell.getData()
            if(!rowData['userNonLocked']) return `<a href="#" class="user-is-unlocked" data-num="${rowData.userId}" onclick="userLocked(this)">정지</a>`
            else return `<a>-</a>`
        }))
        .add(new Column("role").title("권한").width("10%").center())
        .add(new Column("userName").title("이름").width("10%").left())
        .add(new Column("userEmail").title("이메일").width("10%").left())
        .add(new Column().title("주소").center().width("30%")
            .add(new Column("city").title("시/도").width("10%").center())
            .add(new Column("street").title("시군구/읍면동").width("10%").center())
            .add(new Column("zipcode").title("우편번호").width("10%").center())
        )
        .add(new Column("id").title("권한 변경").width("10%").center().formatter(function(cell) {
            const rowData = cell.getData();
            return `<select id="userRole-${rowData.id}" class="form-select-sm select" style="width: 150px" data-num="${rowData.id}" data-userId="${rowData.userId}" data-role="${rowData.role}" onChange="changeSelect(this)">
                        <option value="ROLE_ADMIN" ${(rowData.role === 'ROLE_ADMIN' ? 'selected' : '')}>ROLE_ADMIN</option>
                        <option value="ROLE_USER" ${(rowData.role === 'ROLE_USER' ? 'selected' : '')}>ROLE_USER</option>
                        <option value="ROLE_SUPER" ${(rowData.role === 'ROLE_SUPER' ? 'selected' : '')}>ROLE_SUPER</option>
                    </select>`
        }))
        .add(new Column("userEnable").title("계정 <br/> 비활성화하기").width("10%").center().formatter(function(cell){
            const rowData = cell.getData()
            const txt = (!rowData['userEnable']) ? '비활성화 해제' : '비활성화'
            return `<button type="button" class="btn btn-sm btn-outline-success" data-num="${rowData.id}" data-enable="${rowData['userEnable']}" onclick="userEnable(this)">${txt}</button>`
        }))
        .init()

    /* 사용자 계정 정지 풀기 */
    $("#change-userNonLocked").on("click", function(e) {
        const userId = $("#locked-userId").val();
        Http.put(`/api/reset/userNonLocked/${userId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                Util.alert(`${res.message}`).then(() => {
                    table.submit()
                    $("#alertModal").modal('hide')
                })
            }else {
                Util.alert("해당 사용자의 계정 정지 해제에 실패했습니다.", 'w', 'w')
            }
        })
    })

    /* [계정 비활성화] 활성화 -> 비활성화 변경 */
    $("#change-userEnableToDisableModal").on('click', function(e) {
        const form = document.getElementById("save-userEnableToDisableModal-form");
        if (form.checkValidity() === false) {
            form.classList.add("was-validated");
        } else {
            const userId = $("#user-id").val()
            const enableMsg = $("#userEnableToDisableModal-enableMsg").val()
            const obj = {'userId': userId, 'enableMsg': enableMsg}
            Http.post(`/api/user-enable`, obj).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert("해당 사용자의 계정이 비활성화 되었습니다.").then(() => {
                        window.location.href = '/manager/user'
                    })
                }
            })
        }
    })

    /* [계정 활성화] 비활성화 -> 활성화 변경 */
    $("#change-userDisableToEnableModal").on('click', function(e) {
        const enableId = $("#enable-id").val()
        Http.put(`/api/user-enable/${enableId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                Util.alert("해당 사용자의 계정이 활성화 되었습니다.").then(() => {
                    window.location.href = '/manager/user'
                })
            }
        })
    })
})

/* 사용자 계정 활성화 및 비활성화 */
function userEnable(This) {
    const userId = This.getAttribute("data-num")
    const enable = This.getAttribute("data-enable")

    if(enable === 'true') {
        $("#user-id").val(userId)
        $("#userEnableToDisableModal").modal('show')
    }else {
        Http.get(`/api/user-enable/${userId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                $("#enable-id").val(res.message['enableId'])
                $("#enableMsgRead").val(res.message['enableMsg'])
                $("#userDisableToEnableModal").modal('show')
            }
        })
    }
}

/* 사용자 권한 바꾸기 이벤트 */
function changeSelect(selectElement) {
    const id = selectElement.getAttribute("data-num")
    const userId = selectElement.getAttribute("data-userId")
    const userRole = selectElement.getAttribute("data-role")
    const chanageUserRole = selectElement.options[selectElement.selectedIndex].value

    Util.confirm(`${userId} 사용자의 권한 정보를 바꾸시겠습니까?`).then((isOk) => {
        if(!isOk) $(`#userRole-${id}`).val(userRole)
        else {
            const obj = {'role': chanageUserRole}
            Http.put(`/api/user/${id}/role`, obj).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(res.message).then(() => {
                        table.submit()
                    })
                }else {
                    Util.alert("해당 사용자의 권한 정보 변경에 실패하였습니다.").then(() => {
                        $(`#userRole-${id}`).val(userRole)
                    })
                }
            })
        }
    })
}

/* 사용자 계정 정지 풀기 */
function userLocked(This){
    const userId =This.getAttribute("data-num")
    $("#locked-userId").val(userId)
    $("#alertModal").modal('show')
}