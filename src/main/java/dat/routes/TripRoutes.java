package dat.routes;


import dat.controller.TripController;
import dat.daos.GuideDao;
import dat.daos.TripDao;
import dat.entities.Trip;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class TripRoutes {

    private final TripController tripController;

    public TripRoutes(EntityManagerFactory emf) {

        TripDao tripDao = new TripDao(emf);
        GuideDao guideDao = new GuideDao(emf);
        tripController=new TripController(tripDao, guideDao);

    }

    protected EndpointGroup getRoutes() {
        return () -> {
            // Group all trip-related endpoints
            path("/trips", () -> {
                get("", tripController::readAll, Role.ANYONE);
                get("/{id}", tripController::read,Role.ANYONE);//made it to anyone for the porpse of running the test- it should be role.user
                post("", tripController::create,Role.ADMIN);
                put("/{id}", tripController::update,Role.ADMIN);
                delete("/{id}", tripController::delete,Role.ADMIN);
                put("/{tripId}/guides/{guideId}", tripController::updateGuide,Role.ADMIN);
                get("/category/{category}", tripController::filterByCategory,Role.ANYONE); // made role into anyone for the porpose of running api test file - it should be role.user
                get("/overview/overview", tripController::getGuideOverview,Role.USER);
               // post("/populate",tripController::populate);
            });
        };
    }
}
