package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceException;
import model.Categoria;
import java.util.List;

public class CategoriaDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardar(Categoria categoria) throws jakarta.persistence.PersistenceException  {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Categoria c WHERE c.nombre = :nombre", Long.class);
            query.setParameter("nombre", categoria.getNombre());
            long count = query.getSingleResult();
            if (count > 0)
                throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");

            entityManager.getTransaction().begin();
            entityManager.persist(categoria);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarNombresCategoria() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT c.nombre FROM Categoria c ORDER BY c.nombre", String.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Categoria obtenerCategoriaPorNombre(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Categoria> query = entityManager.createQuery(
                    "SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class);
            query.setParameter("nombre", nombre);
            List<Categoria> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            entityManager.close();
        }
    }
}
