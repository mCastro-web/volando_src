package ui;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import java.time.LocalDate;
import sistema.Sistema;
import ui.utils.SwingUtils;

public class AltaCiudad extends JInternalFrame {
    private JPanel panelAltaCiudad;
    private JTextField nombre;
    private JTextField pais;
    private JTextField aeropuerto;
    private JTextField descripcion;
    private JTextField sitioweb;
    private JComboBox cbDia;
    private JComboBox cbMes;
    private JComboBox cbAnio;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public JPanel getPanel() {
        return panelAltaCiudad;
    }

    public AltaCiudad() {
        setContentPane(panelAltaCiudad);
        setTitle("Alta de Ciudad");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);

        // Carga de fechas en los combos
        SwingUtils.precargarFechas(cbDia, cbMes, cbAnio, 2025, 2035);

        // Acciones con lambdas
        btnConfirmar.addActionListener(e -> registrarCiudad());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarCiudad() {
        try {
            String nombreCiudad = nombre.getText().trim();
            String paisCiudad = pais.getText().trim();
            String nombreAeropuerto = aeropuerto.getText().trim();
            String descripcionAeropuerto = descripcion.getText().trim();
            String sitioWeb = sitioweb.getText().trim();

            int dia = Integer.parseInt(cbDia.getSelectedItem().toString());
            int mes = Integer.parseInt(cbMes.getSelectedItem().toString());
            int anio = Integer.parseInt(cbAnio.getSelectedItem().toString());
            LocalDate fechaAlta = LocalDate.of(anio, mes, dia);

            // Validaciones básicas
            if (nombreCiudad.isEmpty()) {
                mostrarError("El nombre de la ciudad es obligatorio.");
                return;
            }
            if (paisCiudad.isEmpty()) {
                mostrarError("El país es obligatorio.");
                return;
            }
            if (nombreAeropuerto.isEmpty()) {
                mostrarError("El aeropuerto es obligatorio.");
                return;
            }
            if (!sitioWeb.isEmpty() && !sitioWeb.startsWith("www")) {
                mostrarError("El sitio web debe comenzar con www");
                return;
            }

            // Llamada al sistema
            Sistema.getInstance().altaCiudad(
                    nombreCiudad,
                    paisCiudad,
                    nombreAeropuerto,
                    descripcionAeropuerto,
                    sitioWeb,
                    fechaAlta
            );

            JOptionPane.showMessageDialog(panelAltaCiudad,
                    "La ciudad ha sido registrada exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (IllegalArgumentException ex) {
            mostrarError("Error de validación: " + ex.getMessage());
        } catch (jakarta.persistence.PersistenceException ex) {
            mostrarError("Error al registrar la ciudad: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        nombre.setText("");
        pais.setText("");
        aeropuerto.setText("");
        descripcion.setText("");
        sitioweb.setText("");
        cbDia.setSelectedIndex(0);
        cbMes.setSelectedIndex(0);
        cbAnio.setSelectedIndex(0);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(panelAltaCiudad, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
