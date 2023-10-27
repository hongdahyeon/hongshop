class navTab {

    constructor(id) {
        this._id = id
        this._data = []
    }

    setData(data){
        this._data = data
        return this
    }

    draw(){
        const dom = $(`#${this._id}`)
        dom.empty()

        if(this._data.length > 0) {
            this._data.forEach((data, i) => {
                const category =
                    `
                    <div class="d-flex justify-content-between align-items-center">
                        <button class="nav-link text-start category" style="color: black" id="category-${data['categoryId']}" data-bs-toggle="pill" type="button" role="tab" aria-selected="true" data-num="${data['categoryId']}">
                            <i data-feather="home" class="font-medium-2 ms-50"></i>
                            ${data['categoryName']}
                        </button>
                        <button type="button" class="btn btn-link" id="add-product-btn" data-num="${data['categoryId']}">+상품</button>
                    </div>
                    ${this._makeProducts(data['productList'])}
                    `
                dom.append(category)
                feather.replace()
            })
        }
    }

    _makeProducts(products = []){
        const productBody = products.map(data => `
                <button class="nav-link text-start product" id="product-${data['productId']}" data-bs-toggle="pill" type="button" role="tab" aria-selected="false" data-num="${data['productId']}">
                    <i data-feather="package" class="font-medium-2 ms-50"></i>
                    ${data['productName']}
                </button>
            `).join('');

        return productBody;
    }
}