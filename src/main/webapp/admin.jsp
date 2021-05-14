<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link active" href="#">Inicio</a></li>
<li class="nav-item"><a class="nav-link" href="products">Productos</a></li>
<li class="nav-item"><a class="nav-link" href="orders">Pedidos</a></li>
</ul>
<%@include file="shards/social-media.html" %>
</div>
<%@include file="shards/user-session.jsp" %>
</nav>

<div id="admin-body">
    <div class="container-fluid">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center text-primary font-weight-bold">Vista de administración</h5>
                        <div class="card-text">
                            <p>
                                Elige uno de los siguientes elementos para acceder a su correspondiente página de administración:
                            </p>
                            <ul class="list-unstyled text-center text-md-left">
                                <li><a class="text-primary d-none d-md-block" href="products">Administrar productos</a></li>
                                <li><a class="text-primary d-none d-md-block" href="orders">Administrar órdenes de pedido</a></li>
                                <li><button class="btn btn-primary d-block d-md-none"><a class="text-light" href="products">Administrar productos</a></li>
                                <li><button class="btn btn-primary d-block d-md-none"><a class="text-light" href="orders">Administrar órdenes de pedido</a></li>
                            </ul>
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
