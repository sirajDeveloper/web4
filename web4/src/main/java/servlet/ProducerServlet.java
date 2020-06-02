package servlet;

import com.google.gson.internal.$Gson$Preconditions;
import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

@WebServlet(name = "ProducerServlet", urlPatterns = "/producer")
public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        long price = Long.parseLong(req.getParameter("price"));
        Car car = new Car(brand, model, licensePlate, price);
        Long id = CarService.getInstance().addCar(car);
        if (id == null) {
            resp.getWriter().println(HttpServletResponse.SC_FORBIDDEN);
        }
        resp.getWriter().println(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | IllegalStateException e) {
            resp.getWriter().println(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }
    }
}
