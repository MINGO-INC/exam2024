package dat.config;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class Populate {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("trip");

        // Call the populateDatabase method to add initial data
        populateDatabase(emf);
    }

    /**
     * Populates the database with initial data for guides and trips.
     *
     * @param emf the EntityManagerFactory instance
     */
    public static void populateDatabase(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            // Create Guides and Trips
            List<Guide> guides = createGuides();
            List<Trip> trips = createTrips(guides);

            // Persist each Guide and Trip to the database
            guides.forEach(em::persist);
            trips.forEach(em::persist);

            em.getTransaction().commit();
            System.out.println("Database successfully populated with guides and trips.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Failed to populate database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Creates a list of guides to be added to the database.
     *
     * @return list of Guide entities
     */
    private static List<Guide> createGuides() {
        return List.of(
                new Guide("John", "Doe", "johndoe@example.com", "555-0101", 5),
                new Guide("Jane", "Smith", "janesmith@example.com", "555-0102", 8),
                new Guide("Emily", "Carter", "emilycarter@example.com", "555-0103", 10),
                new Guide("Alex", "Brown", "alexbrown@example.com", "555-0104", 6),
                new Guide("Sarah", "Johnson", "sarahjohnson@example.com", "555-0105", 12)
        );
    }

    /**
     * Creates a list of trips to be added to the database.
     * Each trip is assigned to a guide from the provided list.
     *
     * @param guides the list of guides to assign to trips
     * @return list of Trip entities
     */
    private static List<Trip> createTrips(List<Guide> guides) {
        return List.of(
                new Trip(LocalDateTime.of(2024, 12, 1, 9, 0),
                        LocalDateTime.of(2024, 12, 1, 17, 0),
                        "Mount Everest Base",
                        "Mountain Expedition",
                        1200.0,
                        Category.snow,
                        guides.get(0)),
                new Trip(LocalDateTime.of(2024, 12, 2, 10, 0),
                        LocalDateTime.of(2024, 12, 2, 16, 0),
                        "Sahara Desert",
                        "Desert Safari",
                        950.0,
                        Category.beach,
                        guides.get(1)),
                new Trip(LocalDateTime.of(2024, 12, 3, 11, 0),
                        LocalDateTime.of(2024, 12, 3, 15, 0),
                        "" +
                                "Great Barrier Reef",
                        "Scuba Diving Adventure",
                        1500.0,
                        Category.sea,
                        guides.get(2)),
                new Trip(LocalDateTime.of(2024, 12, 4, 8, 0),
                        LocalDateTime.of(2024, 12, 4, 20, 0),
                        "Amazon Rainforest",
                        "Rainforest Exploration",
                        1800.0,
                        Category.forest,
                        guides.get(3)),
                new Trip(LocalDateTime.of(2024, 12, 5, 12, 0),
                        LocalDateTime.of(2024, 12, 5, 19, 0),
                        "Grand Canyon",
                        "Hiking and Sightseeing",
                        800.0,
                        Category.lake,
                        guides.get(4))
        );
    }
}
