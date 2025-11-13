package dao;

import data_types.DtDatosVueloR;
import data_types.DtVuelo;
import data_types.TipoAsiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Vuelo;
import model.RutaVuelo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VueloDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardarVuelo(String nombre,
                             LocalDate fecha,
                             LocalTime duracion,
                             int asientosTurista,
                             int asientosEjecutivo,
                             LocalDate fechaAlta,
                             RutaVuelo ruta,
                             String urlImagen) {

        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            // Verificar si ya existe
            if (entityManager.find(Vuelo.class, nombre) != null)
                throw new IllegalArgumentException("Ya existe un vuelo con el nombre '" + nombre + "'.");

            Vuelo vuelo = new Vuelo(nombre, fecha, duracion, asientosTurista, asientosEjecutivo, fechaAlta, ruta, urlImagen);
            entityManager.persist(vuelo);

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public Vuelo buscarPorNombre(String nombreVuelo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Vuelo> query = entityManager.createQuery(
                    "SELECT v FROM Vuelo v JOIN FETCH v.ruta WHERE v.nombre = :nombre", Vuelo.class);
            query.setParameter("nombre", nombreVuelo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }


    public List<String> listarNombresVuelosPorRuta(String nombreRuta) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT v.nombre FROM Vuelo v WHERE v.ruta.nombre = :nombreRuta", String.class);
            query.setParameter("nombreRuta", nombreRuta);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    // Devuelve la cantidad de asientos ocupados de un vuelo según el tipo de asiento
    public Long contarAsientosOcupados(String nombreVuelo, TipoAsiento tipoAsiento) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT COUNT(p) FROM Pasaje p WHERE p.reserva.vuelo.nombre = :vuelo AND p.reserva.tipoAsiento = :tipo",
                            Long.class)
                    .setParameter("vuelo", nombreVuelo)
                    .setParameter("tipo", tipoAsiento)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public List<DtDatosVueloR> listarVuelosDeRuta(String nombreRuta) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Vuelo> query = entityManager.createQuery(
                    "SELECT v FROM Vuelo v WHERE v.ruta.nombre = :nombreRuta",
                    Vuelo.class
            );
            query.setParameter("nombreRuta", nombreRuta);

            List<Vuelo> vuelos = query.getResultList();
            List<DtDatosVueloR> dtVuelos = new ArrayList<>();

            for (Vuelo v : vuelos) {
                dtVuelos.add(new DtDatosVueloR(
                        v.getNombre(),
                        v.getFecha(),
                        v.getDuracion(),
                        v.getAsientosTurista(),
                        v.getAsientosEjecutivo(),
                        v.getFechaAlta(),
                        v.getRuta().getOrigen().getNombre(),
                        v.getRuta().getDestino().getNombre(),
                        v.getRuta().getCostoBaseEjecutivo(),
                        v.getRuta().getCostoBaseTurista(),
                        v.getRuta().getCostoEquipajeExtra()
                ));
            }
            return dtVuelos;
        } finally {
            entityManager.close();
        }
    }
    public DtVuelo obtenerDtVueloPorNombre(String nombreVuelo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Vuelo> query = entityManager.createQuery(
                    "SELECT v FROM Vuelo v WHERE v.nombre = :nombreVuelo", Vuelo.class);
            query.setParameter("nombreVuelo", nombreVuelo);
            List<Vuelo> resultados = query.getResultList();
            if (resultados.isEmpty()) return null;

            Vuelo vuelo = resultados.get(0);

            DtVuelo dtVuelo = new DtVuelo(
                    vuelo.getNombre(),
                    vuelo.getFecha(),
                    vuelo.getDuracion(),
                    vuelo.getAsientosTurista(),
                    vuelo.getAsientosEjecutivo(),
                    vuelo.getFechaAlta(),
                    null,
                    vuelo.getUrlImagen()
            );

            return dtVuelo;
        } finally {
            entityManager.close();
        }
    }

}
