package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;
import java.util.ArrayList;

public class ListPlatillosTO implements Serializable {
    private PlatilloTO platillo;

    public PlatilloTO getPlatillo() {
        return platillo;
    }

    public void setPlatillo(PlatilloTO platillo) {
        this.platillo = platillo;
    }
}
