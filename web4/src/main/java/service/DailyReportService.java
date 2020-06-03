package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static long earnings;
    private static long soldCars;

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao().getAllDailyReport();
    }

    public void generateReport() {
        new DailyReportDao().saveReportDao(new DailyReport(earnings, soldCars));
    }

    public DailyReport getLastReport() {
        return new DailyReportDao().getLastDayReport();
    }

    public void deleteDailyReport() {
        new DailyReportDao().deleteDailyReportDao();
    }

    public void addReport(DailyReport dailyReport) {
        earnings += dailyReport.getEarnings();
        soldCars += dailyReport.getSoldCars();
    }

    public void newDay() {
        earnings = 0;
        soldCars = 0;
    }

}
