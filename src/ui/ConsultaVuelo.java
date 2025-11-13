package ui;


import data_types.DtDatosVueloR;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.util.List;


public class ConsultaVuelo{
    private JComboBox comboBoxAerolinea;
    private JComboBox comboBoxRuta;
    private JComboBox comboBoxVuelos;
    private JList list1;
    private JPanel panelVuelo;
    private JLabel destino;
    private JLabel fecha;
    private JLabel duracion;
    private JLabel asientosEj;
    private JLabel asientosTu;
    private JLabel costoEj;
    private JLabel costoTu;
    private JLabel costoEqEx;
    private JLabel reservas;
    private JPanel mainPanel;
    public JLabel getSupPanel() {return reservas; }
    private ISistema sistema = Sistema.getInstance();
    public ConsultaVuelo() {
        cargarDatos();
        // Inicialmente ocultamos el panel de vuelo
        panelVuelo.setVisible(false);
        // Mostrar panel de vuelo solo cuando se selecciona un vuelo
        comboBoxVuelos.addActionListener(e -> panelVuelo.setVisible(comboBoxVuelos.getSelectedItem() != null));


    }


    private void cargarDatos() {


        List<String> aerolineas = sistema.listarNicknamesAerolineas();
        comboBoxAerolinea.removeAllItems();
        for (String a : aerolineas) {
            comboBoxAerolinea.addItem(a);
        }
        comboBoxAerolinea.addActionListener(e -> {
            String nickSeleccionado = (String) comboBoxAerolinea.getSelectedItem();
            if (nickSeleccionado != null) {
                List<String> rutas = sistema.listarRutasPorAerolinea(nickSeleccionado);
                comboBoxRuta.removeAllItems();
                for (String r : rutas) {
                    comboBoxRuta.addItem(r);
                }
            }
        });
        comboBoxRuta.addActionListener(e -> {
            String nombreRuta = (String) comboBoxRuta.getSelectedItem();
            if (nombreRuta != null) {
                List<DtDatosVueloR> vuelos = sistema.listarVuelosDeRuta(nombreRuta);
                comboBoxVuelos.removeAllItems();
                for (DtDatosVueloR v : vuelos) {
                    comboBoxVuelos.addItem(v.getNombre());
                }
                comboBoxVuelos.addActionListener(ev -> {
                    String nombreVuelo = (String) comboBoxVuelos.getSelectedItem();
                    if (nombreVuelo != null) {
                        for (DtDatosVueloR v : vuelos) {
                            if (v.getNombre().equals(nombreVuelo)) {
                                destino.setText("Origen: " + v.getOrigen() + " → Destino: " + v.getDestino());
                                fecha.setText("Fecha: " + v.getFecha());
                                duracion.setText("Duración: " + v.getDuracion());
                                asientosEj.setText("Asientos ejecutivos: " + v.getAsientosEjecutivo());
                                costoEj.setText("Costo ejecutivo: $" + v.getCostoEjecutivo());
                                asientosTu.setText("Asientos turista: " + v.getAsientosTurista());
                                costoTu.setText("Costo turista: $" + v.getCostoTurista());
                                costoEqEx.setText("Equipaje extra: $" + v.getCostoEquipajeExtra());
                                DefaultListModel<String> reservasModel = new DefaultListModel<>();
                                List<String> reservas = sistema.listarReservasDeVuelo(nombreVuelo);
                                for (String r : reservas) {
                                    reservasModel.addElement(r);
                                }
                                list1.setModel(reservasModel);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }
    public JPanel getPanel() {
        return mainPanel;
    }
}
