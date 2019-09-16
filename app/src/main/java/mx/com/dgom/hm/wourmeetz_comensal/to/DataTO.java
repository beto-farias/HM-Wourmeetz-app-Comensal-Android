package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class DataTO implements Serializable {
    private String token;
    private UserTO usuario;


    public void setToken(String token) {
        this.token = token;
    }

    public void setUsuario(UserTO usuario) {
        this.usuario = usuario;
    }



    public String getToken() {
        return token;
    }

    public UserTO getUsuario() {
        return usuario;
    }



}
