package casoestudio2.pantsprue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import casoestudio2.datos.Sistema;
import casoestudio2.modelo.Cliente;
import casoestudio2.modelo.Producto;
import casoestudio2.modelo.DetalleFactura;
import casoestudio2.modelo.Factura;
import casoestudio2.excepciones.StockInsuficienteException;


public class FrmFacturacionCodigo extends JFrame {

    // Componentes de entrada y control
    private JTextField txtCedula, txtNombreCliente, txtIdProducto, txtNombreProducto, txtCantidad, txtPrecio;
    private JTextField txtSubtotal, txtImpuesto, txtTotal;
    private JButton btnBuscarCliente, btnAgregar, btnProcesar;
    private JTable tblCarrito;
    private DefaultTableModel modeloTabla;
    private Sistema sistema;
    private Cliente clienteActual;
    private Factura facturaActual;

    public FrmFacturacionCodigo() {
        
        sistema = new Sistema();
        
        setTitle("Módulo de Facturación Electrónica - Fidecompro");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        
        txtCedula = new JTextField(8);
        btnBuscarCliente = new JButton("Buscar");
        txtNombreCliente = new JTextField(15);
        txtNombreCliente.setEditable(false);
        
        panelCliente.add(new JLabel("Cédula:"));
        panelCliente.add(txtCedula);
        panelCliente.add(btnBuscarCliente);
        panelCliente.add(new JLabel("Nombre:"));
        panelCliente.add(txtNombreCliente);

        
        JPanel panelEntradaProd = new JPanel(new GridLayout(5, 2, 5, 8));
        panelEntradaProd.setBorder(BorderFactory.createTitledBorder("Agregar Artículos"));
        
        txtIdProducto = new JTextField();
        txtNombreProducto = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();
        
        txtNombreProducto.setEditable(false);
        txtPrecio.setEditable(false);
        
        btnAgregar = new JButton("Agregar al Carrito");

        panelEntradaProd.add(new JLabel("ID Producto:"));
        panelEntradaProd.add(txtIdProducto);
        panelEntradaProd.add(new JLabel("Nombre:"));
        panelEntradaProd.add(txtNombreProducto);
        panelEntradaProd.add(new JLabel("Cantidad:"));
        panelEntradaProd.add(txtCantidad);
        panelEntradaProd.add(new JLabel("Precio Unitario:"));
        panelEntradaProd.add(txtPrecio);
        panelEntradaProd.add(new JLabel(""));
        panelEntradaProd.add(btnAgregar);

        
        String[] columnas = {"Código", "Detalle", "Cantidad", "Precio Unitario", "Subtotal Linea"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblCarrito = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tblCarrito);
        scrollTabla.setPreferredSize(new Dimension(600, 150));

        
        JPanel panelCentralContenedor = new JPanel(new BorderLayout(5, 5));
        panelCentralContenedor.add(panelEntradaProd, BorderLayout.NORTH);
        panelCentralContenedor.add(scrollTabla, BorderLayout.CENTER);

        
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 5, 5));
        txtSubtotal = new JTextField("0.0", 8); txtSubtotal.setEditable(false);
        txtImpuesto = new JTextField("0.0", 8); txtImpuesto.setEditable(false);
        txtTotal = new JTextField("0.0", 8);    txtTotal.setEditable(false);
        
        panelTotales.add(new JLabel("Subtotal:")); panelTotales.add(txtSubtotal);
        panelTotales.add(new JLabel("IVA (13%):")); panelTotales.add(txtImpuesto);
        panelTotales.add(new JLabel("Total Neto:")); panelTotales.add(txtTotal);

        btnProcesar = new JButton("Procesar Factura y Exportar Física (.txt)");
        btnProcesar.setFont(new Font("Arial", Font.BOLD, 12));

        panelInferior.add(panelTotales, BorderLayout.EAST);
        panelInferior.add(btnProcesar, BorderLayout.WEST);

        
        add(panelCliente, BorderLayout.NORTH);
        add(panelCentralContenedor, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        txtIdProducto.addActionListener(e -> {

        Producto producto = sistema.buscarProducto(txtIdProducto.getText());

        if (producto != null) {

            txtNombreProducto.setText(producto.getNombreComercial());
            txtPrecio.setText(String.valueOf(producto.getPrecioBase()));

        } else {

            txtNombreProducto.setText("");
            txtPrecio.setText("");

        }

    });
        
        btnBuscarCliente.addActionListener(e -> {

           String cedula = txtCedula.getText();

           clienteActual = sistema.buscarCliente(cedula);

           if(clienteActual != null){

               txtNombreCliente.setText(
                   clienteActual.getNombreCompleto()
               );

               facturaActual = new Factura(
                   (int)(Math.random()*10000),
                   clienteActual
               );

            }else{

                JOptionPane.showMessageDialog(this,
                        "Cliente no encontrado");

            }

});

        btnAgregar.addActionListener(e -> {

        try {

            if (facturaActual == null) {
                JOptionPane.showMessageDialog(this,
                        "Primero debe buscar un cliente.");
                return;
            }

            Producto producto = sistema.buscarProducto(txtIdProducto.getText());

            if (producto == null) {
                JOptionPane.showMessageDialog(this,
                        "Producto no encontrado.");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad.getText());

            

            DetalleFactura detalle = new DetalleFactura(producto, cantidad);

            facturaActual.agregarDetalle(detalle);

            modeloTabla.addRow(new Object[]{
                producto.getIdProducto(),
                producto.getNombreComercial(),
                cantidad,
                producto.getPrecioBase(),
                detalle.calcularSubtotal()
            });

            producto.actualizarStock(-cantidad);

            recalcularTotales();

        } catch (NumberFormatException ex) {

    JOptionPane.showMessageDialog(this,
            "Cantidad inválida.");

        } catch (StockInsuficienteException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
        
   });


        btnProcesar.addActionListener(e -> procesarFactura());
    }

    private void recalcularTotales() {

        if (facturaActual == null) {
            return;
        }

        txtSubtotal.setText(String.valueOf(facturaActual.calcularSubtotal()));
        txtImpuesto.setText(String.valueOf(facturaActual.calcularIVA()));
        txtTotal.setText(String.valueOf(facturaActual.calcularTotal()));

        }

    private void procesarFactura() {
        
        if (facturaActual == null) {
        JOptionPane.showMessageDialog(this, "No hay factura para procesar.");
        return;
        }

        sistema.agregarFactura(facturaActual);
        
        int num = (int) (Math.random() * 10000 + 1);
        String name = "factura_codigo_" + num + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            writer.write("=== RECIBO GENERADO AUTOMÁTICAMENTE POR CÓDIGO ===");
            writer.newLine();
            writer.write("Factura N°: " + num);
            writer.newLine();
            writer.write("Cliente: " + facturaActual.getCliente().getNombreCompleto());
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                writer.write(modeloTabla.getValueAt(i, 1) + " x" + modeloTabla.getValueAt(i, 2) + " = ¢" + modeloTabla.getValueAt(i, 4));
                writer.newLine();
            }
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write("TOTAL FINAL DE LA OPERACIÓN: ¢" + txtTotal.getText());

            JOptionPane.showMessageDialog(this, "Factura guardada correctamente como: " + name);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error de escritura: " + ex.getMessage());
        }
    }
}