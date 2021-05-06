package es.conselleria.daparpon.techmarket.dao;

/**
 * Created on 13/06/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface SimpleCrudDAO<T, E> extends MasterCrudDAO<T> {

    /**
     * Obtain specific element.
     *
     * @param identifier element identifier
     * @return an element with code
     */
    T findById(E identifier);
}
