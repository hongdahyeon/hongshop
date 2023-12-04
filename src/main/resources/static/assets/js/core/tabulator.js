class Table {

    constructor(id) {
        this._id = id
        this._subTable = false
        this._layout = "fitDataFill"
        this._placeholder = "검색결과가 존재하지 않습니다."
        this._minHeight = 50
        this._maxHeight = 300
        this._resizeable = false
        this._rowFormatter = null
        this._rowClick = null
        this._selectable = false
        this._afterComplete = null
        this._columns = []
        this._url = ''
        this._data = []
        this._table = null
    }

    /*
    * @param: url
    */
    get(url = '') {
        this._url = url
        return this
    }

    /*
    * row selectable true
    */
    selectable() {
        this._selectable = true
        return this
    }

    /*
    * @param: data[]
    */
    setData(data = []){
        this._data = data
        return this
    }

    /*
    * make subTable
    */
    subTable(){
        this._subTable = true
        return this
    }

    /*
    * @param: layout
    * - default: fitDataFill
    */
    changeLayout(layout = ''){
        this._layout = layout
        return this
    }

    /*
    * rowClick: rowClick use
    */
    rowClick(callback) {
        this._rowClick = callback
        return this
    }

    /*
    * rowFormatter: rowFormatter use
    */
    rowFormatter(callback) {
        this._rowFormatter = callback
        return this
    }

    /*
    * afterComplete: settings after draw table
    */
    afterComplete(callback) {
        this._afterComplete = callback
        return this
    }

    /*
    * @param: val
    * - default: false
    * - can resize column
    * */
    resizeable(){
        this._resizeable = true
        return this
    }

    /*
    * @param: column
    * - table column add
    */
    add(column) {
        if(column instanceof Column) {
            this._columns.push(column.getCol())
        }
        return this
    }

    /*
    * draw table
    * - subTable : use data[] and draw
    * - table : get data and draw
    */
    init() {
        if(this._subTable) {
            const data = this._data.map((item, index) => ({ ...item, index: index + 1 }))
            this._initOptions(data)
        }else {
            Http.get(`${this._url}`).then((res) => {
                if (res['httpStatus'] === 200) {
                    // 1. data with index
                    const data = res.message.map((item, index) => ({...item, index: index + 1}))
                    this._initOptions(data)
                }
            })
        }
    }

    /*
    * table options
    */
    _initOptions(data){
        // 2. tabulator option
        const option = {
            data: data,
            selectable: this._selectable,
            placeholder: this._placeholder,
            layout: this._layout,
            minHeight: this._minHeight,
            columnDefaults: {
                resizable: this._resizeable
            },
            columns:this._columns
        }

        if(this._rowFormatter) {
            option['rowFormatter'] = this._rowFormatter
        }

        const dom = (!this._subTable) ? `#${this._id}` : this._id
        const table = new Tabulator(dom, option)

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