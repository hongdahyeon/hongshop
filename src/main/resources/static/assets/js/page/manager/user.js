$(document).ready(function(e){

    table
        .get("/api/users")
        .headerBottom()
        .add(new Column("index").title("#").width("10%").center())
        .add(new Column("userId").title("사용자Id").width("10%").left())
        .add(new Column("userNonLocked").title("계정정지 여부").width("10%").center().formatter(function(cell) {
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
        .add(new Column("id").title("권한 변경").width("20%").center().formatter(function(cell) {
            const rowData = cell.getData();
            return `<select id="userRole-${rowData.id}" class="form-select-sm select" style="width: 150px" data-num="${rowData.id}" data-userId="${rowData.userId}" data-role="${rowData.role}" onChange="changeSelect(this)">
                        <option value="ROLE_ADMIN" ${(rowData.role === 'ROLE_ADMIN' ? 'selected' : '')}>ROLE_ADMIN</option>
                        <option value="ROLE_USER" ${(rowData.role === 'ROLE_USER' ? 'selected' : '')}>ROLE_USER</option>
                        <option value="ROLE_SUPER" ${(rowData.role === 'ROLE_SUPER' ? 'selected' : '')}>ROLE_SUPER</option>
                    </select>`
        }))
        .init()

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
})


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
}

/* 사용자 계정 정지 풀기 */
function userLocked(This){
    const userId =This.getAttribute("data-num")
    $("#locked-userId").val(userId)
    $("#alertModal").modal('show')
}