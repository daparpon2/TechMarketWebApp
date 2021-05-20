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
                        if (response.shippingDate === undefined) {
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
                        $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td").text($("#customer option:selected").text());
                        $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td").text($("#status option:selected").text());
                        $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td+td").text($("#shipping-cost").val().replace(",", ".") + " €");
                        $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td+td+td").text(printDate(new Date($("#sales-date").val())));
                        
                        if($("#shipping-date").val() === "") {
                            $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td+td+td+td").text("");
                        } else {
                            $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td+td+td+td").text(printDate(new Date($("#shipping-date").val())));
                        }
                        
                        $("#order-table tr td:first-child:contains('" + $("#order-num").val() + "')+td+td+td+td+td+td").text($("#freight-company option:selected").text());
                        
                        $("#order-modal").modal("hide");

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


