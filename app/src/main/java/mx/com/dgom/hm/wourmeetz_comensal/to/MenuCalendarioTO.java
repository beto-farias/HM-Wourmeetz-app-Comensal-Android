package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class MenuCalendarioTO implements Serializable {
    private String uuid;
    private double monto_venta;
    private int platos_disponibles;
    private String fecha;
    private int acepta_efectivo;
    private String hora_inicio;
    private String hora_fin;
    private String tipo_menu;
    private MenuTO menu;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getMonto_venta() {
        return monto_venta/100;
    }

    public void setMonto_venta(double monto_venta) {
        this.monto_venta = monto_venta;
    }

    public int getPlatos_disponibles() {
        return platos_disponibles;
    }

    public void setPlatos_disponibles(int platos_disponibles) {
        this.platos_disponibles = platos_disponibles;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getAcepta_efectivo() {
        return acepta_efectivo;
    }

    public void setAcepta_efectivo(int acepta_efectivo) {
        this.acepta_efectivo = acepta_efectivo;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getTipo_menu() {
        return tipo_menu;
    }

    public void setTipo_menu(String tipo_menu) {
        this.tipo_menu = tipo_menu;
    }

    public MenuTO getMenu() {
        return menu;
    }

    public void setMenu(MenuTO menu) {
        this.menu = menu;
    }
}
