$(document).ready(function() {
    /* 사용자 쿠폰 발급 유저 테이블 -> row 클릭시, 해당 체크박스 클릭 */
    $('.clickable-row').click(function(event) {
        if (event.target.type !== 'checkbox') {
            const checkbox = $(this).find('.user-chk');
            checkbox.prop('checked', !checkbox.prop('checked'));
        }
    });
})

/* 새로운 쿠폰 등록하기 */
$("#new-coupon").on('click', function(e){
    $("#new-coupon-modal").modal('show')
})

/* 지급하기 버튼 클릭 이벤트 */
$(".give-coupon-user").on('click', function(e){
    chooseCouponId = $(this).val()
    $("#choose-user-modal").modal('show')
})

/* 쿠폰 요청 승인하기 버튼 클릭 이벤트 */
$(".approve-request-user").on('click', function(e) {
    const couponId = $(this).val()
    requestCouponId = couponId
    Http.get(`/api/coupon-request-by/${couponId}`).then((res) => {
        if(res['httpStatus'] === 200) {
            drawRequestTable(res.message)           // 쿠폰 요청 승인 테이블 만들기
            $("#request-user-modal").modal('show')
        }
    })
})

/* 요청 승인 모달 테이블 만들기 */
function drawRequestTable(value){
    const dom = $("#request-user-tbody")
    dom.empty()

    if(value.length !== 0) {
        value.forEach((data, i) => {
            const body = `<tr class="clickable-row-request">
                                <td style="width: 5%">
                                    <div class="form-check" style="text-align: center; display: inline-block;">
                                        <input class="form-check-input user-request-chk" type="checkbox" value="${data['hongCouponRequestId']}" data-user="${data['userLongId']}" id="chbox-${data['hongCouponRequestId']}">
                                        <label class="form-check-label" for="chbox-${data['hongCouponRequestId']}"></label>
                                    </div>
                                </td>
                                <td style="width: 10%">${data['userId']}</td>
                                <td style="width: 10%">${data['userName']}</td>
                            </tr>`

            const row = $(body)

            row.click(function (event) {                                     // row 클릭 시, 해당 체크박스 클릭 이벤트
                if (event.target.type !== 'checkbox') {
                    const checkbox = row.find('.user-request-chk');
                    checkbox.prop('checked', !checkbox.prop('checked'));
                }
            });

            dom.append(row)
        })
    }else {
        const body = `<tr>
                                <td colspan="3">사용자의 요청정보가 없습니다.</td>
                            </tr>`
        dom.append(body)
    }
}

/* 요청승인 모달 테이블 -> 승인 버튼 클릭 이벤트  */
$("#request-coupon-btn").on('click', function(e){
    const checked = $('.user-request-chk:checked')
    const lst = checked.map(function(){              // 요청 id
        return $(this).val()
    }).get();

    const user = checked.map(function(){             // 요청한 사용자 id
        return $(this).data('user')
    }).get();

    if(lst.length === 0) Util.alert('승인할 사용자 요청을 선택하세요.', 'w', 'w')
    else {
        const obj = {'couponId': requestCouponId, 'requestId': lst, 'userId': user}
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
    const checked = $('.user-chk:checked')
    let lst = checked.map(function(){
        return $(this).val()
    }).get();

    if(lst.length === 0) Util.alert('쿠폰을 발급할 사용자를 선택하세요.', 'w', 'w')
    else {
        const obj = {'couponId': chooseCouponId, 'userId': lst}      // 체크된 사용자들에게 쿠폰 발급
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

/* 쿠폰 삭제하기 */
$(".coupon-delete-btn").on('click', function(e) {
    const couponId = $(this).val()

    Util.confirm('해당 쿠폰을 삭제하시겠습니까?').then((isOk) => {
        if(isOk) {
            Http.delete(`/api/coupon/${couponId}`).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(`${res.message}`).then(() => {
                        window.location.href = `/manager/coupon`
                    })
                }else {
                    Util.alert("삭제에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
})

/* 쿠폰 수정하기 버튼 클릭 이벤트 */
$(".coupon-update-btn").on('click', function(e){
    const couponId = $(this).val()

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
})

/* 쿠폰 등록하기 모달 닫기 이벤트 */
$("#cancel-new-coupon-btn, #close-new-coupon-modal-btn").on("click", function(e) {
    Util.confirm("작성중인 쿠폰은 저장되지 않습니다. <br/> 쿠폰작성을 취소하시겠습니까?").then((isOk) => {
        if(isOk) {
            $("#new-couponName").val('')
            $("#new-couponRate").val('')
            $("#new-startDate").val('')
            $("#new-endDate").val('')

            const form = document.getElementById("new-coupon-form")
            form.classList.remove("was-validated")      // validation 지우기
            $("#new-coupon-modal").modal('hide')
        }
    })
})

/* 쿠폰 수정하기 모달 닫기 이벤트 */
$("#cancel-update-coupon-btn, #close-update-coupon-modal-btn").on("click", function(e){
    Util.confirm(`수정중이던 쿠폰은 저장되지 않습니다. <br/> 쿠폰수정을 취소하시겠습니까?`).then((isOk) => {
        if(isOk) {
            $("#update-couponName").val('')
            $("#update-couponRate").val('')
            $("#update-startDate").val('')
            $("#update-endDate").val('')

            const form = document.getElementById("update-coupon-form")
            form.classList.remove("was-validated")      // validation 지우기
            $("#update-coupon-modal").modal('hide')
        }
    })
})

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
                                window.location.href = '/manager/coupon'
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
                                window.location.href = `/manager/coupon`
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