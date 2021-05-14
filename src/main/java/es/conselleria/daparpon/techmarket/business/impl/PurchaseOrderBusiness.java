package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.PurchaseOrderDAO;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderBusiness extends TemplateBusiness<PurchaseOrder, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderBusiness.class);

    private static final PurchaseOrderBusiness INSTANCE = new PurchaseOrderBusiness();

    //private constructor to avoid client applications to use constructor
    private PurchaseOrderBusiness() {
        setDao(new PurchaseOrderDAO());
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
        Date date = new Date();
        purchaseOrder.setSalesDate(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        purchaseOrder.setShippingDate(calendar.getTime());

        LOG.info("Adding purchase order to customer {}", purchaseOrder.getCustomer().getName());
        return dao.save(purchaseOrder);
    }

    @Override
    public boolean update(PurchaseOrder purchaseOrder) {
        LOG.info("Updating purchase order to customer {}", purchaseOrder.getCustomer().getName());
        return dao.update(purchaseOrder);
    }
}
