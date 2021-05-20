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
 * @author Daniel Pardo Pont
 */
public class OrderProductDAO implements CompleteCrudDAO<OrderProduct, Integer[]> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Integer[] save(OrderProduct line) {
        String sql = "INSERT INTO ORDER_PRODUCT(ORDER_NUM, PRODUCT_ID, QUANTITY) "
                + "VALUES(?, ?, ?)";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, line.getOrder().getOrderNum());
            ps.setInt(2, line.getProduct().getProductId());
            ps.setInt(3, line.getQuantity());

            int result = ps.executeUpdate();
            LOG.debug("Number of affected rows {}", result);

            return new Integer[]{result};
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean update(OrderProduct line) {
        String sql = "UPDATE ORDER_PRODUCT SET QUANTITY = ? "
                + "WHERE ORDER_NUM = ? AND PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, line.getQuantity());
            ps.setInt(2, line.getOrder().getOrderNum());
            ps.setInt(3, line.getProduct().getProductId());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(OrderProduct line) {
        String sql = "DELETE FROM ORDER_PRODUCT WHERE ORDER_NUM = ? AND PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, line.getOrder().getOrderNum());
            ps.setInt(2, line.getProduct().getProductId());

            int result = ps.executeUpdate();

            if (result > 0) {
                LOG.info("An order line has been deleted: {}", result);
                return true;
            } else {
                LOG.info("Order line could not be deleted: {}", result);
                return false;
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public OrderProduct findById(Integer[] identifier) {
        String sql = "SELECT OP.*, P.DESCRIPTION FROM ORDER_PRODUCT OP "
                + "INNER JOIN PRODUCT P ON OP.PRODUCT_ID = P.PRODUCT_ID "
                + "WHERE OP.ORDER_NUM = ? AND OP.PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifier[0]);
            ps.setInt(2, identifier[1]);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    OrderProduct op = new OrderProduct();
                    op.setQuantity(rs.getInt("QUANTITY"));
                    
                    Product p = new Product();
                    p.setProductId(rs.getInt("OP.PRODUCT_ID"));
                    p.setDescription(rs.getString("DESCRIPTION"));
                    op.setProduct(p);
                    
                    return op;
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Collection<OrderProduct> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Collection<OrderProduct> findByOrderNum(Integer orderNum) {
        String sql = "SELECT OP.*, P.DESCRIPTION FROM ORDER_PRODUCT OP "
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
                    p.setProductId(rs.getInt("PRODUCT_ID"));
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
