package casoestudio2.pantsprue;

import casoestudio2.datos.Sistema;
import casoestudio2.modelo.Usuario;
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
    private Sistema sistema;

    public FrmLoginCodigo() {
        
        sistema = new Sistema();

        
        setTitle("Control de Acceso - Fidecompro");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra en pantalla automáticamente
        setLayout(new BorderLayout(10, 10)); // Margen entre secciones

        
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnIngresar = new JButton("Ingresar");
        btnCancelar = new JButton("Cancelar");

        
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 5, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        
        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtPassword);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);

        
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        
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

       Usuario usuarioLogueado = sistema.validarUsuario(usuario, password);

       if (usuarioLogueado != null) {

           JOptionPane.showMessageDialog(this,
                   "Bienvenido " + usuarioLogueado.getRol());

           new FrmMenuCodigo(usuarioLogueado.getRol()).setVisible(true);

           dispose();

        } else {

           JOptionPane.showMessageDialog(this,
                   "Error: Usuario o contraseña incorrectos",
                   "Error",
                   JOptionPane.ERROR_MESSAGE);

           txtPassword.setText("");
           txtPassword.requestFocus();

        }
    }
}