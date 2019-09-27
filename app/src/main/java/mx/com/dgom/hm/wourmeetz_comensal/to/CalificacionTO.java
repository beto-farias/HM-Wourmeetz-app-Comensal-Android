package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class CalificacionTO implements Serializable {
    private String uuid_comensal;
    private String uuid_anfitrion;
    private String uuid_menu_calendario_comensal;
    private double rating;
    private String comentarios;


    public String getUuid_comensal() {
        return uuid_comensal;
    }

    public void setUuid_comensal(String uuid_comensal) {
        this.uuid_comensal = uuid_comensal;
    }

    public String getUuid_anfitrion() {
        return uuid_anfitrion;
    }

    public void setUuid_anfitrion(String uuid_anfitrion) {
        this.uuid_anfitrion = uuid_anfitrion;
    }

    public String getUuid_menu_calendario_comensal() {
        return uuid_menu_calendario_comensal;
    }

    public void setUuid_menu_calendario_comensal(String uuid_menu_calendario_comensal) {
        this.uuid_menu_calendario_comensal = uuid_menu_calendario_comensal;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
