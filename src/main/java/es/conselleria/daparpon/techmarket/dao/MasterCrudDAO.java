package es.conselleria.daparpon.techmarket.dao;

import java.util.Collection;

/**
 * Created on 07/07/2018.
 * 
 * Modified on 29/04/2021 by Daniel Pardo Pont
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface MasterCrudDAO<T> {

    /**
     * Obtain all elements.
     *
     * @return a collections of elements
     */
    Collection<T> getAll();
}
