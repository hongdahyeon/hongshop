$(document).ready(function(e){

    table
        .get('/api/order-with-reviewChk')
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("userId").title("주문자").width("20%").left())
        .add(new Column("orderStatus").title("주문상태").width("10%").left())
        .add(new Column("orderDate").title("주문날짜").width("10%").left())
        .add(new Column("orderDetails").title("주문상품 개수").width("20%").left().formatter(function(cell) { return `${cell.getValue().length} 개` }))
        .add(new Column("orderId").title("상태변경").width("20%").center().formatter(function(cell) {
            const rowData = cell.getData()
            return `<select id="orderStatus-${rowData.orderId}" class="form-select-sm select" style="width: 150px" data-num="${rowData.orderId}" data-status="${rowData.orderStatus}" ${(rowData.writeReviewEmpty == false) ? 'disabled' : ''} onChange="changeSelect(this)">
                                <option value="CHARGED" ${(rowData.orderStatus == 'CHARGED') ? 'selected' : ''}>CHARGED</option>
                                <option value="CANCEL" ${(rowData.orderStatus == 'CANCEL') ? 'selected' : ''}>CANCEL</option>
                                <option value="DELIVER_SUCCESS" ${(rowData.orderStatus == 'DELIVER_SUCCESS') ? 'selected' : ''}>DELIVER_SUCCESS</option>
                                <option value="DELIVER_ING" ${(rowData.orderStatus == 'DELIVER_ING') ? 'selected' : ''}>DELIVER_ING</option>
                            </select>`
        }))
        .init()

    userOrderListLogTable
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("productName").title("주문상품").left().width("20%"))
        .add(new Column("orderCnt").title("주문개수").left().width("20%").formatter(function(cell) { return `${cell.getValue()} 개`}))
        .add(new Column("orderPrice").title("주문가격").left().width("20%").formatter(function(cell) { return `${Util.priceString(cell.getValue())} 원`}))


    table.rowClick((data, row) => {
        userOrderListLogTable
            .setData(data['orderDetails'])
            .init()
        $("#user-orderList-log-modal").modal('show')
    })
})


function changeSelect(selectElement) {
    const orderId = selectElement.getAttribute("data-num")
    const orderStatus = selectElement.getAttribute("data-status")
    const chanageOrderStatus = selectElement.options[selectElement.selectedIndex].value

    Util.confirm("해당 상품의 주문상태값을 변경하시겠습니까?").then((isOk) => {
        if(!isOk) $(`#orderStatus-${orderId}`).val(orderStatus)
        else {
            const obj = {'orderStatus': chanageOrderStatus}
            Http.put(`/api/order-status/${orderId}`, obj).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(res.message).then(() => {
                        table.submit()
                    })
                }else {
                    Util.alert("해당 상품의 주문 상태값 변경에 실패하였습니다.").then(() => {
                        $(`#orderStatus-${orderId}`).val(orderStatus)
                    })
                }
            })
        }
    })
}