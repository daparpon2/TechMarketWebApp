package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import es.conselleria.daparpon.techmarket.model.Customer;
import es.conselleria.daparpon.techmarket.model.FreightCompany;
import es.conselleria.daparpon.techmarket.model.OrderStatus;
import es.conselleria.daparpon.techmarket.model.Product;
import es.conselleria.daparpon.techmarket.model.PurchaseOrder;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 17/06/2018.
 *
 * Modified on 14/05/2021 by Daniel Pardo Pont
 *
 * @author Cesardl
 */
public class PurchaseOrderDAO implements CompleteCrudDAO<PurchaseOrder, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderDAO.class);

    @Override
    public Collection<PurchaseOrder> getAll() {
        String sql = "SELECT PO.*, C.NAME, FC.NAME, OS.DESCRIPTION "
                + "FROM PURCHASE_ORDER PO "
                + "INNER JOIN CUSTOMER C on PO.CUSTOMER_ID = C.CUSTOMER_ID "
                + "INNER JOIN FREIGHT_COMPANY FC on PO.FREIGHT_COMPANY = FC.COMPANY_ID "
                + "INNER JOIN ORDER_STATUS OS on PO.STATUS = OS.STATUS_CODE "
                + "ORDER BY PO.ORDER_NUM";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<PurchaseOrder> purchaseOrders = new ArrayList<>();

                while (rs.next()) {
                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setOrderNum(rs.getInt("ORDER_NUM"));

                    Customer customer = new Customer();
                    customer.setName(rs.getString("C.NAME"));
                    purchaseOrder.setCustomer(customer);

                    FreightCompany company = new FreightCompany();
                    company.setName(rs.getString("FC.NAME"));
                    purchaseOrder.setFreightCompany(company);

                    OrderStatus status = new OrderStatus();
                    status.setDescription(rs.getString("OS.DESCRIPTION"));
                    purchaseOrder.setStatus(status);

                    purchaseOrder.setShippingCost(rs.getDouble("SHIPPING_COST"));
                    purchaseOrder.setSalesDate(rs.getDate("SALES_DATE"));
                    purchaseOrder.setShippingDate(rs.getDate("SHIPPING_DATE"));

                    purchaseOrders.add(purchaseOrder);
                }
                LOG.info("Loading {} purchase orders", purchaseOrders.size());
                return purchaseOrders;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public PurchaseOrder findById(final Integer identifier) {
        String sql = "SELECT PO.* FROM PURCHASE_ORDER PO WHERE ORDER_NUM = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifier);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    PurchaseOrder pu = new PurchaseOrder();
                    pu.setOrderNum(rs.getInt("ORDER_NUM"));
                    pu.setShippingCost(rs.getDouble("SHIPPING_COST"));
                    pu.setSalesDate(rs.getDate("SALES_DATE"));
                    pu.setShippingDate(rs.getDate("SHIPPING_DATE"));

                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    pu.setCustomer(c);

                    FreightCompany fc = new FreightCompany();
                    fc.setCompanyId(rs.getInt("FREIGHT_COMPANY"));
                    pu.setFreightCompany(fc);

                    OrderStatus os = new OrderStatus();
                    os.setStatusCode(rs.getInt("STATUS"));
                    pu.setStatus(os);
                    return pu;
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Integer save(final PurchaseOrder purchaseOrder) {
        String sql = "INSERT INTO PURCHASE_ORDER(CUSTOMER_ID, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, FREIGHT_COMPANY, STATUS) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, purchaseOrder.getCustomer().getCustomerId());
            ps.setDouble(2, purchaseOrder.getShippingCost());
            ps.setDate(3, new java.sql.Date(purchaseOrder.getSalesDate().getTime()));
            if (purchaseOrder.getShippingDate() == null) {
                ps.setDate(4, null);
            } else {
                ps.setDate(4, new java.sql.Date(purchaseOrder.getShippingDate().getTime()));
            }
            ps.setInt(5, purchaseOrder.getFreightCompany().getCompanyId());
            ps.setInt(6, purchaseOrder.getStatus().getStatusCode());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);

            try (Statement s = conn.createStatement()) {
                s.execute("SELECT MAX(ORDER_NUM) FROM PURCHASE_ORDER");
                ResultSet rs = s.getResultSet();

                if (rs.next()) {
                    int newNum = rs.getInt("MAX(ORDER_NUM)");
                    LOG.info("Checking new order number: {}", newNum);
                    return newNum;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean update(PurchaseOrder t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(final PurchaseOrder purchaseOrder) {
        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = new DBConnection().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, purchaseOrder.getOrderNum());

            int result = ps.executeUpdate();

            LOG.info("A purchase order has been deleted: {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}
