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
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
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
                    "SELECT r.nombre FROM RutaVuelo r WHERE r.aerolinea.nickname = :nick AND r.estado = 'CONFIRMADA'",
                    String.class)
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
                    "SELECT r.nombre FROM RutaVuelo r WHERE r.aerolinea.nickname = :nick AND r.estado='INGRESADA'",
                    String.class)
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
                    "UPDATE RutaVuelo r SET r.estado = :nuevoEstado WHERE r.nombre = :nombreRuta")
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

    public void finalizarRuta(String nombreRuta) {

        EntityManager em = emf.createEntityManager();

        if (obtenerRutaPorNombre(nombreRuta) == null) {
            throw new PersistenceException("La ruta con nombre " + nombreRuta + " no existe.");
        }

        try {
            em.getTransaction().begin();
            Long vuelosPendientes = em.createQuery(
                    "SELECT COUNT(v) FROM Vuelo v " +
                            "WHERE v.ruta.nombre = :nombreRuta AND v.fecha > :fechaActual",
                    Long.class)
                    .setParameter("nombreRuta", nombreRuta)
                    .setParameter("fechaActual", java.time.LocalDate.now())
                    .getSingleResult();

            if (vuelosPendientes > 0) {
                throw new PersistenceException("La ruta tiene vuelos pendientes y no puede finalizarse.");
            }

            Long enPaquetes = em.createQuery(
                    "SELECT COUNT(i) FROM ItemPaquete i " +
                            "WHERE i.rutaVuelo.nombre = :nombreRuta",
                    Long.class)
                    .setParameter("nombreRuta", nombreRuta)
                    .getSingleResult();

            if (enPaquetes > 0) {
                throw new PersistenceException("La ruta está incluida en un paquete y no puede finalizarse.");
            }

            em.createQuery(
                    "UPDATE RutaVuelo r SET r.estado = :nuevoEstado " +
                            "WHERE r.nombre = :nombreRuta")
                    .setParameter("nuevoEstado", EstadoRuta.FINALIZADA)
                    .setParameter("nombreRuta", nombreRuta)
                    .executeUpdate();

            em.getTransaction().commit();

        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /*
     * public DtRutaVuelo obtenerDtRutaPorNombre(String nombre) {
     * EntityManager entityManager = emf.createEntityManager();
     * try {
     * System.out.println("Buscando ruta por nombre: '" + nombre + "'");
     * RutaVuelo ruta = entityManager.createQuery(
     * "SELECT r FROM RutaVuelo r LEFT JOIN FETCH r.vuelos WHERE LOWER(r.nombre) = LOWER(:nombre)"
     * ,
     * RutaVuelo.class)
     * .setParameter("nombre", nombre.trim())
     * .getSingleResult();
     * 
     * // Solo obtenemos los nombres de los vuelos
     * List<String> nombresVuelos = ruta.toDto().getVuelos().stream()
     * .map(v -> v) // solo el nombre
     * .toList();
     * 
     * // Convertir la ruta a DTO
     * return new DtRutaVuelo(
     * ruta.getNombre(),
     * ruta.getDescripcion(),
     * ruta.getFechaAlta(),
     * ruta.getCostoBaseTurista(),
     * ruta.getCostoBaseEjecutivo(),
     * ruta.getCostoEquipajeExtra(),
     * ruta.getOrigen().getNombre(),
     * ruta.getDestino().getNombre(),
     * ruta.getAerolinea().getNombre(),
     * ruta.getCategoria().getNombre(),
     * nombresVuelos,
     * ruta.getUrlImagen(),
     * ruta.getUrlVideo(),
     * ruta.getDescripcionCorta(),
     * ruta.getEstado(),
     * ruta.getCantVisitas()
     * );
     * 
     * } catch (PersistenceException e) {
     * e.printStackTrace(); // mejor imprimir el error para debug
     * return null;
     * } finally {
     * entityManager.close();
     * }
     * }
     */

    public DtRutaVuelo obtenerDtRutaPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Nombre de ruta vacío");
            return null;
        }

        EntityManager entityManager = emf.createEntityManager();
        try {
            // Usamos LOWER y trim para evitar problemas de mayúsculas o espacios
            List<RutaVuelo> rutas = entityManager.createQuery(
                    "SELECT r FROM RutaVuelo r LEFT JOIN FETCH r.vuelos "
                            + "WHERE LOWER(TRIM(r.nombre)) = LOWER(TRIM(:nombre))",
                    RutaVuelo.class)
                    .setParameter("nombre", nombre)
                    .getResultList();

            if (rutas.isEmpty()) {
                System.out.println("No se encontró la ruta: " + nombre);
                return null;
            }

            RutaVuelo ruta = rutas.get(0);

            // Obtenemos solo los nombres de los vuelos
            List<String> nombresVuelos = ruta.toDto().getVuelos().stream()
                    .map(v -> v) // solo el nombre
                    .toList();

            // Creamos y retornamos el DTO
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
                    ruta.getUrlVideo(),
                    ruta.getDescripcionCorta(),
                    ruta.getEstado(),
                    ruta.getCantVisitas());

        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<DtRutaVuelo> listarRutasMasVisitadas() {
        EntityManager entityManager = emf.createEntityManager();

        List<RutaVuelo> rutas = entityManager.createQuery(
                "SELECT r FROM RutaVuelo r\n" +
                        "LEFT JOIN FETCH r.vuelos\n" +
                        "LEFT JOIN FETCH r.origen\n" +
                        "LEFT JOIN FETCH r.destino\n" +
                        "LEFT JOIN FETCH r.aerolinea a " +
                        "LEFT JOIN FETCH r.categoria c " +
                        "ORDER BY r.cantVisitas DESC\n",
                RutaVuelo.class)
                .setMaxResults(5)
                .getResultList();

        entityManager.close();

        return rutas.stream()
                .map(RutaVuelo::toDto)
                .toList();
    }

    public List<String> listarRutasConfirmadasPorCategoria(String nombreCategoria) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT r.nombre FROM RutaVuelo r WHERE r.categoria.nombre = :categoria AND r.estado = 'CONFIRMADA'",
                    String.class)
                    .setParameter("categoria", nombreCategoria)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void incrementarVisitaRuta(String nombreRuta) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery(
                "UPDATE RutaVuelo r SET r.cantVisitas = r.cantVisitas + 1 WHERE r.nombre = :nombre")
                .setParameter("nombre", nombreRuta)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<DtRutaVuelo> buscarRutas(String query) {
        EntityManager em = emf.createEntityManager();
        try {
            List<RutaVuelo> rutas = em.createQuery(
                    "SELECT r FROM RutaVuelo r WHERE LOWER(r.nombre) LIKE LOWER(:query) OR LOWER(r.descripcion) LIKE LOWER(:query) ORDER BY r.fechaAlta DESC",
                    RutaVuelo.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();
            return rutas.stream().map(RutaVuelo::toDto).toList();
        } finally {
            em.close();
        }
    }
}
