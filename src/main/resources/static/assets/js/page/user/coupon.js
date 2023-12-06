$(document).ready(function() {
    /* 쿠폰 요청을 위한 모달 테이블 -> row 클릭시, 해당 체크박스 클릭 */
    $('.clickable-row').click(function(event) {
        if (event.target.type !== 'checkbox') {
            const checkbox = $(this).find('.request-chk');
            checkbox.prop('checked', !checkbox.prop('checked'));
        }
    });

    /* 내가 요청한 쿠폰 리스트 보는 테이블 -> row 클릭시, 해당 체크박스 클릭 */
    $('.clickable-del-row').click(function(event) {
        if (event.target.type !== 'checkbox') {
            const checkbox = $(this).find('.request-del-chk');
            checkbox.prop('checked', !checkbox.prop('checked'));
        }
    });
})

/* 자신이 요청한 쿠폰 리스트 조회 */
$("#request-coupon-lst").on('click', function(e){
    $("#get-request-coupon-modal").modal('show')
})

/* 쿠폰 요청하기 버튼 클릭 이벤트 */
$("#request-coupon").on('click', function(e) {
    $("#choose-coupon-modal").modal('show')
})

/* 쿠폰 요청하기 (쿠폰 요청 모달에서) 버튼 클릭 이벤트 */
$("#choose-coupon-btn").on('click', function(e) {
    const checked = $('.request-chk:checked')
    const lst = checked.map(function(){              // 요청 id
        return $(this).val()
    }).get();

    if(lst.length === 0) Util.alert('요청할 쿠폰을 선택해주세요.', 'w', 'w')
    else {
        const obj = {'couponId': lst}

        Http.post(`/api/coupon-request-all`, obj).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`총 ${res.message}개의 쿠폰이 요청되었습니다.`).then(() => {
                    window.location.href = '/user/coupon'
                })
            } else {
                Util.alert("쿠폰 요청에 실패했습니다.", 'w', 'w')
            }
        })
    }
})

/* 내가 요청한 쿠폰 리스트 모달 테이블 -> 클릭된 row들 삭제 : 내가 요청한 쿠폰들이 승인되기 전에 삭제하는 부분 */
$("#delete-request-coupon-btn").on('click', function(e) {
    const checked = $('.request-del-chk:checked')
    const lst = checked.map(function(){              // 요청 id
        return $(this).val()
    }).get();

    if(lst.length === 0) Util.alert('삭제할 요청을 선택해주세요.', 'w', 'w')
    else {
        const queryString = '?ids=' + lst.join('&ids=');

        Http.delete(`/api/coupon-delete-several${queryString}`).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`총 ${res.message}개를 삭제했습니다.`).then(() => {
                    window.location.href = `/user/coupon`
                })
            } else {
                Util.alert("쿠폰 요청 삭제에 실패했습니다.", 'w', 'w')
            }

        })
    }
})

/* 쿠폰 삭제 : 내가 요청한 쿠폰들이 승인이 된 뒤에 삭제하는 부분 */
$(".coupon-delete-btn").on("click", function(){
    const couponHasId = $(this).val()
    const useAt = $(this).attr('data-delete')

    const msg = (useAt === 'N') ? '해당 쿠폰은 아직 사용 전입니다. <br/> 해당 쿠폰을 삭제하시겠습니까?' : '해당 쿠폰을 삭제하시겠습니까?'
    Util.confirm(`${msg}`).then((isOk) => {
        if(isOk) {
            Http.delete(`/api/coupon-has/${couponHasId}`).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(`${res.message}`).then(() => {
                        window.location.href = `/user/coupon`
                    })
                }else {
                    Util.alert("해당 쿠폰 삭제에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
})