$(document).ready(function() {

    getCategoryWithProduct()

    productUserTable
        .add(new Column("index").title("#").center().width("10%"))
        .add(new Column("userId").title("주문자").left().width("20%"))
        .add(new Column("orderStatus").title("주문상태").left().width("20%"))
        .add(new Column("orderCnt").title("주문개수").left().width("20%").formatter(function(cell) { return `${cell.getValue()} 개` }))
        .add(new Column("orderDate").title("주문날짜").left().width("20%").formatter(function(cell){ return Util.DateSubString(cell.getValue()) }))

    /* [왼쪽] 상품 nav-tab 클릭 */
    $("#navTab").on("click", ".product", function(e) {
        const hongProductId = $(this).attr("data-num")

        document.getElementById("empty-div").style.display = 'none'
        document.getElementById("category-div").style.display = 'none'
        document.getElementById("product-div").style.display = 'block'      // show

        Http.get(`/api/product-check-user/${hongProductId}`).then((res) => {
            if(res['httpStatus'] === 200) {

                // 물품 구매 사용자가 있다면 삭제 버튼 비활성화
                if(!res.message['orderUserEmpty']) $("#delete-product-btn").prop('disabled', true)
                else $("#delete-product-btn").prop('disabled', false)

                uploadFile.domEmptyAndFileGroupIdNull()
                if(res.message['file'] != null) uploadFile.setFileGroupId(res.message['file']['id']).setData(res.message['file']['fileList']).setFiles()

                $("#productName").val(res.message['productName'])                          // 상품 이름
                $("#hongProductId").val(res.message['productId'])                          // 상품 Id
                $("#productCnt").val(res.message['productCnt'])                            // 상품 개수 (변동)
                $("#originProductCnt").val(res.message['productCnt'])                      // 상품 개수 (hidden)
                $("#productPrice").val(res.message['productPrice'])                        // 상품 가격
                $("#productStock").val(res.message['productStock'])                        // 상품 재고
                $(`#newProductYn-${res.message['newProductYn']}`).prop('checked', true)    // 최산 상품 여부
            }
        })
    })

    /* [왼쪽] 카테고리 nav-tab 클릭 */
    $("#navTab").on("click", ".category", function(e) {
        const hongCategoryId = $(this).attr("data-num")

        document.getElementById("empty-div").style.display = 'none'
        document.getElementById("category-div").style.display = 'block'     // show
        document.getElementById("product-div").style.display = 'none'

        Http.get(`/api/category-product/${hongCategoryId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                $("#hongCategoryId").val(res.message['categoryId'])                                        // 카테고리 Id
                $("#orderNum").val(res.message['orderNum'])                                                // 카테고리 순서
                $("#categoryName").val(res.message['categoryName'])                                        // 카테고리 이름
                $("#description").html(res.message['description'])                                         // 카테고리 설명
                if(res.message['productList'].length) $("#delete-category-btn").prop('disabled', true)     // 하위 상품 존재시, 카테고리 삭제 불가능
                else $("#delete-category-btn").prop('disabled', false)
            }
        })
    })

    /* [왼쪽] "+상품" 클릭 */
    $("#navTab").on("click", "#add-product-btn", function(e) {
        const hongCategoryId = $(this).attr("data-num")
        $("#new-product-hongCategoryId").val(hongCategoryId)        // 카테고리 Id (hidden)
        newUploadFile.domEmptyAndFileGroupIdNull()                  // 파일 첨부 영역 dom.empty()
        $("#new-product-modal").modal('show')
    })

    /* [왼쪽] "+카테고리" 클릭 */
    $("#add-category-btn").on("click", function(e) {
        $("#new-category-modal").modal('show')
    })

    /* [왼쪽] "+카테고리" 클릭 -> 나온 모달 "취소" 버튼 */
    $("#cancel-new-category-btn, #close-category-modal-btn").on("click", function(e) {
        Util.confirm("카테고리 작성 정보를 취소하시겠습니까?").then((isOk) => {
            if(isOk) {
                categoryModalEmpty()
                $("#new-category-modal").modal('hide')
            }
        })
    })

    /* [왼쪽] "+상품" 클릭 -> 나온 모달 "취소" 버튼 */
    $("#cancel-new-product-btn, #close-product-modal-btn").on("click", function(e) {
        Util.confirm("상품 작성 정보를 취소하시겠습니까?").then((isOk) => {
            if(isOk) {
                productModalEmpty()
                $("#new-product-modal").modal('hide')
            }
        })
    })

    /* [오른쪽] "삭제" 클릭 (상품) */
    $("#delete-product-btn").on("click", function(e) {
        const productId = $("#hongProductId").val()

        Util.confirm("해당 상품을 삭제하시겠습니까?").then((isOk) => {
            if(isOk) {
                Http.delete(`/api/product/${productId}`).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            defaultPage()
                        })
                    }
                })
            }
        })
    })

    /* [오른쪽] "삭제" 클릭 (카테고리) */
    $("#delete-category-btn").on("click", function(e) {
        const categoryId = $("#hongCategoryId").val()

        Util.confirm("해당 카테고리를 삭제하시겠습니까?").then((isOk) => {
            if(isOk) {
                Http.delete(`/api/category/${categoryId}`).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            defaultPage()
                        })
                    }
                })
            }
        })
    })

    /* [오른쪽] "주문자" 버튼 클릭 -> 상품에 대한 주문 정보 조회 (모달) */
    $("#show-product-order").on("click", function(e) {
        const productId = $("#hongProductId").val()
        productUserTable
            .get(`/api/order-user/${productId}`)
            .init()
        $("#product-user-modal").modal('show')
    })
})

/* 카테고리 추가 및 수정 모달 비우기 */
function categoryModalEmpty(){
    categoryForm.val('')
    const form = document.getElementById("new-category-form")
    form.classList.remove("was-validated")
}

/* 상품 추가 및 수정 모달 비우기 */
function productModalEmpty(){
    productForm.val('')
    $("#new-newProductYn-N").prop('checked', true)
    newUploadFile.domEmptyAndFileGroupIdNull()  // product modal file dom.empty()
    const form = document.getElementById("new-product-form")
    form.classList.remove("was-validated")
}

/* [왼쪽] 카테고리->상품 리스트 조회 */
function getCategoryWithProduct(){
    Http.get(`/api/category-product`).then((res) => {
        if(res['httpStatus'] === 200) {
            nav.setData(res.message).draw()
        }
    })
}

/* [오른쪽] 카테고리 정보 변경 */
window.addEventListener("load", function() {
    const form = document.getElementById("category-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("category-form").then((obj) => {
                const hongCategoryId = $("#hongCategoryId").val()
                Http.put(`/api/category/${hongCategoryId}`, obj).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.alert("카테고리 정보를 수정하였습니다.").then(() => {
                            defaultPage()
                        })
                    } else {
                        Util.alert("카테고리 정보 수정에 실패하였습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

/* [오른쪽] 상품 정보 변경 */
window.addEventListener("load", function() {
    const form = document.getElementById("product-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("product-form").then((obj) => {
                const productId = $("#hongProductId").val()
                const limitProduct = parseInt(obj['originProductCnt']) - $("#productStock").val()

                if(uploadFile.getFileGroupId()) obj['fileGroupId'] = uploadFile.getFileGroupId()
                obj['deleteFile'] = uploadFile.getDeleteFile()

                if(obj['productCnt'] < limitProduct) Util.alert(`해당 상품의 경우 최소 상품이 ${limitProduct}개 필요합니다.`, 'w', 'w')
                else {
                    Http.put(`/api/product/${productId}`, obj).then((res) => {
                        if (res['httpStatus'] === 200) {
                            Util.alert("상품 정보를 수정하였습니다.").then(() => {
                                defaultPage()
                            })
                        } else {
                            Util.alert("상품 정보 수정에 실패하였습니다.", 'w', 'w')
                        }
                    })
                }
            })
        }
    })
})

/* [모달] 새로운 카테고리 등록  */
window.addEventListener("load", function() {
    const form = document.getElementById("new-category-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("new-category-form").then((obj) => {
                Http.post(`/api/category`, obj).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert("카테고리가 추가되었습니다.").then(() => {
                            defaultPage()
                            $("#new-category-modal").modal('hide')
                        })
                    }else {
                        Util.alert("카테고리 추가에 실패하였습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

/* [모달] 새로운 상품 등록  */
window.addEventListener("load", function() {
    const form = document.getElementById("new-product-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("new-product-form").then((obj) => {

                if(newUploadFile.getFileGroupId()) obj['fileGroupId'] = newUploadFile.getFileGroupId()
                obj['deleteFile'] = newUploadFile.getDeleteFile()
                Http.post(`/api/product`, obj).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert("상품이 추가되었습니다.").then(() => {
                            defaultPage()
                            $("#new-product-modal").modal('hide')
                        })
                    }else {
                        Util.alert("상품 추가에 실패하였습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

function defaultPage(){
    document.getElementById("empty-div").style.display = 'block'        // show
    document.getElementById("category-div").style.display = 'none'
    document.getElementById("product-div").style.display = 'none'
    getCategoryWithProduct()

    categoryModalEmpty()
    productModalEmpty()
}

/* [오른쪽] 상품 정보 수정 -> 파일 업로드 */
$('#btn-upload-product').on("click", function (e) {
    e.preventDefault();
    const numberOfLiElements = [...document.querySelectorAll("#upload-container-product li")].length;
    if(numberOfLiElements === 1) Util.alert("최대 업로드 개수는 1개입니다.", 'w', 'w')
    else $('#fileUpload-product').click();
});

/* [+상품] 새로운 상품등록 모달 -> 파일 업로드 */
$('#btn-upload-new-product').on("click", function(e){
    e.preventDefault();
    const numberOfLiElements = [...document.querySelectorAll("#upload-container-new-product li")].length;
    if(numberOfLiElements === 1) Util.alert("최대 업로드 개수는 1개입니다.", 'w', 'w')
    else $('#fileUpload-new-product').click();
})

/* [오른쪽] 상품 정보 수정 -> 파일 업로드 */
$("#fileUpload-product").on("change", function (e) {
    if(e.target.files.length >= 2) Util.alert("최대 업로드 개수는 1개입니다.", 'w', 'w')
    else {
        const selectedFile = e.target.files[0];
        const fileName = selectedFile.name

        if(!selectedFile.type.startsWith("image/")) Util.alert("이미지 파일만 업로드 가능합니다.", 'w', 'w')
        else {
            const formData = new FormData();
            formData.append("file", selectedFile);
            if (uploadFile.getFileGroupId()) formData.append("fileGroupId", uploadFile.getFileGroupId());

            Http.filePost("/api/uploadFile", formData).then((res) => {
                if(res['httpStatus'] === 200) {
                    uploadFile.setFileGroupId(res.message.fileGroupId)
                    uploadFile.addFileList({'originalFileName': fileName, 'id': res.message.id})
                }
            })
        }
    }
})

/* [+상품] 새로운 상품등록 모달 -> 파일 업로드 */
$("#fileUpload-new-product").on("change", function (e) {
    if(e.target.files.length >= 2) Util.alert("최대 업로드 개수는 1개입니다.", 'w', 'w')
    else {
        const selectedFile = e.target.files[0];
        const fileName = selectedFile.name

        if(!selectedFile.type.startsWith("image/")) Util.alert("이미지 파일만 업로드 가능합니다.", 'w', 'w')
        else {

            const formData = new FormData();
            formData.append("file", selectedFile);
            if (newUploadFile.getFileGroupId()) formData.append("fileGroupId", newUploadFile.getFileGroupId());

            Http.filePost("/api/uploadFile", formData).then((res) => {
                if(res['httpStatus'] === 200) {
                    newUploadFile.setFileGroupId(res.message.fileGroupId)
                    newUploadFile.addFileList({'originalFileName': fileName, 'id': res.message.id})
                }
            })
        }
    }
})