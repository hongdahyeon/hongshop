$(document).ready(function(e) {
    table
        .get(`/api/deliver-with-reviewChk`)
        .headerBottom()
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("orderUser").title("주문자Id").width("20%").left())
        .add(new Column("orderDate").title("주문날짜").width("10%").center().formatter(function(cell) { return Util.DateSubString(cell.getValue()) }))
        .add(new Column("orderStatus").title("배송 상태").width("10%").center())
        .add(new Column().title("주소").width("30%").center()
            .add(new Column("address").title("주소: 시/도").width("10%").center().formatter(function(cell) { return cell.getValue().city}))
            .add(new Column("address").title("주소: 시군구/읍면동").width("10%").center().formatter(function(cell) { return cell.getValue().street}))
            .add(new Column("address").title("주소: 우편번호").width("10%").center().formatter(function(cell) { return cell.getValue().zipcode}))
        )
        .add(new Column("deliverId").title("배송 상태 변경").width("20%").center().formatter(function(cell) {
            const rowData = cell.getData()
            return `<select id="deliverStatus-${rowData['deliverId']}" class="form-select-sm select" style="width: 150px" data-num="${rowData['deliverId']}" data-status="${rowData['deliverStatus']}" ${(rowData['writeReviewEmpty'] === false) ? 'disabled' : ''} onChange="changeSelect(this)">
                            <option value="DELIVERING" ${(rowData['deliverStatus'] === 'DELIVERING') ? 'selected' : ''}>DELIVERING</option>
                            <option value="DELIVERED" ${(rowData['deliverStatus'] === 'DELIVERED') ? 'selected' : ''}>DELIVERED</option>
                            <option value="AWAIT" ${(rowData['deliverStatus'] === 'AWAIT') ? 'selected' : ''}>AWAIT</option>
                            <option value="CANCEL" ${(rowData['deliverStatus'] === 'CANCEL') ? 'selected' : ''}>CANCEL</option>
                    </select>`
        }))
        .init()
})

function changeSelect(selectElement) {
    const deliverId = selectElement.getAttribute("data-num")
    const deliverStatus = selectElement.getAttribute("data-status")
    const chanageDeliverStatus = selectElement.options[selectElement.selectedIndex].value

    Util.confirm(`해당 배송 정보를 바꾸시겠습니까?`).then((isOk) => {
        if(!isOk) $(`#deliverStatus-${deliverId}`).val(deliverStatus)
        else {
            const obj = {'status': chanageDeliverStatus}

            Http.put(`/api/deliver-status/${deliverId}`, obj).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(res.message).then(() => {
                        window.location.href='/manager/deliver'
                    })
                }else {
                    Util.alert("해당 배송 정보 변경에 실패하였습니다.").then(() => {
                        $(`#deliverStatus-${deliverId}`).val(deliverStatus)
                    })
                }
            })
        }
    })
}