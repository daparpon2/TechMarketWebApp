package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.model.OrderProduct;
import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.CustomerDAO;
import es.conselleria.daparpon.techmarket.dao.impl.FreightCompanyDAO;
import es.conselleria.daparpon.techmarket.dao.impl.OrderProductDAO;
import es.conselleria.daparpon.techmarket.dao.impl.OrderStatusDAO;
import es.conselleria.daparpon.techmarket.dao.impl.PurchaseOrderDAO;
import es.conselleria.daparpon.techmarket.model.Customer;
import es.conselleria.daparpon.techmarket.model.FreightCompany;
import es.conselleria.daparpon.techmarket.model.OrderStatus;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;

/**
 * Created on 17/06/2018.
 *
 * Modified on 14/05/2021 by Daniel Pardo Pont
 *
 * @author Cesardl
 */
public class PurchaseOrderBusiness extends TemplateBusiness<PurchaseOrder, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderBusiness.class);

    private static final PurchaseOrderBusiness INSTANCE = new PurchaseOrderBusiness();

    private final CustomerDAO customerDAO;
    private final FreightCompanyDAO freightCompanyDAO;
    private final OrderStatusDAO orderStatusDAO;
    private final OrderProductDAO orderProductDAO;

    //private constructor to avoid client applications to use constructor
    private PurchaseOrderBusiness() {
        setDao(new PurchaseOrderDAO());
        customerDAO = new CustomerDAO();
        freightCompanyDAO = new FreightCompanyDAO();
        orderStatusDAO = new OrderStatusDAO();
        orderProductDAO = new OrderProductDAO();
    }

    public static PurchaseOrderBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<PurchaseOrder> all() {
        LOG.info("Getting all purchase orders");
        return dao.getAll();
    }

    @Override
    public PurchaseOrder findById(final Integer identifier) {
        LOG.info("Searching purchase order by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting purchase order: {}", identifier);
        PurchaseOrder product = new PurchaseOrder();
        product.setOrderNum(identifier);
        return dao.delete(product);
    }

    @Override
    public Integer save(PurchaseOrder purchaseOrder) {
        LOG.info("Adding purchase order to customer {}", purchaseOrder.getCustomer().getCustomerId());
        return dao.save(purchaseOrder);
    }

    @Override
    public boolean update(PurchaseOrder purchaseOrder) {
        LOG.info("Updating purchase order to customer {}", purchaseOrder.getCustomer().getName());
        return dao.update(purchaseOrder);
    }

    public Collection<OrderProduct> findOrderProducts(Integer orderNum) {
        LOG.info("Getting all order products");
        return orderProductDAO.findByOrderNum(orderNum);
    }

    public Collection<Customer> getCustomers() {
        LOG.info("Getting all customers");
        return customerDAO.getAll();
    }

    public Collection<FreightCompany> getFreightCompanies() {
        LOG.info("Getting all freight companies");
        return freightCompanyDAO.getAll();
    }

    public Collection<OrderStatus> getStatus() {
        LOG.info("Getting all order statuses");
        return orderStatusDAO.getAll();
    }
}
