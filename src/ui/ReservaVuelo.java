package ui;
import data_types.DtDatosVueloR;
import data_types.TipoAsiento;
import ui.utils.SwingUtils;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReservaVuelo extends JInternalFrame {
    private JPanel panelRV;
    private JComboBox<String> comboBoxAerolinea;
    private JComboBox<String> comboBoxRuta;
    private JComboBox<String> selVuelo;
    private JComboBox<String> comboBoxCliente;
    private JPanel pnVuelo;
    private JPanel pnReservaVuelo;
    public JPanel getSupPanel() {return pnReservaVuelo; }
    private JTextField textField2;
    private JPanel pnPasajeros;
    private JComboBox<String> comboBoxTipoAsiento;
    private JTextField cantidadPasajes;
    private JComboBox<Integer> cbDia;
    private JComboBox<Integer> cbMes;
    private JComboBox<Integer> cbAnio;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel destino;
    private JLabel costoEqEx;
    private JLabel fecha;
    private JLabel duracion;
    private JLabel asientosEj;
    private JLabel costoEj;
    private JLabel asientosTu;
    private JLabel costoTu;

    private ISistema sistema = Sistema.getInstance();

    public ReservaVuelo() {
        // Precargar fechas
        SwingUtils.precargarFechas(cbDia, cbMes, cbAnio, 2025, 2035);
        cargarDatos();
        // Inicialmente ocultamos el panel de vuelo
        pnVuelo.setVisible(false);
        // Mostrar panel de vuelo solo cuando se selecciona un vuelo
        selVuelo.addActionListener(e -> pnVuelo.setVisible(selVuelo.getSelectedItem() != null));


        // Listener cantidad de pasajes
        cantidadPasajes.addActionListener(e -> {
            try {
                int cantidad = Integer.parseInt(cantidadPasajes.getText());
                if (cantidad > 0) {
                    generarCamposPasajeros(cantidad);
                } else {
                    JOptionPane.showMessageDialog(panelRV, "Ingrese un número mayor a 0");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelRV, "Ingrese un número válido");
            }
        });
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón Confirmar
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                procesarAlta();
                limpiarFormulario();
            }
        });
        // Botón Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
    private void generarCamposPasajeros(int cantidad) {
        pnPasajeros.removeAll();
        pnPasajeros.setLayout(new BoxLayout(pnPasajeros, BoxLayout.Y_AXIS));


        for (int i = 1; i <= cantidad; i++) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fila.add(new JLabel("Nombre " + i + ":"));
            fila.add(new JTextField(10));
            fila.add(new JLabel("Apellido " + i + ":"));
            fila.add(new JTextField(10));
            pnPasajeros.add(fila);
        }


        pnPasajeros.revalidate();
        pnPasajeros.repaint();
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
                List<DtDatosVueloR> vuelos = Sistema.getInstance().listarVuelosDeRuta(nombreRuta);
                selVuelo.removeAllItems();
                for (DtDatosVueloR v : vuelos) {
                    selVuelo.addItem(v.getNombre());
                }
                selVuelo.addActionListener(ev -> {
                    String nombreVuelo = (String) selVuelo.getSelectedItem();
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
                                break;
                            }
                        }
                    }
                });
            }
        });


        List<String> clientes = sistema.listarNicknames();
        comboBoxCliente.removeAllItems();
        for (String c : clientes) {
            comboBoxCliente.addItem(c);
        }
    }
    private void procesarAlta() {
        try {
            //Obtener cliente seleccionado
            String nickCliente = (String) comboBoxCliente.getSelectedItem();
            if (nickCliente == null) {
                JOptionPane.showMessageDialog(panelRV, "Seleccione un cliente");
                return;
            }


            //Obtener vuelo seleccionado
            String nombreVuelo = (String) selVuelo.getSelectedItem();
            if (nombreVuelo == null) {
                JOptionPane.showMessageDialog(panelRV, "Seleccione un vuelo");
                return;
            }


            //Tipo de asiento
            String tipoStr = (String) comboBoxTipoAsiento.getSelectedItem();
            if (tipoStr == null) {
                JOptionPane.showMessageDialog(panelRV, "Seleccione un tipo de asiento");
                return;
            }
            TipoAsiento tipoAsiento = TipoAsiento.valueOf(tipoStr.toUpperCase());


            //Cantidad de pasajes
            int cantPasajes;
            try {
                cantPasajes = Integer.parseInt(cantidadPasajes.getText());
                if (cantPasajes <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelRV, "Ingrese una cantidad válida de pasajes");
                return;
            }


            int cantEquipaje;
            try {
                cantEquipaje = Integer.parseInt(textField2.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad de equipaje inválida");
                return;
            }




            //Fecha de reserva
            int dia = (Integer) cbDia.getSelectedItem();
            int mes = (Integer) cbMes.getSelectedItem();
            int anio = (Integer) cbAnio.getSelectedItem();
            LocalDate fechaReserva = LocalDate.of(anio, mes, dia);


            //Nombres y apellidos de pasajeros
            List<String> nombres = new ArrayList<>();
            List<String> apellidos = new ArrayList<>();
            for (Component comp : pnPasajeros.getComponents()) {
                if (comp instanceof JPanel fila) {
                    Component[] hijos = fila.getComponents();
                    nombres.add(((JTextField) hijos[1]).getText());
                    apellidos.add(((JTextField) hijos[3]).getText());
                }
            }
            if (nombres.size() != cantPasajes || apellidos.size() != cantPasajes) {
                JOptionPane.showMessageDialog(panelRV, "Complete todos los nombres y apellidos de los pasajeros");
                return;
            }


            sistema.reservarVuelo(nickCliente, nombreVuelo, tipoAsiento, cantEquipaje, fechaReserva, cantPasajes, nombres, apellidos);




            JOptionPane.showMessageDialog(panelRV, "Reserva creada con éxito");
            limpiarFormulario();


        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(panelRV, "Error al crear reserva: " + ex.getMessage());
        }
    }


    private void limpiarFormulario() {
        cantidadPasajes.setText("");
        pnPasajeros.removeAll();
        pnPasajeros.revalidate();
        pnPasajeros.repaint();
        comboBoxAerolinea.setSelectedIndex(-1);
        comboBoxRuta.setSelectedIndex(-1);
        selVuelo.setSelectedIndex(-1);
        pnVuelo.setVisible(false);
        comboBoxCliente.setSelectedIndex(-1);
        comboBoxTipoAsiento.setSelectedIndex(-1);
    }


    public JPanel getPanel() {
        return panelRV;
    }
}
