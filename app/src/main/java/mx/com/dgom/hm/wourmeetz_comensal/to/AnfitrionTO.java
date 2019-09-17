package mx.com.dgom.hm.wourmeetz_comensal.to;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class AnfitrionTO implements Serializable {
    private String uuid;
    private String nombre_empresa;
    private String descripcion_corta;
    private String descripcion;
    private int num_asientos;
    private int validado;
    private String fecha_validacion;
    private int habilitado;
    private int rating;
    private String direccion;
    private String lat;
    private String lon;



    public LatLng getPosicion(){
        return new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }




    public boolean compareNameTo(String str){

        String nombre = getNombre_empresa().toLowerCase().trim();

        return nombre.contains(str.toLowerCase().trim());
    }

    public boolean compareDescTo(String str){

        String desc = getDescripcion().toLowerCase().trim();

        return desc.contains(str.toLowerCase().trim());
    }

    public boolean compareDescCortaTo(String str){

        String desc = getDescripcion_corta().toLowerCase().trim();

        return desc.contains(str.toLowerCase().trim());
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNum_asientos() {
        return num_asientos;
    }

    public void setNum_asientos(int num_asientos) {
        this.num_asientos = num_asientos;
    }

    public int getValidado() {
        return validado;
    }

    public void setValidado(int validado) {
        this.validado = validado;
    }

    public String getFecha_validacion() {
        return fecha_validacion;
    }

    public void setFecha_validacion(String fecha_validacion) {
        this.fecha_validacion = fecha_validacion;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
