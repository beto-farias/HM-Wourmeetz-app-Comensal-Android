package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuTO implements Serializable {
    private String uuid;
    private String nombre;
    private String descripcion;
    private String fecha_creacion;
    private ArrayList<ListPlatillosTO> platillos;

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

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public ArrayList<ListPlatillosTO> getPlatillos() {
        return platillos;
    }

    public void setPlatillos(ArrayList<ListPlatillosTO> platillos) {
        this.platillos = platillos;
    }
}
