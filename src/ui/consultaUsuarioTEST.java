/*
package ui;

import DataTypes.DtAerolinea;
import DataTypes.DtCliente;
import DataTypes.DtRutaVuelo;
import DataTypes.DtUsuario;
import model.RutaVuelo.ManejadorRutaVuelo;
import sistema.Sistema;
import main.java.ui.utils.SwingUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class consultaUsuarioTEST extends JInternalFrame {

    private JComboBox<String> comboUsuarios;
    private JLabel lblDatosCliente;

    // Panel Cliente
    private JPanel panelCliente;
    private JComboBox<String> comboReservas;
    private JLabel lblDatosReserva;
    private JComboBox<String> comboVuelosCliente;
    private JLabel lblDatosVueloCliente;
    private JComboBox<String> comboPaquetes;
    private JLabel lblDatosPaquete;

    // Panel Aerolínea
    private JPanel panelAerolinea;
    private JComboBox<String> comboRutas;
    private JLabel lblDatosRuta;
    private JComboBox<String> comboVuelosAerolinea;
    private JLabel lblDatosVueloAerolinea;

    // CardLayout
    private JPanel panelCentral;
    private CardLayout cardLayout;

    public consultaUsuarioTEST() {
        super("Consulta de Usuario", true, true, true, true);
        setSize(700, 500);

        // Panel superior con combo de usuarios
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelSuperior.add(new JLabel("Seleccionar Usuario:"));
        comboUsuarios = new JComboBox<>();
        panelSuperior.add(comboUsuarios);

        // -------- Panel Cliente --------
        panelCliente = new JPanel(new GridLayout(0, 2, 10, 10));

        panelCliente.add(new JLabel("Datos Cliente:"));
        lblDatosCliente = new JLabel("-");
        panelCliente.add(lblDatosCliente);

        panelCliente.add(new JLabel("Listar Reserva Vuelo:"));
        comboReservas = new JComboBox<>();
        panelCliente.add(comboReservas);

        panelCliente.add(new JLabel("Datos Reserva:"));
        lblDatosReserva = new JLabel("-");
        panelCliente.add(lblDatosReserva);

        panelCliente.add(new JLabel("Listar Vuelo:"));
        comboVuelosCliente = new JComboBox<>();
        panelCliente.add(comboVuelosCliente);

        panelCliente.add(new JLabel("Datos Vuelo:"));
        lblDatosVueloCliente = new JLabel("-");
        panelCliente.add(lblDatosVueloCliente);

        panelCliente.add(new JLabel("Listar Paquete Vuelo:"));
        comboPaquetes = new JComboBox<>();
        panelCliente.add(comboPaquetes);

        panelCliente.add(new JLabel("Datos Paquete:"));
        lblDatosPaquete = new JLabel("-");
        panelCliente.add(lblDatosPaquete);

        // -------- Panel Aerolínea --------
        panelAerolinea = new JPanel(new GridLayout(0, 2, 10, 10));

        panelAerolinea.add(new JLabel("Listar Ruta Vuelo:"));
        comboRutas = new JComboBox<>();
        panelAerolinea.add(comboRutas);

        panelAerolinea.add(new JLabel("Datos Ruta:"));
        lblDatosRuta = new JLabel("-");
        panelAerolinea.add(lblDatosRuta);

        panelAerolinea.add(new JLabel("Listar Vuelo (desde Ruta):"));
        comboVuelosAerolinea = new JComboBox<>();
        panelAerolinea.add(comboVuelosAerolinea);

        panelAerolinea.add(new JLabel("Datos Vuelo:"));
        lblDatosVueloAerolinea = new JLabel("-");
        panelAerolinea.add(lblDatosVueloAerolinea);

        // -------- CardLayout --------
        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.add(panelCliente, "Cliente");
        panelCentral.add(panelAerolinea, "Aerolínea");

        // -------- Layout Principal --------
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(panelCentral), BorderLayout.CENTER);

        // -------- Cargar usuarios desde DB --------
        cargarUsuarios();

        // -------- Evento selección usuario --------
        comboUsuarios.addActionListener(e -> {
            String seleccionado = (String) comboUsuarios.getSelectedItem();
            if (seleccionado != null && !seleccionado.startsWith("—")) {
                cargarDatosUsuario(seleccionado);
            }
        });
    }

    private void cargarUsuarios() {
        try {
            List<String> nicks = Sistema.getInstance().listarUsuariosPorNick();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("— Seleccione —");
            for (String s : nicks) model.addElement(s);
            comboUsuarios.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la lista de usuarios.\nDetalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosUsuario(String nick) {
        try {
            DtUsuario usuario = Sistema.getInstance().obtenerUsuarioPorNick(nick);

            if (usuario instanceof DtCliente c) {
                mostrarPanelCliente(c);
                cardLayout.show(panelCentral, "Cliente");
            } else if (usuario instanceof DtAerolinea a) {
                mostrarPanelAerolinea(a);
                cardLayout.show(panelCentral, "Aerolínea");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Tipo de usuario desconocido para '" + nick + "'.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos de usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPanelCliente(DtCliente c) {
        lblDatosCliente.setText("<html>"
                + "Nombre: " + c.getNombre() + " " + c.getApellido() + "<br>"
                + "Email: " + c.getEmail() + "<br>"
                + "Nacionalidad: " + c.getNacionalidad()
                + "</html>");


        comboReservas.removeAllItems();
        comboPaquetes.removeAllItems();
        comboVuelosCliente.removeAllItems();

        List<String> reservas = Sistema.getInstance().listarReservasDeCliente(c.getNickname());
        reservas.forEach(comboReservas::addItem);

        List<String> paquetes = Sistema.getInstance().listarPaquetesDeCliente(c.getNickname());
        paquetes.forEach(comboPaquetes::addItem);

        // eventos de combos -> simular cargar detalles
        comboReservas.addActionListener(e -> {
            String r = (String) comboReservas.getSelectedItem();
            if (r != null) lblDatosReserva.setText("Reserva: " + r);
        });

        comboPaquetes.addActionListener(e -> {
            String p = (String) comboPaquetes.getSelectedItem();
            if (p != null) lblDatosPaquete.setText("Paquete: " + p);
        });

        comboVuelosCliente.addActionListener(e -> {
            String v = (String) comboVuelosCliente.getSelectedItem();
            if (v != null) lblDatosVueloCliente.setText("Vuelo: " + v);
        });
    }

    private void mostrarPanelAerolinea(DtAerolinea a) {
        lblDatosRuta.setText("Aerolinea: " + a.getNombre()
                + " | Email: " + a.getEmail()
                + " | Sitio: " + a.getSitioWeb());

        comboRutas.removeAllItems();
        comboVuelosAerolinea.removeAllItems();

        List<String> rutas = Sistema.getInstance().listarRutasVueloPorAerolinea(a.getNickname());
        rutas.forEach(comboRutas::addItem);

        comboRutas.addActionListener(e -> {
            String r = (String) comboRutas.getSelectedItem();
            if (r != null) lblDatosRuta.setText("Ruta: " + r);
        });

        comboVuelosAerolinea.addActionListener(e -> {
            String v = (String) comboVuelosAerolinea.getSelectedItem();
            if (v != null) lblDatosVueloAerolinea.setText("Vuelo: " + v);
        });
    }
}
*/