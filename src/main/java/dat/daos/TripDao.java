package dat.daos;


import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class TripDao implements IDAO<TripDTO> {



    private static EntityManagerFactory emf;

    public TripDao(EntityManagerFactory emf) {
        this.emf = emf;

    }


    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = new Trip(tripDTO);

            if(trip.getName() != null) {
                em.persist(trip);
            }else {
                em.merge(trip);
            }
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(r) FROM Trip r", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null ? new TripDTO(trip) : null;
        }
    }

    @Override
    public TripDTO update(Long id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip t = em.find(Trip.class, id);
            t.setStartTime(tripDTO.getStartTime());
            t.setEndTime(tripDTO.getEndTime());
            Trip newTrip = em.merge(t);
            em.getTransaction().commit();
            return new TripDTO(newTrip);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null){
                em.remove(trip);
            }
            em.getTransaction().commit();
        }
    }

    public TripDTO updateGuide(Long id, Long guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip t = em.find(Trip.class, id);
            Guide guide = em.find(Guide.class, guideId);

            t.setGuide(guide);
            Trip updatedTrip = em.merge(t);
            em.getTransaction().commit();
            return new TripDTO(updatedTrip);
        }
    }
}
