/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import es.conselleria.daparpon.techmarket.model.User;
import es.conselleria.daparpon.techmarket.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class UserDAO implements CompleteCrudDAO<User, String> {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);

    @Override
    public boolean save(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User findById(String identifier) {
        String sql = "SELECT * "
                + "FROM USER "
                + "WHERE EMAIL = ?";

        LOG.debug(DBConnection.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, identifier);

            try (ResultSet rs = ps.executeQuery()) {
                User user = new User();
                if (rs.next()) {
                    user.setEmail(rs.getString("EMAIL"));
                    user.setPassword(rs.getString("PASSWORD"));
                    user.setAdmin(rs.getBoolean("IS_ADMIN"));
                }
                return user;
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
