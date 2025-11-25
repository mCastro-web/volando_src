package ui;

import data_types.TipoAsiento;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class AgregarRDVaPaquete {
    private JPanel agRDVaPaquete;
    private JComboBox<String> cbPaquetes;
    private JComboBox<String> cbAerolinea;
    private JComboBox<String> cbRutas;
    private JTextField cantidad;
    private JComboBox<String> cbTipoAsiento;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    private final ISistema sistema;

    public AgregarRDVaPaquete() {
        sistema = Sistema.getInstance(); // implementación del sistema (singleton)

        // Cargar combos desde el sistema
        cbPaquetes.setModel(new DefaultComboBoxModel<>(
                sistema.listarNombresPaquetes().toArray(new String[0])
        ));
        cbPaquetes.setSelectedIndex(-1);

        cbAerolinea.setModel(new DefaultComboBoxModel<>(
                sistema.listarNicknamesAerolineas().toArray(new String[0])
        ));
        cbAerolinea.setSelectedIndex(-1);

        cbAerolinea.addActionListener(e -> {
            cbRutas.removeAllItems();
            String nickSeleccionado = (String) cbAerolinea.getSelectedItem();
            if (nickSeleccionado != null) {
                List<String> rutas = sistema.listarRutasPorAerolinea(nickSeleccionado);
                for (String nombre : rutas) {
                    cbRutas.addItem(nombre);
                }
            }
        });


        cbTipoAsiento.setModel(new DefaultComboBoxModel<>(
                new String[]{"TURISTA", "EJECUTIVO"}
        ));
        cbTipoAsiento.setSelectedIndex(-1);

        // Eventos
        btnConfirmar.addActionListener(this::agregarRutaPaqueteDesdeUI);
        btnCancelar.addActionListener(e -> limpiarFormulario());
    }

    private void limpiarFormulario() {
        cantidad.setText("");
        cbPaquetes.setSelectedIndex(-1);
        cbAerolinea.setSelectedIndex(-1);
        cbRutas.setSelectedIndex(-1);
        cbTipoAsiento.setSelectedIndex(-1);
    }

    public JPanel getPanel() {
        return agRDVaPaquete;
    }

    private void agregarRutaPaqueteDesdeUI(ActionEvent actionEvent) {
        try {
            String nombrePaquete = (String) cbPaquetes.getSelectedItem();
            String nickAerolinea = (String) cbAerolinea.getSelectedItem();
            String nombreRuta = (String) cbRutas.getSelectedItem();
            String tipoAsientoStr = (String) cbTipoAsiento.getSelectedItem();
            int cantidadIngresada = Integer.parseInt(cantidad.getText());

            if (nombrePaquete == null || nickAerolinea == null || nombreRuta == null || tipoAsientoStr == null)
                throw new IllegalArgumentException("Debe completar todos los campos.");

            TipoAsiento tipoAsiento = TipoAsiento.valueOf(tipoAsientoStr.toUpperCase());

            sistema.agregarRutaPaquete(nombrePaquete, nickAerolinea, nombreRuta, cantidadIngresada, tipoAsiento);

            JOptionPane.showMessageDialog(agRDVaPaquete,
                    "Ruta agregada al paquete con éxito",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(agRDVaPaquete,
                    "La cantidad debe ser un número válido",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(agRDVaPaquete,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
