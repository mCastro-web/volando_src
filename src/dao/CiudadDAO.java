package dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import model.Ciudad;
import java.util.List;

public class CiudadDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardarCiudad(Ciudad ciudad) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Ciudad c WHERE c.nombre = :nombre AND c.pais = :pais", Long.class);
            query.setParameter("nombre", ciudad.getNombre());
            query.setParameter("pais", ciudad.getPais());
            if (query.getSingleResult() > 0)
                throw new IllegalArgumentException("Ya existe una ciudad con ese nombre y país.");

            entityManager.getTransaction().begin();
            entityManager.persist(ciudad);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarNombresCiudades() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c.nombre FROM Ciudad c ORDER BY c.nombre", String.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Ciudad obtenerCiudadPorNombre(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            List<Ciudad> resultados = entityManager.createQuery("SELECT c FROM Ciudad c WHERE c.nombre = :nombre", Ciudad.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            entityManager.close();
        }
    }
}
