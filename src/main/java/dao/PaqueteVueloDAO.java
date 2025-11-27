package dao;

import data_types.DtItemPaquete;
import data_types.DtPaqueteVuelo;
import data_types.TipoAsiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PaqueteVueloDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void registrarPaqueteVuelo(PaqueteVuelo paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser nulo");
        }
        if (paquete.getNombre() == null || paquete.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        EntityManager entityManager = emf.createEntityManager();
        try {
            // Verificar unicidad por nombre
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(p) FROM PaqueteVuelo p WHERE p.nombre = :nombre", Long.class);
            query.setParameter("nombre", paquete.getNombre());
            long dup = query.getSingleResult();

            if (dup > 0) {
                throw new IllegalArgumentException("Ya existe un paquete con ese nombre.");
            }

            entityManager.getTransaction().begin();
            entityManager.persist(paquete);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarNombresPaquetes() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT nombre FROM PaqueteVuelo p", String.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarNombresPaquetesConRutas() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT DISTINCT p.nombre FROM PaqueteVuelo p JOIN p.items i WHERE i.rutaVuelo IS NOT NULL",
                    String.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public DtPaqueteVuelo consultaPaquete(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            PaqueteVuelo paqueteVuelo = entityManager.createQuery(
                    "SELECT p FROM PaqueteVuelo p LEFT JOIN FETCH p.items WHERE p.nombre = :nombre",
                    PaqueteVuelo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            return new DtPaqueteVuelo(
                    paqueteVuelo.getNombre(),
                    paqueteVuelo.getDescripcion(),
                    paqueteVuelo.getDiasValidez(),
                    paqueteVuelo.getDescuento(),
                    paqueteVuelo.getCosto(),
                    paqueteVuelo.getAltaFecha(),
                    paqueteVuelo.getItems(),
                    paqueteVuelo.getReservaPaquete());
        } finally {
            entityManager.close();
        }
    }

    public void agregarRutaPaquete(PaqueteVuelo paqueteVuelo, Aerolinea aerolinea, RutaVuelo rutaVuelo,
            Integer cantidad, TipoAsiento tipoAsiento) {

        if (paqueteVuelo == null)
            throw new IllegalArgumentException("Debe indicar un Paquete de Vuelo");
        if (aerolinea == null)
            throw new IllegalArgumentException("Debe indicar una Aerolínea");
        if (rutaVuelo == null)
            throw new IllegalArgumentException("Debe indicar una Ruta de Vuelo");
        if (tipoAsiento == null)
            throw new IllegalArgumentException("Debe indicar el tipo de asiento");
        if (cantidad == null || cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");

        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            PaqueteVuelo paquete = entityManager.find(PaqueteVuelo.class, paqueteVuelo.getIdentificador());
            if (paquete == null)
                throw new IllegalArgumentException("El paquete indicado no existe");

            RutaVuelo ruta = entityManager.getReference(RutaVuelo.class, rutaVuelo.getNombre());

            ItemPaquete existente = null;
            try {
                existente = entityManager.createQuery(
                        "SELECT i FROM ItemPaquete i " +
                                "WHERE i.paqueteVuelo = :p AND i.rutaVuelo = :r AND i.tipoAsiento = :t",
                        ItemPaquete.class)
                        .setParameter("p", paquete)
                        .setParameter("r", ruta)
                        .setParameter("t", tipoAsiento)
                        .setMaxResults(1)
                        .getSingleResult();
            } catch (NoResultException ignore) {
                // No hacer nada si no hay resultado
            }
            float costo = calcularCostoPaquete(rutaVuelo, tipoAsiento, cantidad);
            if (existente != null) {
                existente.setCant(existente.getCant() + cantidad);
            } else {
                ItemPaquete nuevo = new ItemPaquete(cantidad, tipoAsiento, rutaVuelo, paqueteVuelo);
                entityManager.persist(nuevo);
            }

            paquete.setCosto(paquete.getCosto() + costo);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }

    }

    public float calcularCostoPaquete(RutaVuelo rutaVuelo, TipoAsiento tipo, int cant) {
        if (tipo == TipoAsiento.TURISTA)
            return rutaVuelo.getCostoBaseTurista() * cant;
        else
            return rutaVuelo.getCostoBaseEjecutivo() * cant;
    }

    public List<DtItemPaquete> listarItemsDePaquete(Long idPaquete) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            PaqueteVuelo paqueteVuelo;
            try {
                paqueteVuelo = entityManager.createQuery(
                        "SELECT p FROM PaqueteVuelo p " +
                                "JOIN FETCH p.items i " +
                                "JOIN FETCH i.rutaVuelo " +
                                "WHERE p.identificador = :id",
                        PaqueteVuelo.class)
                        .setParameter("id", idPaquete)
                        .getSingleResult();
            } catch (NoResultException e) {
                return Collections.emptyList();
            }

            List<DtItemPaquete> dtItems = new ArrayList<>();
            for (ItemPaquete item : paqueteVuelo.getItems()) {
                dtItems.add(new DtItemPaquete(
                        item.getCant(),
                        item.getTipoAsiento(),
                        item.toDto().getNombreRuta()));
            }
            return dtItems;
        } finally {
            entityManager.close();
        }
    }

    // Guardar reserva asociada a un paquete
    public void guardarReservaPaquete(Reserva reserva) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(reserva);

            // Crear los items si hay paquete
            if (reserva.getPaquete() != null) {
                PaqueteVuelo paquete = reserva.getPaquete();
                Cliente cliente = reserva.getCliente();

                for (ItemPaquete item : paquete.getItems()) {
                    PaqueteClienteItem pci = new PaqueteClienteItem(
                            cliente,
                            item.getCant(),          // cantidad inicial
                            item.getTipoAsiento(),
                            item.getRutaVuelo(),
                            reserva                   // referencia a la reserva
                    );

                    entityManager.persist(pci); // persistimos con el mismo EM
                }
            }

            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public PaqueteVuelo buscarPaqueteVuelo(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT p FROM PaqueteVuelo p LEFT JOIN FETCH p.items WHERE p.nombre = :nombre", PaqueteVuelo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
    public boolean clienteYaComproPaquete(Cliente cliente, PaqueteVuelo paquete) {
        if (paquete == null || paquete.getIdentificador() == null) {
            throw new IllegalArgumentException("El paquete no es válido");
        }

        Long idPaquete = paquete.getIdentificador();

        return cliente.getReserva().stream()
                .anyMatch(res -> res.getPaquete() != null
                        && res.getPaquete().getIdentificador().equals(idPaquete));
    }

    public DtPaqueteVuelo obtenerDtPaquetePorNombre(String nombre) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            PaqueteVuelo paquete = entityManager.createQuery(
                    "SELECT p FROM PaqueteVuelo p WHERE p.nombre = :nombre",
                    PaqueteVuelo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            return new DtPaqueteVuelo(
                    paquete.getNombre(),
                    paquete.getDescripcion(),
                    paquete.getDiasValidez(),
                    paquete.getDescuento(),
                    paquete.getCosto(),
                    paquete.getAltaFecha());

        } catch (PersistenceException e) {
            e.printStackTrace(); // te ayuda a ver errores de query o nulls
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<PaqueteVuelo> listarPaquetes() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM PaqueteVuelo p", PaqueteVuelo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<String> buscarNombresRutasPorNombrePaquete(String nombrePaquete) {
        if (nombrePaquete == null || nombrePaquete.isBlank()) {
            throw new IllegalArgumentException("El nombre del paquete no puede ser nulo o vacío");
        }

        EntityManager em = emf.createEntityManager();
        try {
            PaqueteVuelo paquete = em.createQuery(
                    "SELECT p FROM PaqueteVuelo p " +
                            "JOIN FETCH p.items i " +
                            "JOIN FETCH i.rutaVuelo " +
                            "WHERE p.nombre = :nombre",
                    PaqueteVuelo.class)
                    .setParameter("nombre", nombrePaquete)
                    .getSingleResult();

            return paquete.getItems().stream()
                    .map(item -> item.getRutaVuelo().getNombre())
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();
        } catch (NoResultException e) {
            return List.of();
        } finally {
            em.close();
        }
    }

    public List<DtPaqueteVuelo> buscarPaquetes(String query) {
        EntityManager em = emf.createEntityManager();
        try {
            List<PaqueteVuelo> paquetes = em.createQuery(
                    "SELECT p FROM PaqueteVuelo p WHERE LOWER(p.nombre) LIKE LOWER(:query) OR LOWER(p.descripcion) LIKE LOWER(:query) ORDER BY p.altaFecha DESC",
                    PaqueteVuelo.class)
                    .setParameter("query", "%" + query + "%")
                    .getResultList();
            return paquetes.stream().map(p -> new DtPaqueteVuelo(
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getDiasValidez(),
                    p.getDescuento(),
                    p.getCosto(),
                    p.getAltaFecha())).toList();
        } finally {
            em.close();
        }
    }
}