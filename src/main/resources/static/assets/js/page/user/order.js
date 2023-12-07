$(document).ready(function(){
    table
        .get('/api/order-with-deliver')
        .headerBottom()
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("orderStatStr").title("주문 상태").width("10%").left())
        .add(new Column("orderDate").title("주문 날짜").width("10%").left().formatter(function(cell) { return Util.DateSubString(cell.getValue()) }))
        .add(new Column("useCoupon").title("쿠폰 사용 여부").width("10%").left().formatter(function(cell){
            const rowData = cell.getData()
            return (rowData['useCoupon']) ? `사용 <br/> ${Util.priceString(rowData['couponRate'])}` : '미사용'
        }))
        .add(new Column("orderId").title("주문 상세 정보 보기").width("10%").center().formatter(function(cell) {
            const rowData = cell.getData()
            return `<button type="button" class="btn btn-sm btn-outline-success" data-num="${rowData['orderId']}" onclick="getOrderDetail(this)">보기</button>`
        }))
        .add(new Column("").width("2%"))
        .add(new Column("deliverStatStr").title("배송상태").width("5%").left())
        .add(new Column().title("주소").center().width("30%")
            .add(new Column("address").title("시/도").center().width("10%").formatter(function(cell) { return cell.getValue()['city'] }))
            .add(new Column("address").title("시군구 <br/> 읍면동").center().width("10%").formatter(function(cell) { return cell.getValue()['street'] }))
            .add(new Column("address").title("우편번호").center().width("10%").formatter(function(cell) { return cell.getValue()['zipcode'] }))
        )
        .add(new Column("deliverId").title("배송지 <br/> 변경하기").center().width("10%").formatter(function(cell) {
            const rowData = cell.getData()
            return ` <button type="button" class="btn btn-outline-primary chg-address" data-deliver="${rowData['deliverId']}" data-order="${rowData['orderId']}" data-city="${rowData['address']['city']}" data-status="${rowData['deliverStatus']}"
                               data-street="${rowData['address']['street']}" data-zipcode="${rowData['address']['zipcode']}" onclick="changeAddress(this)"> ${(rowData['deliverStatus'] === 'DELIVERED') ? '리뷰작성' : '변경'} </button>`
        }))
        .init()

    orderDetailTable
        .add(new Column("index").title("#").center().width("10%"))
        .add(new Column("productName").title("상품명").left().width("20%"))
        .add(new Column("orderCnt").title("주문개수").center().width("20%").formatter(function(cell){ return `${cell.getValue()} 개` }))
        .add(new Column("orderPrice").title("총 가격").left().width("20%").formatter(function(cell){ return `${Util.priceString(cell.getValue())} 원` }))

})

/* 주문 상세 정보 보기 버튼 클릭 이벤트 */
function getOrderDetail(This) {
    const orderId = This.getAttribute("data-num")

    orderDetailTable
        .get(`/api/order-detail/${orderId}`)
        .init()
    $("#order-detail-modal").modal('show')
}

/* 주소변경 버튼 클릭 이벤트 , 리뷰작성 버튼 클릭 이벤트 */
function changeAddress(This) {
    const deliverId = This.getAttribute("data-deliver")
    const orderId = This.getAttribute("data-order")
    const city = This.getAttribute("data-city")
    const street = This.getAttribute("data-street")
    const zipcode = This.getAttribute("data-zipcode")
    const deliverStatus = This.getAttribute("data-status")

    // 만일 배송완료된 주문건이라면 -> 해당 주문에 대해 리뷰작성이 가능하다.
    // 이때 이미 그 주문건에 대해 리뷰가 작성됐다면 더이상 리뷰 작성이 불가능하다.
    if(deliverStatus === 'DELIVERED') {
        Http.get(`/api/user-order-review/${orderId}`).then((res) => {
            if(!res.message) Util.alert("이미 작성된 리뷰가 있습니다.", 'w', 'w')
            else {
                setRadioDiv(orderId)                    // 주문 -> 상품건들에 대한 라디오 버튼 div 만들기
                $("#review-write-modal").modal('show')
            }
        })
    } else if(deliverStatus !== 'AWAIT') Util.alert("배송지의 경우 대기 상태의 경우에만 변경이 가능합니다.", 'w', 'w')
    else {
        $("#city").val(city)
        $("#street").val(street)
        $("#zipcode").val(zipcode)
        $("#deliverId").val(deliverId)
        $("#change-address-modal").modal('show')
    }
}

