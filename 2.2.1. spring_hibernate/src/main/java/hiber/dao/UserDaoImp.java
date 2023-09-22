package hiber.dao;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
      sessionFactory.getCurrentSession().save(user.getCar());
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUsersList() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      String HQL = "from User user where user.car.model = :paramModel and user.car.series = :paramSeries";

      return  sessionFactory.getCurrentSession().createQuery(HQL, User.class)
              .setParameter("paramModel",model)
              .setParameter("paramSeries", series)
              .uniqueResult();
   }

}
