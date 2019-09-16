package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class PlatilloTO implements Serializable {
    private String uuid;
    private String nombre;
    private String descripcion;
    private String url;
    private TipoPlatilloTO tipo_platillo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TipoPlatilloTO getTipo_platillo() {
        return tipo_platillo;
    }

    public void setTipo_platillo(TipoPlatilloTO tipo_platillo) {
        this.tipo_platillo = tipo_platillo;
    }
}
