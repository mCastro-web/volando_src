package ui;

import data_types.DtAerolinea;
import data_types.DtCliente;
import data_types.DtUsuario;
import sistema.Sistema;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.JDesktopPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaUsuario extends JInternalFrame {
    private JPanel mainPanel;
    private JComboBox<String> comboUsuario;
    private JPanel panelCliente;
    private JPanel panelAerolinea;

    // Panel cliente
    private JTextField textNickC;
    private JTextField textNomC;
    private JTextField textApeC;
    private JTextField textEmailC;
    private JTextField textFechaNacC;
    private JTextField textNroDocC;
    private JTextField textNacC;
    private JList<String> listaReservas;
    private JList<String> listaPaquetes;
    private JButton verDetallePaquete;
    private JButton verDetalleReserva;
    // Panel aerolínea
    private JTextField textNickA;
    private JTextField textNombreA;
    private JTextField textEmailA;
    private JTextField textSitioA;
    private JTextArea textDesc;
    private JTable listaRutas;
    private JButton verDetalleRutaDeButton;
    private JPanel supPanel;
    private JPanel panelPaquetes;
    private JLabel rutasDeVueloLabel;
    public JPanel getPanelPaquetes() {return panelPaquetes; }
    public JLabel datosPanel() {return rutasDeVueloLabel; }
    public JPanel getSupPanel() {return supPanel; }

    public ConsultaUsuario() {
        setContentPane(mainPanel);
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(false);

        setTitle("Consulta de Usuario");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(700, 500);

        inicializarComboUsuarios();
        configurarBotones();
    }

    private void inicializarComboUsuarios() {
        try {
            List<String> nicks = Sistema.getInstance().listarUsuariosPorNick();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("— Seleccione —");
            for (String s : nicks) model.addElement(s);
            comboUsuario.setModel(model);

            comboUsuario.addActionListener(e -> {
                String nick = (String) comboUsuario.getSelectedItem();
                if (nick == null || nick.isBlank() || nick.startsWith("—")) {
                    panelCliente.setVisible(false);
                    panelAerolinea.setVisible(false);
                    return;
                }
                cargarDatosUsuario(nick);
            });
        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la lista de usuarios.\nDetalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            comboUsuario.setModel(new DefaultComboBoxModel<>(new String[]{"(sin datos)"}));
        }
    }

    private void configurarBotones() {
        // Detalle de ruta de aerolínea
        verDetalleRutaDeButton.addActionListener(e -> {
            int fila = listaRutas.getSelectedRow();
            if (fila != -1) {
                String rutaSeleccionada = (String) listaRutas.getValueAt(fila, 0);
                if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
                    ConsultaRDV consultaRuta = new ConsultaRDV();
                    JInternalFrame frame = new JInternalFrame("Consulta de Ruta de Vuelo", true, true, true, true);
                    frame.setContentPane(consultaRuta.getPanel());
                    frame.pack();
                    JDesktopPane desktop = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, mainPanel);
                    if (desktop != null) {
                        desktop.add(frame);
                        frame.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Seleccione una ruta primero.");
            }
        });

        // Detalle de paquete del cliente
        verDetallePaquete.addActionListener(e -> {
            String paqueteSeleccionado = listaPaquetes.getSelectedValue();
            if (paqueteSeleccionado != null && !paqueteSeleccionado.isEmpty()) {
                ConsultaPaqueteRDV consultaPaquete = new ConsultaPaqueteRDV();
                JInternalFrame frame = new JInternalFrame("Consulta de Paquete", true, true, true, true);
                frame.setContentPane(consultaPaquete.getPanel());
                frame.pack();
                JDesktopPane desktop = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, mainPanel);
                if (desktop != null) {
                    desktop.add(frame);
                    frame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Seleccione un paquete primero.");
            }
        });

        // Detalle de vuelo de reservas del cliente
        verDetalleReserva.addActionListener(e -> {
            String vueloSeleccionado = listaReservas.getSelectedValue();
            if (vueloSeleccionado != null && !vueloSeleccionado.isEmpty()) {
                ConsultaVuelo consultaVuelo = new ConsultaVuelo();
                JInternalFrame frame = new JInternalFrame("Consulta de Vuelo", true, true, true, true);
                frame.setContentPane(consultaVuelo.getPanel());
                frame.pack();
                JDesktopPane desktop = (JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, mainPanel);
                if (desktop != null) {
                    desktop.add(frame);
                    frame.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Seleccione un vuelo primero.");
            }
        });
    }

    private void cargarDatosUsuario(String nick) {
        try {
            DtUsuario usuario = Sistema.getInstance().dataUsuarioPorNick(nick);

            if (usuario instanceof DtCliente dtCliente) {
                mostrarPanelCliente(dtCliente);
            } else if (usuario instanceof DtAerolinea dtAerolinea) {
                mostrarPanelAerolinea(dtAerolinea);
            } else {
                panelCliente.setVisible(false);
                panelAerolinea.setVisible(false);
                JOptionPane.showMessageDialog(this, "Tipo de usuario desconocido para '" + nick + "'.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (jakarta.persistence.PersistenceException ex) {
            panelCliente.setVisible(false);
            panelAerolinea.setVisible(false);
            JOptionPane.showMessageDialog(this, "No se pudo cargar el usuario '" + nick + "'.\nDetalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPanelCliente(DtCliente dtCliente) {
        panelCliente.setVisible(true);
        panelAerolinea.setVisible(false);

        textNickC.setText(dtCliente.getNickname());
        textNomC.setText(dtCliente.getNombre());
        textApeC.setText(dtCliente.getApellido());
        textEmailC.setText(dtCliente.getEmail());
        textNacC.setText(dtCliente.getNacionalidad());
        textNroDocC.setText(dtCliente.getNumeroDoc());

        if (dtCliente.getFechaNacimiento() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            textFechaNacC.setText(dtCliente.getFechaNacimiento().format(fmt));
        } else {
            textFechaNacC.setText("__/__/____");
        }

        List<String> reservas = Sistema.getInstance().listarReservasDeCliente(dtCliente.getNickname());
        listaReservas.setListData(reservas.toArray(new String[0]));

        List<String> paquetes = Sistema.getInstance().listarPaquetesDeCliente(dtCliente.getNickname());
        listaPaquetes.setListData(paquetes.toArray(new String[0]));
    }

    private void mostrarPanelAerolinea(DtAerolinea dtAerolinea) {
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(true);

        textNickA.setText(dtAerolinea.getNickname());
        textNombreA.setText(dtAerolinea.getNombre());
        textEmailA.setText(dtAerolinea.getEmail());
        textSitioA.setText(dtAerolinea.getSitioWeb());
        textDesc.setText(dtAerolinea.getDescripcion());

        List<String> rutas = Sistema.getInstance().listarRutasPorAerolinea(dtAerolinea.getNickname());
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre de la Ruta"}, 0);
        for (String r : rutas) model.addRow(new Object[]{r});
        listaRutas.setModel(model);
    }
}
