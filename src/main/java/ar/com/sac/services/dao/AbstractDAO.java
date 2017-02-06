package ar.com.sac.services.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractDAO<E, PK extends Serializable> {
      @PersistenceContext
      protected EntityManager entityManager;
      private Class<E> type;
      
      public synchronized EntityManager getEntityManager() {
         return entityManager;
      }
      
      public synchronized void setEntityManager( EntityManager entityManager ) {
         this.entityManager = entityManager;
      }

      public AbstractDAO(Class<E> type){
         this.type = type;
      }
      
      public void persist(E entity) { entityManager.persist(entity); }

      public void remove(E entity) { entityManager.remove(entity); }
      
      public void update(E entity) { entityManager.merge(entity); }

      public E findById(PK id) { return entityManager.find(type, id); }
      
}
