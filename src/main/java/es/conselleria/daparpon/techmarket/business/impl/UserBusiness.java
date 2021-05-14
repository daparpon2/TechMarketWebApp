/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.conselleria.daparpon.techmarket.business.impl;

import es.conselleria.daparpon.techmarket.business.TemplateBusiness;
import es.conselleria.daparpon.techmarket.dao.impl.UserDAO;
import es.conselleria.daparpon.techmarket.model.User;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Yo mismo
 */
public class UserBusiness extends TemplateBusiness<User, String> {

    private static final Logger LOG = LoggerFactory.getLogger(UserBusiness.class);

    private static final UserBusiness INSTANCE = new UserBusiness();

    private UserBusiness() {
        setDao(new UserDAO());
    }

    public static UserBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<User> all() {
        LOG.info("Getting all users");
        return dao.getAll();
    }

    @Override
    public User findById(String identifier) {
        LOG.info("Searching user by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(String identifier) {
        LOG.info("Deleting user: {}", identifier);
        User user = new User();
        user.setEmail(identifier);
        return dao.delete(user);
    }

    @Override
    public String save(User user) {
        LOG.info("Adding user: {}", user);
        return dao.save(user);
    }

    @Override
    public boolean update(User user) {
        LOG.info("Updating user: {}", user);
        return dao.update(user);
    }

}
