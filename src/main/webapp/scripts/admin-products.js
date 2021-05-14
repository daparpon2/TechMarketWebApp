function initializeTableButtonListeners() {
    $(".detail-button").click(manageProductDetails);
    $(".remove-button").click(removeProduct);
}

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

function showProductDetails(pressedButton) {
    $.ajax({
        url: "product-service",
        type: "get",
        data: {
            "id": pressedButton.parent().parent().parent().find("td:nth-child(2)").text()
        },
        dataType: "json",

        success: function (response) {
            pressedButton.parent().parent().parent().after("<tr><td colspan='5'><table class='table table-borderless'>"
                    + "<tr><th>Fabricante: </th><td>" + response.manufacturer.name + "</td></tr>"
                    + "<tr><th>Categoría: </th><td>" + response.productCode.description + "</td></tr>"
                    + "<tr><th>Disponible: </th><td>" + (response.available ? "Sí" : "No") + "</td></tr>"
                    + "<tr><th>Cantidad: </th><td>" + response.quantityOnHand + "</td></tr>"
                    + "<tr><th>Margen: </th><td>" + response.markup + "%</td></tr>"
                    + "<tr><th>Descuento: </th><td>" + response.productCode.discountCode.rate + "%</td></tr>"
                    + "</table></td></tr>"
                    );
            pressedButton.find("span.icon").load("shards/bootstrap-icons/hide-icon.html");
            pressedButton.find("span.label").text("Ocultar");
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

function hideProductDetails(pressedButton) {
    pressedButton.parent().parent().parent().next().remove();
    pressedButton.find("span.icon").load("shards/bootstrap-icons/show-icon.html");
    pressedButton.find("span.label").text("Detalles");
}

function manageProductDetails() {
    switch ($(this).find("span.label").text().trim().toUpperCase()) {
        case "DETALLES":
            showProductDetails($(this));
            break;
        case "OCULTAR":
            hideProductDetails($(this));
            break;
        default:
    }
}

function removeProduct() {
    Swal.fire({
        icon: "question",
        title: "Confirmación eliminar producto",
        text: "¿Seguro que deseas eliminar el producto con ID " + $(this).parent().parent().parent().find("td:nth-child(2)").text() + "?",
        showCancelButton: true,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "#591259",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            var pressedButton = $(this);
            $.ajax({
                url: "product-service?id=" + pressedButton.parent().parent().parent().find("td:nth-child(2)").text(),
                type: "delete",
                success: function (response) {
                    if (response === true) {
                        pressedButton.parent().parent().parent().remove();
                        Swal.fire({
                            icon: "success",
                            title: "Producto eliminado",
                            text: "Producto eliminado con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al eliminar",
                            text: "Ha ocurrido un error. El producto no se ha podido eliminar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    }
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
    });
}

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

function initializeFormValidation() {
    $.validator.setDefaults({
        submitHandler: function () {
            var httpMethod;
            var quantity;
            if ($("#modal-button").text() === "Crear") {
                httpMethod = "post";
            } else {
                httpMethod = "put";
            }

            if (!$("#available").prop("checked")) {
                quantity = 0;
            } else {
                quantity = $("#quantity").val();
            }

            $.ajax({
                url: "product-service",
                type: httpMethod,
                data: {
                    "id": $("#product-id").val(),
                    "image": "default-" + $("#category option:selected").text().toLowerCase() + ".png",
                    "description": $("#description").val(),
                    "purchaseCost": $("#price").val(),
                    "manufacturerId": $("#manufacturer").val(),
                    "prodCode": $("#category").val(),
                    "available": $("#available").prop("checked"),
                    "quantityOnHand": quantity,
                    "markup": $("#markup").val()
                },
                dataType: "json",

                success: function (response) {
                    switch (httpMethod.toUpperCase()) {
                        case "POST":
                            if (response !== null) {
                                $("#product-table").append("<tr>"
                                        + "<td class='text-center'><img class='table-image' src='resources/images/products/" + response.image + "'/></td>"
                                        + "<td>" + response.productId + "</td>"
                                        + "<td class='text-truncate'>" + response.description + "</td>"
                                        + "<td>" + response.purchaseCost + " €</td>"
                                        + operationButtonsHTML()
                                        );
                                $("#product-table tr:last-child td:last-child .detail-button .icon").load("shards/bootstrap-icons/show-icon.html");
                                $("#product-table tr:last-child td:last-child .edit-button .icon").load("shards/bootstrap-icons/edit-icon.html");
                                $("#product-table tr:last-child td:last-child .remove-button .icon").load("shards/bootstrap-icons/remove-icon.html");
                                initializeTableButtonListeners();
                                $("#modal").modal("hide");

                                Swal.fire({
                                    icon: "success",
                                    title: "Producto añadido",
                                    text: "Producto nuevo creado con éxito.",
                                    confirmButtonText: "Aceptar",
                                    confirmButtonColor: "#591259"
                                });
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "Error al insertar",
                                    text: "Ha ocurrido un error. El producto no se ha podido insertar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                                    confirmButtonText: "Aceptar",
                                    confirmButtonColor: "#591259"
                                });
                            }
                            break;
                        case "PUT":
                            if (response) {
                                $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td").text($("#description").val());
                                $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td").text($("#price").val().replace(",",".") + " €");
                                
                                if($("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible .label").text() === "Ocultar") {
                                    $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible").click();
                                }
                                
                                $("#modal").modal("hide");

                                Swal.fire({
                                    icon: "success",
                                    title: "Producto editado",
                                    text: "Producto editado con éxito.",
                                    confirmButtonText: "Aceptar",
                                    confirmButtonColor: "#591259"
                                });
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "Error al modificar",
                                    text: "Ha ocurrido un error. El producto no se ha podido modificar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                                    confirmButtonText: "Aceptar",
                                    confirmButtonColor: "#591259"
                                });
                            }
                            break;
                        default:
                    }
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
    });

    $.validator.addMethod("validOption", function (value, element) {
        var selectedIndex = element.selectedIndex;
        return selectedIndex !== null && selectedIndex > 0;
    });

    $.validator.addMethod("requiredIfAvailable", function (value, element) {
        return !($("#available").prop("checked")) || element.value !== "";
    });

    $("#modal form").validate({
        rules: {
            description: {
                required: true
            },
            price: {
                required: true,
                number: true,
                min: 0
            },
            manufacturer: {
                required: true,
                validOption: true
            },
            category: {
                required: true,
                validOption: true
            },
            quantity: {
                requiredIfAvailable: true,
                digits: true,
                min: 1
            },
            markup: {
                required: true,
                number: true,
                min: 0
            }
        },

        messages: {
            description: {
                required: "El campo no puede estar vacío."
            },
            price: {
                required: "El campo no puede estar vacío.",
                number: "Debe ser un número real (precio en €).",
                min: "No puede ser un número negativo."
            },
            manufacturer: {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            },
            category: {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            },
            quantity: {
                requiredIfAvailable: "El campo no puede estar vacío si \"Disponible\" está seleccionado.",
                digits: "Debe ser un número entero.",
                min: "Debe ser mayor que 0."
            },
            markup: {
                required: "El campo no puede estar vacío.",
                number: "Debe ser un porcentaje real (sobre 100).",
                min: "No puede ser un número negativo."
            }
        }
    });
}

function init() {
    initializeTableButtonListeners();

    $("#modal").on("show.bs.modal", initializeModal);
    $("#modal input, #modal select").change(function () {
        $("#modal #modal-button").prop("disabled", false);
    });
    $("#modal #available").click(function () {
        $("#modal #quantity").prop("disabled", !($(this).is(":checked")));
    });

    initializeFormValidation();
}

$(document).ready(init);


