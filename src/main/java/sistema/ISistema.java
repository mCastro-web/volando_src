
package sistema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import data_types.*;
import jakarta.servlet.http.Part;
import model.Aerolinea;
import model.Cliente;
import model.PaqueteVuelo;

public interface ISistema {

    // === CIUDAD ===
    void altaCiudad(String nombre, String pais, String nombreAeropuerto,
            String descripcionAeropuerto, String sitioWeb, LocalDate fechaAlta) throws Exception;

    // === CLIENTE ===
    DtUsuario altaCliente(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String apellido, LocalDate nacimiento,
            String nacionalidad, TipoDoc documento, String numeroDoc);

    // List<Cliente> listarClientes();
    DtUsuario buscarPorNick(String nickname);

    List<String> listarNicknames();

    List<String> listarReservasDeCliente(String nicknameCliente);

    List<String> listarPaquetesDeCliente(String nicknameCliente);

    void modificarCliente(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String apellido, LocalDate nacimiento,
            String nacionalidad, TipoDoc documento, String numeroDoc, String nick);

    // === AEROLÍNEA ===
    DtUsuario altaAerolinea(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String descripcion, String sitioWeb);

    void modificarAerolinea(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String descripcion, String sitioWeb, String nick);

    // === USUARIO ===
    void eliminarSocial(String idSeguidor, String idSeguido);

    void guardarSocial(String idSeguidor, String idSeguido);

    DtUsuario consultaUsuario(String nickname);

    DtUsuario dataUsuarioPorNick(String nickname);

    List<String> listarRutasConfirmadasAerolinea(String nombre);

    // === CATEGORÍA ===
    void altaCategoria(String nombreCategoria) throws Exception;

    void actualizarEstadoRuta(String nombreRuta, EstadoRuta nuevoEstado);

    // === RUTA DE VUELO ===
    void altaRutaVuelo(String nombre, String descripcion, LocalDate fechaAlta,
            float costoBaseTurista, float costoBaseEjecutivo, float costoEquipajeExtra,
            String nickAerolinea, String origenNombre, String destinoNombre, String nombreCategoria, String urlImagen,
            String urlVideo, String descripcionCorta);

    List<String> listarRutasConfirmadasPorCategoria(String nombreCategoria);

    void finalizarRutaDeVuelo(String nombreRuta);

    List<String> listarNombresCategorias();

    List<String> listarNombresCiudades();

    List<String> listarNicknamesAerolineas();

    DtRutaVuelo obtenerDtRutaPorNombre(String nombre);

    /*
     * List<String> listarRutasIngresadasPorAerolinea(String nombre);
     * void actualizarEstadoRuta(String nombreRuta, String nuevoEstadoStr);
     */
    void incrementarVisitaRuta(String nombreRuta);

    List<DtRutaVuelo> listarRutasMasVisitadas();

    // === VUELO ===
    void altaVuelo(String nombre, LocalDate fecha, LocalTime duracion,
            int asientosTurista, int asientosEjecutivo, LocalDate fechaAlta, String nombreRuta, String urlImagen);

    List<String> listarRutasPorAerolinea(String nombre);

    List<DtDatosVueloR> listarVuelosDeRuta(String nombreRuta);

    List<String> listarReservasDeVuelo(String nombreVuelo);

    List<String> listarNombresVuelosPorRuta(String nombreRuta);

    void reservarVuelo(String nickCliente, String nombreVuelo, TipoAsiento tipoAsiento,
            int cantEquipaje, LocalDate fechaReserva,
            int cantPasajes, List<String> nombresPasajeros, List<String> apellidosPasajeros);

    // === PAQUETE DE VUELO ===
    void altaPaqueteRV(String nombre, String descripcion, Integer diasValidez, Float descuento, LocalDate fecha);

    void reservarVueloConPaquete(
            String nickCliente, String nombreVuelo, TipoAsiento tipoAsiento,
            int cantEquipaje, LocalDate fechaReserva,
            int cantPasajes, List<String> nombresPasajeros, List<String> apellidosPasajeros);

    void agregarRutaPaquete(String nombrePaquete, String nickAerolinea, String nombreRuta, Integer cantidad,
            TipoAsiento tipoAsiento);

    List<PaqueteVuelo> listarPaquetes();

    List<DtPaqueteVuelo> listarDTPaquetes();

    List<String> listarNombresPaquetes();

    PaqueteVuelo buscarPaqueteVuelo(String nombre);

    List<DtItemPaquete> listarItemsDePaquete(Long idPaquete);

    // === COMPRA DE PAQUETE ===
    void comprarPaquete(String nickCliente, String nombrePaquete, LocalDate fechaCompra);

    List<String> listarNombresPaquetesConRutas();

    DtPaqueteVuelo obtenerDtPaquetePorNombre(String nombre);

    DtPaqueteVuelo consultaPaquete(String nombrePaquete);

    DtVuelo obtenerDtVueloPorNombre(String nombreVuelo);
    // === MÉTODOS AUXILIARES ===

    boolean validarCedulaUruguaya(String cedula);

    boolean validarPasaporte(String pasaporte);

    DtUsuario iniciarSesionUsuario(String identificador, String contra);

    DtUsuario cerrarSesionUsuario(String nick);

    List<DtReserva> listarDtReservasDeVuelo(String nombreVuelo);

    List<String> obtenerReservasPorClienteVuelo(String vuelo, String cliente);

    String subirImagen(Part filePart, String nombreArchivo);

    DtReserva obtenerDtReservaPorClienteVuelo(String vuelo, String cliente);

    List<Aerolinea> listarAerolineas();

    List<Cliente> listarClientes();

    List<String> listarPasajesReservasDeVuelo(String nombreReserva);

    List<String> obtenerListaNombresRutasPorPaquete(String nombreReserva);

    List<DtReserva> listarPaquetesCompradosPorCliente(String nickCliente);

    boolean existeEmail(String email);

    boolean existeNickname(String nickname);

    List<DtRutaVuelo> buscarRutas(String query);

    List<DtPaqueteVuelo> buscarPaquetes(String query);
    List<Long> listarReservasPendientesCheckin(String nick);
    void realizarCheckin(Long idReserva);
    List<Long> listarReservasConCheckin(String nick);
    DtReserva obtenerReservaCheckin(Long idReserva);
    DtCheckin obtenerCheckinPorReserva(Long idReserva);
    DtUsuarioExtendido consultaUsuarioExtendido(String nicknameConsultado, String nicknameLogueado);
    DtUsuarioExtendido dataUsuarioPorNickExtendido(String nicknameConsultado,  String nicknameLogueado);

}
