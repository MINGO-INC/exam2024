package dat.controller;


import dat.daos.GuideDao;
import dat.daos.TripDao;
import dat.dtos.GuideDTO;
import dat.dtos.GuideOverviewDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.exceptions.ApiException;
import dat.security.exceptions.ErrorResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripController {
    private final Logger log = LoggerFactory.getLogger(TripController.class);
    private EntityManagerFactory emf;
    private TripDao tripDao;
    private GuideDao guideDao;

    public TripController(TripDao tripDao, GuideDao guideDao) {
        this.tripDao = tripDao;
        this.guideDao = guideDao;

    }


    public void read(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();

            TripDTO tripDTO = tripDao.getById(id);

            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e) {
            // log.error("400{}",e.getMessage());
            ctx.json(new ErrorResponse("Internal server error", 500));
            //throw new ApiException(400, e.getMessage());
        }
    }

    public void create(Context ctx) {
        try {
            // Parse request body as DoctorDTO
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);

            // Call DAO to create doctor
            TripDTO createdDoctorDTO = tripDao.create(tripDTO);

            ctx.status(HttpStatus.CREATED);
            ctx.json(createdDoctorDTO);

            // == response ==
            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);

            // == querying ==
            Long id = ctx.pathParamAsClass("id", long.class).get();
            tripDao.update(id, tripDTO);

            // == response ==
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void delete(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            // entity
            tripDao.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            //log.error("400{}", e.getMessage());
            ctx.json(new ErrorResponse("Internal server error", 500));
            //throw new ApiException(400, e.getMessage());
        }
    }

    public void readAll(Context ctx) {
        try {
            List<TripDTO> tripDTOS = tripDao.getAll();
            // response
            ctx.res().setStatus(200);
            ctx.json(tripDTOS);
        } catch (Exception e) {
            log.error("500{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updateGuide(Context ctx) {
        try {
            Long tripId = ctx.pathParamAsClass("tripId", Long.class).get();
            Long guideId = ctx.pathParamAsClass("guideId", Long.class).get();

            TripDTO updatedTripDTO = tripDao.updateGuide(tripId, guideId);


            // == response ==
            ctx.json(updatedTripDTO);
            ctx.res().setStatus(200);

        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void filterByCategory(Context ctx) {
        try {
            String categoryParam = ctx.pathParam("category");
            Category category = Category.valueOf(categoryParam.toLowerCase());

            List<TripDTO> trips = tripDao.getAll();

            // Filter trips by the specified category
            List<TripDTO> filteredTrips = trips.stream()
                    .filter(trip -> trip.getCategory() == category)
                    .collect(Collectors.toList());

            ctx.json(filteredTrips);
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400{}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void getGuideOverview(Context ctx) {
        try {
            List<TripDTO> trips = tripDao.getAll();
            Map<Long, Double> guideTotals = new HashMap<>();

            for (TripDTO trip : trips) {
                if (trip.getGuide() != null && trip.getGuide().getId() != null) { // Ensure guide and ID are not null
                    guideTotals.merge(trip.getGuide().getId(), trip.getPrice(), Double::sum);
                }
            }
            List<GuideOverviewDTO> responseList = new ArrayList<>();

            for (Map.Entry<Long, Double> entry : guideTotals.entrySet()) {
                Long guideId = entry.getKey();
                GuideDTO guide = guideDao.getById(guideId);
                if (guide != null) {
                    GuideOverviewDTO guideOverview = new GuideOverviewDTO();
                    guideOverview.setGuideId(guide.getId());
                    guideOverview.setTotalPrice(entry.getValue());
                    responseList.add(guideOverview);
                }
            }

            ctx.json(responseList);
            ctx.status(200);
        } catch (Exception e) {
            log.error("Error occurred while retrieving guide overview: {}", e.getMessage());
            ctx.status(500).json(new ApiException(500, "Internal Server Error: " + e.getMessage()));
        }
    }

}