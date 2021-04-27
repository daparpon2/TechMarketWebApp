<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link" href="site">Inicio</a></li>
<li class="nav-item"><a class="nav-link active" href="#">¿Quién somos?</a></li>
<li class="nav-item"><a class="nav-link" href="products">Nuestros productos</a></li>
</ul>
<%@include file="shards/social-media.html" %>
</div>
</nav>

<div id="home-body">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center text-primary font-weight-bold"">Sobre nosotros</h5>
                        <div class="card-text text-justify">
                            <p>
                                Nuestra empresa nació en la Lima (Perú) del año 1988, como un pequeño minorista familiar
                                especializado en productos tecnológicos. Nuestra ilusión y compromiso con
                                la vanguardia tecnológica hizo que creciéramos hasta convertirnos en una de las empresas
                                líderes del mercado. Más de 85 establecimientos físicos y 300 empleados y empleadas avalan
                                nuestro éxito.
                            </p>
                            <p>
                                En el año 2018 comenzamos nuestra expansión hacia EEUU y Europa, y en 2021 nos adentramos finalmente 
                                en el maravilloso mundo del comercio on-line.
                                Conservar y mantener el carácter del pequeño comercio, la cercanía con el cliente y el trato personalizado son las premisas sobre las que se basa nuestra filosofía.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row d-none d-md-block">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center text-primary font-weight-bold"">¡Echa un vistazo!</h5>
                        <div class="card-text text-justify">
                            <p>
                                Para realizar pedidos se requiere estar registrado/a con una cuenta de usuario.
                                Si dispones de una, puedes iniciar sesión <a class="text-primary" href="site">AQUÍ</a>.
                            </p>
                            <p>
                                Si estás impaciente por comenzar a explorar nuestro extenso catálogo a la de ya, puedes hacerlo pulsando
                                <a class="text-primary" href="products">AQUÍ</a>.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>  
</div>


<%@include file="shards/footer.jsp" %>
<%@include file="shards/libscripts.html" %>
<%@include file="shards/closing.html" %>
