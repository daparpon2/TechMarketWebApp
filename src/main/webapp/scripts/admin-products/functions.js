function operationButtonsHTML() {
    return "<td><span>"
            + "<button class='btn btn-outline-primary detail-button d-none d-md-inline'>"
            + "<span class='icon'></span><span class='label'>Detalles</span></button>"
            + "<button class='btn btn-outline-secondary edit-button d-none d-md-inline' data-toggle='modal' data-target='#modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-outline-danger remove-button d-none d-md-inline'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "<button class='btn btn-primary detail-button d-block d-md-none'>"
            + "<span class='icon'></span><span class='label'>Detalles</span></button>"
            + "<button class='btn btn-secondary edit-button d-block d-md-none' data-toggle='modal' data-target='#modal' data-action='edit'>"
            + "<span class='icon'></span><span class='label'>Editar</span></button>"
            + "<button class='btn btn-danger remove-button d-block d-md-none'>"
            + "<span class='icon'></span><span class='label'>Eliminar</span></button>"
            + "</span></td>";
}


