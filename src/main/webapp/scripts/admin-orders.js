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

function initializeTableButtonListeners() {
    $(".line-button").click(manageOrderLines);
    $(".remove-button").click(removeOrder);
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

function showOrderLines(pressedButton) {
    $.ajax({
        url: "line-service",
        type: "get",
        data: {
            "num": pressedButton.parent().parent().parent().find("td:first-child").text()
        },
        dataType: "json",

        success: function (response) {
            var tableData = "<tr><td colspan='8'>";
            tableData += addLineButtonsHTML();
            tableData += "<table class='table table-hover table-bordered line-table' id='" + pressedButton.parent().parent().parent().find("td:first-child").text() + "'>"
                    + "<tr><th>Producto</th><th>Cantidad</th><th>Operaciones</th></tr>";
            $.each(response, function (index, element) {
                tableData += "<tr id='" + element.product.productId + "'><td>" + element.product.description + "</td>"
                        + "<td>" + element.quantity + "</td>"
                        + operationLineButtonsHTML()
                        + "</tr>";
            });
            tableData += "</table></td></tr>";

            pressedButton.parent().parent().parent().after(tableData);
            pressedButton.parent().parent().parent().next().find(".add-button .icon").load("shards/bootstrap-icons/add-icon.html");
            pressedButton.parent().parent().parent().next().find(".edit-button .icon").load("shards/bootstrap-icons/edit-icon.html");
            pressedButton.parent().parent().parent().next().find(".remove-line-button .icon").load("shards/bootstrap-icons/remove-icon.html");
            pressedButton.parent().parent().parent().next().find(".remove-line-button").click(removeOrderProduct);
            pressedButton.find("span.icon").load("shards/bootstrap-icons/collapse-icon.html");
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

function hideOrderLines(pressedButton) {
    pressedButton.parent().parent().parent().next().remove();
    pressedButton.find("span.icon").load("shards/bootstrap-icons/list-icon.html");
    pressedButton.find("span.label").text("Líneas");
}

function manageOrderLines() {
    switch ($(this).find("span.label").text().trim().toUpperCase()) {
        case "LÍNEAS":
            showOrderLines($(this));
            break;
        case "OCULTAR":
            hideOrderLines($(this));
            break;
        default:
    }
}

function removeOrder() {
    Swal.fire({
        icon: "question",
        title: "Confirmación eliminar pedido",
        text: "¿Seguro que deseas eliminar el pedido número " + $(this).parent().parent().parent().find("td:first-child").text() + "?",
        showCancelButton: true,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "#591259",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            var pressedButton = $(this);
            $.ajax({
                url: "order-service?num=" + pressedButton.parent().parent().parent().find("td:first-child").text(),
                type: "delete",
                success: function (response) {
                    if (response === true) {
                        if (pressedButton.parent().find(".line-button:visible .label").text() === "Ocultar") {
                            pressedButton.parent().find(".line-button:visible").click();
                        }
                        pressedButton.parent().parent().parent().remove();
                        Swal.fire({
                            icon: "success",
                            title: "Pedido eliminado",
                            text: "Pedido eliminado con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al eliminar",
                            text: "Ha ocurrido un error. El pedido no se ha podido eliminar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
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

function removeOrderProduct() {
    Swal.fire({
        icon: "question",
        title: "Confirmación eliminar producto",
        text: "¿Seguro que deseas eliminar la línea del producto " + $(this).parent().parent().parent().find("td:first-child").text() + " en el pedido " + $(this).parent().parent().parent().parent().parent().attr("id") + "?",
        showCancelButton: true,
        confirmButtonText: "Aceptar",
        confirmButtonColor: "#591259",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            var pressedButton = $(this);
            $.ajax({
                url: "line-service?num=" + pressedButton.parent().parent().parent().parent().parent().attr("id") + "&productId=" + pressedButton.parent().parent().parent().attr("id"),
                type: "delete",
                success: function (response) {
                    if (response === true) {
                        pressedButton.parent().parent().parent().remove();
                        Swal.fire({
                            icon: "success",
                            title: "Producto eliminado",
                            text: "Producto eliminado con éxito del pedido.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al eliminar",
                            text: "Ha ocurrido un error. El producto no se ha podido eliminar del pedido. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
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

function loadCustomerSelect(modal) {
    $.ajax({
        url: "order-service",
        type: "get",
        data: {
            "selector": "customer"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#customer").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#customer").append("<option value='" + element.customerId + "'>" + element.name + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de los clientes. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function loadFreightCompanySelect(modal) {
    $.ajax({
        url: "order-service",
        type: "get",
        data: {
            "selector": "freight"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#freight-company").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#freight-company").append("<option value='" + element.companyId + "'>" + element.name + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de las transportistas. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function loadStatusSelect(modal) {
    $.ajax({
        url: "order-service",
        type: "get",
        data: {
            "selector": "status"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#status").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#status").append("<option value='" + element.statusCode + "'>" + element.description + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de los estados de pedido. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function initializeCreationModal(modal) {
    modal.find("#order-modal-title").text("Añadir nuevo pedido");
    modal.find("#order-modal-button").text("Crear");

    modal.find("input").val("");
    modal.find("select option").remove();
    $("#order-modal #order-modal-button").prop("disabled", true);

    loadCustomerSelect(modal);
    loadFreightCompanySelect(modal);
    loadStatusSelect(modal);
}

function initializeEditionModal(modal, dataRow) {
    modal.find("#order-modal-title").text("Editar pedido existente");
    modal.find("#order-modal-button").text("Guardar cambios");

    modal.find("input").val("");
    modal.find("select option").remove();
    loadCustomerSelect(modal);
    loadFreightCompanySelect(modal);
    loadStatusSelect(modal);

    $.ajax({
        url: "order-service",
        type: "get",
        data: {
            "num": dataRow.find("td:first-child").text(),
            "type": "order"
        },
        dataType: "json",

        success: function (response) {
            modal.find("#order-num").val(response.orderNum);
            modal.find("#customer option[value='" + response.customer.customerId + "']").prop("selected", true);
            modal.find("#status option[value='" + response.status.statusCode + "']").prop("selected", true);
            modal.find("#shipping-cost").val(response.shippingCost);
            modal.find("#sales-date").val(processDate(new Date(response.salesDate)));
            modal.find("#shipping-date").val(processDate(new Date(response.shippingDate)));
            modal.find("#freight-company option[value='" + response.freightCompany.companyId + "']").prop("selected", true);

            $("#order-modal #order-modal-button").prop("disabled", true);
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

function loadProductSelect(modal) {
    $.ajax({
        url: "product-service",
        type: "get",
        dataType: "json",

        success: function (response) {
            modal.find("#product").append("<option value=''></option>");
            $.each(response, function (index, element) {
                modal.find("#product").append("<option value='" + element.productId + "'>" + element.description + "</option>");
            });
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la información de los productos. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
}

function initializeCreationLineModal(modal, table) {
    modal.find("#line-modal-title").text("Añadir producto");
    modal.find("#line-modal-button").text("Añadir");
    modal.find("#line-order-num").text(table.attr("id"));

    modal.find("input").val("");
    modal.find("select option").remove();
    $("#line-modal #line-modal-button").prop("disabled", true);

    loadProductSelect(modal);
}

function initializeLineModal(event) {
    switch ($(event.relatedTarget).data("action").toString().trim().toUpperCase()) {
        case "ADD":
            initializeCreationLineModal($(this), $(event.relatedTarget).siblings("table"));
            break;
        case "EDIT":
            initializeEditionLineModal($(this), $(event.relatedTarget).parent().parent().parent());
            break;
        default:
    }
}

function submitOrderModal(httpMethod) {
    $.ajax({
        url: "order-service",
        type: httpMethod,
        data: {
            "num": $("#order-num").val(),
            "customerId": $("#customer").val(),
            "customerName": $("#customer option:selected").text(),
            "statusCode": $("#status").val(),
            "statusDescription": $("#status option:selected").text(),
            "shippingCost": $("#shipping-cost").val(),
            "salesDate": $("#sales-date").val(),
            "shippingDate": $("#shipping-date").val(),
            "freightCompanyId": $("#freight-company").val(),
            "freightCompanyName": $("#freight-company option:selected").text()
        },
        dataType: "json",

        success: function (response) {
            switch (httpMethod.toUpperCase()) {
                case "POST":
                    if (response !== null) {
                        var shippingDateValue;
                        if(response.shippingDate === undefined) {
                            shippingDateValue = "";
                        } else {
                            shippingDateValue = response.shippingDate;
                        }
                        $("#order-table").append("<tr>"
                                + "<td>" + response.orderNum + "</td>"
                                + "<td>" + response.customer.name + "</td>"
                                + "<td>" + response.status.description + "</td>"
                                + "<td>" + response.shippingCost + " €</td>"
                                + "<td>" + response.salesDate + "</td>"
                                + "<td>" + shippingDateValue + "</td>"
                                + "<td>" + response.freightCompany.name + "</td>"
                                + operationOrderButtonsHTML()
                                + "</tr>");
                        $("#order-table tr:last-child td:last-child .line-button .icon").load("shards/bootstrap-icons/list-icon.html");
                        $("#order-table tr:last-child td:last-child .edit-button .icon").load("shards/bootstrap-icons/edit-icon.html");
                        $("#order-table tr:last-child td:last-child .remove-button .icon").load("shards/bootstrap-icons/remove-icon.html");
                        initializeTableButtonListeners();

                        $("#order-modal").modal("hide");

                        Swal.fire({
                            icon: "success",
                            title: "Pedido añadido",
                            text: "Pedido nuevo creado con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al insertar",
                            text: "Ha ocurrido un error. El pedido no se ha podido insertar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    }
                    break;
                case "PUT":
                    if (response) {
                        $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td").text($("#description").val());
                        $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td").text($("#price").val().replace(",", ".") + " €");

                        if ($("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible .label").text() === "Ocultar") {
                            $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible").click();
                        }

                        $("#modal").modal("hide");

                        Swal.fire({
                            icon: "success",
                            title: "Pedido editado",
                            text: "Pedido editado con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al modificar",
                            text: "Ha ocurrido un error. El pedido no se ha podido modificar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
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

function submitLineModal(httpMethod) {
    $.ajax({
        url: "line-service",
        type: httpMethod,
        data: {
            "num": $("#line-order-num").text(),
            "productId": $("#product").val(),
            "productDescription": $("#product option:selected").text(),
            "quantity": $("#quantity").val()
        },
        dataType: "json",

        success: function (response) {
            switch (httpMethod.toUpperCase()) {
                case "POST":
                    if (response !== null) {
                        var table = $("#" + response.order.orderNum);
                        table.append("<tr id='" + response.product.productId + "'>"
                                + "<td>" + response.product.description + "</td>"
                                + "<td>" + response.quantity + "</td>"
                                + operationLineButtonsHTML()
                                + "</tr>");
                        table.find(".edit-button .icon").load("shards/bootstrap-icons/edit-icon.html");
                        table.find(".remove-line-button .icon").load("shards/bootstrap-icons/remove-icon.html");
                        table.find(".remove-line-button").click(removeOrderProduct);

                        $("#line-modal").modal("hide");

                        Swal.fire({
                            icon: "success",
                            title: "Producto añadido a pedido",
                            text: "Producto añadido a pedido con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al insertar",
                            text: "Ha ocurrido un error. La línea de pedido no se ha podido insertar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    }
                    break;
                case "PUT":
                    if (response) {
                        $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td").text($("#description").val());
                        $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td").text($("#price").val().replace(",", ".") + " €");

                        if ($("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible .label").text() === "Ocultar") {
                            $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible").click();
                        }

                        $("#modal").modal("hide");

                        Swal.fire({
                            icon: "success",
                            title: "Pedido editado",
                            text: "Pedido editado con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al modificar",
                            text: "Ha ocurrido un error. El pedido no se ha podido modificar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
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

function initializeFormValidation() {
    $.validator.setDefaults({
        submitHandler: function (form) {
            var httpMethod;
            if ($(form).find("button[type=submit]").text() === "Crear" || $(form).find("button[type=submit]").text() === "Añadir") {
                httpMethod = "post";
            } else {
                httpMethod = "put";
            }

            switch ($(form).attr("id")) {
                case "order-modal-form":
                    submitOrderModal(httpMethod);
                    break;
                case "line-modal-form":
                    submitLineModal(httpMethod);
                default:
            }
        }
    });

    $.validator.addMethod("validOption", function (value, element) {
        var selectedIndex = element.selectedIndex;
        return selectedIndex !== null && selectedIndex > 0;
    });

    $("#order-modal-form").validate({
        rules: {
            customer: {
                required: true,
                validOption: true
            },
            status: {
                required: true,
                validOption: true
            },
            "shipping-cost": {
                required: true,
                number: true,
                min: 0
            },
            "sales-date": {
                required: true,
                date: true
            },
            "shipping-date": {
                date: true
            },
            "freight-company": {
                required: true,
                validOption: true
            }
        },

        messages: {
            customer: {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            },
            status: {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            },
            "shipping-cost": {
                required: "El campo no puede estar vacío.",
                number: "Debe ser un número real (precio en €).",
                min: "No puede ser un número negativo."
            },
            "sales-date": {
                required: "El campo no puede estar vacío.",
                date: "El formato no es válido. Debe indicarse una fecha real con formato dd/mm/yyyy."
            },
            "shipping-date": {
                date: "El formato no es válido. Debe indicarse una fecha real con formato dd/mm/yyyy."
            },
            "freight-company": {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            }
        }
    });

    $("#line-modal-form").validate({
        rules: {
            product: {
                required: true,
                validOption: true
            },
            quantity: {
                required: true,
                digits: true,
                min: 1
            }
        },

        messages: {
            product: {
                required: "El campo no puede estar vacío.",
                validOption: "La opción seleccionada no es válida. Seleccione otra."
            },
            quantity: {
                required: "El campo no puede estar vacío.",
                digits: "Debe ser un número entero.",
                min: "Debe ser mayor que 0."
            }
        }
    });
}

function init() {
    initializeTableButtonListeners();

    $("#order-modal").on("show.bs.modal", initializeModal);
    $("#order-modal input, #order-modal select").change(function () {
        $("#order-modal #order-modal-button").prop("disabled", false);
    });

    $("#line-modal").on("show.bs.modal", initializeLineModal);
    $("#line-modal input, #line-modal select").change(function () {
        $("#line-modal #line-modal-button").prop("disabled", false);
    });

    initializeFormValidation();
}

$(document).ready(init);
