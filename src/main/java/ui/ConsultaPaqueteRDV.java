package ui;

import data_types.DtItemPaquete;
import model.PaqueteVuelo;
import data_types.DtRutaVuelo;
import sistema.ISistema;
import sistema.Sistema;
import ui.utils.SwingUtils;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import java.util.List;

public class ConsultaPaqueteRDV {
    private JComboBox<String> cbPaquetes;
    private JButton verDetalleRutaDeButton;
    private JButton salirButton;
    private JPanel panelRutas;
    private JPanel panelPrincipal;
    private JPanel infoPaquete;
    private JLabel nombrePaquete;
    private JLabel descPaquete;
    private JLabel costoPaquete;
    private JLabel descuentoPaquete;
    private JLabel validezPaquete;
    private JScrollPane rutas;
    private JTable tablaRutas;
    public JPanel getSupPanel() {return panelRutas; }
    public JPanel datosPanel() {return infoPaquete; }
    private final ISistema sistema;

    public ConsultaPaqueteRDV() {
        sistema = Sistema.getInstance();

        try {
            inicializarComponentes();

            // Cargar paquetes en el combo
            List<String> nombresPaquetes = sistema.listarNombresPaquetes();
            for (String nombre : nombresPaquetes) {
                cbPaquetes.addItem(nombre);
            }

            // Ocultar todos los campos al inicio
            ocultarCamposPaquete();

            // Acción al seleccionar paquete
            cbPaquetes.addActionListener(e -> {
                String nombre = (String) cbPaquetes.getSelectedItem();
                if (nombre != null && !nombre.isEmpty()) {
                    mostrarPaquete(nombre);
                    mostrarCamposPaquete();
                } else {
                    ocultarCamposPaquete();
                }
            });

            // Acción botón "Ver detalle de ruta"
            verDetalleRutaDeButton.addActionListener(e -> {
                int filaSeleccionada = tablaRutas.getSelectedRow();
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(
                            panelPrincipal,
                            "Debe seleccionar una ruta en la tabla",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                String nombreRuta = (String) tablaRutas.getValueAt(filaSeleccionada, 2); // Columna "Ruta"
                if (nombreRuta != null && !nombreRuta.isEmpty()) {
                    DtRutaVuelo ruta = sistema.obtenerDtRutaPorNombre(nombreRuta);
                    JTextArea infoRuta = new JTextArea(20, 50);
                    infoRuta.setEditable(false);
                    SwingUtils.mostrarInfoRuta(ruta, panelRutas
                    );
                }
            });

            // Acción botón "Salir"
            salirButton.addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();
            });

        } catch (jakarta.persistence.PersistenceException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Ocurrió un error al inicializar la consulta de paquetes:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void inicializarComponentes() {
        // Inicializar tabla
        tablaRutas = new JTable(new DefaultTableModel(
                new Object[]{"Cantidad", "Tipo Asiento", "Ruta"}, 0
        ));
        tablaRutas.setFillsViewportHeight(true);

        // Asociar scroll
        rutas.setViewportView(tablaRutas);
    }

    private void ocultarCamposPaquete() {
        nombrePaquete.setVisible(false);
        descPaquete.setVisible(false);
        costoPaquete.setVisible(false);
        descuentoPaquete.setVisible(false);
        validezPaquete.setVisible(false);
        rutas.setVisible(false);
        tablaRutas.setVisible(false);
        verDetalleRutaDeButton.setVisible(false);
    }

    private void mostrarCamposPaquete() {
        nombrePaquete.setVisible(true);
        descPaquete.setVisible(true);
        costoPaquete.setVisible(true);
        descuentoPaquete.setVisible(true);
        validezPaquete.setVisible(true);
        rutas.setVisible(true);
        tablaRutas.setVisible(true);
        verDetalleRutaDeButton.setVisible(true);
    }

    private void mostrarPaquete(String nombrePaqueteStr) {
        PaqueteVuelo paquete = sistema.buscarPaqueteVuelo(nombrePaqueteStr);
        if (paquete == null) return;

        // Mostrar información del paquete
        nombrePaquete.setText("Nombre: " + paquete.getNombre());
        descPaquete.setText("Descripción: " + paquete.getDescripcion());
        costoPaquete.setText("Costo: " + paquete.getCosto());
        descuentoPaquete.setText("Descuento: " + paquete.getDescuento());
        validezPaquete.setText("Días de validez: " + paquete.getDiasValidez());

        // Traer items del paquete
        List<DtItemPaquete> items = sistema.listarItemsDePaquete(paquete.getIdentificador());

        // Limpiar tabla
        DefaultTableModel modelo = (DefaultTableModel) tablaRutas.getModel();
        modelo.setRowCount(0);

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Este paquete no tiene rutas cargadas.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Llenar tabla
            for (DtItemPaquete item : items) {
                modelo.addRow(new Object[]{
                        item.getCant(),
                        item.getTipoAsiento(),
                        item.getNombreRuta()
                });
            }
            tablaRutas.revalidate();
            tablaRutas.repaint();
        }
    }

    public JPanel getPanel() {
        return panelPrincipal;
    }
}
