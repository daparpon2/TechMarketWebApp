package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.MasterCrudDAO;
import es.conselleria.daparpon.techmarket.model.DiscountCode;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 08/07/2018.
 *
 * @author Cesardl
 */
public class DiscountCodeDAO implements MasterCrudDAO<DiscountCode> {

    private static final Logger LOG = LoggerFactory.getLogger(DiscountCodeDAO.class);

    @Override
    public Collection<DiscountCode> getAll() {
        String sql = "SELECT DISCOUNT_CODE, RATE FROM DISCOUNT_CODE";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<DiscountCode> discountCodes = new ArrayList<>();

                while (rs.next()) {
                    DiscountCode dc = new DiscountCode();
                    dc.setDiscountCode(rs.getString("DISCOUNT_CODE").charAt(0));
                    dc.setRate(rs.getDouble("RATE"));

                    discountCodes.add(dc);
                }
                LOG.info("Loading {} discount codes", discountCodes.size());
                return discountCodes;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
