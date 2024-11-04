package dat;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        EntityManagerFactory emf=HibernateConfig.getEntityManagerFactory("trip");
        ApplicationConfig.startServer(7080, emf);



    }
}