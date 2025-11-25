package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import model.Aerolinea;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AerolineaDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");

    public void guardarAerolinea(Aerolinea aerolinea) {
        EntityManager entityM = emf.createEntityManager();
        EntityTransaction transaction = entityM.getTransaction();
        try {
            transaction.begin();
            entityM.persist(aerolinea);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            entityM.close();
        }
    }

    public Aerolinea obtenerAerolineaPorNick(String nick) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.find(Aerolinea.class, nick);
        } finally {
            entityManager.close();
        }
    }


    public List<String> listarNicknamesAerolinea() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a.nickname FROM Aerolinea a", String.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void modificarAerolinea(Aerolinea aero, String nick) {
        if (aero == null) throw new IllegalArgumentException("La aerolínea es obligatoria");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Aerolinea aerolinea = entityManager.find(Aerolinea.class, nick);
            if (aerolinea == null) {
                throw new IllegalArgumentException("No existe aerolínea con nickname: " + nick);
            }

            aerolinea.setNombre(aero.getNombre());
            aerolinea.setDescripcion(aero.getDescripcion());
            aerolinea.setSitioWeb(aero.getSitioWeb());
            if (aerolinea.getContrasenia() != aero.getContrasenia() && aero.getContrasenia() != null) {
                String pass = BCrypt.hashpw(aero.getContrasenia(), BCrypt.gensalt());
                aerolinea.setContrasenia(pass);
            }
            aerolinea.setUrlImagen(aero.getUrlImagen());
            transaction.commit();
        } catch (PersistenceException ex) {
            if (transaction.isActive()) transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    public List<Aerolinea> listarAerolineas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Aerolinea a", Aerolinea.class).getResultList();
        } finally {
            em.close();
        }
    }
}
