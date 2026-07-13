package casoestudio2.pantsprue;

import casoestudio2.pantsprue.FrmFacturacionCodigo;
import javax.swing.*;
import java.awt.*;

public class FrmMenuCodigo extends JFrame {

    private String rolUsuario;
    private JLabel lblBienvenida;
    private JMenuBar barraMenu;
    private JMenu menuArchivo, menuMantenimientos, menuProcesos;
    private JMenuItem itemSalir, itemClientes, itemProductos, itemFacturacion;

    public FrmMenuCodigo(String rol) {
        this.rolUsuario = rol;

        // Configuración de la ventana contenedora
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Construcción de la barra de menú superior
        barraMenu = new JMenuBar();
        
        menuArchivo = new JMenu("Archivo");
        itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);

        menuMantenimientos = new JMenu("Mantenimientos");
        itemClientes = new JMenuItem("Clientes");
        itemProductos = new JMenuItem("Productos");
        menuMantenimientos.add(itemClientes);
        menuMantenimientos.add(itemProductos);

        menuProcesos = new JMenu("Procesos");
        itemFacturacion = new JMenuItem("Facturación");
        menuProcesos.add(itemFacturacion);

        barraMenu.add(menuArchivo);
        barraMenu.add(menuMantenimientos);
        barraMenu.add(menuProcesos);
        setJMenuBar(barraMenu); // Asigna la barra al marco superior

        // Panel Central con texto de bienvenida informativo
        setLayout(new BorderLayout());
        lblBienvenida = new JLabel("Cargando...", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBienvenida, BorderLayout.CENTER);

        // Barra de estado inferior simulada
        JLabel lblEstado = new JLabel(" Conectado al sistema de sucursales Fidecompro");
        lblEstado.setBorder(BorderFactory.createEtchedBorder());
        add(lblEstado, BorderLayout.SOUTH);

        // Eventos básicos
        itemSalir.addActionListener(e -> System.exit(0));
        itemFacturacion.addActionListener(e -> new FrmFacturacionCodigo().setVisible(true));

        aplicarPermisos();
    }

    private void aplicarPermisos() {
        lblBienvenida.setText("Bienvenido al Sistema: Sesión activa como [" + rolUsuario + "]");
        if (rolUsuario.equalsIgnoreCase("Cajero")) {
            itemProductos.setEnabled(false); // Bloquea acceso administrativo por código
            setTitle("Fidecompro - Módulo de Punto de Venta (Cajero)");
        } else {
            setTitle("Fidecompro - Sistema de Control Total (Administrador)");
        }
    }
}