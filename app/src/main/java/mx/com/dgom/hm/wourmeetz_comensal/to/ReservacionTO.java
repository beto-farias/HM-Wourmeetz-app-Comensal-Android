package mx.com.dgom.hm.wourmeetz_comensal.to;

import java.io.Serializable;

public class ReservacionTO implements Serializable {
        private String uuid_comensal;
        private String uuid_anfitrion;
        private String uuid_calendario_menu;
        private int numero_platos;
        private int pago_electronico;
        private double monto_pago;

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

        public String getUuid_calendario_menu() {
        return uuid_calendario_menu;
    }

        public void setUuid_calendario_menu(String uuid_calendario_menu) {
        this.uuid_calendario_menu = uuid_calendario_menu;
    }

        public int getNumero_platos() {
        return numero_platos;
    }

        public void setNumero_platos(int numero_platos) {
        this.numero_platos = numero_platos;
    }

        public int getPago_electronico() {
        return pago_electronico;
    }

        public void setPago_electronico(int pago_electronico) {
        this.pago_electronico = pago_electronico;
    }

        public double getMonto_pago() {
        return monto_pago;
    }

        public void setMonto_pago(double monto_pago) {
        this.monto_pago = monto_pago;
    }


}
