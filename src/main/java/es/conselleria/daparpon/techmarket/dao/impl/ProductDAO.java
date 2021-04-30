package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import es.conselleria.daparpon.techmarket.model.Manufacturer;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.ProductCode;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductDAO implements CompleteCrudDAO<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Collection<Product> getAll() {
        String sql = "SELECT PRODUCT_ID, DESCRIPTION FROM PRODUCT WHERE QUANTITY_ON_HAND <> 0 AND AVAILABLE = TRUE";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<Product> products = new ArrayList<>();

                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("PRODUCT_ID"));
                    p.setDescription(rs.getString("DESCRIPTION"));

                    products.add(p);
                }
                LOG.info("Loading {} products", products.size());
                return products;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean save(final Product product) {
        String sql = "INSERT INTO PRODUCT(PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());
            ps.setInt(2, product.getManufacturer().getManufacturerId());
            ps.setString(3, product.getProductCode().getProdCode());
            ps.setDouble(4, product.getPurchaseCost());
            ps.setInt(5, product.getQuantityOnHand());
            ps.setDouble(6, product.getMarkup());
            ps.setBoolean(7, product.isAvailable());
            ps.setString(8, product.getDescription());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(final Product product) {
        String sql = "UPDATE PRODUCT SET MANUFACTURER_ID = ?, PRODUCT_CODE = ?, PURCHASE_COST = ?, QUANTITY_ON_HAND = ?, MARKUP = ?, AVAILABLE = ?, DESCRIPTION = ? " +
                "WHERE PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getManufacturer().getManufacturerId());
            ps.setString(2, product.getProductCode().getProdCode());
            ps.setDouble(3, product.getPurchaseCost());
            ps.setInt(4, product.getQuantityOnHand());
            ps.setDouble(5, product.getMarkup());
            ps.setBoolean(6, product.isAvailable());
            ps.setString(7, product.getDescription());
            ps.setInt(8, product.getProductId());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Product findById(final int identifier) {
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.MARKUP, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE "
                + "FROM PRODUCT P "
                + "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID "
                + "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE "
                + "WHERE PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifier);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return parseProduct(rs);
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Collection<Product> buscarNombre(final String description) {
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.MARKUP, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE "
                + "FROM PRODUCT P "
                + "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID "
                + "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE "
                + "WHERE UPPER(P.DESCRIPTION) LIKE ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + description.toUpperCase() + "%");

            Collection<Product> vProducts = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    vProducts.add(parseProduct(rs));
                }
            }

            return vProducts;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(final Product product) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());

            int result = ps.executeUpdate();

            LOG.info("A product has been deleted: {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    private Product parseProduct(final ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setDescription(rs.getString("DESCRIPTION"));
        p.setPurchaseCost(rs.getDouble("PURCHASE_COST"));
        p.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
        p.setMarkup(rs.getDouble("MARKUP"));
        p.setAvailable(rs.getBoolean("AVAILABLE"));

        Manufacturer m = new Manufacturer();
        m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        m.setName(rs.getString("NAME"));
        p.setManufacturer(m);

        ProductCode pc = new ProductCode();
        pc.setProdCode(rs.getString("PROD_CODE"));
        p.setProductCode(pc);

        return p;
    }
}
