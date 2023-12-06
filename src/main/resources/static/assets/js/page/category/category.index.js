$(document).ready(function(e){
    feather.replace()

    /* 카테고리 nav 클릭에 따른 text 변경 */
    $(".nav-category-link").on("click", function(e){
        const categoryName = $(this).text();
        $("#category-name").text(categoryName)
    })

    /* 상품구매 모달창 닫기 */
    $("#close-productModal, #close-product-purchase-modal-btn").on("click", function(e){
        $("#product-order-num").val('')
        const form = document.getElementById("product-purchase-form")
        form.classList.remove("was-validated")      // validation 지우기
        $("#product-purchase-modal").modal('hide')
    })

    /* 상품구매 모달창 닫힐때 -> validation 초기화 및 주문개수 초기화 */
    $("#product-purchase-modal").on('hidden.bs.modal', function (e) {
        $("#product-order-num").val('')
        const form = document.getElementById("product-purchase-form")
        form.classList.remove("was-validated")      // validation 지우기
    });

    /* alertModal 모달창 닫기 */
    $("#close-alertModal").on("click", function(e){
        $("#alertModal").modal('hide')
    })

    /* alertModal -> [문의글 작성하러가기] 버튼 클릭 이벤트 */
    $("#goto-qna-post").on("click", function(e){
        if(qnaPost) window.location.href = `/bbs/${qnaPost['postTypeId']}`
        else {
            Util.alert(`죄송합니다. <br /> 아직 질의응답 게시판이 없습니다. <br /> 잠시만 기다려주세요.`).then(() => {
                $("#alertModal").modal('hide')
            })
        }
    })

    /* 이미지 버튼 클릭 이벤트 */
    $(".img-btn").on("click", function(e){
        const productId = $(this).attr("data-num")
        const productStock = $(this).attr("data-stock")
        const productName = $(`#name-${productId}`).text()
        const productImg = $(`#img-${productId}`).attr('src')
        const productPrice = $(`#price-${productId}`).attr('data-price')

        if(productStock == 0) $("#alertModal").modal('show')
        else {
            const price = Util.priceString(productPrice)
            $("#hongProductId").val(productId)
            $("#product-name").text(productName)                            // 상품 이름
            $("#product-price").text(`가격: ${price} 원`)                    // 상품 가격
            $("#product-stock").text(`재고: ${productStock} 개`)             // 상품 재고
            $("#product-img").attr("src", productImg)                       // 상품 이미지

            $("#product-order-num").attr('max', productStock)                                   // 상품 최대 주문 개수
            $("#product-invalid-feedback").text(`최대 주문가능 개수는 ${productStock}개 입니다.`)    // 상품 주문 form -> validation text
            $("#product-purchase-modal").modal('show')
        }
    })
})

/* 상품 구매하기 form */
window.addEventListener("load", function() {
    const form = document.getElementById("product-purchase-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("product-purchase-form").then((obj) => {

                const submitButtonId = event.submitter.id;

                // 장바구니 담기
                if(submitButtonId === 'into-cart') {
                    Util.confirm("해당 상품을 장바구니에 담으시겠습니까?").then((isOk) => {
                        if(isOk) {
                            Http.get(`/api/find-cart/${obj['hongProductId']}`).then((res) => {
                                if(res['httpStatus'] === 200) {
                                    if(!res.message) Util.alert(`이미 해당 상품이 장바구니에 있습니다. <br/> 확인해주세요.`, 'w', 'w')
                                    else {
                                        const OBJ = {'hongProductId': obj['hongProductId'], 'cartCnt': obj['product-order-num']}
                                        Http.post(`/api/cart`, OBJ).then((res) => {
                                            if (res['httpStatus'] === 200) {
                                                Util.confirm(`해당 물품이 장바구니에 담겼습니다. <br/> 장바구니를 보러가시겠습니까?`).then((isOk) => {
                                                    if (isOk) window.location.href = '/user/cart'
                                                    else $("#product-purchase-modal").modal('hide')
                                                })
                                            } else {
                                                Util.alert("해당 물품을 장바구니에 담기에 실패했습니다.", 'w', 'w')
                                            }
                                        })
                                    }
                                }
                            })
                        }
                    })
                } else if(submitButtonId === 'into-purchase') {
                    // 구매하기
                    Util.confirm("해당상품을 구입하시겠습니까?").then((isOk) => {
                        if(isOk) window.location.href = `/order/shop?productId=${obj['hongProductId']}&orderNum=${obj['product-order-num']}`
                    })
                }
            })
        }
    })
})