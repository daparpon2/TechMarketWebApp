package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.MasterCrudDAO;
import es.conselleria.daparpon.techmarket.model.MicroMarket;
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
public class MicroMarketDAO implements MasterCrudDAO<MicroMarket> {

    private static final Logger LOG = LoggerFactory.getLogger(MicroMarketDAO.class);

    @Override
    public Collection<MicroMarket> getAll() {
        String sql = "SELECT ZIP_CODE FROM MICRO_MARKET";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<MicroMarket> microMarkets = new ArrayList<>();

                while (rs.next()) {
                    MicroMarket mm = new MicroMarket();
                    mm.setZipCode(rs.getString("ZIP_CODE"));

                    microMarkets.add(mm);
                }
                LOG.info("Loading {} micro markets", microMarkets.size());
                return microMarkets;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
