package ui;

import ui.utils.SwingUtils;
import sistema.Sistema;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CrearPaqueteDeRutasDeVuelo {
    private JPanel crearPaqueteRDV;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtDiasValidez;
    private JTextField txtDescuento;
    private JComboBox cbDia;
    private JComboBox cbMes;
    private JComboBox cbAnio;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public JPanel getPanel() {
        return crearPaqueteRDV;
    }

    public CrearPaqueteDeRutasDeVuelo() {
        // Precargar combos de fecha
        SwingUtils.precargarFechas(cbDia, cbMes, cbAnio, 2025, 2035);

        // Acción confirmar
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registrarPaqueteRDV();
            }
        });

        // Acción cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                limpiarFormulario();
            }
        });
    }

    private void registrarPaqueteRDV() {
        try {
            String nombrePaquete = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String diasValidezStr = txtDiasValidez.getText().trim();
            String descuentoStr = txtDescuento.getText().trim();

            // Validaciones básicas
            if (nombrePaquete.isEmpty() || descripcion.isEmpty() || diasValidezStr.isEmpty() || descuentoStr.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            int diasValidez = Integer.parseInt(diasValidezStr);
            if (diasValidez <= 0) throw new IllegalArgumentException("Los días de validez deben ser mayores a 0.");

            float descuento = Float.parseFloat(descuentoStr);
            if (descuento < 0 || descuento > 100)
                throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");

            int dia = Integer.parseInt(cbDia.getSelectedItem().toString());
            int mes = Integer.parseInt(cbMes.getSelectedItem().toString());
            int anio = Integer.parseInt(cbAnio.getSelectedItem().toString());
            LocalDate fechaAlta = LocalDate.of(anio, mes, dia);

            // Llamada al sistema
            Sistema.getInstance().altaPaqueteRV(
                    nombrePaquete, descripcion, diasValidez, descuento, fechaAlta
            );

            JOptionPane.showMessageDialog(crearPaqueteRDV,
                    "✅ El paquete fue registrado exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(crearPaqueteRDV,
                    "Los campos de días de validez y descuento deben ser numéricos.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(crearPaqueteRDV,
                    ex.getMessage(),
                    "Error de validación", JOptionPane.WARNING_MESSAGE);
        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(crearPaqueteRDV,
                    "Error inesperado al registrar el paquete: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtDiasValidez.setText("");
        txtDescuento.setText("");
        cbDia.setSelectedIndex(0);
        cbMes.setSelectedIndex(0);
        cbAnio.setSelectedIndex(0);
    }
}
