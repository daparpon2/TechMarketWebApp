/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.MasterCrudDAO;
import es.conselleria.daparpon.techmarket.model.FreightCompany;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import java.util.Collection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class FreightCompanyDAO implements MasterCrudDAO<FreightCompany> {

    private static final Logger LOG = LoggerFactory.getLogger(FreightCompanyDAO.class);

    @Override
    public Collection<FreightCompany> getAll() {
        String sql = "SELECT * FROM FREIGHT_COMPANY ORDER BY NAME";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
                Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<FreightCompany> companies = new ArrayList<>();

                while (rs.next()) {
                    FreightCompany fc = new FreightCompany();
                    fc.setCompanyId(rs.getInt("COMPANY_ID"));
                    fc.setName(rs.getString("NAME"));

                    companies.add(fc);
                }
                LOG.info("Loading {} freight companies", companies.size());
                return companies;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
