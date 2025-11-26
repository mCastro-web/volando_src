package sistema;

import dao.*;
import data_types.*;
import model.Aerolinea;
import model.Categoria;
import model.Ciudad;
import model.Cliente;
import model.RutaVuelo;
import model.PaqueteVuelo;
import model.Reserva;
import model.Pasaje;
import model.Aeropuerto;
import model.Vuelo;
import model.PaqueteClienteItem;

import org.mindrot.jbcrypt.BCrypt;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.Part;

import javax.swing.*;

public class Sistema implements ISistema {

    private static Sistema INSTANCE = null;

    // === DAOs ===
    private final CiudadDAO ciudadDAO = new CiudadDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final RutaVueloDAO rutaDAO = new RutaVueloDAO();
    private final VueloDAO vueloDAO = new VueloDAO();
    private final PaqueteVueloDAO paqueteDAO = new PaqueteVueloDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AerolineaDAO aerolineaDAO = new AerolineaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final SocialDAO socialDAO = new SocialDAO();
    private final PaqueteClienteItemDAO paqueteClienteItemDAO = new PaqueteClienteItemDAO();

    private Sistema() {
    }

    public static Sistema getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Sistema();
        return INSTANCE;
    }

    // ===================== CLIENTE =====================
    public DtUsuario altaCliente(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String apellido, LocalDate nacimiento,
            String nacionalidad, TipoDoc documento, String numeroDoc) {

        if (nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("El nickname es obligatorio");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (email == null || email.isBlank() || !esEmailValido(email))
            throw new IllegalArgumentException("Formato de mail incorrecto.");
        if (apellido == null || apellido.isBlank())
            throw new IllegalArgumentException("El apellido es obligatorio");
        if (nacimiento == null)
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        if (nacionalidad == null || nacionalidad.isBlank())
            throw new IllegalArgumentException("La nacionalidad es obligatoria");
        if (documento == null)
            throw new IllegalArgumentException("El tipo de documento es obligatorio");
        if (numeroDoc == null || numeroDoc.isBlank())
            throw new IllegalArgumentException("El número de documento es obligatorio");

        if (documento == TipoDoc.CEDULA && !validarCedulaUruguaya(numeroDoc))
            throw new IllegalArgumentException("La cédula es inválida.");
        if (documento == TipoDoc.PASAPORTE && !validarPasaporte(numeroDoc))
            throw new IllegalArgumentException("Formato de pasaporte incorrecto.");

        String pass = BCrypt.hashpw(contrasenia, BCrypt.gensalt());

        Cliente cliente = new Cliente(
                nickname.trim(),
                nombre.trim(),
                email.trim(),
                pass,
                urlImagen == null ? null : urlImagen.trim(),
                apellido.trim(),
                nacimiento,
                nacionalidad.trim(),
                documento,
                numeroDoc.trim());

        clienteDAO.guardarCliente(cliente);
        return usuarioDAO.registrarEstadoUsuario(cliente);
    }

    public void modificarCliente(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String apellido, LocalDate nacimiento,
            String nacionalidad, TipoDoc documento, String numeroDoc, String nick) {
        Cliente cliente = new Cliente(nickname.trim(), nombre.trim(), email.trim(), contrasenia.trim(),
                urlImagen != null ? urlImagen.trim() : null,
                apellido.trim(), nacimiento, nacionalidad.trim(),
                documento, numeroDoc.trim());
        clienteDAO.modificarCliente(cliente, nick);

    }

    // ===================== AEROLÍNEA =====================
    public DtUsuario altaAerolinea(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String descripcion, String sitioWeb) {

        if (nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("El nickname es obligatorio");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (email == null || !esEmailValido(email))
            throw new IllegalArgumentException("Formato de mail incorrecto");
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción es obligatoria");

        String pass = BCrypt.hashpw(contrasenia, BCrypt.gensalt());

        Aerolinea aerolinea = new Aerolinea(nickname.trim(), nombre.trim(), email.trim(), pass.trim(),
                urlImagen != null ? urlImagen.trim() : null,
                descripcion.trim(), sitioWeb != null ? sitioWeb.trim() : null);

        aerolineaDAO.guardarAerolinea(aerolinea);
        return usuarioDAO.registrarEstadoUsuario(aerolinea);
    }

    public void modificarAerolinea(String nickname, String nombre, String email, String contrasenia, String urlImagen,
            String descripcion, String sitioWeb, String nick) {
        Aerolinea aerolinea = new Aerolinea(
                nickname.trim(),
                nombre.trim(),
                email.trim(),
                contrasenia.trim(),
                urlImagen == null ? null : urlImagen.trim(),
                descripcion == null ? null : descripcion.trim(),
                sitioWeb == null ? null : sitioWeb.trim());
        aerolineaDAO.modificarAerolinea(aerolinea, nick);
    }

    // ===================== CONSULTAS =====================
    public DtUsuario consultaUsuario(String nickname) {
        if (nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("El nickname es obligatorio");

        DtUsuario usuarioDto = usuarioDAO.dataUsuarioPorNick(nickname);
        if (usuarioDto == null)
            throw new IllegalArgumentException("No existe un usuario con nickname: " + nickname);

        return usuarioDto;
    }

    @Override
    public DtUsuario dataUsuarioPorNick(String nickname) {
        return usuarioDAO.dataUsuarioPorNick(nickname);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    @Override
    public DtUsuario buscarPorNick(String nickname) {
        return usuarioDAO.dataUsuarioPorNick(nickname);
    }

    @Override
    public List<String> listarNicknames() {
        return clienteDAO.listarNicknames();
    }

    public List<String> listarUsuariosPorNick() {
        return usuarioDAO.listarNicknamesUsuarios();
    }

    public List<String> listarReservasDeCliente(String nombre) {
        return reservaDAO.listarReservasDeCliente(nombre);
    }

    public List<String> listarPaquetesDeCliente(String nombre) {
        return reservaDAO.listarPaquetesDeCliente(nombre);
    }

    public Cliente obtenerClientePorNickname(String nickname) {
        return clienteDAO.obtenerClientePorNickname(nickname);
    }

    public Aerolinea obtenerAerolineaPorNick(String nickAerolinea) {
        return aerolineaDAO.obtenerAerolineaPorNick(nickAerolinea);
    }

    // ===================== RUTAS Y VUELOS =====================
    public void altaRutaVuelo(String nombre, String descripcion, LocalDate fechaAlta,
            float costoBaseTurista, float costoBaseEjecutivo, float costoEquipajeExtra,
            String nickAerolinea, String origenNombre, String destinoNombre, String nombreCategoria, String urlImagen,
            String urlVideo, String descripcionCorta) {

        Ciudad origen = ciudadDAO.obtenerCiudadPorNombre(origenNombre);
        Ciudad destino = ciudadDAO.obtenerCiudadPorNombre(destinoNombre);
        Aerolinea aerolinea = aerolineaDAO.obtenerAerolineaPorNick(nickAerolinea);
        Categoria categoria = categoriaDAO.obtenerCategoriaPorNombre(nombreCategoria);

        if (origen == null || destino == null)
            throw new IllegalArgumentException("Ciudad de origen o destino no encontrada");
        if (origen.getIdentificador().equals(destino.getIdentificador()))
            throw new IllegalArgumentException("La ciudad de origen y destino no pueden ser la misma");

        RutaVuelo ruta = new RutaVuelo(nombre, descripcion, fechaAlta,
                costoBaseTurista, costoBaseEjecutivo, costoEquipajeExtra,
                origen, destino, aerolinea, categoria, urlImagen, urlVideo, descripcionCorta);

        rutaDAO.guardarRutaVuelo(ruta);
    }

    @Override
    public List<String> listarNombresCategorias() {
        return categoriaDAO.listarNombresCategoria();
    }

    @Override
    public List<String> listarNombresCiudades() {
        return ciudadDAO.listarNombresCiudades();
    }

    @Override
    public List<String> listarNicknamesAerolineas() {
        return aerolineaDAO.listarNicknamesAerolinea();
    }

    @Override
    public DtRutaVuelo obtenerDtRutaPorNombre(String nombre) {
        return rutaDAO.obtenerDtRutaPorNombre(nombre);
    }

    public void altaVuelo(String nombre, LocalDate fecha, LocalTime duracion,
            int asientosTurista, int asientosEjecutivo, LocalDate fechaAlta,
            String nombreRuta, String urlImagen) {

        RutaVuelo ruta = rutaDAO.obtenerRutaPorNombre(nombreRuta);
        if (ruta == null)
            throw new IllegalArgumentException("La ruta '" + nombreRuta + "' no existe.");

        if (duracion == null || duracion.equals(LocalTime.MIDNIGHT))
            throw new IllegalArgumentException("Duración inválida");
        if (asientosTurista <= 0 || asientosEjecutivo <= 0)
            throw new IllegalArgumentException("Cantidad de asientos inválida");

        vueloDAO.guardarVuelo(nombre, fecha, duracion, asientosTurista, asientosEjecutivo, fechaAlta, ruta, urlImagen);
    }

    public void actualizarEstadoRuta(String nombreRuta, EstadoRuta nuevoEstado) {
        rutaDAO.actualizarEstado(nombreRuta, nuevoEstado);
    };

    @Override
    public List<String> listarRutasPorAerolinea(String nombre) {
        return rutaDAO.listarRutasPorAerolinea(nombre);
    }

    public List<String> listarRutasConfirmadasAerolinea(String nombre) {
        return rutaDAO.listarRutasConfirmadasPorAerolinea(nombre);
    }

    public List<String> listarRutasConfirmadasPorCategoria(String nombreCategoria) {
        return rutaDAO.listarRutasConfirmadasPorCategoria(nombreCategoria);
    }

    // ===================== RESERVA DE VUELOS =====================

    public List<DtDatosVueloR> listarVuelosDeRuta(String nombreRuta) {
        if (nombreRuta == null || nombreRuta.isBlank())
            throw new IllegalArgumentException("El nombre de la ruta es obligatorio");

        return vueloDAO.listarVuelosDeRuta(nombreRuta);
    }

    @Override
    public List<String> listarReservasDeVuelo(String nombreVuelo) {
        return reservaDAO.listarReservasDeVuelo(nombreVuelo);
    }

    @Override
    public List<String> listarNombresVuelosPorRuta(String nombreRuta) {
        return vueloDAO.listarNombresVuelosPorRuta(nombreRuta);
    }

    /*
     * public List<String> listarNicknamesClientes() {
     * return clienteDAO.listarNicknames();
     * }
     */

    public void reservarVueloConPaquete(
            String nickCliente, String nombreVuelo, TipoAsiento tipoAsiento,
            int cantEquipaje, LocalDate fechaReserva,
            int cantPasajes, List<String> nombresPasajeros, List<String> apellidosPasajeros) {

        // --- Validaciones básicas ---
        if (nickCliente == null || nickCliente.isBlank())
            throw new IllegalArgumentException("El cliente es obligatorio");
        if (nombreVuelo == null || nombreVuelo.isBlank())
            throw new IllegalArgumentException("El vuelo es obligatorio");
        if (tipoAsiento == null)
            throw new IllegalArgumentException("Debe indicar el tipo de asiento");
        if (cantPasajes <= 0)
            throw new IllegalArgumentException("La cantidad de pasajes debe ser mayor a 0");
        if (nombresPasajeros.size() != cantPasajes || apellidosPasajeros.size() != cantPasajes)
            throw new IllegalArgumentException(
                    "Los nombres y apellidos de pasajeros no coinciden con la cantidad de pasajes");

        Cliente cliente = clienteDAO.obtenerClientePorNickname(nickCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no encontrado");

        Vuelo vuelo = vueloDAO.buscarPorNombre(nombreVuelo);
        if (vuelo == null)
            throw new IllegalArgumentException("Vuelo no encontrado");

        RutaVuelo ruta = vuelo.getRuta();

        // === 1. Buscar ítem de paquete disponible ===
        PaqueteClienteItem pci = paqueteClienteItemDAO
                .obtenerItemDisponible(cliente, ruta, tipoAsiento);

        if (pci == null)
            throw new IllegalArgumentException("El cliente no tiene un paquete válido para esta ruta y asiento");

        // === 2. Verificar que alcance la cantidad ===
        if (pci.getCantidadRestante() < cantPasajes)
            throw new IllegalArgumentException("El paquete no alcanza para cubrir la reserva");

        // === 3. Chequear capacidad del vuelo ===
        Long ocupados = vueloDAO.contarAsientosOcupados(nombreVuelo, tipoAsiento);
        int maxAsientos = (tipoAsiento == TipoAsiento.TURISTA)
                ? vuelo.getAsientosTurista()
                : vuelo.getAsientosEjecutivo();

        if (ocupados + cantPasajes > maxAsientos)
            throw new IllegalArgumentException("No hay asientos suficientes en el vuelo");

        // === 4. Crear la reserva (costo = 0 porque se usa paquete) ===
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setVuelo(vuelo);
        reserva.setTipoAsiento(tipoAsiento);
        reserva.setEquipajeExtra(0); // normalmente paquete no incluye extra
        reserva.setCantPasajes(cantPasajes);
        reserva.setFecha(LocalDate.now());
        reserva.setCosto(0);// <<< Costo del vuelo pagado con paquete
        // reserva.setPaquete(pci.getReservaPaquete().getPaquete());

        // Crear pasajes
        for (int i = 0; i < cantPasajes; i++) {
            Pasaje pasaje = new Pasaje();
            pasaje.setReserva(reserva);
            pasaje.setNombre(nombresPasajeros.get(i));
            pasaje.setApellido(apellidosPasajeros.get(i));
            reserva.getPasajes().add(pasaje);
        }

        // === 5. Descontar del paquete ===
        pci.setCantidadRestante(pci.getCantidadRestante() - cantPasajes);
        paqueteClienteItemDAO.actualizar(pci);

        // === 6. Guardar reserva ===
        reservaDAO.guardar(reserva);
    }

    public void reservarVuelo(String nickCliente, String nombreVuelo, TipoAsiento tipoAsiento,
            int cantEquipaje, LocalDate fechaReserva,
            int cantPasajes, List<String> nombresPasajeros, List<String> apellidosPasajeros) {

        if (nickCliente == null || nickCliente.isBlank())
            throw new IllegalArgumentException("El cliente es obligatorio");
        if (nombreVuelo == null || nombreVuelo.isBlank())
            throw new IllegalArgumentException("El vuelo es obligatorio");
        if (tipoAsiento == null)
            throw new IllegalArgumentException("Debe indicar el tipo de asiento");
        if (cantPasajes <= 0)
            throw new IllegalArgumentException("La cantidad de pasajes debe ser mayor a 0");
        if (nombresPasajeros.size() != cantPasajes || apellidosPasajeros.size() != cantPasajes)
            throw new IllegalArgumentException(
                    "Los nombres y apellidos de pasajeros no coinciden con la cantidad de pasajes");

        // Obtener cliente y vuelo
        Cliente cliente = clienteDAO.obtenerClientePorNickname(nickCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no encontrado");

        Vuelo vuelo = vueloDAO.buscarPorNombre(nombreVuelo);
        if (vuelo == null)
            throw new IllegalArgumentException("Vuelo no encontrado");

        // Verificar disponibilidad
        Long ocupados = vueloDAO.contarAsientosOcupados(nombreVuelo, tipoAsiento);
        int maxAsientos = (tipoAsiento == TipoAsiento.TURISTA) ? vuelo.getAsientosTurista()
                : vuelo.getAsientosEjecutivo();

        if (ocupados + cantPasajes > maxAsientos)
            throw new IllegalArgumentException("No hay suficientes asientos disponibles en este vuelo");

        // Calcular costo
        float costoUnitario = (tipoAsiento == TipoAsiento.TURISTA)
                ? vuelo.getRuta().getCostoBaseTurista() + cantEquipaje * vuelo.getRuta().getCostoEquipajeExtra()
                : vuelo.getRuta().getCostoBaseEjecutivo() + cantEquipaje * vuelo.getRuta().getCostoEquipajeExtra();

        float costoTotal = costoUnitario * cantPasajes;

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setVuelo(vuelo);
        reserva.setTipoAsiento(tipoAsiento);
        reserva.setEquipajeExtra(cantEquipaje);
        reserva.setCantPasajes(cantPasajes);
        reserva.setCosto(costoTotal);
        reserva.setFecha(fechaReserva);

        // Crear pasajes asociados
        for (int i = 0; i < cantPasajes; i++) {
            Pasaje pasaje = new Pasaje();
            pasaje.setReserva(reserva);
            pasaje.setNombre(nombresPasajeros.get(i));
            pasaje.setApellido(apellidosPasajeros.get(i));
            reserva.getPasajes().add(pasaje);
        }

        // Asociar reserva al cliente
        cliente.getReserva().add(reserva);

        // Persistir usando DAO
        reservaDAO.guardar(reserva);
    }

    // ===================== ALTA CIUDAD =====================
    public void altaCiudad(String nombre, String pais, String nombreAeropuerto,
            String descripcionAeropuerto, String sitioWeb, LocalDate fechaAlta) {

        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la ciudad es obligatorio");
        if (pais == null || pais.isBlank())
            throw new IllegalArgumentException("El país es obligatorio");
        if (nombreAeropuerto == null || nombreAeropuerto.isBlank())
            throw new IllegalArgumentException("El aeropuerto es obligatorio");

        Aeropuerto aeropuerto = new Aeropuerto(
                nombreAeropuerto.trim(),
                descripcionAeropuerto != null ? descripcionAeropuerto.trim() : null,
                sitioWeb != null ? sitioWeb.trim() : null,
                fechaAlta,
                null);

        Ciudad ciudad = new Ciudad(nombre.trim(), pais.trim(), aeropuerto);
        try {
            ciudadDAO.guardarCiudad(ciudad);
        } catch (jakarta.persistence.PersistenceException e) {
            throw new RuntimeException("Error al guardar la ciudad: " + e.getMessage(), e);
        }
    }

    // ===================== ALTA PAQUETES =====================
    public void altaPaqueteRV(String nombre, String descripcion, Integer diasValidez, Float descuento,
            LocalDate fecha) {

        // Validaciones
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del Paquete es obligatorio");
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("Se debe ingresar una descripción");
        if (diasValidez == null)
            throw new IllegalArgumentException("Se debe ingresar los días de validez del paquete");
        if (descuento == null)
            throw new IllegalArgumentException("El valor del descuento es obligatorio");
        if (fecha == null)
            throw new IllegalArgumentException("La fecha del paquete es obligatoria");

        // Crear el paquete en memoria
        PaqueteVuelo nuevoPaquete = new PaqueteVuelo(nombre.trim(), descripcion.trim(), diasValidez, descuento, fecha);

        // Persistir usando DAO directamente
        try {
            paqueteDAO.registrarPaqueteVuelo(nuevoPaquete);
        } catch (jakarta.persistence.PersistenceException e) {
            throw new RuntimeException("Error al registrar el paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PaqueteVuelo> listarPaquetes() {
        return paqueteDAO.listarPaquetes();
    }

    @Override
    public List<DtPaqueteVuelo> listarDTPaquetes() {
        return List.of();
    }

    @Override
    public PaqueteVuelo buscarPaqueteVuelo(String nombre) {
        return paqueteDAO.buscarPaqueteVuelo(nombre);
    }

    // ===================== AGREGAR RUTA A PAQUETES =====================
    public void agregarRutaPaquete(String nombrePaquete, String nickAerolinea, String nombreRuta, Integer cantidad,
            TipoAsiento tipoAsiento) {
        if (nombrePaquete == null)
            throw new IllegalArgumentException("Debe indicar un Paquete de Vuelo");
        if (nickAerolinea == null)
            throw new IllegalArgumentException("Debe indicar una Aerolinea");
        if (nombreRuta == null)
            throw new IllegalArgumentException("Debe indicar una Ruta de Vuelo");
        if (tipoAsiento == null)
            throw new IllegalArgumentException("Debe indicar el tipo de asiento");
        if (cantidad == null || cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        PaqueteVuelo paqueteVuelo = paqueteDAO.buscarPaqueteVuelo(nombrePaquete);
        Aerolinea aerolinea = aerolineaDAO.obtenerAerolineaPorNick(nickAerolinea);
        RutaVuelo rutaVuelo = rutaDAO.obtenerRutaPorNombre(nombreRuta);
        try {
            paqueteDAO.agregarRutaPaquete(paqueteVuelo, aerolinea, rutaVuelo, cantidad, tipoAsiento);
        } catch (jakarta.persistence.PersistenceException e) {
            throw new RuntimeException("Error al agregar la ruta al paquete: " + e.getMessage(), e);
        }
    }

    // --------------caso de uso: consulta de paquete de rutas de vuelo---
    public List<String> listarNombresPaquetes() {
        return paqueteDAO.listarNombresPaquetes();
    }

    public DtPaqueteVuelo consultaPaquete(String nombrePaquete) {
        return paqueteDAO.consultaPaquete(nombrePaquete);
    }

    public List<DtItemPaquete> listarItemsDePaquete(Long idPaquete) {
        return paqueteDAO.listarItemsDePaquete(idPaquete);
    }

    public DtVuelo obtenerDtVueloPorNombre(String nombreVuelo) {
        return vueloDAO.obtenerDtVueloPorNombre(nombreVuelo);
    }

    // ===================== COMPRA PAQUETE =====================
    public List<String> listarNombresPaquetesConRutas() {
        return paqueteDAO.listarNombresPaquetesConRutas();
    }

    public DtPaqueteVuelo obtenerDtPaquetePorNombre(String nombre) {
        return paqueteDAO.obtenerDtPaquetePorNombre(nombre);
    }

    public void comprarPaquete(String nickCliente, String nombrePaquete, LocalDate fechaCompra) {
        Cliente cliente = clienteDAO.obtenerClientePorNickname(nickCliente);
        if (cliente == null)
            throw new IllegalArgumentException("Cliente no encontrado");

        // Obtener paquete desde DAO
        PaqueteVuelo paquete = paqueteDAO.buscarPaqueteVuelo(nombrePaquete);
        if (paquete == null)
            throw new IllegalArgumentException("Paquete no encontrado");

        // Validar que el cliente no haya comprado el paquete previamente
        if (paqueteDAO.clienteYaComproPaquete(cliente, paquete))
            throw new IllegalStateException("El cliente ya compró este paquete");

        // Calcular fecha de vencimiento del paquete
        LocalDate vencimiento = fechaCompra.plusDays(paquete.getDiasValidez());

        // Crear reserva
        Reserva reserva = new Reserva(
                null, // TipoAsiento (no aplica)
                fechaCompra,
                0, // Equipaje extra
                paquete.getCosto(),
                cliente,
                null, // Vuelo (no aplica)
                0, // Cantidad de pasajes
                paquete,
                new ArrayList<>(), // Lista de items (vacía)
                vencimiento);

        // Asociar reserva a cliente y paquete
        cliente.getReserva().add(reserva);
        paquete.getReservaPaquete().add(reserva);

        // Persistir reserva
        paqueteDAO.guardarReservaPaquete(reserva);
    }

    // ===================== ALTA CATEGORIA =====================
    public void altaCategoria(String nombreCategoria) throws jakarta.persistence.PersistenceException {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombreCategoria);
        categoriaDAO.guardar(categoria);
    }

    // ===================== MÉTODOS AUXILIARES =====================
    private boolean esEmailValido(String email) {
        if (email == null)
            return false;
        String email2 = email.trim();
        return !email2.isEmpty() && email2.contains("@") && email2.indexOf('@') > 0
                && email2.indexOf('@') < email2.length() - 1;
    }

    public boolean validarCedulaUruguaya(String cedula) {
        cedula = cedula.replace(".", "").replace("-", "");
        if (!cedula.matches("\\d{7,8}"))
            return false;
        if (cedula.length() == 7)
            cedula = "0" + cedula;

        int[] coef = { 2, 9, 8, 7, 6, 3, 4 };
        int suma = 0;
        for (int i = 0; i < 7; i++)
            suma += Character.getNumericValue(cedula.charAt(i)) * coef[i];
        int resto = suma % 10;
        int digitoVerificador = (resto == 0) ? 0 : 10 - resto;
        int ultimo = Character.getNumericValue(cedula.charAt(7));
        return digitoVerificador == ultimo;
    }

    public boolean validarPasaporte(String pasaporte) {
        return pasaporte != null && pasaporte.matches("^[A-Z]{1}\\d{7}$");
    }

    public DtUsuario iniciarSesionUsuario(String identificador, String contra) {
        return usuarioDAO.iniciarSesion(identificador, contra);

    }

    public DtUsuario cerrarSesionUsuario(String nick) {
        return usuarioDAO.cerrarSesion(nick);
    }

    public String subirImagen(Part filePart, String nombreArchivo) {
        try {
            String supabaseUrl = "https://myzsshgrfrupiwxbsuhr.supabase.co";
            String serviceKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im15enNzaGdyZnJ1cGl3eGJzdWhyIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc2MDI4NDAyOCwiZXhwIjoyMDc1ODYwMDI4fQ.9I4KwOlhf0y3kmSxWAMWKucxzvBftBgpnOquhB1BALg";
            String bucket = "Imagenes";

            String fileName = filePart.getSubmittedFileName();
            String path = "uploads/" + nombreArchivo + "_" + fileName;
            String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucket + "/" + path + "?upsert=true";

            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + serviceKey);
            conn.setRequestProperty("apikey", serviceKey);
            conn.setRequestProperty("Content-Type", filePart.getContentType());

            try (InputStream input = filePart.getInputStream();
                    OutputStream output = conn.getOutputStream()) {
                input.transferTo(output);
            }

            int status = conn.getResponseCode();
            conn.disconnect();

            if (status >= 200 && status < 300) {
                String publicUrl = supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + path;
                System.out.println("URL generada: " + publicUrl);
                conn.disconnect();
                return publicUrl;
            } else {
                System.err.println("Error al subir imagen: código " + status);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Aerolinea> listarAerolineas() {
        return aerolineaDAO.listarAerolineas();
    }

    public List<DtReserva> listarDtReservasDeVuelo(String nombreVuelo) {
        return reservaDAO.listarDtReservasDeVuelo(nombreVuelo);
    }

    public List<String> obtenerReservasPorClienteVuelo(String vuelo, String cliente) {
        return reservaDAO.obtenerReservaVueloPorCliente(vuelo, cliente);
    }

    public List<String> listarPasajesReservasDeVuelo(String nombreReserva) {
        return reservaDAO.listarPasajesDeReservas(nombreReserva);
    }

    public DtReserva obtenerDtReservaPorClienteVuelo(String vuelo, String cliente) {
        return reservaDAO.obtenerDtReservaPorCliVue(vuelo, cliente);
    }

    public List<String> obtenerListaNombresRutasPorPaquete(String nombreReserva) {
        return paqueteDAO.buscarNombresRutasPorNombrePaquete(nombreReserva);
    }

    public List<DtReserva> listarPaquetesCompradosPorCliente(String nickCliente) {
        return reservaDAO.listarCompraPaqueteCliente(nickCliente);
    }

    public void finalizarRutaDeVuelo(String nombreRuta) {
        rutaDAO.finalizarRuta(nombreRuta);
    }

    public void guardarSocial(String idSeguidor, String idSeguido) {
        socialDAO.seguirUsuario(idSeguidor, idSeguido);
    }

    public void eliminarSocial(String idSeguidor, String idSeguido) {
        socialDAO.dejarSeguirUsuario(idSeguidor, idSeguido);
    }

    @Override
    public void incrementarVisitaRuta(String nombreRuta) {
        rutaDAO.incrementarVisitaRuta(nombreRuta);
    }

    @Override
    public List<DtRutaVuelo> listarRutasMasVisitadas() {
        return rutaDAO.listarRutasMasVisitadas();
    }

    // ===================== SEGUIDORES =====================
    public boolean yaSigue(String idSeguidor, String idSeguido) {
        return socialDAO.existeRelacion(idSeguidor, idSeguido);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioDAO.existeUsuarioConEmail(email);
    }

    @Override
    public boolean existeNickname(String nickname) {
        return usuarioDAO.dataUsuarioPorNick(nickname) != null;
    }

    @Override
    public List<DtRutaVuelo> buscarRutas(String query) {
        return rutaDAO.buscarRutas(query);
    }

    @Override
    public List<DtPaqueteVuelo> buscarPaquetes(String query) {
        return paqueteDAO.buscarPaquetes(query);
    }

    
    @Override
    public List<Long> listarReservasPendientesCheckin(String nicknameCliente) {
        return reservaDAO.listarReservasPendientesCheckin(nicknameCliente);
    }

    @Override
    public void realizarCheckin(Long idReserva) {
        reservaDAO.realizarCheckin(idReserva);
    }

    @Override
    public List<Long> listarReservasConCheckin(String nicknameCliente) {
        return reservaDAO.listarReservasConCheckin(nicknameCliente);
    }
    @Override
    public DtReserva obtenerReservaCheckin(Long idReserva) {
        return reservaDAO.obtenerReservaCheckin(idReserva);
    }

    @Override
    public DtCheckin obtenerCheckinPorReserva(Long idReserva) {
        return reservaDAO.obtenerCheckinPorReserva(idReserva);
    }
}
