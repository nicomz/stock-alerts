package ar.com.sac.services.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDAO {
      @PersistenceContext
      private EntityManager manager;

      
      public synchronized EntityManager getManager() {
         return manager;
      }
      
     /* @Autowired
      private SessionFactory sessionFactory;
   
      
      public synchronized SessionFactory getSessionFactory() {
         return sessionFactory;
      }

      
      public synchronized void setSessionFactory( SessionFactory sessionFactory ) {
         this.sessionFactory = sessionFactory;
      }

      protected Session getSession() {
          return sessionFactory.getCurrentSession();
      }
   
      public void persist(Object entity) {
          getSession().persist(entity);
      }
   
      public void delete(Object entity) {
          getSession().delete(entity);
      }
      */

}
