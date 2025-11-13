package ui;


import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import sistema.ISistema;
import sistema.Sistema;
import data_types.DtPaqueteVuelo;


public class CompraPaquete extends JInternalFrame {


    private JComboBox<String> cbClientes;
    private JComboBox<String> cbPaquetes;
    private JTextArea textArea1;
    private JButton confirmarButton;
    private JButton cancelarButton;
    private JPanel panelPrincipal;


    private final ISistema sistema;


    public CompraPaquete() {
        sistema = Sistema.getInstance();


        setContentPane(panelPrincipal);
        setTitle("Compra de Paquete de Vuelo");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);


        cargarClientes();
        cargarPaquetes();



        // Mostrar detalles del paquete al seleccionarlo
        cbPaquetes.addActionListener(e -> {
            String nombrePaquete = (String) cbPaquetes.getSelectedItem();
            if (nombrePaquete != null) {
                DtPaqueteVuelo paquete = sistema.obtenerDtPaquetePorNombre(nombrePaquete);
                textArea1.setText(
                        "📦 Paquete: " + paquete.nombre() + "\n" +
                                "Descripción: " + paquete.descripcion() + "\n" +
                                "Días de validez: " + paquete.diasValidez() + "\n" +
                                "Descuento: " + paquete.descuento() + "%\n" +
                                "Fecha alta: " + paquete.altaFecha()
                );
            } else {
                textArea1.setText("");
            }
        });




        // Confirmar compra
        confirmarButton.addActionListener((ActionEvent e) -> {
            String nickCliente = (String) cbClientes.getSelectedItem();
            String nombrePaquete = (String) cbPaquetes.getSelectedItem();


            if (nickCliente == null || nombrePaquete == null) {
                JOptionPane.showMessageDialog(panelPrincipal,
                        "Debe seleccionar un cliente y un paquete.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            LocalDate fechaCompra = LocalDate.now();


            try {
                sistema.comprarPaquete(nickCliente, nombrePaquete, fechaCompra);


                JOptionPane.showMessageDialog(panelPrincipal,
                        "✅ Compra registrada exitosamente.\n" +
                                "Cliente: " + nickCliente + "\n" +
                                "Paquete: " + nombrePaquete + "\n" +
                                "Fecha: " + fechaCompra,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);


            } catch (IllegalArgumentException ex1) {
                JOptionPane.showMessageDialog(panelPrincipal,
                        "Error: " + ex1.getMessage(),
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
            } catch (jakarta.persistence.PersistenceException ex) {
                JOptionPane.showMessageDialog(panelPrincipal,
                        "Error al registrar la compra: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });


        // Cancelar
        cancelarButton.addActionListener(e -> dispose());
    }


    private void cargarClientes() {
        List<String> clientes = sistema.listarNicknames();
        cbClientes.removeAllItems();
        for (String c : clientes) {
            cbClientes.addItem(c);
        }
    }


    private void cargarPaquetes() {
        try {
            cbPaquetes.setModel(new DefaultComboBoxModel<>(
                    sistema.listarNombresPaquetesConRutas().toArray(new String[0])
            ));
            cbPaquetes.setSelectedIndex(-1);
        } catch (jakarta.persistence.PersistenceException e) {
            JOptionPane.showMessageDialog(panelPrincipal, "Error al cargar paquetes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public JPanel getPanel() {
        return panelPrincipal;
    }
}

