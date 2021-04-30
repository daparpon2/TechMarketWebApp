package es.conselleria.daparpon.techmarket.business;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 07/07/2018.
 *
 * @param <T>
 * @author Cesardl
 */
public abstract class TemplateBusiness<T> {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateBusiness.class);

    protected CompleteCrudDAO<T> dao;

    private int identifierSize;

    public void setDao(CompleteCrudDAO<T> dao) {
        this.dao = dao;
    }

    protected void setIdentifierSize(int origin) {
        this.identifierSize = origin;
    }

    public abstract Collection<T> all();

    public abstract boolean saveOrUpdate(final T element);

    public abstract T findById(Integer identifier);

    public abstract boolean delete(Integer identifier);

    protected int generateIdentifier() {
        int id;
        do {
            id = getIdentifierByStrategy();
            LOG.debug("Generating random product identifier {}", id);
        } while (dao.findById(id) != null);
        return id;
    }

    private int getIdentifierByStrategy() {
        switch (identifierSize) {
            case 6:
                return ThreadLocalRandom.current().nextInt(100000, 1000000);

            case 8:
                return ThreadLocalRandom.current().nextInt(10000000, 100000000);

            default:
                return ThreadLocalRandom.current().nextInt(1000000);
        }
    }
}
