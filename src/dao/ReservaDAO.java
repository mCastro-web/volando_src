package dao;

import java.util.List;

import data_types.*;
import jakarta.persistence.*;
import model.Reserva;

public class ReservaDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public void guardar(Reserva reserva) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(reserva);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) { // captura todas las excepciones
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e; // re-lanzamos para que el test o el servicio lo detecten
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }


    public List<String> listarReservasDeVuelo(String nombreVuelo) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT CONCAT('Reserva Nro: ', r.identificador, ' - Cliente: ', r.cliente.nickname, " +
                            "' - Pasajes: ', r.cantPasajes, ' - Tipo de asiento: ', r.tipoAsiento) " +
                            "FROM Reserva r WHERE r.vuelo.nombre = :nombreVuelo", String.class);
            query.setParameter("nombreVuelo", nombreVuelo);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarReservasDeCliente(String nicknameCliente) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT CONCAT('Reserva Nro: ', r.identificador, ' - Vuelo: ', r.vuelo.nombre, " +
                            "' - Pasajes: ', r.cantPasajes, ' - Tipo: ', r.tipoAsiento) " +
                            "FROM Reserva r WHERE r.cliente.nickname = :nick AND r.paquete IS NULL",
                    String.class
            );
            query.setParameter("nick", nicknameCliente);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarPaquetesDeCliente(String nicknameCliente) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<String> query = entityManager.createQuery(
                    "SELECT CONCAT('Reserva Nro: ', r.identificador, ' - Paquete: ', r.paquete.nombre, r.paquete.id) " +
                            "FROM Reserva r " +
                            "WHERE r.cliente.nickname = :nick AND r.paquete IS NOT NULL",
                    String.class
            );
            query.setParameter("nick", nicknameCliente);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<String> obtenerReservaVueloPorCliente(String nicknameCliente, String nombreVuelo) {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<String> query = em.createQuery(

                    "SELECT CONCAT('Reserva Nro: ', r.id, " +
                            " ' - Vuelo: ', r.vuelo.nombre, " +
                            " ' - Pasajes: ', r.cantPasajes, " +
                            " ' - Tipo: ', r.tipoAsiento) " +
                            "FROM Reserva r " +
                            "JOIN r.cliente c " +
                            "JOIN r.vuelo v " +
                            "WHERE c.nickname = :nick AND v.nombre = :nombreVuelo",
                    String.class
            );

            query.setParameter("nick", nicknameCliente);
            query.setParameter("nombreVuelo", nombreVuelo);
            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public List<DtReserva> listarDtReservasDeVuelo(String nombreVuelo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE r.vuelo.nombre = :nombreVuelo",
                    Reserva.class);
            query.setParameter("nombreVuelo", nombreVuelo);
            List<Reserva> reservas = query.getResultList();

            return reservas.stream().map(reserva -> {
                DtCliente dtCliente = new DtCliente(
                        reserva.getCliente().getNickname(),
                        reserva.getCliente().getNombre(),
                        reserva.getCliente().getEmail(),
                        reserva.getCliente().getUrlImagen(),
                        reserva.getCliente().getApellido(),
                        reserva.getCliente().getFechaNac(),
                        reserva.getCliente().getNacionalidad(),
                        reserva.getCliente().getTipoDocumento(),
                        reserva.getCliente().getNumDocumento()
                );

                DtVuelo dtVuelo = new DtVuelo(
                        reserva.getVuelo().getNombre(),
                        reserva.getVuelo().getFecha(),
                        reserva.getVuelo().getDuracion(),
                        reserva.getVuelo().getAsientosTurista(),
                        reserva.getVuelo().getAsientosEjecutivo(),
                        reserva.getVuelo().getFechaAlta(),
                        reserva.getVuelo().getRuta().getNombre(),
                        reserva.getVuelo().getUrlImagen()
                );

                List<DtPasaje> dtPasajeList = reserva.getPasajes().stream().map(p -> new DtPasaje(
                        p.getIdentificador(),
                        p.getNombre(),
                        p.getNombre()
                )).toList();

                return new DtReserva(reserva.getFecha(), reserva.getTipoAsiento(), reserva.getEquipajeExtra(), reserva.getCosto(), dtCliente,
                        dtVuelo, null, dtPasajeList, reserva.getVencimiento());
            }).toList();
        } finally {
            em.close();
        }
    }

    public List<DtReserva> listarCompraPaqueteCliente(String nombreCliente) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r WHERE r.cliente.nombre = :nombreCliente AND r.paquete.id IS NOT NULL",
                    Reserva.class);
            query.setParameter("nombreCliente", nombreCliente);
            List<Reserva> reservas = query.getResultList();

            return reservas.stream().map(reserva -> {
                DtCliente dtCliente = new DtCliente(
                        reserva.getCliente().getNickname(),
                        reserva.getCliente().getNombre(),
                        reserva.getCliente().getEmail(),
                        reserva.getCliente().getUrlImagen(),
                        reserva.getCliente().getApellido(),
                        reserva.getCliente().getFechaNac(),
                        reserva.getCliente().getNacionalidad(),
                        reserva.getCliente().getTipoDocumento(),
                        reserva.getCliente().getNumDocumento()
                );

                DtVuelo dtVuelo = new DtVuelo(
                        reserva.getVuelo().getNombre(),
                        reserva.getVuelo().getFecha(),
                        reserva.getVuelo().getDuracion(),
                        reserva.getVuelo().getAsientosTurista(),
                        reserva.getVuelo().getAsientosEjecutivo(),
                        reserva.getVuelo().getFechaAlta(),
                        reserva.getVuelo().getRuta().getNombre(),
                        reserva.getVuelo().getUrlImagen()
                );

                List<DtPasaje> dtPasajeList = reserva.getPasajes().stream().map(p -> new DtPasaje(
                        p.getIdentificador(),
                        p.getNombre(),
                        p.getNombre()
                )).toList();

                return new DtReserva(reserva.getFecha(), reserva.getTipoAsiento(), reserva.getEquipajeExtra(), reserva.getCosto(), dtCliente,
                        dtVuelo, null, dtPasajeList, reserva.getVencimiento());
            }).toList();
        } finally {
            em.close();
        }
    }

    public List<String> listarPasajesDeReservas(String nombreReserva) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<String> query = em.createQuery(
                    "SELECT CONCAT('Nombre: ', p.nombre, ' - Apellido: ', p.apellido) " +
                            "FROM Pasaje p " +
                            "WHERE p.reserva.id = :idReserva",
                    String.class
            );
            query.setParameter("idReserva", nombreReserva);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public DtReserva obtenerDtReservaPorCliVue(String nicknameCliente, String nombreVuelo) {
        EntityManager em = emf.createEntityManager();
        try {

            TypedQuery<Reserva> query = em.createQuery(
                    "SELECT r FROM Reserva r " +
                            "JOIN FETCH r.cliente c " +
                            "JOIN FETCH r.vuelo v " +
                            "WHERE c.nickname = :nick AND v.nombre = :nombreVuelo",
                    Reserva.class
            );

            query.setParameter("nick", nicknameCliente);
            query.setParameter("nombreVuelo", nombreVuelo);

            Reserva reserva = query.getSingleResult();

            if (reserva.getCliente() == null) {
                return null;
            }

            var cli = reserva.getCliente();
            DtCliente dtCliente = new DtCliente(
                    cli.getNickname(),
                    cli.getNombre(),
                    cli.getEmail(),
                    cli.getUrlImagen(),
                    cli.getApellido(),
                    cli.getFechaNac(),
                    cli.getNacionalidad(),
                    cli.getTipoDocumento(),
                    cli.getNumDocumento()
            );

            if (reserva.getVuelo() == null) {
                return null;
            }

            var vuelo = reserva.getVuelo();
            DtVuelo dtVuelo = new DtVuelo(
                    vuelo.getNombre(),
                    vuelo.getFecha(),
                    vuelo.getDuracion(),
                    vuelo.getAsientosTurista(),
                    vuelo.getAsientosEjecutivo(),
                    vuelo.getFechaAlta(),
                    vuelo.getRuta() != null ? vuelo.getRuta().getNombre() : null,
                    vuelo.getUrlImagen()
            );

            DtPaqueteVuelo dtPaquete = null;
            if (reserva.getPaquete() != null) {
                var p = reserva.getPaquete();
                dtPaquete = new DtPaqueteVuelo(
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getDiasValidez(),
                        p.getDescuento(),
                        p.getAltaFecha()
                );
            }

            List<DtPasaje> dtPasajeList = List.of();
            if (reserva.getPasajes() != null && !reserva.getPasajes().isEmpty()) {
                dtPasajeList = reserva.getPasajes().stream().map(p -> {
                    return new DtPasaje(
                            p.getIdentificador(),
                            p.getNombre(),
                            p.getNombre()
                    );
                }).toList();
            }

            DtReserva dtReserva = new DtReserva(
                    reserva.getFecha(),
                    reserva.getTipoAsiento(),
                    reserva.getEquipajeExtra(),
                    reserva.getCosto(),
                    dtCliente,
                    dtVuelo,
                    dtPaquete,
                    dtPasajeList,
                    reserva.getVencimiento()
            );
            return dtReserva;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
