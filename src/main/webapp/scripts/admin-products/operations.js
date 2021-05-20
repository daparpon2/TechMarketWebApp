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
                        if (pressedButton.parent().find(".detail-button:visible .label").text() === "Ocultar") {
                            pressedButton.parent().find(".detail-button:visible").click();
                        }
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

