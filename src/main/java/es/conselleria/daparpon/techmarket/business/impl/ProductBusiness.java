/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.ManufacturerDAO;
import es.conselleria.daparpon.techmarket.dao.impl.ProductCodeDAO;
import es.conselleria.daparpon.techmarket.dao.impl.ProductDAO;
import es.conselleria.daparpon.techmarket.model.Manufacturer;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.ProductCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author Eliana Zapata
 * @since 2009
 */
public class ProductBusiness extends TemplateBusiness<Product, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBusiness.class);

    private static final ProductBusiness INSTANCE = new ProductBusiness();

    private final ProductCodeDAO productCodeDAO;
    private final ManufacturerDAO manufacturerDAO;

    //private constructor to avoid client applications to use constructor
    private ProductBusiness() {
        setDao(new ProductDAO());
        productCodeDAO = new ProductCodeDAO();
        manufacturerDAO = new ManufacturerDAO();
    }

    public static ProductBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Product> all() {
        LOG.info("Getting all products");
        return dao.getAll();
    }

    public Collection<ProductCode> getProductCodes() {
        LOG.info("Getting all product codes");
        return productCodeDAO.getAll();
    }

    public Collection<Manufacturer> getManufacturers() {
        LOG.info("Getting all manufacturers");
        return manufacturerDAO.getAll();
    }

    @Override
    public Product findById(final Integer identifier) {
        LOG.info("Searching product by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    public Collection<Product> findName(String str) {
        LOG.info("Searching product by description: {}", str);
        return ((ProductDAO) dao).findName(str);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting product: {}", identifier);
        Product product = new Product();
        product.setProductId(identifier);
        return dao.delete(product);
    }

    @Override
    public boolean save(Product product) {
        LOG.info("Adding product: {}", product);
        return dao.save(product);
    }

    @Override
    public boolean update(Product product) {
        LOG.info("Updating product: {}", product);
        return dao.update(product);
    }
}
