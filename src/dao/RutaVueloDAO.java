package dao;

import data_types.DtRutaVuelo;
import data_types.EstadoRuta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import model.RutaVuelo;

import java.util.List;

public class RutaVueloDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardarRutaVuelo(RutaVuelo ruta) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ruta);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public RutaVuelo obtenerRutaPorNombre(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT r FROM RutaVuelo r WHERE r.nombre = :nombre", RutaVuelo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }


    public List<String> listarRutasPorAerolinea(String nickAerolinea) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT r.nombre FROM RutaVuelo r WHERE r.aerolinea.nickname = :nick", String.class)
                    .setParameter("nick", nickAerolinea)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarRutasConfirmadasPorAerolinea(String nickAerolinea) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT r.nombre FROM RutaVuelo r WHERE r.aerolinea.nickname = :nick AND r.estado = 'CONFIRMADA'", String.class)
                    .setParameter("nick", nickAerolinea)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarRutasIngresadasPorAerolinea(String nickAerolinea) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT r.nombre FROM RutaVuelo r WHERE r.aerolinea.nickname = :nick AND r.estado='INGRESADA'", String.class)
                    .setParameter("nick", nickAerolinea)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
    public void actualizarEstado(String nombreRuta, EstadoRuta nuevoEstado) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery(
                            "UPDATE RutaVuelo r SET r.estado = :nuevoEstado WHERE r.nombre = :nombreRuta"
                    )
                    .setParameter("nuevoEstado", nuevoEstado)
                    .setParameter("nombreRuta", nombreRuta)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public DtRutaVuelo obtenerDtRutaPorNombre(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            RutaVuelo ruta = entityManager.createQuery(
                            "SELECT r FROM RutaVuelo r LEFT JOIN FETCH r.vuelos WHERE r.nombre = :nombre",
                            RutaVuelo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            // Solo obtenemos los nombres de los vuelos
            List<String> nombresVuelos = ruta.toDto().getVuelos().stream()
                    .map(v -> v) // solo el nombre
                    .toList();

            // Convertir la ruta a DTO
            return new DtRutaVuelo(
                    ruta.getNombre(),
                    ruta.getDescripcion(),
                    ruta.getFechaAlta(),
                    ruta.getCostoBaseTurista(),
                    ruta.getCostoBaseEjecutivo(),
                    ruta.getCostoEquipajeExtra(),
                    ruta.getOrigen().getNombre(),
                    ruta.getDestino().getNombre(),
                    ruta.getAerolinea().getNombre(),
                    ruta.getCategoria().getNombre(),
                    nombresVuelos,
                    ruta.getUrlImagen(),
                    ruta.getDescripcionCorta(),
                    ruta.getEstado()
            );

        } catch (PersistenceException e) {
            e.printStackTrace(); // mejor imprimir el error para debug
            return null;
        } finally {
            entityManager.close();
        }
    }
}
