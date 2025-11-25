package ui.utils;

import data_types.DtRutaVuelo;
import data_types.DtVuelo;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;
import java.util.List;

public class SwingUtils {
    private static ISistema sistema = Sistema.getInstance();
    public static void precargarFechas(JComboBox<Integer> cbDia,
                                       JComboBox<Integer> cbMes,
                                       JComboBox<Integer> cbAnio,
                                       int anioInicio,
                                       int anioFin) {
        cbDia.removeAllItems();
        cbMes.removeAllItems();
        cbAnio.removeAllItems();

        for (int d = 1; d <= 31; d++) cbDia.addItem(d);
        for (int m = 1; m <= 12; m++) cbMes.addItem(m);
        for (int a = anioInicio; a <= anioFin; a++) cbAnio.addItem(a);
    }

    public static void mostrarInfoRuta(DtRutaVuelo ruta, JComponent parent) {
        if (ruta == null) {
            JOptionPane.showMessageDialog(parent, "No se encontró la ruta de vuelo.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Info de la ruta en JTextArea
        JTextArea infoRuta = new JTextArea(formatearInfoRuta(ruta));
        infoRuta.setEditable(false);
        panel.add(new JScrollPane(infoRuta));

        // Vuelos asociados en JList
        List<String> vuelos = ruta.getVuelos(); // lista de nombres de vuelos
        if (vuelos != null && !vuelos.isEmpty()) {
            panel.add(new JLabel("Vuelos asociados:"));
            JList<String> listaVuelos = new JList<>(vuelos.toArray(new String[0]));
            listaVuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            panel.add(new JScrollPane(listaVuelos));

            // Botón para ver detalle del vuelo seleccionado
            JButton btnDetalleVuelo = new JButton("Ver detalle del vuelo");
            btnDetalleVuelo.addActionListener(e -> {
                String vueloSeleccionado = listaVuelos.getSelectedValue();
                if (vueloSeleccionado != null) {
                    DtVuelo dtVuelo = sistema.obtenerDtVueloPorNombre(vueloSeleccionado);
                    if (dtVuelo != null) {
                        mostrarDetalleVuelo(dtVuelo, parent); // método que muestra ventana con detalle de DtVuelo
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Seleccione un vuelo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            });
            panel.add(btnDetalleVuelo);
        } else {
            panel.add(new JLabel("No hay vuelos asociados."));
        }

        JOptionPane.showMessageDialog(parent, panel, "Información de la Ruta", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String formatearInfoRuta(DtRutaVuelo ruta) {
        if (ruta == null) return "No se encontró la ruta de vuelo.";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Nombre: ").append(ruta.getNombre()).append("\n");
        stringBuilder.append("Descripción: ").append(ruta.getDescripcion()).append("\n");
        stringBuilder.append("Fecha de alta: ").append(ruta.getFechaAlta()).append("\n");
        stringBuilder.append("Costo Base Turista: ").append(ruta.getCostoBaseTurista()).append("\n");
        stringBuilder.append("Costo Base Ejecutivo: ").append(ruta.getCostoBaseEjecutivo()).append("\n");
        stringBuilder.append("Costo Equipaje Extra: ").append(ruta.getCostoEquipajeExtra()).append("\n");
        stringBuilder.append("Origen: ").append(ruta.getOrigen()).append("\n");
        stringBuilder.append("Destino: ").append(ruta.getDestino()).append("\n");
        stringBuilder.append("Aerolinea: ").append(ruta.getAerolinea()).append("\n");
        stringBuilder.append("Categoria: ").append(ruta.getCategoria()).append("\n\n");

        stringBuilder.append("Vuelos asociados:\n");
        if (ruta.getVuelos() != null && !ruta.getVuelos().isEmpty()) {
            for (String vuelo : ruta.getVuelos()) {
                stringBuilder.append("  Vuelo: ").append(vuelo).append("\n");
            }
        } else {
            stringBuilder.append("  No hay vuelos asociados.\n");
        }

        return stringBuilder.toString();
    }

    public static void mostrarDetalleVuelo(DtVuelo dtVuelo, JComponent parent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Nombre: ").append(dtVuelo.getNombre()).append("\n");
        stringBuilder.append("Fecha: ").append(dtVuelo.getFecha()).append("\n");
        stringBuilder.append("Duración: ").append(dtVuelo.getDuracion()).append("\n");
        stringBuilder.append("Asientos Turista: ").append(dtVuelo.getAsientosTurista()).append("\n");
        stringBuilder.append("Asientos Ejecutivo: ").append(dtVuelo.getAsientosEjecutivo()).append("\n");
        stringBuilder.append("Fecha Alta: ").append(dtVuelo.getFechaAlta()).append("\n");

        JTextArea detalle = new JTextArea(stringBuilder.toString());
        detalle.setEditable(false);
        JScrollPane scroll = new JScrollPane(detalle);

        JOptionPane.showMessageDialog(parent, scroll, "Detalle del Vuelo", JOptionPane.INFORMATION_MESSAGE);
    }


}

