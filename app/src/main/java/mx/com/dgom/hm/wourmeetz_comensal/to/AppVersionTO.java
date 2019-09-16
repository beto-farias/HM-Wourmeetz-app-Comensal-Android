package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class AppVersionTO implements Serializable {

    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
