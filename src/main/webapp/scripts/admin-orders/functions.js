function processDate(date) {
    var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!
    var yyyy = date.getFullYear();
    if (dd < 10) {
        dd = "0" + dd;
    }
    if (mm < 10) {
        mm = "0" + mm;
    }

    return yyyy + "-" + mm + "-" + dd;
}

function printDate(date) {
    var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!
    var yyyy = date.getFullYear();
    if (dd < 10) {
        dd = "0" + dd;
    }
    if (mm < 10) {
        mm = "0" + mm;
    }

    return dd + "/" + mm + "/" + yyyy;
}

function addLineButtonsHTML() {
    return "<button class='btn btn-outline-secondary add-button d-none d-md-block' data-toggle='modal' data-target='#line-modal' data-action='add'>"
            + "<span class='icon'></span><span class='label'>Añadir producto</span></button>"
            + "<button class='btn btn-secondary add-button d-block d-md-none' data-toggle='modal' data-target='#line-modal' data-action='add'>"
            + "<span class='icon'></span><span class='label'>Añadir producto</span></button>";
}

function operationOrderButtonsHTML() {
    return "<td><span>"
            + "<button class='btn btn-outline-primary line-button d-none d-md-inline'>"
            + "<span class='icon'></span><span class='label'>Líneas</span></button>"
            + "<button class='btn btn-outline-secondary edit-button d-none d-md-inline' data-toggle='modal' data-target='#order-modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-outline-danger remove-button d-none d-md-inline'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "<button class='btn btn-primary line-button d-block d-md-none'>"
            + "<span class='icon'></span><span class='label'>Líneas</span></button>"
            + "<button class='btn btn-secondary edit-button d-block d-md-none' data-toggle='modal' data-target='#order-modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-danger remove-button d-block d-md-none'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "</span></td>";
}

function operationLineButtonsHTML() {
    return "<td><span>"
            + "<button class='btn btn-outline-secondary edit-button d-none d-md-inline' data-toggle='modal' data-target='#line-modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-outline-danger remove-line-button d-none d-md-inline'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "<button class='btn btn-secondary edit-button d-block d-md-none' data-toggle='modal' data-target='#line-modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-danger remove-line-button d-block d-md-none'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "</span></td>";
}


