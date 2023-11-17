class FormDataToObj {
    static async getParameter(formId) {
        const form = document.getElementById(formId);
        const formData = new FormData(form);
        const data = Array.from(formData.entries()).reduce((perv, [key, value]) => {
            if (perv[key]) {
                Array.isArray(perv[key]) ? perv[key].push(value) : (perv[key] = [perv[key], value]);
            } else {
                perv[key] = value;
            }
            return perv;
        }, {});
        return data;
    }
}

class Util {

    static priceString(value){
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }

    static alert(html, icon = 's', btn = 's') {
        return Swal.fire({
            html,
            icon: (icon === 's') ? 'success' : 'warning',
            confirmButtonColor: (btn === 's') ? '#3085d6' : '#d33'
        });
    }

    static confirm(html, icon = 'w', confirmButtonText = '확인', cancelButtonText = '취소') {
        return Swal.fire({
            html,
            icon: (icon === 'w') ? 'warning' : 'success',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: confirmButtonText,
            cancelButtonText: cancelButtonText,
        }).then((res) => res.isConfirmed)
    }
}

class Http {

    static get(url, params = '', method = 'GET') {
        return $.ajax({
            type: method,
            url: url,
            data: params,
            dataType: 'json'
        })
    }

    static syncGet(url, params='', async = false, method = 'GET'){
        return $.ajax({
            type: method,
            url: url,
            data: params,
            async: false,
            dataType: 'json'
        })
    }

    static post(url, data, method = 'POST') {
            return $.ajax({
                type: method,
                url: url,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json'
            })
    }

    static delete(url, data, method = 'DELETE') {
        return $.ajax({
            type: method,
            url: url,
            contentType: 'application/json'
        })
    }

    static put(url, data, method='PUT') {
        return $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json'
        })
    }

    static filePost(url, formData, method = 'POST'){
        return $.ajax({
            type: method,
            url: url,
            data: formData,
            contentType: false,
            processData: false
        })
    }

}
