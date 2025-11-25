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
import java.util.List;
import ui.utils.SwingUtils;
import sistema.ISistema;
import sistema.Sistema;


public class AltaRutaVuelo extends JInternalFrame {
    private JPanel mainPanel;
    private JTextField textFieldNom;
    private JTextField textFieldDesc;
    private JTextField textFieldCostoT;
    private JTextField textFieldCostoE;
    private JTextField textFieldCostoU;
    private JComboBox comboBoxCiuO;
    private JComboBox comboBoxCiuD;
    private JComboBox comboBoxAnio;
    private JComboBox comboBoxMes;
    private JComboBox comboBoxDia;
    private JComboBox comboBoxCat;
    private JComboBox comboAerolinea;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel supPanel;
    private JPanel datosPanel;

    public JPanel getSupPanel() {return supPanel; }
    public JPanel datosPanel() {return datosPanel; }
    private ISistema sistema = Sistema.getInstance();

    public AltaRutaVuelo() {
        setContentPane(mainPanel);
        setTitle("Alta de Ruta de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(600, 400);
        configurarEventos();
        SwingUtils.precargarFechas(comboBoxDia, comboBoxMes, comboBoxAnio, 2025, 2035);
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
        List<String> nombres = sistema.listarNombresCategorias();
        comboBoxCat.removeAllItems();
        for (String nombre : nombres) {
            comboBoxCat.addItem(nombre);
        }
        List<String> ciudades = sistema.listarNombresCiudades();
        for (String c : ciudades){
            comboBoxCiuO.addItem(c);
            comboBoxCiuD.addItem(c);
        }
        List<String> aerolineas = sistema.listarNicknamesAerolineas();
        comboAerolinea.removeAllItems();
        for (String a : aerolineas) {
            comboAerolinea.addItem(a);
        }


    }
    private void procesarAlta() {
        try {
            String nombre = textFieldNom.getText().trim();
            String descripcion = textFieldDesc.getText().trim();
            float costoT = Float.parseFloat(textFieldCostoT.getText().trim());
            float costoE = Float.parseFloat(textFieldCostoE.getText().trim());
            float costoU = Float.parseFloat(textFieldCostoU.getText().trim()); // si lo usas
            String origenNombre = (String) comboBoxCiuO.getSelectedItem();
            String destinoNombre = (String) comboBoxCiuD.getSelectedItem();
            String categoriaNombre = (String) comboBoxCat.getSelectedItem();
            String aerolineaNick = ((String) comboAerolinea.getSelectedItem()).trim();


            // Convertir fecha
            int anio = (int) comboBoxAnio.getSelectedItem();
            int mes = (int) comboBoxMes.getSelectedItem();
            int dia = (int) comboBoxDia.getSelectedItem();
            LocalDate fechaAlta = LocalDate.of(anio, mes, dia);
            String Url ="imagen123";
            String urlVideo = "video123";
            String desCorta = "muy linda";


            sistema.altaRutaVuelo(nombre, descripcion, fechaAlta, costoT, costoE, costoU,
                    aerolineaNick, origenNombre, destinoNombre, categoriaNombre, Url, urlVideo, desCorta);


            JOptionPane.showMessageDialog(this, "Ruta de vuelo registrada correctamente.");


        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }




    }


    private void limpiarFormulario() {
        textFieldNom.setText("");
        textFieldDesc.setText("");
        textFieldCostoT.setText("");
        textFieldCostoE.setText("");
        textFieldCostoU.setText("");
        comboAerolinea.setSelectedIndex(-1);
        comboBoxAnio.setSelectedIndex(-1);
        comboBoxMes.setSelectedIndex(-1);
        comboBoxDia.setSelectedIndex(-1);
        comboBoxCat.setSelectedIndex(-1);
    }


}
