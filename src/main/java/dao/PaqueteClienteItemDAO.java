package dao;

import data_types.TipoAsiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.*;


public class PaqueteClienteItemDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardar(PaqueteClienteItem pci) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pci);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void crearItemsDePaqueteParaCliente(Reserva reservaPaquete) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            PaqueteVuelo paquete = reservaPaquete.getPaquete();
            Cliente cliente = reservaPaquete.getCliente();

            for (ItemPaquete item : paquete.getItems()) {

                PaqueteClienteItem pci = new PaqueteClienteItem(
                        cliente,
                        item.getCant(),              // cantidadRestante inicial
                        item.getTipoAsiento(),
                        item.getRutaVuelo(),
                        reservaPaquete               // referencia a la reserva que representa la compra del paquete
                );

                em.persist(pci);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(PaqueteClienteItem pci) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pci);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene el item del paquete que el cliente puede usar
     * según ruta + tipo de asiento.
     */
    public PaqueteClienteItem obtenerItemDisponible(
            Cliente cliente,
            RutaVuelo ruta,
            TipoAsiento tipoAsiento
    ) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("""
            SELECT pci FROM PaqueteClienteItem pci
                JOIN FETCH pci.reservaPaquete rp
                JOIN FETCH rp.paquete p
            WHERE pci.cliente = :cliente
              AND pci.rutaVuelo = :ruta
              AND pci.tipoAsiento = :tipoAsiento
              AND pci.cantidadRestante > 0
            """, PaqueteClienteItem.class)
                    .setParameter("cliente", cliente)
                    .setParameter("ruta", ruta)
                    .setParameter("tipoAsiento", tipoAsiento)   // <-- corregido
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

}


