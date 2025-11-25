package ui;

import data_types.DtRutaVuelo;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.JDesktopPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.List;

public class ConsultaRDV extends JInternalFrame {
    private JPanel mainPanel;
    private JComboBox<String> comboAerolineas;
    private JComboBox<String> comboRutas;
    private JLabel lblNombreRuta;
    private JLabel lblOrigen;
    private JLabel lblDestino;
    private JLabel lblCostoTurista;
    private JLabel lblCostoEjecutivo;
    private JTextField textNombreRuta;
    private JTextField textOrigen;
    private JTextField textDestino;
    private JTextField textCostoTurista;
    private JTextField textCostoEjecutivo;
    private JLabel lblVuelos;
    private JList<String> listaVuelos;
    private JButton btnVerDetalleVuelo;
    private ISistema sistema = Sistema.getInstance();
    public JPanel getPanel() {
        return mainPanel;
    }

    public ConsultaRDV() {
        setTitle("Consulta de Ruta de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(900, 700);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // --- Aerolíneas
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Seleccione Aerolínea:"), gbc);
        comboAerolineas = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 0;
        mainPanel.add(comboAerolineas, gbc);

        // --- Rutas
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Seleccione Ruta:"), gbc);
        comboRutas = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(comboRutas, gbc);
        comboRutas.setVisible(false);

        // --- Labels y campos de detalle de ruta
        lblNombreRuta = new JLabel("Nombre de Ruta:");
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblNombreRuta, gbc);
        lblNombreRuta.setVisible(false);

        textNombreRuta = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(textNombreRuta, gbc);
        textNombreRuta.setVisible(false);

        lblOrigen = new JLabel("Origen:");
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(lblOrigen, gbc);
        lblOrigen.setVisible(false);

        textOrigen = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        mainPanel.add(textOrigen, gbc);
        textOrigen.setVisible(false);

        lblDestino = new JLabel("Destino:");
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(lblDestino, gbc);
        lblDestino.setVisible(false);

        textDestino = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        mainPanel.add(textDestino, gbc);
        textDestino.setVisible(false);

        lblCostoTurista = new JLabel("Costo Turista:");
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(lblCostoTurista, gbc);
        lblCostoTurista.setVisible(false);

        textCostoTurista = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 5;
        mainPanel.add(textCostoTurista, gbc);
        textCostoTurista.setVisible(false);

        lblCostoEjecutivo = new JLabel("Costo Ejecutivo:");
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(lblCostoEjecutivo, gbc);
        lblCostoEjecutivo.setVisible(false);

        textCostoEjecutivo = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 6;
        mainPanel.add(textCostoEjecutivo, gbc);
        textCostoEjecutivo.setVisible(false);

        // --- Lista de vuelos
        lblVuelos = new JLabel("Vuelos:");
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(lblVuelos, gbc);
        lblVuelos.setVisible(false);

        listaVuelos = new JList<>();
        JScrollPane scrollVuelos = new JScrollPane(listaVuelos);
        scrollVuelos.setPreferredSize(new Dimension(200, 100));
        gbc.gridx = 1; gbc.gridy = 7;
        mainPanel.add(scrollVuelos, gbc);
        scrollVuelos.setVisible(false);

        // --- Botón de detalle de vuelo
        btnVerDetalleVuelo = new JButton("Ver Detalle de Vuelo");
        gbc.gridx = 1; gbc.gridy = 8;
        mainPanel.add(btnVerDetalleVuelo, gbc);
        btnVerDetalleVuelo.setVisible(false);

        setContentPane(mainPanel);

        // --- Cargar aerolíneas
        cargarAerolineas();

        // --- Listeners
        comboAerolineas.addActionListener(e -> {
            String nombreAerolinea = (String) comboAerolineas.getSelectedItem();
            if (nombreAerolinea != null) {
                cargarRutas(nombreAerolinea);
                comboRutas.setVisible(true);
            } else {
                comboRutas.setVisible(false);
            }
        });

        comboRutas.addActionListener(e -> {
            String rutaSeleccionada = (String) comboRutas.getSelectedItem();
            if (rutaSeleccionada != null) {
                mostrarDetallesRuta(rutaSeleccionada);

                // Mostrar todo
                lblNombreRuta.setVisible(true);
                textNombreRuta.setVisible(true);
                lblOrigen.setVisible(true);
                textOrigen.setVisible(true);
                lblDestino.setVisible(true);
                textDestino.setVisible(true);
                lblCostoTurista.setVisible(true);
                textCostoTurista.setVisible(true);
                lblCostoEjecutivo.setVisible(true);
                textCostoEjecutivo.setVisible(true);
                lblVuelos.setVisible(true);
                scrollVuelos.setVisible(true);
                btnVerDetalleVuelo.setVisible(true);
            } else {
                // Ocultar todo si no hay ruta seleccionada
                lblNombreRuta.setVisible(false);
                textNombreRuta.setVisible(false);
                lblOrigen.setVisible(false);
                textOrigen.setVisible(false);
                lblDestino.setVisible(false);
                textDestino.setVisible(false);
                lblCostoTurista.setVisible(false);
                textCostoTurista.setVisible(false);
                lblCostoEjecutivo.setVisible(false);
                textCostoEjecutivo.setVisible(false);
                lblVuelos.setVisible(false);
                scrollVuelos.setVisible(false);
                btnVerDetalleVuelo.setVisible(false);
            }
        });

        btnVerDetalleVuelo.addActionListener(e -> {
            String vueloSeleccionado = listaVuelos.getSelectedValue();
            if (vueloSeleccionado != null) {
                ConsultaVuelo consultaVuelo = new ConsultaVuelo();
                JInternalFrame frame = new JInternalFrame("Consulta de Vuelo", true, true, true, true);
                frame.setContentPane(consultaVuelo.getPanel());
                frame.pack();
                ((JDesktopPane) SwingUtilities.getAncestorOfClass(JDesktopPane.class, mainPanel)).add(frame);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Seleccione un vuelo primero.");
            }
        });
    }

    private void cargarAerolineas() {
        List<String> aerolineas = Sistema.getInstance().listarNicknamesAerolineas();
        comboAerolineas.setModel(new DefaultComboBoxModel<>(aerolineas.toArray(new String[0])));
        comboAerolineas.setSelectedIndex(-1);
    }

    private void cargarRutas(String nombreAerolinea) {
        List<String> rutas = sistema.listarRutasPorAerolinea(nombreAerolinea);
        comboRutas.setModel(new DefaultComboBoxModel<>(rutas.toArray(new String[0])));
        comboRutas.setSelectedIndex(-1);
    }

    private void mostrarDetallesRuta(String nombreRuta) {
        DtRutaVuelo dtRutaVuelo = sistema.obtenerDtRutaPorNombre(nombreRuta);

        if (dtRutaVuelo != null) {
            textNombreRuta.setText(dtRutaVuelo.getNombre());
            textOrigen.setText(dtRutaVuelo.getOrigen());
            textDestino.setText(dtRutaVuelo.getDestino());
            textCostoTurista.setText(String.valueOf(dtRutaVuelo.getCostoBaseTurista()));
            textCostoEjecutivo.setText(String.valueOf(dtRutaVuelo.getCostoBaseEjecutivo()));

            List<String> vuelos = sistema.listarNombresVuelosPorRuta(nombreRuta);
            listaVuelos.setListData(vuelos.toArray(new String[0]));
        }
    }
}
