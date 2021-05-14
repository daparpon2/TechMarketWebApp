/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.CustomerDAO;
import es.conselleria.daparpon.techmarket.dao.impl.DiscountCodeDAO;
import es.conselleria.daparpon.techmarket.dao.impl.MicroMarketDAO;
import es.conselleria.daparpon.techmarket.model.Customer;
import es.conselleria.daparpon.techmarket.model.DiscountCode;
import es.conselleria.daparpon.techmarket.model.MicroMarket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author Cesardl
 */
public class CustomerBusiness extends TemplateBusiness<Customer, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBusiness.class);

    private static final CustomerBusiness INSTANCE = new CustomerBusiness();

    private final DiscountCodeDAO discountCodeDAO;
    private final MicroMarketDAO microMarketDAO;

    //private constructor to avoid client applications to use constructor
    private CustomerBusiness() {
        setDao(new CustomerDAO());
        discountCodeDAO = new DiscountCodeDAO();
        microMarketDAO = new MicroMarketDAO();
    }

    public static CustomerBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Customer> all() {
        LOG.info("Getting all customers");
        return dao.getAll();
    }

    public Collection<DiscountCode> getDiscountCodes() {
        LOG.info("Getting all discount codes");
        return discountCodeDAO.getAll();
    }

    public Collection<MicroMarket> getMicroMarkets() {
        LOG.info("Getting all micro markets");
        return microMarketDAO.getAll();
    }

    @Override
    public Customer findById(final Integer identifier) {
        LOG.info("Searching customer by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting customer: {}", identifier);
        Customer product = new Customer();
        product.setCustomerId(identifier);
        return dao.delete(product);
    }

    @Override
    public Integer save(Customer customer) {
        LOG.info("Adding customer: {}", customer);
        return dao.save(customer);
    }

    @Override
    public boolean update(Customer customer) {
        LOG.info("Updating customer: {}", customer);
        return dao.update(customer);
    }
}
