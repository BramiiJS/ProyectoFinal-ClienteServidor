package casoestudio2.modelo;

import casoestudio2.excepciones.StockInsuficienteException;

public class DetalleFactura {

    private Producto producto;
    private int cantidad;

    public DetalleFactura(Producto producto, int cantidad) throws StockInsuficienteException {

    if (cantidad > producto.getStockDisponible()) {
        throw new StockInsuficienteException("Stock insuficiente para el producto.");
    }

    this.producto = producto;
    this.cantidad = cantidad;
}

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecioBase() * cantidad;
    }
}
