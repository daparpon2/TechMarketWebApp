function initializeTableButtonListeners() {
    $(".detail-button").click(manageProductDetails);
    $(".remove-button").click(removeProduct);
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


