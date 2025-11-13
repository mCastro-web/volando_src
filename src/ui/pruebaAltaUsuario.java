package ui;

import data_types.TipoDoc;
import sistema.Sistema;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pruebaAltaUsuario extends JInternalFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel clientePanel;
    private JTextField nickName;
    private JTextField nombre;
    private JTextField apellido;
    private JTextField email;
    private JTextField nacionalidad;
    private JTextField numDocumento;
    private JComboBox<TipoDoc> comboTipoDoc;
    private JComboBox anio;
    private JComboBox mes;
    private JComboBox dia;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JTextField nickAero;
    private JTextField nomAero;
    private JTextField emailAero;
    private JTextArea descAero;
    private JTextField sitioweb;
    private JButton confirmarButton1;
    private JButton cancelarButton1;
    public JTabbedPane getSupPanel() {return tabbedPane1; }

    public JPanel getPanel(){
        return clientePanel;
    }

    public pruebaAltaUsuario() {
        setContentPane(mainPanel);
        setTitle("Alta de Usuario");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(500, 300);
        initFechaCombos();
        configurarEventos();
    }

    private void configurarEventos() {
        // Botón Confirmar
        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                registrarCliente();
            }
        });
        // Botón Cancelar
        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                limpiarFormularioCli();
            }
        });
        // Botón Confirmar
        confirmarButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                registrarAerolinea();
            }
        });
        // Botón Cancelar
        cancelarButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                limpiarFormularioAero();
            }
        });
    }

    private void registrarCliente(){
        try{

            String nick = nickName.getText().trim();
            String nomb = nombre.getText().trim();
            String app = apellido.getText().trim();
            String mail = email.getText().trim();
            String nacio =nacionalidad.getText().trim();
            TipoDoc tipo = (TipoDoc) comboTipoDoc.getSelectedItem();
            String num = numDocumento.getText().trim();

            Integer year = (Integer) anio.getSelectedItem();
            Integer month  = (Integer) mes.getSelectedItem();
            Integer day  = (Integer) dia.getSelectedItem();
            java.time.LocalDate fechaNac = java.time.LocalDate.of(year, month, day);
            String contra ="contra";
            String Url ="imagen123";

            Sistema.getInstance().altaCliente(nick, nomb, mail, contra, Url, app, fechaNac, nacio, tipo, num);
            JOptionPane.showMessageDialog(clientePanel, "El cliente ha sido registrado exitosamente");
            limpiarFormularioCli();

        }catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(clientePanel, "Error: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch (jakarta.persistence.PersistenceException ex){
            JOptionPane.showMessageDialog(clientePanel, "Error al registrar al cliente " + ex.getMessage(), "Erroe", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarAerolinea(){
        try{

            String nick = nickAero.getText().trim();
            String nomb = nomAero.getText().trim();
            String mail = emailAero.getText().trim();
            String desc = descAero.getText().trim();
            String sitio =sitioweb.getText().trim();
            String contra ="contra";
            String Url ="imagen123";

            Sistema.getInstance().altaAerolinea(nick, nomb, mail, contra, Url, desc, sitio);
            JOptionPane.showMessageDialog(clientePanel, "La aerolinea ha sido registrada exitosamente");
            limpiarFormularioAero();

        }catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(clientePanel, "Error: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }catch (jakarta.persistence.PersistenceException ex){
            JOptionPane.showMessageDialog(clientePanel, "Error al registrar a la aerolinea " + ex.getMessage(), "Erroe", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void initFechaCombos() {
        // --- AÑO: del actual hacia atrás hasta 1900
        int anioActual = java.time.LocalDate.now().getYear();
        DefaultComboBoxModel<Integer> modeloAnio = new DefaultComboBoxModel<>();
        for (int y = anioActual; y >= 1900; y--) modeloAnio.addElement(y);
        anio.setModel(modeloAnio);

        // --- MES: 1..12
        DefaultComboBoxModel<Integer> modeloMes = new DefaultComboBoxModel<>();
        for (int m = 1; m <= 12; m++) modeloMes.addElement(m);
        mes.setModel(modeloMes);

        // --- DÍA: según año/mes seleccionados
        actualizarDias();

        // Cuando cambie año o mes, recalcular días
        java.awt.event.ItemListener listener = e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                actualizarDias();
            }
        };
        anio.addItemListener(listener);
        mes.addItemListener(listener);

        // (Opcional) Seleccionar hoy por defecto
        var hoy = java.time.LocalDate.now();
        anio.setSelectedItem(hoy.getYear());
        mes.setSelectedItem(hoy.getMonthValue());
        actualizarDias(); // recalcula con estos valores
        dia.setSelectedItem(hoy.getDayOfMonth());
    }

    private void actualizarDias() {
        Integer year = (Integer) anio.getSelectedItem();
        Integer month = (Integer) mes.getSelectedItem();
        if (year == null || month == null) return;

        int diasMes = java.time.YearMonth.of(year, month).lengthOfMonth();

        // Intento conservar el día elegido si sigue siendo válido
        Integer diaPrev = (Integer) dia.getSelectedItem();

        DefaultComboBoxModel<Integer> modeloDia = new DefaultComboBoxModel<>();
        for (int d = 1; d <= diasMes; d++) modeloDia.addElement(d);
        dia.setModel(modeloDia);

        if (diaPrev != null && diaPrev >= 1 && diaPrev <= diasMes) {
            dia.setSelectedItem(diaPrev);
        } else {
            dia.setSelectedItem(1);
        }
    }

    private void limpiarFormularioCli(){
        nickName.setText("");
        nombre.setText("");
        email.setText("");
        apellido.setText("");
        nacionalidad.setText("");
        dia.setSelectedIndex(0);
        mes.setSelectedIndex(0);
        dia.setSelectedIndex(0);
        numDocumento.setText("");
    }

    private void limpiarFormularioAero(){
        nickAero.setText("");
        nomAero.setText("");
        emailAero.setText("");
        descAero.setText("");
        sitioweb.setText("");
    }

    private void createUIComponents() {
        comboTipoDoc = new JComboBox<>(TipoDoc.values());
    }
}




