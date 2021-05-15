/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import es.conselleria.daparpon.techmarket.model.OrderProduct;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class OrderProductDAO implements CompleteCrudDAO<OrderProduct, Integer[]> {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Integer[] save(OrderProduct t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(OrderProduct t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(OrderProduct t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderProduct findById(Integer[] identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<OrderProduct> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<OrderProduct> findByOrderNum(Integer orderNum) {
        String sql = "SELECT P.DESCRIPTION, OP.QUANTITY FROM ORDER_PRODUCT "
                + "INNER JOIN PRODUCT P ON OP.PRODUCT_ID = P.PRODUCT_ID "
                + "WHERE ORDER_NUM = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderNum);

            try (ResultSet rs = ps.executeQuery()) {
                Collection<OrderProduct> orderProducts = new ArrayList<>();
                
                while (rs.next()) {
                    OrderProduct op = new OrderProduct();
                    op.setQuantity(rs.getInt("QUANTITY"));
                    
                    Product p = new Product();
                    p.setDescription(rs.getString("DESCRIPTION"));
                    
                    op.setProduct(p);

                    orderProducts.add(op);
                }
                LOG.info("Loading {} order products", orderProducts.size());
                return orderProducts;
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
    
}
