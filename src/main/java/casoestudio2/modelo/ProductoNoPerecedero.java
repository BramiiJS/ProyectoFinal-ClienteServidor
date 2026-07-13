package casoestudio2.modelo;

public class ProductoNoPerecedero extends Producto {

    private String materialComposicion;
    private String seccionAlmacenamiento;

    public ProductoNoPerecedero(String idProducto,
            String nombreComercial,
            double precioBase,
            int stockDisponible,
            String materialComposicion,
            String seccionAlmacenamiento) {

        super(idProducto, nombreComercial, precioBase, stockDisponible);

        this.materialComposicion = materialComposicion;
        this.seccionAlmacenamiento = seccionAlmacenamiento;
    }

    public String getMaterialComposicion() {
        return materialComposicion;
    }

    public String getSeccionAlmacenamiento() {
        return seccionAlmacenamiento;
    }

    @Override
    public String getTipo() {
        return "No Perecedero";
    }

}