package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.PersonDAO;
import Model.Pojo.Person;
import com.mongodb.MongoClient;

public class DeletePersonServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for delete operation");
        }
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

        PersonDAO personDAO = new PersonDAO(mongo);
        Person p = new Person();
        p.setId(id);
        personDAO.deletePerson(p);
        System.out.println("Person deleted successfully with id=" + id);
        request.setAttribute("success", "Person deleted successfully");
        List<Person> persons = personDAO.readAllPerson();
        request.setAttribute("persons", persons);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/persons.jsp");
        rd.forward(request, response);
    }

}