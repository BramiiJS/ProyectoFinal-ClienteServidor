package casoestudio2.modelo;

import java.util.ArrayList;

public class Factura {

    private int numeroFactura;
    private Cliente cliente;
    private ArrayList<DetalleFactura> detalles;

    public Factura(int numeroFactura, Cliente cliente) {
        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
    }

    public ArrayList<DetalleFactura> getDetalles() {
        return detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public double calcularSubtotal() {

        double subtotal = 0;

        for (DetalleFactura detalle : detalles) {
            subtotal += detalle.calcularSubtotal();
        }

        return subtotal;
    }

    public double calcularIVA() {
        return calcularSubtotal() * 0.13;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

}