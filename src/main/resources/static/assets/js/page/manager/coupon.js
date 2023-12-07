$(document).ready(function() {

    table
        .get('/api/coupon-chk-deleteAble')
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("couponName").title("쿠폰 이름").width("10%").left())
        .add(new Column("couponRate").title("할인 가격(원)").width("10%").left().formatter(function(cell) { return Util.priceString(cell.getValue()) }))
        .add(new Column("useAt").title("사용여부").width("5%").left().formatter(function(cell) { return Util.strYN(cell.getValue(), "사용", "미사용") }))
        .add(new Column("startDate").title("사용기간").width("10%").center().formatter(function(cell){
            const rowData = cell.getData();
            return `${rowData['startDate']} ~ ${rowData['endDate']}`
        }))
        .add(new Column("couponId").title("지급하기").width("10%").center().formatter(function(cell) {
            const rowData = cell.getData();
            return `<button type="button" class="btn btn-sm btn-outline-info give-coupon-user" data-num="${rowData['couponId']}" onclick="giveCoupon(this)">지급하기</button>`
        }))
        .add(new Column("couponId").title("요청승인").width("10%").center().formatter(function(cell) {
            const rowData = cell.getData();
            return `<button type="button" class="btn btn-sm btn-outline-success approve-request-user" data-num="${rowData['couponId']}" onclick="okUserCoupon(this)">승인하기</button>`
        }))
        .add(new Column("couponId").title("수정 및 삭제").width("20%").center().formatter(function(cell){
            const rowData = cell.getData();
            return `<button type="button" class="btn btn-sm btn-outline-secondary coupon-update-btn" data-num="${rowData['couponId']}" onclick="updateCoupon(this)">수정</button>
                    <button type="button" class="btn btn-sm btn-outline-danger coupon-delete-btn" ${(!rowData['userIsEmpty']) ? 'disabled' : ''} data-num="${rowData['couponId']}" onclick="deleteCoupon(this)">삭제</button>`
        }))
        .init()

    chooseUserTable
        .get(`/api/user-coupon`)
        .add(new Column("index").title("#").center().width("10%"))
        .add(new Column("userId").title("사용자Id").left().width("20%"))
        .add(new Column("userName").title("사용자 이름").left().width("20%"))
        .selectable()
        .init()

    requestUserTable
        .add(new Column("index").title("#").center().width("10%"))
        .add(new Column("userId").title("사용자Id").left().width("20%"))
        .add(new Column("userName").title("사용자 이름").left().width("20%"))
        .selectable()
})

/* 지급하기 버튼 클릭 이벤트 */
function giveCoupon(This) {
    chooseCouponId = This.getAttribute("data-num")
    $("#choose-user-modal").modal('show')
}

/* 쿠폰 요청 승인하기 버튼 클릭 이벤트 */
function okUserCoupon(This) {
    const couponId = This.getAttribute("data-num")
    requestCouponId = couponId

    requestUserTable
        .get(`/api/coupon-request-by/${couponId}`)
        .init()

    $("#request-user-modal").modal('show')
}

/* 쿠폰 수정하기 버튼 클릭 이벤트 */
function updateCoupon(This) {
    const couponId = This.getAttribute("data-num")

    Http.get(`/api/coupon/${couponId}`).then((res) => {
        if(res['httpStatus'] === 200) {
            hongCouponId= res.message['couponId']
            $("#update-couponName").val(res.message['couponName'])
            $("#update-couponRate").val(res.message['couponRate'])
            $(`#update-useAt-${res.message['useAt']}`).prop('checked', true)
            $("#update-startDate").val(res.message['startDate'])
            $("#update-endDate").val(res.message['endDate'])
            $("#update-coupon-modal").modal('show')              // 쿠폰 수정 모달 띄우기
        }
    })
}

/* 쿠폰 삭제하기 */
function deleteCoupon(This) {
    const couponId = This.getAttribute("data-num")

    Util.confirm('해당 쿠폰을 삭제하시겠습니까?').then((isOk) => {
        if(isOk) {
            Http.delete(`/api/coupon/${couponId}`).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(`${res.message}`).then(() => {
                        table.submit()
                    })
                }else {
                    Util.alert("삭제에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
}

/* 새로운 쿠폰 등록하기 */
$("#new-coupon").on('click', function(e){
    $("#new-coupon-modal").modal('show')
})

/* 요청승인 모달 테이블 -> 승인 버튼 클릭 이벤트  */
$("#request-coupon-btn").on('click', function(e){
    const selectedRows = requestUserTable.getSelectedRows()
    const idLst = selectedRows.map(item => item['hongCouponRequestId'])
    const user = selectedRows.map(item => item['userLongId'])

    if(idLst.length === 0) Util.alert('승인할 사용자 요청을 선택하세요.', 'w', 'w')
    else {
        const obj = {'couponId': requestCouponId, 'requestId': idLst, 'userId': user}
        Http.put(`/api/coupon-request-approve`, obj).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`${res.message}명의 요청을 승인했습니다.`).then(() => {
                    window.location.href = '/manager/coupon'
                })
            } else {
                Util.alert(`죄송합니다. 요청 승인에 실패했습니다.`, 'w', 'w')
            }
        })
    }
})

