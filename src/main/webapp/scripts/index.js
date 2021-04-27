function init() {
    $.validator.setDefaults({
        submitHandler: function () {
            $.ajax({
                url: "login",
                type: "post",
                data: {
                    "email": $("#email").val(),
                    "pwd": $("#pwd").val()
                },
                dataType: "json",

                success: function (response) {
                    if (response.login === "true") {
                        switch (response.usertype.toUpperCase()) {
                            case "ADMIN" :
                                $(location).attr("href", "adminhome");
                                break;
                            default:
                                Swal.fire({
                                    icon: "success",
                                    title: "Logueado correctamente como cliente",
                                    text: "Esta función aún no está disponible. ¡Gracias por su paciencia!",
                                    confirmButtonText: "Aceptar"
                                });
                        }
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error al iniciar sesión",
                            text: "Usuario o contraseña erróneos. Por favor, vuelva a intentarlo.",
                            confirmButtonText: "Aceptar"
                        });
                    }
                },

                error: function (xhr, status) {
                    Swal.fire({
                        icon: "error",
                        title: "Error de conexión",
                        text: "Ha ocurrido un error al conectar con la base de datos. Por favor, vuelva a intentarlo o contacte con el equipo de administración.",
                        confirmButtonText: "Aceptar"
                    });
                }
            });
        }
    });

    $("#loginform").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            pwd: {
                required: true
            }
        },

        messages: {
            email: {
                required: "El campo no puede estar vacío.",
                email: "El formato del correo electrónico no es válido."
            },

            pwd: {
                required: "El campo no puede estar vacío."
            }
        }
    });
}

$(document).ready(init);


