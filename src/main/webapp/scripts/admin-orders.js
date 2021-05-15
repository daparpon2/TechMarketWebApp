function initializeTableButtonListeners() {
    $(".line-button").click(manageOrderLines);
    $(".remove-button").click(removeOrder);
}

function showOrderLines(pressedButton) {
    $.ajax({
        url: "order-service",
        type: "get",
        data: {
            "num": pressedButton.parent().parent().parent().find("td:first-child").text()
        },
        dataType: "json",

        success: function (response) {
            var tableData = "<tr><td colspan='5'><table class='table table-hover table-bordered line-table'>"
                    + "<tr><th>Producto</th><th>Cantidad</th><th>Operaciones</th></tr>";
            $.each(response, function(index, element) {
                tableData += "<tr><td>" + element.product.description + "</td>"
                    + "<tr><td>" + element.quantity + "</td>"
                    + "<td></td>"
                    + "</tr>";
            });
            tableData += "</table></td></tr>";
            pressedButton.parent().parent().parent().after(tableData);
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

function init() {
    initializeTableButtonListeners();

    $("#order-modal").on("show.bs.modal", initializeModal);
    $("#order-modal input, #order-modal select").change(function () {
        $("#order-modal #order-modal-button").prop("disabled", false);
    });

    initializeFormValidation();
}

$(document).ready(init);

