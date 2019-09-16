package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class UserTO implements Serializable {
    private int id_comensal;
    private String uuid;
    private String nombre_completo;
    private String url_avatar;
    private int rating;
    private String nombre;
    private String correo;
    private String password;
    private String telefono;


    public int getId_comensal() {
        return id_comensal;
    }

    public void setId_comensal(int id_comensal) {
        this.id_comensal = id_comensal;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
