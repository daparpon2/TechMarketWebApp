function initializeFormValidation() {
    $.validator.setDefaults({
        submitHandler: function (form) {
            var httpMethod;
            if ($(this.submitButton).text() === "Crear" || $(this.submitButton).text() === "Añadir") {
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
                number: true,
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
                number: "Debe ser un número entero.",
                digits: "Debe ser un número entero.",
                min: "Debe ser mayor que 0."
            }
        }
    });
}


