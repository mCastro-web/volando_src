package ui;

import data_types.EstadoRuta;
import sistema.ISistema;
import sistema.Sistema;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AceptarRV extends JInternalFrame {

    private JButton aceptarButton;
    private JButton rechazarButton;
    private JComboBox comboAerolinea;
    private JComboBox comboRutas;
    private JPanel mainPanel;

    private ISistema sistema = Sistema.getInstance();

    public AceptarRV() {
        setContentPane(mainPanel);
        setTitle("Aceptar o Rechazar Ruta de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(300, 200);
        configurarEventos();
        cargarDatos();
    }

    private void configurarEventos() {
        // Botón Confirmar
        aceptarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                confirmarRV();
                limpiarFormulario();
            }
        });
        // Botón Cancelar
        rechazarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                rechazarRV();
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

    private void confirmarRV(){
        String nombreRuta = (String) comboRutas.getSelectedItem();
        if (nombreRuta != null) {
            sistema.actualizarEstadoRuta(nombreRuta, EstadoRuta.CONFIRMADA);
            JOptionPane.showMessageDialog(this, "Ruta confirmada correctamente.");
        }
    }
    private void rechazarRV() {
        String nombreRuta = (String) comboRutas.getSelectedItem();
        if (nombreRuta != null) {
            sistema.actualizarEstadoRuta(nombreRuta, EstadoRuta.RECHAZADA);
            JOptionPane.showMessageDialog(this, "Ruta rechazada correctamente.");
        }
    }


    private void limpiarFormulario() {
        comboAerolinea.setSelectedIndex(-1);
        comboRutas.setSelectedIndex(-1);
    }

}
