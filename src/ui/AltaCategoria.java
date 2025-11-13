package ui;

import sistema.Sistema;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class AltaCategoria extends JInternalFrame {
    private JPanel altaCat;
    private JTextField nombreCat;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public JPanel getPanel() {
        return altaCat;
    }

    public AltaCategoria() {
        setContentPane(altaCat);
        setTitle("Alta de Categoría");
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(500, 350);

        // Confirmar
        btnConfirmar.addActionListener(e -> {
            String nombre = nombreCat.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this.getPanel(),
                        "El nombre no puede estar vacío.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Sistema.getInstance().altaCategoria(nombre);
                JOptionPane.showMessageDialog(this.getPanel(),
                        "Categoría guardada con éxito.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                nombreCat.setText("");
                nombreCat.requestFocus();
            } catch (IllegalArgumentException | jakarta.persistence.PersistenceException ex) {
                JOptionPane.showMessageDialog(this.getPanel(),
                        ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }
}
