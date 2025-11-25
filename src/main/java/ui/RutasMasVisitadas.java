package ui;

import data_types.DtRutaVuelo;
import sistema.ISistema;
import sistema.Sistema;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.DefaultListModel;
import java.util.List;

public class RutasMasVisitadas extends JInternalFrame {
    private JPanel panel1;
    private JList listaN;
    private JList listaCantV;
    private JList listaCiuD;
    private JList listaCiuO;
    private JList listaAero;
    private JList listaRutas;

    private ISistema sistema = Sistema.getInstance();

    public RutasMasVisitadas() {
        setContentPane(panel1);
        setTitle("Aceptar o Rechazar Ruta de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(400, 300);
        cargarDatos();
    }
    private void cargarDatos() {
        // Llamada al sistema para traer los DTOs (top 5)
        List<DtRutaVuelo> rutas = sistema.listarRutasMasVisitadas();

        DefaultListModel<String> modeloN = new DefaultListModel<>();
        DefaultListModel<String> modeloRutas = new DefaultListModel<>();
        DefaultListModel<String> modeloAero = new DefaultListModel<>();
        DefaultListModel<String> modeloCiuO = new DefaultListModel<>();
        DefaultListModel<String> modeloCiuD = new DefaultListModel<>();
        DefaultListModel<String> modeloCantV = new DefaultListModel<>();

        if (rutas == null || rutas.isEmpty()) {
            // rellenar con placeholders si no hay datos
            for (int i = 1; i <= 5; i++) {
                modeloN.addElement(String.valueOf(i));
                modeloRutas.addElement("-");
                modeloAero.addElement("-");
                modeloCiuO.addElement("-");
                modeloCiuD.addElement("-");
                modeloCantV.addElement("-");
            }
        } else {
            int contador = 1;
            for (DtRutaVuelo r : rutas) {
                modeloN.addElement(String.valueOf(contador++));
                modeloRutas.addElement(r.getNombre() != null ? r.getNombre() : "-");
                modeloAero.addElement(r.getAerolinea() != null ? r.getAerolinea() : "-");
                modeloCiuO.addElement(r.getOrigen() != null ? r.getOrigen() : "-");
                modeloCiuD.addElement(r.getDestino() != null ? r.getDestino() : "-");
                modeloCantV.addElement(String.valueOf(r.getCantVisitas()));
            }
            // Si hay menos de 5 resultados, completar las filas restantes para mantener la tabla
            for (int i = rutas.size() + 1; i <= 5; i++) {
                modeloN.addElement(String.valueOf(i));
                modeloRutas.addElement("-");
                modeloAero.addElement("-");
                modeloCiuO.addElement("-");
                modeloCiuD.addElement("-");
                modeloCantV.addElement("-");
            }
        }
        listaN.setModel(modeloN);
        listaRutas.setModel(modeloRutas);
        listaAero.setModel(modeloAero);
        listaCiuO.setModel(modeloCiuO);
        listaCiuD.setModel(modeloCiuD);
        listaCantV.setModel(modeloCantV);
    }
}
