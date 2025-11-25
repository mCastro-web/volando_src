package ui;


import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import ui.utils.SwingUtils;
import sistema.ISistema;
import sistema.Sistema;


public class AltaVuelo extends JInternalFrame {


    private JPanel mainPanel;


    private JPanel supPanel;
    private JComboBox comboAerolinea;


    private JComboBox comboRutas;
    private JPanel rutaPanel;


    private JPanel datosPanel;
    private JTextField textFieldNom;
    private JTextField textFieldDur;
    private JComboBox comboBoxAnio1;
    private JComboBox comboBoxMes1;
    private JComboBox comboBoxDia1;
    private JComboBox comboBoxAnio2;
    private JComboBox comboBoxMes2;
    private JComboBox comboBoxDia2;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JTextField textFieldCantAT;
    private JTextField textFieldCantAE;
    private ISistema sistema = Sistema.getInstance();
    public JPanel getSupPanel() {return supPanel; }
    public JPanel datosPanel() {return datosPanel; }
    public JPanel getRutaPanel() {return rutaPanel; }

    public AltaVuelo() {
        setContentPane(mainPanel);
        setTitle("Alta de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(500, 350);
        configurarEventos();
        SwingUtils.precargarFechas(comboBoxDia1, comboBoxMes1, comboBoxAnio1, 2025, 2035);
        SwingUtils.precargarFechas(comboBoxDia2, comboBoxMes2, comboBoxAnio2, 2025, 2035);
        cargarDatos();
    }


    private void configurarEventos() {
        // Botón Confirmar
        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                procesarAlta();
                limpiarFormulario();
            }
        });
        // Botón Cancelar
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }


    private void cargarDatos() {


        List<String> aerolineas = sistema.listarNicknamesAerolineas();
        comboAerolinea.removeAllItems();
        for (String a : aerolineas) {
            comboAerolinea.addItem(a);
        }
        comboAerolinea.addActionListener(e -> {
            String nickSeleccionado = (String) comboAerolinea.getSelectedItem();
            if (nickSeleccionado != null) {
                List<String> rutas = sistema.listarRutasPorAerolinea(nickSeleccionado);
                comboRutas.removeAllItems();
                for (String r : rutas) {
                    comboRutas.addItem(r);
                }
            }
        });
    }
    private void procesarAlta() {
        try {
            String rutaNombre = ((String) comboRutas.getSelectedItem()).trim();


            String nombre = textFieldNom.getText().trim();
            if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre del vuelo es obligatorio.");


            int anio = (int) comboBoxAnio1.getSelectedItem();
            int mes = (int) comboBoxMes1.getSelectedItem();
            int dia = (int) comboBoxDia1.getSelectedItem();
            LocalDate fecha = LocalDate.of(anio, mes, dia);


            String duracionStr = textFieldDur.getText().trim();
            if (!duracionStr.matches("\\d{1,2}:\\d{2}"))
                throw new IllegalArgumentException("Duración debe tener formato HH:MM.");
            String[] partes = duracionStr.split(":");
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            LocalTime duracion = java.time.LocalTime.of(horas, minutos);


            int asientosTurista = Integer.parseInt(textFieldCantAT.getText().trim());
            int asientosEjecutivo = Integer.parseInt(textFieldCantAE.getText().trim());
            if (asientosTurista < 0 || asientosEjecutivo < 0)
                throw new IllegalArgumentException("Los asientos no pueden ser negativos.");


            int anioAlta = (int) comboBoxAnio2.getSelectedItem();
            int mesAlta = (int) comboBoxMes2.getSelectedItem();
            int diaAlta = (int) comboBoxDia2.getSelectedItem();
            LocalDate fechaAlta = LocalDate.of(anioAlta, mesAlta, diaAlta);
            String Url = "imagen123";


            sistema.altaVuelo(nombre, fecha, duracion, asientosTurista,
                    asientosEjecutivo, fechaAlta, rutaNombre, Url);


            JOptionPane.showMessageDialog(this, "Vuelo registrado correctamente.");


        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void limpiarFormulario() {
        textFieldNom.setText("");
        textFieldDur.setText("");
        textFieldCantAT.setText("");
        textFieldCantAE.setText("");
        comboAerolinea.setSelectedIndex(-1);
        comboRutas.setSelectedIndex(-1);
        comboBoxAnio1.setSelectedIndex(-1);
        comboBoxMes1.setSelectedIndex(-1);
        comboBoxDia1.setSelectedIndex(-1);
        comboBoxAnio2.setSelectedIndex(-1);
        comboBoxMes2.setSelectedIndex(-1);
        comboBoxDia2.setSelectedIndex(-1);
    }
}
