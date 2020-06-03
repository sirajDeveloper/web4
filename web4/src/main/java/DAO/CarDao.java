package DAO;

import model.Car;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import service.CarService;
import util.DBHelper;

import java.util.List;

public class CarDao {

    private Session session;
    private SessionFactory sessionFactory;

    public CarDao() {
        this.sessionFactory = DBHelper.getSessionFactory();
    }

    public List<Car> getAllCar() {
        return (List<Car>) session.createQuery("FROM Car").list();
    }

    public Long addCarDao(Car car) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long id = (long) session.save(car);
        transaction.commit();
        session.close();
        return id;
    }

    public Car getCarDao(Car car) {
        session = sessionFactory.openSession();
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
        session = sessionFactory.openSession();
        Query query = session.createQuery("DELETE from Car WHERE id = :id");
        query.setParameter("id", id);
        int res = query.executeUpdate();
        session.close();
        return res > 0;
    }


    public void deleteAllCarsDao() {
        session = sessionFactory.openSession();
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
        session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        List list = criteria.list();
        session.close();
        return list.size();
    }

}
