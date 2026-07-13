package casoestudio2.modelo;

public abstract class Producto {

    protected String idProducto;
    protected String nombreComercial;
    protected double precioBase;
    protected int stockDisponible;

    public Producto(String idProducto, String nombreComercial, double precioBase, int stockDisponible) {
        this.idProducto = idProducto;
        this.nombreComercial = nombreComercial;
        this.precioBase = precioBase;
        this.stockDisponible = stockDisponible;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void actualizarStock(int cantidad) {
        stockDisponible += cantidad;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return nombreComercial;
    }
}