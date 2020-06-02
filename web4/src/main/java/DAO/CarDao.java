package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) { this.session = session; } // сделать конструктор пустым

    public List<Car> getAllCar() {
        return (List<Car>) session.createQuery("FROM Car").list();
    }

    public Long addCarDao(Car car) {
        Transaction transaction = session.beginTransaction();
        long id = (long) session.save(car);
        transaction.commit();
        session.close();
        return id;
    }

    public Car getCarDao(Car car) {
        String brand = car.getBrand();
        String model = car.getModel();
        String licensePlate = car.getLicensePlate();
        String hql = "FROM Car"
                + " WHERE brand=:brand"
                + " AND model=:model"
                + " AND licensePlate=:licensePlate"
                ;
        Query query = session.createQuery(hql);
        query.setString("brand", brand);
        query.setString("model", model);
        query.setString("licensePlate", licensePlate);
        List list = query.list();
        session.close();
        return (Car) list.get(0);
    }

    public Car getCarByIdDao(long id) {
        return (Car) session.load(Car.class, id);
    }

    public boolean deleteCarDao(long id) {
        Query query = session.createQuery("DELETE from Car WHERE id = :id");
        query.setParameter("id", id);
        int res = query.executeUpdate();
        session.close();
        return res > 0;
    }


    public void deleteAllCarsDao() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE from Car").executeUpdate();
            transaction.commit();
            session.close();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
                session.close();
            }
        }
    }

    public int countBrand(String brand) {
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        List list = criteria.list();
        return list.size();
    }

}
