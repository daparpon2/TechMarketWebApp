<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt"%>
<%@include file="shards/opening.html" %>
<%@include file="shards/navbar-header.html" %>
<li class="nav-item"><a class="nav-link" href="site">Inicio</a></li>
<li class="nav-item"><a class="nav-link" href="products">Productos</a></li>
<li class="nav-item"><a class="nav-link active" href="#">Pedidos</a></li>
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
                        <h5 class="card-title text-center text-primary font-weight-bold">Administración de pedidos</h5>
                        <div class="card-text">
                            <div class="table-responsive">
                                <button class="btn btn-outline-secondary add-button d-none d-md-block" data-toggle="modal" data-target="#order-modal" data-action="add">
                                    <%@include file="shards/bootstrap-icons/add-icon.html" %>
                                    <span>Añadir pedido</span>
                                </button>
                                <button class="btn btn-secondary add-button d-block d-md-none" data-toggle="modal" data-target="#order-modal" data-action="add">
                                    <%@include file="shards/bootstrap-icons/add-icon.html" %>
                                    <span>Añadir pedido</span>
                                </button>
                                <table class="table table-hover table-bordered" id="order-table">
                                    <tr>
                                        <th>Número</th>
                                        <th>Cliente</th>
                                        <th>Estado</th>
                                        <th>Coste de envío</th>
                                        <th>Fecha de venta</th>
                                        <th>Fecha de envío</th>
                                        <th>Transportista</th>
                                        <th>Operaciones</th>
                                    </tr>
                                    <c:forEach items="${orders}" var="order">
                                        <tr>
                                            <td>${order.orderNum}</td>
                                            <td>${order.customer.name}</td>
                                            <td>${order.status.description}</td>
                                            <td>${order.shippingCost} €</td>
                                            <td><fmt:formatDate type="date" pattern="dd/MM/yyyy" value="${order.salesDate}"/></td>
                                            <td><fmt:formatDate type="date" pattern="dd/MM/yyyy" value="${order.shippingDate}"/></td>
                                            <td>${order.freightCompany.name}</td>
                                            <td>
                                                <span>
                                                    <button class="btn btn-outline-primary line-button d-none d-md-inline">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/list-icon.html" %></span>
                                                        <span class="label">Líneas</span>
                                                    </button>
                                                    <button class="btn btn-outline-secondary edit-button d-none d-md-inline" data-toggle="modal" data-target="#order-modal" data-action="edit">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/edit-icon.html" %></span>
                                                        <span class="label">Editar</span>
                                                    </button>
                                                    <button class="btn btn-outline-danger remove-button d-none d-md-inline">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/remove-icon.html" %></span>
                                                        <span class="label">Eliminar</span>
                                                    </button>
                                                    <button class="btn btn-primary line-button d-block d-md-none">
                                                        <span class='icon'><%@include file="shards/bootstrap-icons/list-icon.html" %></span>
                                                        <span class="label">Líneas</span>
                                                    </button>
                                                    <button class="btn btn-secondary edit-button d-block d-md-none" data-toggle="modal" data-target="#order-modal" data-action="edit">
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

                <div class="modal fade" id="order-modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-primary font-weight-bold" id="order-modal-title"></h5>
                                <button type="button" class="close" data-dismiss="modal">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <form id="order-modal-form">
                                <div class="modal-body">
                                    <input type="text" id="order-num" hidden="hidden"/>
                                    <div class="form-group">
                                        <label for="customer" class="col-form-label">Cliente<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="customer" name="customer"></select>
                                    </div>
                                    <div class="form-group">
                                        <label for="status" class="col-form-label">Estado<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="status" name="status"></select>
                                    </div>
                                    <div class="form-group">
                                        <label for="shipping-cost" class="col-form-label">Coste de envío<span class="obligatorio">*</span></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">€</span>
                                            </div>
                                            <input type="number" class="form-control" id="shipping-cost" name="shipping-cost" min="0" step="0.01"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sales-date" class="col-form-label">Fecha de venta<span class="obligatorio">*</span></label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <%@include file="shards/bootstrap-icons/calendar-icon.html" %>
                                            </div>
                                            <input type="date" class="form-control" id="sales-date" name="sales-date" placeholder="dd/mm/aaaa"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="shipping-date" class="col-form-label">Fecha de envío</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <%@include file="shards/bootstrap-icons/calendar-icon.html" %>
                                            </div>
                                            <input type="date" class="form-control" id="shipping-date" name="shipping-date" placeholder="dd/mm/aaaa"/>
                                        </div>
                                    </div>        
                                    <div class="form-group">
                                        <label for="freight-company" class="col-form-label">Transportista<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="freight-company" name="freight-company"></select>
                                    </div>
                                    <%@include file="shards/form-footer.html" %>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" id="order-modal-button" disabled="disabled"></button>
                                    <button type="button" class="btn btn-light" data-dismiss="modal">Cerrar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                                
                <div class="modal fade" id="line-modal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title text-primary font-weight-bold" id="line-modal-title"></h5>
                                <button type="button" class="close" data-dismiss="modal">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <form id="line-modal-form">
                                <div class="modal-body">
                                    <span id="line-order-num" hidden="hidden"></span>
                                    <div class="form-group">
                                        <label for="product" class="col-form-label">Producto<span class="obligatorio">*</span></label>
                                        <select class="form-control" id="product" name="product"></select>
                                    </div>
                                    <div class="form-group">
                                        <label for="quantity" class="col-form-label">Cantidad<span class="obligatorio">*</span></label>
                                        <input type="number" class="form-control" id="quantity" name="quantity" min="0" step="1"/>
                                    </div>
                                    <%@include file="shards/form-footer.html" %>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" id="line-modal-button" disabled="disabled"></button>
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
    <%@include file="scripts/admin-orders-scripts.html" %>
    <%@include file="shards/closing.html" %>
