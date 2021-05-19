/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.OrderProductDAO;
import es.conselleria.daparpon.techmarket.model.OrderProduct;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class OrderProductBusiness extends TemplateBusiness<OrderProduct, Integer[]> {
    
    private static final Logger LOG = LoggerFactory.getLogger(OrderProductBusiness.class);

    private static final OrderProductBusiness INSTANCE = new OrderProductBusiness();
    
    //private constructor to avoid client applications to use constructor
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
    public boolean update(OrderProduct element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderProduct findById(Integer[] identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer[] identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
