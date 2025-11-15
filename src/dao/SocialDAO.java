package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import model.Cliente;

public class SocialDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void seguirUsuario(String idSeguidor, String idSeguido) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        if (idSeguidor.equals(idSeguido))
            throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo");

        if (idSeguidor == null || idSeguido == null || idSeguidor.isEmpty() || idSeguido.isEmpty())
            throw new IllegalArgumentException("Los IDs de seguidor y seguido no pueden ser nulos o vacíos");

        try {

            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nickname = :nick", Cliente.class);
            query.setParameter("nick", idSeguidor);

            try {
                query.getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguidor no existe");
            }

            query.setParameter("nick", idSeguido);

            try {
                query.getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguido no existe");
            }

        } finally {
            entityManager.close();
        }

        try {
            transaction.begin();
            model.Social social = new model.Social(idSeguidor, idSeguido);
            entityManager.persist(social);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void dejarSeguirUsuario(String idSeguidor, String idSeguido) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        if (idSeguidor.equals(idSeguido))
            throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo");

        if (idSeguidor == null || idSeguido == null || idSeguidor.isEmpty() || idSeguido.isEmpty())
            throw new IllegalArgumentException("Los IDs de seguidor y seguido no pueden ser nulos o vacíos");

        try {

            TypedQuery<Cliente> query = entityManager.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nickname = :nick", Cliente.class);
            query.setParameter("nick", idSeguidor);

            try {
                query.getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguidor no existe");
            }

            query.setParameter("nick", idSeguido);

            try {
                query.getSingleResult();
            } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguido no existe");
            }

        } finally {
            entityManager.close();
        }

        try {
            transaction.begin();
            int deleted = entityManager.createQuery(
                    "DELETE FROM Social s WHERE s.idSeguidor = :seguidor AND s.idSeguido = :seguido")
                    .setParameter("seguidor", idSeguidor)
                    .setParameter("seguido", idSeguido)
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
