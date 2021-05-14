package es.conselleria.daparpon.techmarket.business;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created on 07/07/2018.
 *
 * @param <T>
 * @author Cesardl
 */
public abstract class TemplateBusiness<T, E> {

    protected CompleteCrudDAO<T, E> dao;

    public void setDao(CompleteCrudDAO<T, E> dao) {
        this.dao = dao;
    }

    public abstract Collection<T> all();

    public abstract E save(T element);
    
    public abstract boolean update(T element);

    public abstract T findById(E identifier);

    public abstract boolean delete(E identifier);
}
