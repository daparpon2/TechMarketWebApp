package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.OrderProductDAO;
import es.conselleria.daparpon.techmarket.model.OrderProduct;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel Pardo Pont
 */
public class OrderProductBusiness extends TemplateBusiness<OrderProduct, Integer[]> {
    
    private static final Logger LOG = LoggerFactory.getLogger(OrderProductBusiness.class);

    private static final OrderProductBusiness INSTANCE = new OrderProductBusiness();
    
    private OrderProductBusiness() {
        setDao(new OrderProductDAO());
    }

    public static OrderProductBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<OrderProduct> all() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer[] save(OrderProduct line) {
        LOG.info("Adding product to order {}", line.getOrder().getOrderNum());
        return dao.save(line);
    }

    @Override
    public boolean update(OrderProduct line) {
        LOG.info("Updating product {} from order {}", line.getProduct().getProductId(), line.getOrder().getOrderNum());
        return dao.update(line);
    }

    @Override
    public OrderProduct findById(Integer[] identifier) {
        LOG.info("Searching order line of product {} in order {}", identifier[1], identifier[0]);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(Integer[] identifier) {
        LOG.info("Deleting order line: Order number {}, product ID {}", identifier[0], identifier[1]);
        OrderProduct line = new OrderProduct();
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderNum(identifier[0]);
        line.setOrder(order);
        Product product = new Product();
        product.setProductId(identifier[1]);
        line.setProduct(product);
        return dao.delete(line);
    }
    
}
