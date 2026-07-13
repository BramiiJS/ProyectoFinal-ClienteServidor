package casoestudio2.pantsprue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmLoginCodigo extends JFrame {

    // Declaración de componentes como atributos de la clase
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JButton btnCancelar;

    public FrmLoginCodigo() {
        // 1. Configuración básica de la ventana externa
        setTitle("Control de Acceso - Fidecompro");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra en pantalla automáticamente
        setLayout(new BorderLayout(10, 10)); // Margen entre secciones

        // 2. Inicialización de componentes internos
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnIngresar = new JButton("Ingresar");
        btnCancelar = new JButton("Cancelar");

        // 3. Crear el panel central estructurado en una cuadrícula (Grid) de 2 columnas
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        
        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtPassword);

        // 4. Crear el panel inferior para agrupar los botones horizontalmente
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);

        // 5. Agregar los paneles organizados al JFrame principal
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // 6. Programar la lógica de los eventos (ActionListeners)
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarLogin();
            }
        });

        btnCancelar.addActionListener(e -> {
            txtUsuario.setText("");
            txtPassword.setText("");
            txtUsuario.requestFocus();
        });
    }

    private void ejecutarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (usuario.equals("admin") && password.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Bienvenido Administrador.");
            new FrmMenuCodigo("Administrador").setVisible(true);
            this.dispose();
        } else if (usuario.equals("cajero") && password.equals("5678")) {
            JOptionPane.showMessageDialog(this, "Bienvenido Cajero.");
            new FrmMenuCodigo("Cajero").setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error: Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }
}