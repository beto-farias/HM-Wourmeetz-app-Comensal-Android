package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class TerminosCondicionesTO implements Serializable {
    private String uuid;
    private String html;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
