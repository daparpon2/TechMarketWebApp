function loadManufacturerSelect(modal) {
    $.ajax({
        url: "product-service",
        type: "get",
        data: {
            "selector": "manufacturer"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#manufacturer").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#manufacturer").append("<option value='" + element.manufacturerId + "'>" + element.name + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de los fabricantes. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function loadCategorySelect(modal) {
    $.ajax({
        url: "product-service",
        type: "get",
        data: {
            "selector": "category"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#category").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#category").append("<option value='" + element.prodCode + "'>" + element.description + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de las categorías. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function initializeCreationModal(modal) {
    modal.find("#modal-title").text("Añadir nuevo producto");
    modal.find("#modal-button").text("Crear");

    modal.find("input").val("");
    modal.find("select option").remove();
    modal.find("input[type='checkbox']").prop("checked", false);
    modal.find("#quantity").prop("disabled", true);
    $("#modal #modal-button").prop("disabled", true);

    loadManufacturerSelect(modal);
    loadCategorySelect(modal);
}

function initializeEditionModal(modal, dataRow) {
    modal.find("#modal-title").text("Editar producto existente");
    modal.find("#modal-button").text("Guardar cambios");

    modal.find("input").val("");
    modal.find("select option").remove();
    loadManufacturerSelect(modal);
    loadCategorySelect(modal);

    $.ajax({
        url: "product-service",
        type: "get",
        async: "false",
        data: {
            "id": dataRow.find("td:nth-child(2)").text()
        },
        dataType: "json",

        success: function (response) {
            modal.find("#product-id").val(response.productId);
            modal.find("#description").val(response.description);
            modal.find("#price").val(response.purchaseCost);
            modal.find("#manufacturer option[value='" + response.manufacturer.manufacturerId + "']").prop("selected", true);
            modal.find("#category option[value='" + response.productCode.prodCode + "']").prop("selected", true);
            modal.find("#available").prop("checked", response.available);
            modal.find("#quantity").prop("disabled", !response.available);
            if (response.available) {
                modal.find("#quantity").val(response.quantityOnHand);
            }
            modal.find("#markup").val(response.markup);

            $("#modal #modal-button").prop("disabled", true);
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function initializeModal(event) {
    switch ($(event.relatedTarget).data("action").toString().trim().toUpperCase()) {
        case "ADD":
            initializeCreationModal($(this));
            break;
        case "EDIT":
            initializeEditionModal($(this), $(event.relatedTarget).parent().parent().parent());
            break;
        default:
    }
}


