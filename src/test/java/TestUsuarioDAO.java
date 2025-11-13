package test.java;

import dao.UsuarioDAO;
import data_types.DtUsuario;
import data_types.EstadoUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestUsuarioDAO {

    private static UsuarioDAO usuarioDAO;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void inicializar() {
        usuarioDAO = UsuarioDAO.getInstance();
        entityManagerFactory = Persistence.createEntityManagerFactory("appPU");

        // 🔹 Crear y persistir usuarios base
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Cliente clienteTest = new Cliente(
                "nicktest",
                "Laura",
                "laura@test.com",
                BCrypt.hashpw("clave123", BCrypt.gensalt()),
                "https://foto.com/laura.jpg",
                "Fernandez",
                LocalDate.of(1990, 2, 15),
                "Uruguaya",
                data_types.TipoDoc.CEDULA,
                "51234567"
        );

        entityManager.persist(clienteTest);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void testRegistrarEstadoUsuario() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cliente cliente = entityManager.find(Cliente.class, "nicktest");

        DtUsuario dtCliente = usuarioDAO.registrarEstadoUsuario(cliente);
        assertNotNull(dtCliente);
        assertEquals("nicktest", dtCliente.getNickname());
        assertEquals(EstadoUsuario.INGRESADO, cliente.getEstado());

        entityManager.close();
    }

    @Test
    public void testListarNicknamesUsuarios() {
        List<String> listaNicknames = usuarioDAO.listarNicknamesUsuarios();
        assertNotNull(listaNicknames);
        assertTrue(listaNicknames.contains("nicktest"));
    }

    @Test
    public void testExisteUsuarioConEmail() {
        boolean existeEmail = usuarioDAO.existeUsuarioConEmail("laura@test.com");
        assertTrue(existeEmail);
        assertFalse(usuarioDAO.existeUsuarioConEmail("noexiste@test.com"));
    }

    @Test
    public void testDataUsuarioPorNick() {
        DtUsuario dtUsuario = usuarioDAO.dataUsuarioPorNick("nicktest");
        assertNotNull(dtUsuario);
        assertEquals("nicktest", dtUsuario.getNickname());
    }

    @Test
    public void testIniciarYCerrarSesion() {
        DtUsuario dtInicioSesion = usuarioDAO.iniciarSesion("nicktest", "clave123");
        assertNotNull(dtInicioSesion);
        assertEquals("nicktest", dtInicioSesion.getNickname());

        DtUsuario dtCierreSesion = usuarioDAO.cerrarSesion("nicktest");
        assertNotNull(dtCierreSesion);
        assertEquals("nicktest", dtCierreSesion.getNickname());
    }

}
