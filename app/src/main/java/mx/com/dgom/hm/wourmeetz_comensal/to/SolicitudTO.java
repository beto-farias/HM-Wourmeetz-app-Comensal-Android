package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class SolicitudTO implements Serializable {
    private double monto_venta;
    private String fecha_venta;
    private String menu_nombre;
    private String anfitrion_nombre;
    private int pago_electronico;
    private String fecha;
    private String uuid_relacion_menu;
    private String uuid_anfitrion;

    public String getUuid_relacion_menu() {
        return uuid_relacion_menu;
    }

    public void setUuid_relacion_menu(String uuid_relacion_menu) {
        this.uuid_relacion_menu = uuid_relacion_menu;
    }

    public String getUuid_anfitrion() {
        return uuid_anfitrion;
    }

    public void setUuid_anfitrion(String uuid_anfitrion) {
        this.uuid_anfitrion = uuid_anfitrion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto_venta() {
        return monto_venta/100;
    }

    public void setMonto_venta(double monto_venta) {
        this.monto_venta = monto_venta;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getMenu_nombre() {
        return menu_nombre;
    }

    public void setMenu_nombre(String menu_nombre) {
        this.menu_nombre = menu_nombre;
    }

    public String getAnfitrion_nombre() {
        return anfitrion_nombre;
    }

    public void setAnfitrion_nombre(String anfitrion_nombre) {
        this.anfitrion_nombre = anfitrion_nombre;
    }

    public int getPago_electronico() {
        return pago_electronico;
    }

    public void setPago_electronico(int pago_electronico) {
        this.pago_electronico = pago_electronico;
    }
}
