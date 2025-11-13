package ui;

import data_types.DtAerolinea;
import data_types.DtCliente;
import data_types.DtUsuario;
import data_types.TipoDoc;
import sistema.ISistema;
import sistema.Sistema;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ModificarUsuario extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel supPanel;
    public JPanel getSupPanel() {return supPanel; }
    private JPanel panelCliente;
    private JTextField textNickC;
    private JTextField textNomC;
    private JTextField textApellido;
    private JTextField textEmailC;
    private JTextField textNac;
    private JTextField textNumDoc;
    private JComboBox comboAnio;
    private JComboBox comboMes;
    private JComboBox comboDia;
    private JComboBox comboTipoDoc;
    private JButton confirmarButton;
    private JButton cancelarButton;

    private JPanel panelAerolinea;
    private JTextField textNickA;
    private JTextField textNombreA;
    private JTextField textEmailA;
    private JTextField textSitioW;
    private JTextArea textADesc;
    private JButton cancelarButton1;
    private JButton confirmarButton1;
    private JComboBox comboUsuario;
    private ISistema sistema = Sistema.getInstance();
    public ModificarUsuario() {
        initFechaCombos();
        setContentPane(mainPanel);
        setTitle("Modificar Usuario");
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(450, 300);

        // Configurar componentes
        configurarComponentes();
        // Configurar eventos
        configurarEventos();
        // Inicialmente ocultar paneles
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(false);
    }

    private void configurarComponentes() {
        cargarUsuariosEnComboSincrono();
    }

    private void cargarUsuariosEnComboSincrono() {
        try {
            List<String> nicks = Sistema.getInstance().listarUsuariosPorNick();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("— Seleccione —");
            for (String s : nicks) model.addElement(s);
            comboUsuario.setModel(model);
            comboUsuario.addActionListener(e -> {
                String nick = (String) comboUsuario.getSelectedItem();
                if (nick == null || nick.isBlank() || nick.startsWith("—")) {
                    cargarDatosUsuario(null); // ocultar paneles
                    return;
                }
                cargarDatosUsuario(nick);     // tu método: private void cargarDatosUsuario(String nick)
            });
        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la lista de usuarios.\nDetalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            comboUsuario.setModel(new DefaultComboBoxModel<>(new String[]{"(sin datos)"}));
        }
    }

    private void initFechaCombos() {
        // --- AÑO: del actual hacia atrás hasta 1900 (ajustá el rango si querés)
        int anioActual = java.time.LocalDate.now().getYear();
        DefaultComboBoxModel<Integer> modeloAnio = new DefaultComboBoxModel<>();
        for (int y = anioActual; y >= 1900; y--) modeloAnio.addElement(y);
        comboAnio.setModel(modeloAnio);

        // --- MES: 1..12 (si querés nombres, te muestro abajo cómo)
        DefaultComboBoxModel<Integer> modeloMes = new DefaultComboBoxModel<>();
        for (int m = 1; m <= 12; m++) modeloMes.addElement(m);
        comboMes.setModel(modeloMes);

        // --- DÍA: según año/mes seleccionados
        actualizarDias();

        // Cuando cambie año o mes, recalcular días
        java.awt.event.ItemListener listener = e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                actualizarDias();
            }
        };
        comboAnio.addItemListener(listener);
        comboMes.addItemListener(listener);

        // (Opcional) Seleccionar hoy por defecto
        var hoy = java.time.LocalDate.now();
        comboAnio.setSelectedItem(hoy.getYear());
        comboMes.setSelectedItem(hoy.getMonthValue());
        actualizarDias(); // recalcula con estos valores
        comboDia.setSelectedItem(hoy.getDayOfMonth());
    }

    private void actualizarDias() {
        Integer year = (Integer) comboAnio.getSelectedItem();
        Integer month = (Integer) comboMes.getSelectedItem();
        if (year == null || month == null) return;

        int diasMes = java.time.YearMonth.of(year, month).lengthOfMonth();

        // Intento conservar el día elegido si sigue siendo válido
        Integer diaPrev = (Integer) comboDia.getSelectedItem();

        DefaultComboBoxModel<Integer> modeloDia = new DefaultComboBoxModel<>();
        for (int d = 1; d <= diasMes; d++) modeloDia.addElement(d);
        comboDia.setModel(modeloDia);

        if (diaPrev != null && diaPrev >= 1 && diaPrev <= diasMes) {
            comboDia.setSelectedItem(diaPrev);
        } else {
            comboDia.setSelectedItem(1);
        }
    }

    private void limpiarFormularioCli() {
        textNickC.setText("");
        textNomC.setText("");
        textEmailC.setText("");
        textApellido.setText("");
        textNac.setText("");
        comboDia.setSelectedIndex(0);
        comboMes.setSelectedIndex(0);
        comboAnio.setSelectedIndex(0);
        textNumDoc.setText("");
    }


    private void configurarEventos() {
        // Evento para selección de usuario
        comboUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String usuarioSeleccionado = (String) comboUsuario.getSelectedItem();
                cargarDatosUsuario(usuarioSeleccionado);

            }
        });
        // Botones de confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guardarCambiosCliente();
                modificarCliente();
            }
        });
        confirmarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guardarCambiosAerolinea();
                modificarAerolinea();
            }
        });

        // Botones de cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                limpiarFormularioCli();
            }
        });
        cancelarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

    private void modificarCliente() {
        try {
            String nick = textNickC.getText().trim();  // PK del cliente
            String nomb = textNomC.getText().trim();
            String app = textApellido.getText().trim();
            String mail = textEmailC.getText().trim();
            String nacio = textNac.getText().trim();
            TipoDoc tipo = (TipoDoc) comboTipoDoc.getSelectedItem();
            String num = textNumDoc.getText().trim();

            Integer year = (Integer) comboAnio.getSelectedItem();
            Integer month = (Integer) comboMes.getSelectedItem();
            Integer day = (Integer) comboDia.getSelectedItem(); // <-- FIX: antes tomabas de comboAnio
            java.time.LocalDate fechaNac = java.time.LocalDate.of(year, month, day);

            // Si necesitás saber el nick seleccionado en el combo:
            String nickNameSeleccionado = (String) comboUsuario.getSelectedItem();
            String contra ="contra";
            String Url ="imagen123";

            // Llamado a tu capa lógica (ajustá firma exacta)
            Sistema.getInstance().modificarCliente(
                    nick, nomb, mail, contra, Url, app, fechaNac, nacio, tipo, num, nickNameSeleccionado
            );

            JOptionPane.showMessageDialog(panelCliente, "Los cambios del cliente se guardaron correctamente.");
            limpiarFormularioCli();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(panelCliente, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(panelCliente, "Error al guardar los cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarAerolinea() {
        try {
            String nick = textNickA.getText().trim();      // PK de la aerolínea (no lo cambies acá)
            String nombre = textNombreA.getText().trim();
            String email = textEmailA.getText().trim();
            String sitio = (textSitioW.getText() == null) ? null : textSitioW.getText().trim();
            String desc = (textADesc.getText() == null) ? null : textADesc.getText().trim();

            // Nick actualmente seleccionado en el combo (clave "original" a modificar)
            String nickNameSeleccionado = (String) comboUsuario.getSelectedItem();
            String contra ="contra";
            String Url ="imagen123";

            // Llamado a la capa lógica (misma firma que hiciste para cliente, pero de aerolínea)
            Sistema.getInstance().modificarAerolinea(
                    nick, nombre, email, contra, Url, desc, sitio, nickNameSeleccionado
            );

            JOptionPane.showMessageDialog(panelAerolinea,
                    "Los cambios de la aerolínea se guardaron correctamente.");

            // Opcional: limpiar o recargar
            // limpiarFormularioAerolinea(); // si tenés helper

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(panelAerolinea,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (jakarta.persistence.PersistenceException ex) {
            JOptionPane.showMessageDialog(panelAerolinea,
                    "Error al guardar los cambios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarDatosUsuario(String nick) {
        // Placeholders o selección vacía
        if (nick == null || nick.trim().isEmpty() || nick.startsWith("—")) {
            panelCliente.setVisible(false);
            panelAerolinea.setVisible(false);
            String nickSeleccionado = null;
            DtUsuario seleccionado = null;
            return;
        }
        DtUsuario seleccionado;
        String nickSeleccionado;

        try {
            // Traer DTO desde la lógica
            seleccionado = sistema.dataUsuarioPorNick(nick); // ajustá nombre si difiere
            nickSeleccionado = nick;

            if (seleccionado instanceof DtCliente dtCliente) {
                mostrarPanelCliente(dtCliente);
            } else if (seleccionado instanceof DtAerolinea dtAerolinea) {
                mostrarPanelAerolinea(dtAerolinea);
            } else {
                // Si no conocés el tipo, ocultá todo
                panelCliente.setVisible(false);
                panelAerolinea.setVisible(false);
                JOptionPane.showMessageDialog(this,
                        "Tipo de usuario desconocido para '" + nick + "'.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (jakarta.persistence.PersistenceException ex) {
            panelCliente.setVisible(false);
            panelAerolinea.setVisible(false);
            nickSeleccionado = null;
            seleccionado = null;
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar el usuario '" + nick + "'.\nDetalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarPanelCliente(DtCliente dtCliente) {
        // mostrar/ocultar
        panelCliente.setVisible(true);
        panelAerolinea.setVisible(false);

        // completar campos
        textNickC.setText(dtCliente.getNickname());
        textNickC.setEditable(false); // si el nick es PK natural, no permitir edición

        textNomC.setText(dtCliente.getNombre());
        textApellido.setText(dtCliente.getApellido());
        textEmailC.setText(dtCliente.getEmail());
        textNac.setText(dtCliente.getNacionalidad());

        // TipoDoc
        if (comboTipoDoc.getModel().getSize() == 0) {
            comboTipoDoc.setModel(new DefaultComboBoxModel<>(TipoDoc.values()));
        }
        if (dtCliente.getTipoDoc() != null) comboTipoDoc.setSelectedItem(dtCliente.getTipoDoc());

        textNumDoc.setText(dtCliente.getNumeroDoc());

        // Fecha nacimiento
        if (dtCliente.getFechaNacimiento() != null) {
            var fechaNacimiento = dtCliente.getFechaNacimiento();
            comboAnio.setSelectedItem(fechaNacimiento.getYear());
            comboMes.setSelectedItem(fechaNacimiento.getMonthValue());
            actualizarDias(); // recalcula días según año/mes
            comboDia.setSelectedItem(fechaNacimiento.getDayOfMonth());
        }
    }

    private void mostrarPanelAerolinea(DtAerolinea dtAerolinea) {
        // mostrar/ocultar
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(true);

        // completar campos
        textNickA.setText(dtAerolinea.getNickname());
        textNickA.setEditable(false); // igual que arriba, si es PK

        textNombreA.setText(dtAerolinea.getNombre());
        textEmailA.setText(dtAerolinea.getEmail());
        textSitioW.setText(dtAerolinea.getSitioWeb());
        textADesc.setText(dtAerolinea.getDescripcion());
    }


    private void cargarDatosAerolinea(String usuario) {
        // Ocultar panel de cliente y mostrar panel de aerolínea
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(true);

        /*if (usuario.contains("Aerolíneas Ejemplo")) {
            textNickA.setText("aero123");
            textNombreA.setText("Aerolíneas Ejemplo");
            textEmailA.setText("contacto@aerolineasejemplo.com");
            textSitioW.setText("www.aerolineasejemplo.com");
            textADesc.setText("Aerolínea líder en servicios nacionales e internacionales con más de 20 años de experiencia en el mercado. Ofrecemos vuelos regulares a los principales destinos de América del Sur.");

        } else if (usuario.contains("Sky Airlines")) {
            textNickA.setText("sky456");
            textNombreA.setText("Sky Airlines");
            textEmailA.setText("info@skyairlines.com");
            textSitioW.setText("www.skyairlines.com");
            textADesc.setText("Aerolínea low-cost especializada en vuelos regionales con las mejores tarifas del mercado. Nuestro compromiso es hacer que volar sea accesible para todos.");
        }*/
    }

    private void guardarCambiosCliente() {
        if (validarDatosCliente()) {
            // Simular guardado de datos
            String nombreCompleto = textNomC.getText() + " " + textApellido.getText();
            String fechaNacimiento = comboDia.getSelectedItem() + "/" +
                    comboMes.getSelectedItem() + "/" +
                    comboAnio.getSelectedItem();

            JOptionPane.showMessageDialog(this,
                    "Datos del cliente actualizados exitosamente:\n" +
                            "Nombre: " + nombreCompleto + "\n" +
                            "Email: " + textEmailC.getText() + "\n" +
                            "Fecha Nacimiento: " + fechaNacimiento + "\n" +
                            "Nacionalidad: " + textNac.getText() + "\n" +
                            "Documento: " + comboTipoDoc.getSelectedItem() + " " + textNumDoc.getText(),
                    "Cambios Guardados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void guardarCambiosAerolinea() {
        if (validarDatosAerolinea()) {
            // Simular guardado de datos
            JOptionPane.showMessageDialog(this,
                    "Datos de la aerolínea actualizados exitosamente:\n" +
                            "Nombre: " + textNombreA.getText() + "\n" +
                            "Email: " + textEmailA.getText() + "\n" +
                            "Sitio Web: " + textSitioW.getText() + "\n" +
                            "Descripción: " + textADesc.getText().substring(0, Math.min(50, textADesc.getText().length())) + "...",
                    "Cambios Guardados",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarDatosCliente() {
        // Validar campos obligatorios
        if (textNomC.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio", textNomC);
            return false;
        }

        if (textApellido.getText().trim().isEmpty()) {
            mostrarError("El apellido es obligatorio", textApellido);
            return false;
        }

        if (textNumDoc.getText().trim().isEmpty()) {
            mostrarError("El número de documento es obligatorio", textNumDoc);
            return false;
        }

        if (textNac.getText().trim().isEmpty()) {
            mostrarError("La nacionalidad es obligatoria", textNac);
            return false;
        }

        return true;
    }

    private boolean validarDatosAerolinea() {
        // Validar campos obligatorios
        if (textNombreA.getText().trim().isEmpty()) {
            mostrarError("El nombre de la aerolínea es obligatorio", textNombreA);
            return false;
        }

        if (textADesc.getText().trim().isEmpty()) {
            mostrarError("La descripción es obligatoria", textADesc);
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje, JComponent componente) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
        componente.requestFocus();
    }

    private void limpiarCampos() {
        // Limpiar campos de cliente
        textNickC.setText("");
        textNomC.setText("");
        textApellido.setText("");
        textEmailC.setText("");
        textNac.setText("");
        textNumDoc.setText("");
        comboTipoDoc.setSelectedIndex(0);
        comboAnio.setSelectedIndex(0);
        comboMes.setSelectedIndex(0);
        comboDia.setSelectedIndex(0);

        // Limpiar campos de aerolínea
        textNickA.setText("");
        textNombreA.setText("");
        textEmailA.setText("");
        textSitioW.setText("");
        textADesc.setText("");

        // Ocultar paneles
        panelCliente.setVisible(false);
        panelAerolinea.setVisible(false);
    }
}


