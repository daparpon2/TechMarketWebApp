<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link" href="site">Inicio</a></li>
<li class="nav-item"><a class="nav-link active" href="#">Productos</a></li>
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
                        <h5 class="card-title text-center text-primary font-weight-bold">Administración de productos</h5>
                        <div class="card-text">
                            <div class="table-responsive">
                                <button class="btn btn-outline-secondary add-button d-none d-md-block" data-toggle="modal" data-target="#modal" data-action="add">
                                    <%@include file="shards/bootstrap-icons/add-icon.html" %>
                                    <span>Añadir producto</span>
                                </button>
                                <button class="btn btn-secondary add-button d-block d-md-none" data-toggle="modal" data-target="#modal" data-action="add">
                                    <%@include file="shards/bootstrap-icons/add-icon.html" %>
                                    <span>Añadir producto</span>
                                </button>
                                <table class="table table-hover table-bordered">
                                    <tr>
                                        <th>Imagen</th>
                                        <th>ID</th>
                                        <th>Descripción</th>
                                        <th>Precio</th>
                                        <th>Operaciones</th>
                                    </tr>
                                    <c:forEach items="${products}" var="product">
                                        <tr>
                                            <td class="text-center"><img class="table-image" src="resources/images/products/${product.image}"/></td>
                                            <td>${product.productId}</td>
                                            <td class="text-truncate">${product.description}</td>
                                            <td>${product.purchaseCost} €</td>
                                            <td>
                                                <span>
                                                    <button class="btn btn-outline-primary detail-button d-none d-md-inline">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/show-icon.html" %></span>
                                                        <span class="label">Detalles</span>
                                                    </button>
                                                    <button class="btn btn-outline-secondary edit-button d-none d-md-inline" data-toggle="modal" data-target="#modal" data-action="edit">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/edit-icon.html" %></span>
                                                        <span class="label">Editar</span>
                                                    </button>
                                                    <button class="btn btn-outline-danger remove-button d-none d-md-inline">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/remove-icon.html" %></span>
                                                        <span class="label">Eliminar</span>
                                                    </button>
                                                    <button class="btn btn-primary detail-button d-block d-md-none">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/show-icon.html" %></span>
                                                        <span class="label">Detalles</span>
                                                    </button>
                                                    <button class="btn btn-secondary edit-button d-block d-md-none" data-toggle="modal" data-target="#modal" data-action="edit">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/edit-icon.html" %></span>
                                                        <span class="label">Editar</span>
                                                    </button>
                                                    <button class="btn btn-danger remove-button d-block d-md-none">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/remove-icon.html" %></span>
                                                        <span class="label">Eliminar</span>
                                                    </button>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-primary font-weight-bold" id="modal-title"></h5>
                                <button type="button" class="close" data-dismiss="modal">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <form>
                                <div class="modal-body">
                                    <!--
                                    <div class="form-group">
                                        <label for="image" class="col-form-label">Imagen</label>
                                        <input type="file" class="form-control" id="image"/>
                                    </div>
                                    -->
                                    <div class="form-group">
                                        <label for="description" class="col-form-label">Descripción<span class="obligatorio">*</span></label>
                                        <input type="text" class="form-control" id="description" name="description"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="price" class="col-form-label">Precio<span class="obligatorio">*</span></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">€</span>
                                            </div>
                                            <input type="number" class="form-control" id="price" name="price" min="0" step="0.1"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="manufacturer" class="col-form-label">Fabricante<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="manufacturer" name="manufacturer"></select>
                                    </div>
                                    <div class="form-group">
                                        <label for="category" class="col-form-label">Categoría<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="category" name="category"></select>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" id="available"/>
                                        <label class="form-check-label" for="available">Disponible</label>
                                    </div>
                                    <div class="form-group">
                                        <label for="quantity" class="col-form-label">Cantidad disponible<span class="obligatorio">*</span></label>
                                        <input type="number" class="form-control" id="quantity" name="quantity" min="1" step="1" disabled="disabled"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="markup" class="col-form-label">Margen de beneficio<span class="obligatorio">*</span></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">%</span>
                                            </div>
                                            <input type="number" class="form-control" id="markup" name="markup" min="0" step="0.1"/>
                                        </div>
                                    </div>
                                    <small class="form-text">
                                        El descuento aplicable sobre el precio se asociará de forma automática en función de la categoría del producto.
                                    </small>
                                    <%@include file="shards/form-footer.html" %>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" id="modal-button" disabled="disabled"></button>
                                    <button type="button" class="btn btn-light" data-dismiss="modal">Cerrar</button>
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
    <script src="scripts/admin-products.js"></script>
    <%@include file="shards/closing.html" %>