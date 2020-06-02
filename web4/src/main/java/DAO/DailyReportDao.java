package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void deleteDailyReportDao() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from DailyReport").executeUpdate();
        transaction.commit();
        session.close();
    }

    public void saveReportDao(DailyReport dailyReport) {
        Transaction transaction = session.beginTransaction();
        session.save(dailyReport);
        transaction.commit();
        session.close();
    }

    public DailyReport getLastDayReport() {
        List<DailyReport> list = session.createQuery("from DailyReport").list();
        return list.get(list.size() - 1);
    }
}
