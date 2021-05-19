/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.MasterCrudDAO;
import es.conselleria.daparpon.techmarket.model.OrderStatus;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class OrderStatusDAO implements MasterCrudDAO<OrderStatus> {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusDAO.class);

    @Override
    public Collection<OrderStatus> getAll() {
        String sql = "SELECT * FROM ORDER_STATUS ORDER BY STATUS_CODE";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<OrderStatus> statuses = new ArrayList<>();

                while (rs.next()) {
                    OrderStatus os = new OrderStatus();
                    os.setStatusCode(rs.getInt("STATUS_CODE"));
                    os.setDescription(rs.getString("DESCRIPTION"));

                    statuses.add(os);
                }
                LOG.info("Loading {} statuses", statuses.size());
                return statuses;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
    
}
