$(document).ready(function(e) {
    window.history.replaceState({}, document.title, window.location.pathname);
    if(product !== null) Util.alert("선택하신 상품에 대한 정보 및 주소정보를 확인해주세요.", 'w', 'w')       // 만일 장바구니 선택 없이 넘어오게되면 내보내기
    else {
        Util.alert("잘못된 접근입니다.", 'w', 'w').then(() => {
            window.location.href = "/"
        })
    }
})

// change-address-btn: 배송 주문 주소 정보 변경하기 활성화
// update-address-cancel: 배송 주문 주소 정보 변경하기 활성화 취소
$("#change-address-btn, #update-address-cancel").on("click", function(e) {
    addressChanging(addressChange)
})

/* 상품 구경 화면 돌아가기 버튼 */
$("#goto-shop").on("click", function(e) {
    window.location.href = `/category/new`
})

/* 쿠폰 라디오 버튼 클릭 이벤트 -> 클릭에 따른 총 가격값 변동 */
$(".chk-coupon-btn").on("change", function(e) {
    const rate = $('input[name=hongCouponHasId]:checked').data('num');
    setPurchasePrice(rate)
})

/* 쿠폰 사용 초기화 */
$("#coupon-chk-reset").on("click", function(e){
    $('.form-check-input').prop('checked', false);
    setPurchasePrice()
})

/* 총 가격 및 할인 가격 text setting */
function setPurchasePrice(price = 0){
    $("#totalPrice-before").text(Util.priceString(totalPrice))
    $("#coupon-price").text(Util.priceString(price))
    $("#totalPrice").text(Util.priceString(totalPrice - price))
}

/* 결제하기 버튼 이벤트 */
$("#purchase-btn").on("click", function(e) {
    const orders = []
    const city = $("#city").val()
    const street = $("#street").val()
    const zipcode = $("#zipcode").val()

    const obj = {'userId': userId, 'city': city, 'street': street, 'zipcode': zipcode, 'hongProductId': product['productId'], 'orderCnt': orderNum}
    if($('.form-check-input').is(':checked')) obj['hongCouponHasId'] =  $('input[name=hongCouponHasId]:checked').val()

    Util.confirm("해당 상품들을 결제하시겠습니까?").then((isOk) => {
        if(isOk) {
            Http.post(`/api/order-from-shop`, obj).then((res) => {
                if (res['httpStatus'] === 200) {
                    Util.alert(`${res.message}번으로 주문되었습니다.`).then(() => {
                        window.location.href = '/user/order'
                    })
                } else {
                    Util.alert("주문에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
})

/* 배송 주문 주소 input-text disable, 버튼 show & hide */
function addressChanging(chk){
    if(chk) {
        addressInput.prop('disabled', true)
        addressChange = false
        updateAddressBtn.hide()
        changeAddressBtn.show()
    }else {
        addressInput.prop('disabled', false)
        addressChange = true
        updateAddressBtn.show()
        changeAddressBtn.hide()
    }
}

/* 주소 정보 수정하기 (update) */
window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("update-address-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();

            Util.confirm("현재 주소를 사용자 주소 정보로 수정하시겠습니까?").then((isOk) => {
                if(isOk) {
                    FormDataToObj.getParameter("update-address-form").then(obj => {

                        Http.put(`/api/user`, obj).then((res) => {
                            if (res['httpStatus'] === 200) {
                                Util.alert("주소 정보를 변경하였습니다.").then(() => {
                                    addressChanging(true)
                                })
                            } else {
                                Util.alert("주소 변경에 실패하였습니다.", 'w', 'w')
                            }
                        })
                    })
                } else {
                    addressChanging(true)
                }
            })
        }
    });
});