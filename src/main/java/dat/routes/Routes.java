package dat.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

/**
 * @author laith kaseb
 **/


public class Routes {
    private final TripRoutes tripRoutes;

    public Routes(EntityManagerFactory emf) {
        tripRoutes = new TripRoutes(emf);


    }

    public EndpointGroup getRoutes() {
        return () -> {
            path("/",tripRoutes.getRoutes());



        };
    }
}
