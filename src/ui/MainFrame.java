package ui;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class
MainFrame extends JFrame {
    private JDesktopPane desktopPane;

    public MainFrame() {
        setTitle("volando.uy - Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
        crearMenu();
        crearStatusBar();
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Usuarios
        JMenu menuUsuarios = new JMenu("Usuarios");
        menuUsuarios.add(crearMenuItem("Alta de Usuario", e -> abrirAltaUsuario()));
        menuUsuarios.add(crearMenuItem("Consulta de Usuario", e -> abrirConsultaUsuario()));
        menuUsuarios.add(crearMenuItem("Modificar Datos de Usuario", e -> abrirModificarUsuario()));
        menuBar.add(menuUsuarios);

        // Menú Vuelos
        JMenu menuVuelos = new JMenu("Vuelos");
        menuVuelos.add(crearMenuItem("Alta de Ruta de Vuelo", e -> abrirAltaRuta()));
        menuVuelos.add(crearMenuItem("Consulta de Ruta de Vuelo", e -> abrirConsultaRuta()));
        menuVuelos.add(crearMenuItem("Aceptar/Rechazar Ruta de Vuelo", e -> abrirAceptarRV()));
        menuVuelos.add(crearMenuItem("Rutas de Vuelo más visitadas", e -> abrirRutasMasVisitadas()));
        menuVuelos.add(crearMenuItem("Alta de Vuelo", e -> abrirAltaVuelo()));
        menuVuelos.add(crearMenuItem("Consulta de Vuelo", e -> abrirConsultaVuelo()));
        menuVuelos.add(crearMenuItem("Reserva de Vuelo", e -> abrirReservaVuelo()));
        menuBar.add(menuVuelos);

        // Menú Ciudades
        JMenu menuCiudades = new JMenu("Ciudades");
        menuCiudades.add(crearMenuItem("Alta de Ciudad", e -> abrirAltaCiudad()));
        menuBar.add(menuCiudades);

        // Menú Paquetes
        JMenu menuPaquetes = new JMenu("Paquetes");
        menuPaquetes.add(crearMenuItem("Crear Paquete", e -> abrirCrearPaquete()));
        menuPaquetes.add(crearMenuItem("Agregar Ruta a Paquete", e -> abrirAgregarRutaPaquete()));
        menuPaquetes.add(crearMenuItem("Consulta de Paquete", e -> abrirConsultaPaquete()));
        menuPaquetes.add(crearMenuItem("Compra de Paquete", e -> abrirCompraPaquete()));
        menuBar.add(menuPaquetes);

        // Menú Categorías
        JMenu menuCategorias = new JMenu("Categorías");
        menuCategorias.add(crearMenuItem("Alta de Categoría", e -> abrirAltaCategoria()));
        menuBar.add(menuCategorias);

        setJMenuBar(menuBar);
    }

    private void crearStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(new JLabel("Sistema de Gestión de Vuelos - Administrador"));
        add(statusBar, BorderLayout.SOUTH);
    }

    private JMenuItem crearMenuItem(String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(listener);
        return item;
    }

    private void abrirFormulario(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        centrarVentanaInterna(frame);
    }

    private void centrarVentanaInterna(JInternalFrame frame) {
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((desktopSize.width - frameSize.width) / 2,
                (desktopSize.height - frameSize.height) / 2);
    }

    // Métodos para abrir cada formulario (sin implementar aún)
    private void abrirAltaUsuario() {
        pruebaAltaUsuario form = new pruebaAltaUsuario();
        abrirFormulario(form);
    }

    private void abrirConsultaUsuario() {
        ConsultaUsuario form = new ConsultaUsuario();
        abrirFormulario(form);

    }

    private void abrirModificarUsuario() {ModificarUsuario form = new ModificarUsuario();
       abrirFormulario(form);
    }

    private void abrirAltaRuta() {
      AltaRutaVuelo form = new AltaRutaVuelo();
      abrirFormulario(form);
    }

    public void abrirConsultaRuta() {
        JInternalFrame frame = new JInternalFrame("Consulta de Ruta de Vuelo", true, true, true, true);
        ConsultaRDV form = new ConsultaRDV();
        frame.setContentPane(form.getPanel());
        frame.pack();
        abrirFormulario(frame);
    }
    public void abrirAceptarRV() {
        AceptarRV form = new AceptarRV();
        abrirFormulario(form);
    }
    public void abrirRutasMasVisitadas() {
        RutasMasVisitadas form = new RutasMasVisitadas();
        abrirFormulario(form);
    }
    private void abrirAltaVuelo() {
       AltaVuelo form = new AltaVuelo();
       abrirFormulario(form);
    }

    public void abrirConsultaVuelo() {
        ConsultaVuelo consultaVuelo = new ConsultaVuelo();
        JInternalFrame frame = new JInternalFrame("Consulta de Vuelo", true, true, true, true);
        frame.setContentPane(consultaVuelo.getPanel());
        frame.pack();
        abrirFormulario(frame);
    }

    private void abrirReservaVuelo() {
        ReservaVuelo reserva = new ReservaVuelo();
        JInternalFrame frame = new JInternalFrame("Reserva de Vuelo", true, true, true, true);
        frame.setContentPane(reserva.getPanel());
        frame.pack();
        abrirFormulario(frame);
    }

    private void abrirAltaCiudad() {
        AltaCiudad altaCiu = new AltaCiudad();
        altaCiu.pack();
        abrirFormulario(altaCiu);
    }

    private void abrirCrearPaquete() {
        CrearPaqueteDeRutasDeVuelo crearPaquete = new CrearPaqueteDeRutasDeVuelo();
        JInternalFrame frame = new JInternalFrame("Crear Paquete", true, true, true, true);
        frame.setContentPane(crearPaquete.getPanel());
        frame.pack();
        abrirFormulario(frame);
    }

    private void abrirAgregarRutaPaquete() {
        AgregarRDVaPaquete agregarRutaaPaq = new AgregarRDVaPaquete();
        JInternalFrame frame = new JInternalFrame("Agregar Ruta a Paquete", true, true, true, true);
        frame.setContentPane(agregarRutaaPaq.getPanel());
        frame.pack();
        abrirFormulario(frame);
    }

    public void abrirConsultaPaquete() {
        ConsultaPaqueteRDV consultaPaquete = new ConsultaPaqueteRDV();
        JInternalFrame frame = new JInternalFrame("Consulta de Paquete", true, true, true, true);
        frame.setContentPane(consultaPaquete.getPanel());
        frame.setSize(700, 400);
        abrirFormulario(frame);
    }

    private void abrirCompraPaquete() {
        CompraPaquete comprapaq = new CompraPaquete();
        comprapaq.pack();
        abrirFormulario(comprapaq);
    }

    private void abrirAltaCategoria() {
        AltaCategoria altacat = new AltaCategoria();
        altacat.pack();
        abrirFormulario(altacat);
    }

   public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());  // Podés probar también FlatDarkLaf o FlatLightLaf
        } catch (javax.swing.UnsupportedLookAndFeelException e) {
            System.err.println("Error al aplicar el tema FlatLaf.");
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
