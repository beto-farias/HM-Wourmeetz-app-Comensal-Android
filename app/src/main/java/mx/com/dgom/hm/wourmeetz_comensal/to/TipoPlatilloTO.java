package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class TipoPlatilloTO implements Serializable {
    private String uuid;
    private String token;
    private String nombre;
    private String descripcion;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
