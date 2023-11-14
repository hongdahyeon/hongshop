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

    static returnIcon(icon) {
        switch(icon) {
            case 'w':
                return 'warning'
            case 's':
                return 'success'
        }
    }

    static alert(html, icon = 's') {
        return Swal.fire({
            icon: this.returnIcon(icon),
            html
        });
    }

    static confirm(html, icon = 'w', confirmButtonText = '확인', cancelButtonText = '취소') {
        return Swal.fire({
            html,
            icon: this.returnIcon(icon),
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: confirmButtonText,
            cancelButtonText: cancelButtonText,
        }).then((res) => res.isConfirmed)
    }
}
