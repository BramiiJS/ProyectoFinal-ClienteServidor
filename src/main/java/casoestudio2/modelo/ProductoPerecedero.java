package casoestudio2.modelo;

public class ProductoPerecedero extends Producto {

    private String fechaVencimiento;
    private String temperaturaRequerida;

    public ProductoPerecedero(String idProducto, String nombreComercial,
            double precioBase, int stockDisponible,
            String fechaVencimiento, String temperaturaRequerida) {

        super(idProducto, nombreComercial, precioBase, stockDisponible);

        this.fechaVencimiento = fechaVencimiento;
        this.temperaturaRequerida = temperaturaRequerida;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getTemperaturaRequerida() {
        return temperaturaRequerida;
    }

    @Override
    public String getTipo() {
        return "Perecedero";
    }

}
