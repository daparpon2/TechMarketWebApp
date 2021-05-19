package es.conselleria.daparpon.techmarket.dao;

import java.util.Collection;

/**
 * Created on 01/07/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface CompleteCrudDAO<T, E> extends MasterCrudDAO<T> {

    /**
     * Obtain specific element.
     *
     * @param identifier element identifier
     * @return an element with code
     */
    T findById(E identifier);
    
    /**
     * Save an element.
     *
     * @param t the element
     * @return the E identifier of the new row in database, null if could not be saved
     */
    E save(T t);

    /**
     * Updating an element.
     *
     * @param t the element
     * @return true if was updated
     */
    boolean update(T t);

    /**
     * Deleting an element.
     *
     * @param t the element
     * @return true if was deleted
     */
    boolean delete(T t);
}
