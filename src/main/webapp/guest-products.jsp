<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link" href="site">Inicio</a></li>
<li class="nav-item"><a class="nav-link" href="about.jsp">¿Quién somos?</a></li>
<li class="nav-item"><a class="nav-link active" href="#">Nuestros productos</a></li>
</ul>
<%@include file="shards/social-media.html" %>
</div>
</nav>

<div id="home-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <div class="card" style="background-color:rgba(255,255,255,1)">
                    <div class="card-body">
                        <h5 class="card-title text-center text-primary font-weight-bold">Nuestros productos</h5>
                        <div class="card-text">
                            <div class="text-center">
                                <p>
                                    Para ir abriendo boca, te invitamos a echar un vistazo a nuestro escaparate aquí debajo.
                                </p>
                                <p>
                                    Recuerda: si deseas comprar alguno de estos productos, debes iniciar sesión
                                    <button class="btn btn-primary d-inline d-md-none"><a class="text-light" href="site">Aquí</a></button>
                                    <a class="text-primary d-none d-md-inline" href="site">AQUÍ</a>.
                                </p>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered" id="product-table">
                                    <tr>
                                        <th>Imagen</th>
                                        <th>ID</th>
                                        <th>Descripción</th>
                                        <th>Precio</th>
                                    </tr>
                                    <c:forEach items="${products}" var="product">
                                        <tr>
                                            <td class="text-center"><img class="table-image" src="resources/images/products/${product.image}"/></td>
                                            <td>${product.productId}</td>
                                            <td class="text-truncate">${product.description}</td>
                                            <td>${product.purchaseCost} €</td>
                                        </tr>
                                    </c:forEach>
                                </table>
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