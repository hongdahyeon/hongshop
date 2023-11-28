class Table {

    constructor(id) {
        this._id = id
        this._layout = "fitDataFill"
        this._resizeable = true
        this._rowClick = null
        this._afterComplete = null
        this._columns = []
        this._url = ''
        this._table = null
    }

    get(url = '') {
        this._url = url
        return this
    }

    rowClick(callback) {
        this._rowClick = callback
        return this
    }

    afterComplete(callback) {
        this._afterComplete = callback
        return this
    }

    resizeable(val = true){
        this._resizeable = val
        return this
    }

    add(column) {
        if(column instanceof Column) {
            this._columns.push(column.getCol())
        }
        return this
    }

    init() {

        Http.get(`${this._url}`).then((res) => {
            if(res['httpStatus'] === 200) {

                // 1. data with index
                const data = res.message.map((item, index) => ({ ...item, index: index + 1 }))
                console.log("data : ", data)
                // 2. tabulator option
                const option = {
                    data: data,
                    layout: this._layout,
                    columnDefaults: {
                        resizable: this._resizeable
                    },
                    columns:this._columns
                }

                const table = new Tabulator(`#${this._id}`, option)

                // if clicking row not null
                if(this._rowClick) {
                    table.on('rowClick', (e, row) => {
                        this._rowClick(row.getData(), row._row)
                    })
                }

                // if after complete not null
                if(this._afterComplete) {
                    table.on("renderComplete", () => this._afterComplete())
                }

                this._table = table
            }
        })
    }
}

class Column {

    constructor(field) {
        this._title = ''
        this._field = field
        this._headerSort = false
        this._width = '10%'
        this._hozAlign = 'center'
        this._headerHozAlign = 'center'
        this._formatter = null
    }

    title(title = '') {
        this._title = title
        return this
    }

    width(width = '10%'){
        this._width = width
        return this
    }

    headerSort(sort = false) {
        this._headerSort = sort
        return this
    }

    center(){
        this._hozAlign = 'center';
        this._headerHozAlign = 'center'
        return this
    }

    left(){
        this._hozAlign = 'left';
        this._headerHozAlign = 'left'
        return this
    }

    right(){
        this._hozAlign = 'right';
        this._headerHozAlign = 'right'
        return this
    }

    formatter(callback) {
        this._formatter = callback;
        return this
    }

    getCol() {
        const obj = {
            title : this._title,
            field : this._field,
            headerSort: this._headerSort,
            width: this._width,
            hozAlign: this._hozAlign,
            headerHozAlign: this._headerHozAlign
        }

        if(this._formatter) obj.formatter = this._formatter

        return obj
    }
}