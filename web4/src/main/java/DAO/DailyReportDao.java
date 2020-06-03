package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sun.security.pkcs11.Secmod;
import util.DBHelper;

import java.util.List;

public class DailyReportDao {

    private Session session;
    private SessionFactory sessionFactory;

    public DailyReportDao() {
        sessionFactory = DBHelper.getSessionFactory();
    }

    public List<DailyReport> getAllDailyReport() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void deleteDailyReportDao() {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void saveReportDao(DailyReport dailyReport) {
        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public DailyReport getLastDayReport() {
        session = sessionFactory.openSession();
        List<DailyReport> list = session.createQuery("from DailyReport").list();
        session.close();
        return list.get(list.size() - 1);
    }
}
