package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCar();
    }

    public Long addCar(Car car) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        Long id = null;
        if (carDao.countBrand(car.getBrand()) < 10) {
            id = carDao.addCarDao(car);
        }
        return id;
    }

    public Car sellCar(Car car) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        Car carOfBd = carDao.getCarDao(car);
        if (carOfBd != null) {
            DailyReportService.getInstance().addReport(new DailyReport(carOfBd.getPrice(), 1L));
            deleteCar(carOfBd);
        }
        return carOfBd;
    }

    public boolean deleteCar(Car car) {
       return new CarDao(sessionFactory.openSession()).deleteCarDao(car.getId());
    }

    public Car getCarById(long id) {
        return new CarDao(sessionFactory.openSession()).getCarByIdDao(id);
    }

    public void deleteAllCars() {
        new CarDao(sessionFactory.openSession()).deleteAllCarsDao();
    }


}
