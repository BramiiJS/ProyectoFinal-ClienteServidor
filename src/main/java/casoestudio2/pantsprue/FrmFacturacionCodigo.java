package casoestudio2.pantsprue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FrmFacturacionCodigo extends JFrame {

    // Componentes de entrada y control
    private JTextField txtCedula, txtNombreCliente, txtIdProducto, txtNombreProducto, txtCantidad, txtPrecio;
    private JTextField txtSubtotal, txtImpuesto, txtTotal;
    private JButton btnBuscarCliente, btnAgregar, btnProcesar;
    private JTable tblCarrito;
    private DefaultTableModel modeloTabla;

    public FrmFacturacionCodigo() {
        setTitle("Módulo de Facturación Electrónica - Fidecompro");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana, no el programa entero
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- BLOQUE 1: PANEL SUPERIOR (Datos Cliente) ---
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        
        txtCedula = new JTextField(8);
        btnBuscarCliente = new JButton("Buscar");
        txtNombreCliente = new JTextField(15);
        txtNombreCliente.setEditable(false); // Solo lectura simulada
        
        panelCliente.add(new JLabel("Cédula:"));
        panelCliente.add(txtCedula);
        panelCliente.add(btnBuscarCliente);
        panelCliente.add(new JLabel("Nombre:"));
        panelCliente.add(txtNombreCliente);

        // --- BLOQUE 2: PANEL CENTRAL IZQUIERDO (Entrada de Producto) ---
        JPanel panelEntradaProd = new JPanel(new GridLayout(5, 2, 5, 8));
        panelEntradaProd.setBorder(BorderFactory.createTitledBorder("Agregar Artículos"));
        
        txtIdProducto = new JTextField();
        txtNombreProducto = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();
        btnAgregar = new JButton("Agregar al Carrito");

        panelEntradaProd.add(new JLabel("ID Producto:"));
        panelEntradaProd.add(txtIdProducto);
        panelEntradaProd.add(new JLabel("Nombre:"));
        panelEntradaProd.add(txtNombreProducto);
        panelEntradaProd.add(new JLabel("Cantidad:"));
        panelEntradaProd.add(txtCantidad);
        panelEntradaProd.add(new JLabel("Precio Unitario:"));
        panelEntradaProd.add(txtPrecio);
        panelEntradaProd.add(new JLabel("")); // Celda de relleno en el Grid
        panelEntradaProd.add(btnAgregar);

        // --- BLOQUE 3: TABLA DE DETALLES (Carrito de Compras) ---
        String[] columnas = {"Código", "Detalle", "Cantidad", "Precio Unitario", "Subtotal Linea"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblCarrito = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tblCarrito);
        scrollTabla.setPreferredSize(new Dimension(600, 150));

        // Unión de entradas y tabla en un contenedor intermedio
        JPanel panelCentralContenedor = new JPanel(new BorderLayout(5, 5));
        panelCentralContenedor.add(panelEntradaProd, BorderLayout.NORTH);
        panelCentralContenedor.add(scrollTabla, BorderLayout.CENTER);

        // --- BLOQUE 4: PANEL INFERIOR (Totales y Facturación) ---
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

        // --- MONTAJE FINAL EN EL FRAME ---
        add(panelCliente, BorderLayout.NORTH);
        add(panelCentralContenedor, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // --- ASIGNACIÓN DE EVENTOS POR CÓDIGO ---
        btnBuscarCliente.addActionListener(e -> txtNombreCliente.setText("Cliente Genérico S.A."));

        btnAgregar.addActionListener(e -> {
            try {
                String id = txtIdProducto.getText();
                String nombre = txtNombreProducto.getText();
                int cant = Integer.parseInt(txtCantidad.getText());
                double pre = Double.parseDouble(txtPrecio.getText());
                double subLine = cant * pre;

                modeloTabla.addRow(new Object[]{id, nombre, cant, pre, subLine});
                recalcularTotales();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifique los datos numéricos de cantidad o precio.", "Error de conversión", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnProcesar.addActionListener(e -> procesarFactura());
    }

    private void recalcularTotales() {
        double subtotalGeneral = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            subtotalGeneral += (double) modeloTabla.getValueAt(i, 4);
        }
        double ivatasa = subtotalGeneral * 0.13;
        double totalNeto = subtotalGeneral + ivatasa;

        txtSubtotal.setText(String.valueOf(subtotalGeneral));
        txtImpuesto.setText(String.valueOf(ivatasa));
        txtTotal.setText(String.valueOf(totalNeto));
    }

    private void procesarFactura() {
        int num = (int) (Math.random() * 10000 + 1);
        String name = "factura_codigo_" + num + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            writer.write("=== RECIBO GENERADO AUTOMÁTICAMENTE POR CÓDIGO ===");
            writer.newLine();
            writer.write("Factura N°: " + num);
            writer.newLine();
            writer.write("Cliente: " + txtNombreCliente.getText());
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