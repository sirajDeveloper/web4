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
        new CarDao().getAllCar();
        return new CarDao().getAllCar();
    }

    public Long addCar(Car car) {
        //CarDao carDao = new CarDao(sessionFactory.openSession());
        CarDao carDao = new CarDao();
        Long id = null;
        if (carDao.countBrand(car.getBrand()) < 10) {
            id = carDao.addCarDao(car);
        }
        return id;
    }

    public Car sellCar(Car car) {
        CarDao carDao = new CarDao();
        Car carOfBd = carDao.getCarDao(car);
        if (carOfBd != null) {
            DailyReportService.getInstance().addReport(new DailyReport(carOfBd.getPrice(), 1L));
            deleteCar(carOfBd);
        }
        return carOfBd;
    }

    public boolean deleteCar(Car car) {
       return new CarDao().deleteCarDao(car.getId());
    }

    public Car getCarById(long id) {
        return new CarDao().getCarByIdDao(id);
    }

    public void deleteAllCars() {
        new CarDao().deleteAllCarsDao();
    }


}
