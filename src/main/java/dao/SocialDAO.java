package dao;

import jakarta.persistence.*;
import model.Usuario;
import model.Social;
import java.util.List;

public class SocialDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    // Verifica si ya existe la relación seguidor->seguido
    private boolean existeRelacion(EntityManager em, String idSeguidor, String idSeguido) {
        Long count = em.createQuery(
                        "SELECT COUNT(s) FROM Social s WHERE s.idSeguidor = :seguidor AND s.idSeguido = :seguido",
                        Long.class)
                .setParameter("seguidor", idSeguidor)
                .setParameter("seguido", idSeguido)
                .getSingleResult();
        return count != null && count > 0;
    }

    public void seguirUsuario(String idSeguidor, String idSeguido) {

        if (idSeguidor == null || idSeguido == null || idSeguidor.isEmpty() || idSeguido.isEmpty())
            throw new IllegalArgumentException("Los IDs de seguidor y seguido no pueden ser nulos o vacíos");

        if (idSeguidor.equals(idSeguido))
            throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Verificar existencia de seguidor
            TypedQuery<Usuario> qSeguidor = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class);
            qSeguidor.setParameter("nick", idSeguidor);
            try { qSeguidor.getSingleResult(); } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguidor no existe");
            }

            // Verificar existencia de seguido
            TypedQuery<Usuario> qSeguido = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class);
            qSeguido.setParameter("nick", idSeguido);
            try { qSeguido.getSingleResult(); } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguido no existe");
            }

            // Verificar si ya existe la relación
            if (existeRelacion(em, idSeguidor, idSeguido)) {
                throw new IllegalArgumentException("Ya estás siguiendo a " + idSeguido);
            }

            // Persistir relación
            tx.begin();
            Social social = new Social(idSeguidor, idSeguido);
            em.persist(social);
            tx.commit();

        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public void dejarSeguirUsuario(String idSeguidor, String idSeguido) {

        if (idSeguidor == null || idSeguido == null || idSeguidor.isEmpty() || idSeguido.isEmpty())
            throw new IllegalArgumentException("Los IDs de seguidor y seguido no pueden ser nulos o vacíos");

        if (idSeguidor.equals(idSeguido))
            throw new IllegalArgumentException("Un usuario no puede dejar de seguirse a sí mismo");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Verificar existencia de seguidor
            TypedQuery<Usuario> qSeguidor = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class);
            qSeguidor.setParameter("nick", idSeguidor);
            try { qSeguidor.getSingleResult(); } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguidor no existe");
            }

            // Verificar existencia de seguido
            TypedQuery<Usuario> qSeguido = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class);
            qSeguido.setParameter("nick", idSeguido);
            try { qSeguido.getSingleResult(); } catch (NoResultException e) {
                throw new IllegalArgumentException("El seguido no existe");
            }

            // Verificar si existe la relación
            if (!existeRelacion(em, idSeguidor, idSeguido)) {
                throw new IllegalArgumentException("No estás siguiendo a " + idSeguido);
            }

            // Eliminar relación
            tx.begin();
            em.createQuery(
                            "DELETE FROM Social s WHERE s.idSeguidor = :seguidor AND s.idSeguido = :seguido")
                    .setParameter("seguidor", idSeguidor)
                    .setParameter("seguido", idSeguido)
                    .executeUpdate();
            tx.commit();

        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
    }
    public boolean existeRelacion(String idSeguidor, String idSeguido) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Social s WHERE s.idSeguidor = :seguidor AND s.idSeguido = :seguido", Long.class)
                    .setParameter("seguidor", idSeguidor)
                    .setParameter("seguido", idSeguido)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

        public List<String> listarSeguidores(String idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.idSeguidor FROM Social s WHERE s.idSeguido = :usuario", String.class)
                    .setParameter("usuario", idUsuario)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Listar usuarios que sigue un usuario
    public List<String> listarSeguidos(String idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.idSeguido FROM Social s WHERE s.idSeguidor = :usuario", String.class)
                    .setParameter("usuario", idUsuario)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
