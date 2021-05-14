<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link active" href="#">Inicio</a></li>
<li class="nav-item"><a class="nav-link" href="about.jsp">¿Quién somos?</a></li>
<li class="nav-item"><a class="nav-link" href="products">Nuestros productos</a></li>
</ul>
<%@include file="shards/social-media.html" %>
</div>
</nav>

<div class="container-fluid" id="home-body">
    <div class="row">
        <div class="col-12 order-2 col-md-6 order-md-1">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center text-primary font-weight-bold">¡Bienvenido/a a Tech Market!</h5>
                    <h6 class="card-title text-center text-primary font-weight-bold">Especialistas en productos tecnológicos</h6>
                    <div class="card-text text-justify">
                        <p>
                            En nuestra tienda encontrarás todos los productos tecnológicos que necesitas o que siempre has deseado.
                            Desde equipos informáticos a componentes accesorios, e incluso el mejor software como suites ofimáticas
                            y antivirus, la tecnología más puntera está en Tech Market a tu libre disposición.
                        </p>
                        <p>
                            Y si tienes una empresa, ¡no te preocupes! En Tech Market somos especialistas también en el mercado mayorista y
                            disponemos de grandes ofertas y precios adaptados, así como asesoría personalizada y servicio de instalación y montaje.
                            Independientemente de si tu compañía necesita servidores, dispositivos de red, equipos individuales o
                            smartphones, en Tech Market disponemos de todo para que puedas mantener tu negocio
                            a la vanguardia de los nuevos tiempos. Además, ¡hacemos descuentos a socios y por compra de lotes!
                        </p>
                        <p>
                            Como ves, no importa cuáles sean tus necesidades, nosotros nos ocuparemos de satisfacerlas.
                            Esperamos que disfrutes de
                            nuestra tienda web y te sientas como en casa mientras buscas esos productos que tanto te interesan.
                            ¡Gracias por elegirnos!
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 order-1 col-md-4 offset-md-2 order-md-2">
            <div class="card">
                <div class="card-body">
                    <h6 class="card-title text-center text-primary font-weight-bold">¿Eres cliente de nuestra empresa? ¿O quizá trabajas con nosotros?</h6>
                    <h5 class="card-title text-center text-primary font-weight-bold">¡Identifícate para acceder a todas nuestras opciones!</h5>
                    <div class="card-text">
                        <form id="loginform">
                            <div class="form-group">
                                <label for="email">Correo electrónico<span class="obligatorio">*</span></label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Ingresa tu correo"/>
                            </div>
                            <div class="form-group">
                                <label for="pwd">Contraseña<span class="obligatorio">*</span></label>
                                <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Ingresa tu contraseña"/>
                                <%@include file="shards/form-footer.html" %>
                                <!-- <small class="form-text"><a class="text-primary" href="">¿Has olvidado tu contraseña?</a></small> -->
                            </div>
                            <div>
                                <button type="submit" class="btn btn-outline-primary btn-block d-none d-lg-block">Iniciar sesión</button>
                                <button type="submit" class="btn btn-primary btn-block d-block d-lg-none">Iniciar sesión</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="shards/footer.jsp" %>
<%@include file="shards/libscripts.html" %>
<script src="scripts/index.js"></script>
<%@include file="shards/closing.html" %>