/* 사용자 쿠폰 발급 모달 창 -> 쿠폰발급 버튼 클릭 이벤트 */
$("#give-coupon-btn").on('click', function(e) {
    const selectedRows = chooseUserTable.getSelectedRows()
    const idList = selectedRows.map(item => item.id)

    if(idList.length === 0) Util.alert('쿠폰을 발급할 사용자를 선택하세요.', 'w', 'w')
    else {
        const obj = {'couponId': chooseCouponId, 'userId': idList}      // 체크된 사용자들에게 쿠폰 발급
        Http.post(`/api/coupon-has-user`, obj).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`총 ${res.message}명에게 ${chooseCouponId}번 쿠폰이 지급되었습니다.`).then(() => {
                    window.location.href = '/manager/coupon'
                })
            } else {
                Util.alert(`죄송합니다. 쿠폰 발급에 실패했습니다.`, 'w', 'w')
            }
        })
    }
})


/* 쿠폰 등록하기 모달 닫기 이벤트 */
$("#cancel-new-coupon-btn, #close-new-coupon-modal-btn").on("click", function(e) {
    Util.confirm("작성중인 쿠폰은 저장되지 않습니다. <br/> 쿠폰작성을 취소하시겠습니까?").then((isOk) => {
        if(isOk) clearNewCouponForm()
    })
})

/* 쿠폰 등록 폼 clear */
function clearNewCouponForm(){
    newCouponForm.val('')
    const form = document.getElementById("new-coupon-form")
    form.classList.remove("was-validated")
    $("#new-coupon-modal").modal('hide')
}


/* 쿠폰 수정하기 모달 닫기 이벤트 */
$("#cancel-update-coupon-btn, #close-update-coupon-modal-btn").on("click", function(e){
    Util.confirm(`수정중이던 쿠폰은 저장되지 않습니다. <br/> 쿠폰수정을 취소하시겠습니까?`).then((isOk) => {
        if(isOk) clearUpdateCouponForm()
    })
})

/* 쿠폰 수정 폼 clear */
function clearUpdateCouponForm(){
    updateCouponForm.val('')
    const form = document.getElementById("update-coupon-form")
    form.classList.remove("was-validated")
    $("#update-coupon-modal").modal('hide')
}

/* 쿠폰 등록하기 */
window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("new-coupon-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();
            FormDataToObj.getParameter("new-coupon-form").then(obj => {
                const start = new Date(obj['startDate'])
                const end = new Date(obj['endDate'])

                if(start > end) Util.alert("쿠폰 사용 종료일이 시작일보다 먼저 올수는 없습니다.", 'w', 'w')       // 쿠폰 사용 시작일보다 종료일이 앞서는 경우, 막기
                else {
                    Http.post(`/api/coupon`, obj).then((res) => {
                        if (res['httpStatus'] === 200) {
                            Util.alert(`쿠폰이 추가되었습니다.`).then(() => {
                                table.submit()
                                clearNewCouponForm()
                            })
                        } else {
                            Util.alert(`쿠폰 등록에 실패했습니다.`, 'w', 'w')
                        }
                    })
                }
            })
        }
    });
});

/* 쿠폰 수정하기 */
window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("update-coupon-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();
            FormDataToObj.getParameter("update-coupon-form").then(obj => {
                const start = new Date(obj['startDate'])
                const end = new Date(obj['endDate'])

                if(start > end) Util.alert("쿠폰 사용 종료일이 시작일보다 먼저 올수는 없습니다.", 'w', 'w')  // 쿠폰 사용 시작일보다 종료일이 앞서는 경우, 막기
                else {
                    Http.put(`/api/coupon/${hongCouponId}`, obj).then((res) => {
                        if (res['httpStatus'] === 200) {
                            Util.alert("쿠폰 정보를 변경하였습니다.").then(() => {
                                table.submit()
                                clearUpdateCouponForm()
                            })
                        } else {
                            Util.alert("쿠폰 변경에 실패했습니다.", 'w', 'w')
                        }
                    })
                }
            })
        }
    });
});