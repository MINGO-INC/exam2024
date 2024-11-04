package dat.daos;

import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;



public class GuideDao implements IDAO<GuideDTO>{
    private static EntityManagerFactory emf;

    public GuideDao(EntityManagerFactory emf) {
        this.emf = emf;

    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide = new Guide(guideDTO);

            if(guide.getEmail() != null) {
                em.persist(guide);
            }else {
                em.merge(guide);
            }
            em.getTransaction().commit();
            return new GuideDTO(guide);
        }    }

    @Override
    public List<GuideDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<GuideDTO> query = em.createQuery("SELECT new dat.dtos.GuideDTO(r) FROM Guide r", GuideDTO.class);
            return query.getResultList();
        }    }

    @Override
    public GuideDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            return guide != null ? new GuideDTO(guide) : null;
        }     }

    @Override
    public GuideDTO update(Long id, GuideDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide g = em.find(Guide.class, id);
            g.setFirstName(dto.getFirstName());
            g.setLastName(dto.getLastName());
            g.setEmail(dto.getEmail());
            Guide newGuide = em.merge(g);
            em.getTransaction().commit();
            return new GuideDTO(newGuide);
        }
    }
    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null){
                em.remove(guide);
            }
            em.getTransaction().commit();
        }
    }


}

