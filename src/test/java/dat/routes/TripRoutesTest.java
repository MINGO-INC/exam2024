package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.daos.GuideDao;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.entities.Category;
import dat.entities.Guide;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class TripRoutesTest {

    private static EntityManagerFactory emf;
    private static Javalin app;

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            // Ensure a clean state before each test
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE trip_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
        // Now populate the database with fresh data
        Populate.populateDatabase(emf);
    }
    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE Trip_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
    @AfterAll
    static void shutDownAfterAll() {
        ApplicationConfig.stopServer(app);

    }
    @BeforeAll
    static void setUpBeforeAll() {
        RestAssured.baseURI = "http://localhost:7007/api";
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = ApplicationConfig.startServer(7007, emf);

    }

    @Test
    @DisplayName("getting all doctors")
    void readALL() {
        RestAssured.given()
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("size()", equalTo(5));
    }

    /*@Test
    @DisplayName("creating a trip")
    void createDoctor() {
        GuideDao guideDao = new GuideDao(emf);
        GuideDTO guideDTO = new GuideDTO();
        guideDTO.setEmail("test@test.com");
        guideDTO.setFirstName("John");
        guideDTO.setLastName("Doe");
        guideDTO.setPhone("222-1223");
        guideDTO.setYearsOfExperience(2);
        guideDao.create(guideDTO);

        TripDTO tripDTO = new TripDTO();
        tripDTO.setName("Mountain");
        tripDTO.setPrice(2412.2);
        tripDTO.setStartposition("james");
        tripDTO.setStartTime(LocalDateTime.of(2010, 12, 2, 10, 0));
        tripDTO.setEndTime(LocalDateTime.of(2020, 1, 20, 15, 30));
        tripDTO.setCategory(Category.beach);
        tripDTO.setGuide(guideDTO);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(tripDTO)
                .when()
                .post("")
                .then()
                .statusCode(201)
                .body("name", equalTo("Dr. Test Doctor"));
    }

     */

    @Test
    @DisplayName("read by id")
    void readById() {
        String jwt_token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKb24gQmVydGVsc2VuIiwic3ViIjoidXNlciIsImV4cCI6MTczMDcyMjc3OSwicm9sZXMiOiJ1c2VyIiwidXNlcm5hbWUiOiJ1c2VyIn0.bbCi5qsFzR6lmoknmOMxQH04K8bWOk9Hfr7yWPjxfxQ";

        RestAssured.given()
                .header("Authorization","Bearer"+jwt_token) // Include your token here

                .when()
                .get("/trips/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Mountain Expedition"));
    }

    @Test
    @DisplayName("test category")
    void testSpeciality() {
        String jwt_token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKb24gQmVydGVsc2VuIiwic3ViIjoidXNlciIsImV4cCI6MTczMDcyMjc3OSwicm9sZXMiOiJ1c2VyIiwidXNlcm5hbWUiOiJ1c2VyIn0.bbCi5qsFzR6lmoknmOMxQH04K8bWOk9Hfr7yWPjxfxQ";
        RestAssured.given()
                .header("Authorization","Bearer"+jwt_token) // Include your token here
                .when()
                .get("/trips/category/snow")
                .then()
                .statusCode(200)
                .body("[0].category", equalTo("snow"));
    }


}