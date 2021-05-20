function initializeTableButtonListeners() {
    $(".line-button").click(manageOrderLines);
    $(".remove-button").click(removeOrder);
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


