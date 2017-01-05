package Model;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MongoDBContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String host=ctx.getInitParameter("MONGODB_HOST") +":"+ Integer.parseInt(ctx.getInitParameter("MONGODB_PORT"));


        MongoClientOptions mongoClientOptions= MongoClientOptions.builder().sslEnabled(true).build();


        MongoClient mongo = new MongoClient(host,mongoClientOptions);

        sce.getServletContext().setAttribute("MONGO_CLIENT", mongo);
        System.out.println("MongoClient initialized successfully");

    }



    public void contextDestroyed(ServletContextEvent sce) {
        MongoClient mongo = (MongoClient) sce.getServletContext().getAttribute("MONGO_CLIENT");
        mongo.close();
        System.out.println("MongoClient closed successfully");
    }



}