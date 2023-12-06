/* 체크박스 전체 선택/해제 버튼 */
$("#chk-all").on("click", function(e) {
    if(cart.length === 0) Util.alert(`아직 장바구니가 비어있습니다. <br/> 상품을 먼저 담아주세요.`, 'w', 'w')
    else {
        const checkboxes = $(".image-checkbox")
        const checkedLen = getCheckedChkBox().length
        const chkTF = (checkedLen === cart.length) ? false : true
        const mssg = (checkedLen === cart.length) ? '✔ 전체 선택' : '전체선택 해제'

        $("#chk-all").text(mssg)
        checkboxes.each(function () {
            $(this).prop('checked', chkTF);
        });
    }
})

/* 장바구니 재고 변경하기 버튼 클릭 이벤트 */
$(".chng-cart").on("click", function(e) {
    const cartId = $(this).val()
    const productId = $(this).attr('data-product')
    const productStock = $(this).attr('data-stock')
    const productPrice = $(this).attr('data-price')
    const cartCnt = $(`#order-${cartId}`).attr('data-order')
    const productName = $(`#name-${cartId}`).text()
    const productImg = $(`#img-${cartId}`).attr('src')

    if(productStock == 0) Util.alert("현재 해당 상품의 재고가 없어서 주문 및 변경이 불가능합니다.", 'w', 'w')
    else {
        // 모달 열기
        const price = Util.priceString(productPrice)
        $("#hongCartId").val(cartId)
        $("#hongProductId").val(productId)
        $("#product-name").text(productName)
        $("#product-price").text(`상품 가격: ${price}원`)
        $("#product-stock").text(`상품 재고: ${productStock} 개`)
        $("#product-img").attr("src", productImg)
        $("#cart-cnt").text(`현재 담긴 개수: ${cartCnt} 개`)

        $("#product-order-num").attr('max', productStock)
        $("#product-invalid-feedback").text(`최대 주문가능 개수는 ${productStock}개 입니다.`)
        $("#cart-change-modal").modal('show')
    }
})

/* 장바구니 정보 변경 모달창 닫기 */
$("#close-cartChangeModal, #close-cart-change-modal-btn").on("click", function(e){
    $("product-order-num").val('')
    const form = document.getElementById("cart-change-form")
    form.classList.remove("was-validated")      // validation 지우기
    $("#cart-change-modal").modal('hide')
})

/* 장바구니 정보 변경 모달창 닫힐때 -> validation 초기화 및 주문개수 초기화 */
$("#cart-change-modal").on('hidden.bs.modal', function (e) {
    $("#product-order-num").val('')
    const form = document.getElementById("cart-change-form")
    form.classList.remove("was-validated")      // validation 지우기
});

/* 장바구니 재고 변경하기 */
window.addEventListener("load", function() {
    const form = document.getElementById("cart-change-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("cart-change-form").then((obj) => {
                Util.confirm("해당 정보를 변경하시겠습니까?").then((isOk) => {
                    if(isOk) {
                        const OBJ = {'cartCnt': obj['product-order-num'], hongProductId: obj['hongProductId']}

                        Http.put(`/api/cart/${obj['hongCartId']}`, OBJ).then((res) => {
                            if (res['httpStatus'] === 200) {
                                Util.alert(`${res.message}`).then(() => {
                                    window.location.href = '/user/cart'
                                })
                            } else {
                                Util.alert("장바구니 정보 수정에 실패하였습니다.", 'w', 'w')
                            }
                        })
                    }
                })
            })
        }
    })
})


/* 이미지버튼 클릭시 -> 해당 체크박스 클릭 */
$(".img-btn").on("click", function(e) {
    const cartId = $(this).attr('data-num')
    const checkbox =  $(`#chk-${cartId}`)
    checkbox.prop('checked', !checkbox.prop('checked'))
})

/* 장바구니 삭제 버튼 */
$("#delete-btn").on("click", function(e) {
    const ids = getCheckedChkBox()

    if(ids.length === 0) Util.alert("삭제할 상품을 선택해주세요.", 'w', 'w')
    else {
        Util.confirm("해당 상품들을 삭제하시겠습니까?").then((isOk) => {
            if(isOk) {
                const queryString = '?ids=' + ids.join('&ids=')
                Http.delete(`/api/cart${queryString}`).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            window.location = `/user/cart`
                        })
                    }
                })
            }
        })
    }
})

/* 체크박스 리스트 가져오기 */
function getCheckedChkBox(){
    const checkboxes = $(".image-checkbox:checked")
    const checkedValues = checkboxes.map(function() {
        return $(this).val();
    }).get();
    return checkedValues;
}

/* 재고보다 장바구니개수가 많은 상품이 있는지 체크 */
function canPurchase() {
    const checkboxes = $(".image-checkbox:checked");
    let result = true;

    for (let i = 0; i < checkboxes.length; i++) {
        const able = $(checkboxes[i]).attr("data-able");
        if (able == 'false') {
            result = false
            return false;
        }
    }
    return true;
}

/* 상품 주문하기 */
$("#order-btn").on("click", function(e) {
    const ids = getCheckedChkBox()
    const able = canPurchase()

    if(!able) Util.alert("주문 불가능한 상품이 있습니다. <br/> 확인 부탁드립니다.", 'w', 'w')
    else {
        if (ids.length === 0) Util.alert("주문할 상품을 선택해주세요.", 'w', 'w')
        else {
            const queryString = '?ids=' + ids.join('&ids=');
            Util.confirm("해당 상품들을 주문하시겠습니까?").then((isOk) => {
                if(isOk) window.location.href = `/order/cart${queryString}`
            })
        }
    }
})