/* 주소 수정 모달창 닫기 */
$("#close-addressChangeModal, #close-change-address-modal-btn").on("click", function(e) {
    addressForm.val('')
    const form = document.getElementById("change-address-form")
    form.classList.remove("was-validated")      // validation 지우기
    $("#change-address-modal").modal('hide')
})

/* 상품구매 모달창 닫힐때 -> validation 초기화 및 주문개수 초기화 */
$("#change-address-modal").on('hidden.bs.modal', function (e) {
    addressForm.val('')
    const form = document.getElementById("change-address-form")
    form.classList.remove("was-validated")      // validation 지우기
});

/* 리뷰 작성폼 -> 주문상품 건 라디오 버튼 div 만들기
*  > 이때 이미 리뷰가 작성된 상품건에 대해선 선택 불가
* */
function setRadioDiv(orderId){
    const dom = $("#choose-product-radio-div")
    dom.empty()

    Http.get(`/api/order-review/${orderId}`).then((res) => {
        if(res['httpStatus'] === 200) {
            const datas = res.message

            datas.forEach((data, i) => {
                const disableOrNot = (data['reviewChkEmpty']) ? '' : 'disabled'
                const body = `<div class="form-check">
                                            <input class="form-check-input" type="radio" name="hongOrderDetailId" value="${data['orderDetailId']}" id="orderDetailId-${data['orderDetailId']}" ${disableOrNot} required>
                                            <label class="form-check-label" for="orderDetailId-${data['orderDetailId']}">
                                               ${data['productName']}
                                            </label>
                                        </div>`

                dom.append(body)
            })
        }
    })
}

/* 리뷰작성 모달창 닫기 */
$("#close-reviewWriteModal, #close-review-write-modal-btn").on("click", function(e) {
    Util.confirm("작성중인 리뷰는 저장되지 않습니다. <br/> 리뷰작성을 취소하시겠습니까?").then((isOk) => {
        if(isOk) {
            uploadFile.domEmptyAndFileGroupIdNull();
            $("#reviewContent").val('')

            const form = document.getElementById("review-write-form")
            form.classList.remove("was-validated")      // validation 지우기

            $("#review-write-modal").modal('hide')
        }
    })
})

/* 주소 변경하기 form */
window.addEventListener("load", function() {
    const form = document.getElementById("change-address-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("change-address-form").then((obj) => {
                const deliverId = $("#deliverId").val()

                Http.put(`/api/deliver-address/${deliverId}`, obj).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            table.submit()
                            $("#change-address-modal").modal('hide')
                        })
                    }else {
                        Util.alert("해당 배송 주소정보 변경에 실패하였습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

/* 리뷰작성하기 form */
window.addEventListener("load", function() {
    const form = document.getElementById("review-write-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("review-write-form").then((obj) => {
                obj['deleteFile'] = uploadFile.getDeleteFile();
                if(uploadFile.getFileGroupId()) obj['fileGroupId'] = uploadFile.getFileGroupId()

                Http.post(`/api/review`, obj).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.confirm('리뷰작성에 성공하셨습니다. <br/> 리뷰를 보러가시겠습니까?').then((isOk) => {
                            if (isOk) window.location.href = '/user/review'
                            else window.location.href = '/user/order'
                        })
                    } else {
                        Util.alert("리뷰작성에 실패했습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

$('#btn-upload').on("click", function (e) {
    e.preventDefault();
    $('#fileUpload').click();
});

$("#fileUpload").on("change", function (e) {
    const selectedFile = e.target.files[0];
    const fileName = selectedFile.name

    const formData = new FormData();
    formData.append("file", selectedFile);
    if(uploadFile.getFileGroupId()) formData.append("fileGroupId", uploadFile.getFileGroupId());


    Http.filePost("/api/uploadFile", formData).then((res) => {
        if(res['httpStatus'] === 200) {
            uploadFile.setFileGroupId(res.message.fileGroupId)
            uploadFile.addFileList({'originalFileName': fileName, 'id': res.message.id})
        }
    })
})