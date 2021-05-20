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
                    if (response) {
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


