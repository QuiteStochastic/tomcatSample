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

public class AddPersonServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String country = request.getParameter("country");
        if ((name == null || name.equals(""))|| (country == null || country.equals(""))) {

            request.setAttribute("error", "Mandatory Parameters Missing");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/persons.jsp");
            rd.forward(request, response);
        }
        else {
            Person p = new Person();
            p.setCountry(country);
            p.setName(name);
            MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");

            PersonDAO personDAO = new PersonDAO(mongo);
            personDAO.createPerson(p);

            System.out.println("Person Added Successfully with id="+p.getId());
            request.setAttribute("success", "Person Added Successfully");


            List<Person> personList = personDAO.readAllPerson();
            request.setAttribute("persons", personList);

/*            for (Person person:personList) {
                System.out.println("ID: "+person.getId()+", name: "+person.getName());
            }*/

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/persons.jsp");
            rd.forward(request, response);
        }
    }

}
