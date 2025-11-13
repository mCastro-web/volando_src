package dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import model.Cliente;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class ClienteDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    // Registrar cliente
    public void guardarCliente(Cliente cliente) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            // Validaciones de unicidad
            Long countNick = entityManager.createQuery(
                            "SELECT COUNT(u) FROM Cliente u WHERE u.nickname = :nick", Long.class)
                    .setParameter("nick", cliente.getNickname())
                    .getSingleResult();
            if (countNick > 0) throw new IllegalArgumentException("El nickname ya existe");

            Long countEmail = entityManager.createQuery(
                            "SELECT COUNT(u) FROM Cliente u WHERE u.email = :mail", Long.class)
                    .setParameter("mail", cliente.getEmail())
                    .getSingleResult();
            if (countEmail > 0) throw new IllegalArgumentException("El email ya existe");

            Long countDoc = entityManager.createQuery(
                            "SELECT COUNT(u) FROM Cliente u WHERE u.tipoDocumento = :tipo AND u.numDocumento = :num", Long.class)
                    .setParameter("tipo", cliente.getTipoDocumento())
                    .setParameter("num", cliente.getNumDocumento())
                    .getSingleResult();
            if (countDoc > 0) throw new IllegalArgumentException("Documento ya registrado");





            transaction.begin();
            entityManager.persist(cliente);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public Cliente obtenerClientePorNickname(String nick) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT c FROM Cliente c LEFT JOIN FETCH c.reserva WHERE c.nickname = :nick", Cliente.class);
            query.setParameter("nick", nick);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<String> listarNicknames() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT c.nickname FROM Cliente c", String.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    public void modificarCliente(Cliente cli, String nick) {
        if (cli == null) throw new IllegalArgumentException("El cliente es obligatorio");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cliente cliente = entityManager.find(Cliente.class, nick);
            if (cliente == null) {
                throw new IllegalArgumentException("No existe cliente con nickname: " + cli.getNickname());
            }

            cliente.setNombre(cli.getNombre());
            cliente.setApellido(cli.getApellido());
            cliente.setNacionalidad(cli.getNacionalidad());
            cliente.setTipoDocumento(cli.getTipoDocumento());
            cliente.setNumDocumento(cli.getNumDocumento());

            if (cliente.getContrasenia() != cli.getContrasenia() && cli.getContrasenia() != null) {
                String pass = BCrypt.hashpw(cli.getContrasenia(), BCrypt.gensalt());
                cliente.setContrasenia(pass);
            }

            cliente.setUrlImagen(cli.getUrlImagen());

            transaction.commit();
        } catch (PersistenceException ex) {
            if (transaction.isActive()) transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public List<Cliente> listarClientes() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c ORDER BY LOWER(c.nickname)", Cliente.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
