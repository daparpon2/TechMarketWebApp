function loadProductSelect(modal, table) {
    $.ajax({
        url: "product-service",
        type: "get",
        dataType: "json",

        success: function (response) {
            modal.find("#product").append("<option value=''></option>");
            var addedProductCount = 0;
            $.each(response, function (index, element) {
                var found = false;
                table.find("tr").each(function () {
                    if ($(this).attr("id") === element.productId.toString()) {
                        found = true;
                        return false;
                    }
                });
                if (!found) {
                    modal.find("#product").append("<option value='" + element.productId + "'>" + element.description + "</option>");
                    addedProductCount++;
                }
            });

            if (addedProductCount === 0) {
                modal.find("#product").prop("disabled", true);
                modal.find("#quantity").prop("disabled", true);
                Swal.fire({
                    icon: "error",
                    title: "No hay más productos disponibles",
                    text: "Todos los productos disponibles ya han sido cargados en este pedido. Si desea modificar la cantidad asignada, elija la opción Editar.",
                    confirmButtonText: "Aceptar",
                    confirmButtonColor: "#591259"
                });
            }
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
    $("#product").prop("disabled", false);
    $("#quantity").prop("disabled", false);
    $("#line-modal #line-modal-button").prop("disabled", true);

    loadProductSelect(modal, table);
}

function initializeEditionLineModal(modal, dataRow) {
    modal.find("#line-modal-title").text("Editar producto en pedido");
    modal.find("#line-modal-button").text("Guardar cambios");
    modal.find("#line-order-num").text(dataRow.parent().parent().attr("id"));

    modal.find("select option").remove();
    $("#line-modal #line-modal-button").prop("disabled", true);

    $.ajax({
        url: "line-service",
        type: "get",
        dataType: "json",
        data: {
            "num": modal.find("#line-order-num").text(),
            "productId": dataRow.attr("id")
        },

        success: function (response) {
            $("#product").append("<option value='" + response.product.productId + "'>" + response.product.description + "</option>")
            $("#product").prop("disabled", true);
            $("#quantity").val(response.quantity);
            $("#quantity").prop("disabled", false);
        },

        error: function (xhr, status) {
            Swal.fire({
                icon: "error",
                title: "Error de conexión",
                text: "Ha ocurrido un error al conectar con la base de datos para cargar la línea de pedido. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#591259"
            });
        }
    });
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
                        var table = $("#" + $("#line-order-num").text());
                        table.find("tr#" + $("#product").val() + " td:nth-child(2)").text($("#quantity").val());
                        $("#line-modal").modal("hide");

                        Swal.fire({
                            icon: "success",
                            title: "Línea de pedido editada",
                            text: "Línea de pedido editada con éxito.",
                            confirmButtonText: "Aceptar",
                            confirmButtonColor: "#591259"
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al modificar",
                            text: "Ha ocurrido un error. La línea de pedido no se ha podido modificar. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
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


