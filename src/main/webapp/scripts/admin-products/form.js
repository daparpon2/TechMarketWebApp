function initializeFormValidation() {
    $.validator.setDefaults({
        submitHandler: function () {
            var httpMethod;
            var quantity;
            if ($(this.submitButton).text() === "Crear") {
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
                                $("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td").text($("#price").val().replace(",", ".") + " €");

                                if ($("#product-table tr td:nth-child(2):contains('" + $("#product-id").val() + "')+td+td+td .detail-button:visible .label").text() === "Ocultar") {
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

