package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class CompraTO implements Serializable {
    private String uuid;
    private int platos_solicitados;
     private int pago_electronico;
    private String asistio;
    private String rating_comensal;
    private String raiting_anfitrion;
    private double monto_venta;
    private String comentario_anfitrion;
    private String comentario_comensal;
    private String menu_pagado;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPlatos_solicitados() {
        return platos_solicitados;
    }

    public void setPlatos_solicitados(int platos_solicitados) {
        this.platos_solicitados = platos_solicitados;
    }

    public int getPago_electronico() {
        return pago_electronico;
    }

    public void setPago_electronico(int pago_electronico) {
        this.pago_electronico = pago_electronico;
    }

    public String getAsistio() {
        return asistio;
    }

    public void setAsistio(String asistio) {
        this.asistio = asistio;
    }

    public String getRating_comensal() {
        return rating_comensal;
    }

    public void setRating_comensal(String rating_comensal) {
        this.rating_comensal = rating_comensal;
    }

    public String getRaiting_anfitrion() {
        return raiting_anfitrion;
    }

    public void setRaiting_anfitrion(String raiting_anfitrion) {
        this.raiting_anfitrion = raiting_anfitrion;
    }

    public double getMonto_venta() {
        return monto_venta;
    }

    public void setMonto_venta(double monto_venta) {
        this.monto_venta = monto_venta;
    }

    public String getComentario_anfitrion() {
        return comentario_anfitrion;
    }

    public void setComentario_anfitrion(String comentario_anfitrion) {
        this.comentario_anfitrion = comentario_anfitrion;
    }

    public String getComentario_comensal() {
        return comentario_comensal;
    }

    public void setComentario_comensal(String comentario_comensal) {
        this.comentario_comensal = comentario_comensal;
    }

    public String getMenu_pagado() {
        return menu_pagado;
    }

    public void setMenu_pagado(String menu_pagado) {
        this.menu_pagado = menu_pagado;
    }
}
