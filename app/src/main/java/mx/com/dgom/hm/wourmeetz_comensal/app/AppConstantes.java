package mx.com.dgom.hm.wourmeetz_comensal.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;

public class AppConstantes {

    //Guardamos al usuario actual
    public static UserTO USER;
    public static String TOKEN;

    //Servicio de google maps
    //private static final String GOOGLE_API_KEY = "AIzaSyDkSJ6n7fvVF4-GiHs2vjP6VOiepGPBi2k";

    //URL de Servicios
   private static final String API_REST_URL_LOCAL          = "http://192.168.0.11:8080/2019/hungryMom/hungryMom/web/service-comensal/";
    private static final String API_REST_URL_DEV            = "http://dev.2geeksonemonkey.com/hungryMom/hungryMom/web/service-comensal/";
    public static final String API_URL_IMAGES               = "http://dev.2geeksonemonkey.com/hungryMom/hungryMom/web/";
     private static final String API_REST_URL_QA            = "";

    //Nombre Servicios
    public static final String API_REST_VERSION_ANDROID     = "http://dev.2geeksonemonkey.com/hungryMom/hungryMom/web/service/version-android";
    public static final String API_LOGIN                    = "login";
    public static final String API_LOGIN_FIREBASE           = "login-firebase";
    public static final String API_RECOVER_PASSWORD         = "recover-password";
    public static final String API_CREAR_CUENTA             = "crear-cuenta";
    public static final String API_TYC                      = "http://dev.2geeksonemonkey.com/hungryMom/hungryMom/web/service/get-tyc";
    public static final String API_AVISO_PRIVACIDAD         = "http://dev.2geeksonemonkey.com/hungryMom/hungryMom/web/service/get-aviso";
    public static final String API_RECUPERA_ANFITRIONES     = "recupera-anfitriones";
    public static final String API_GET_DISPONIBILIDAD_ANF   = "get-disponibilidad-anfitrion";
    public static final String API_COMPRA_MENU              = "compra-menu";
    public static final String API_LISTA_SOLICITUDES        = "lista-solicitudes";



    public static final String ANFITRION = "ANFITRION";
    public static final String MENU = "MENU_ACTUAL";

    //Version que utiliza QA o DEV
    public static final api ambiente = api.DEV;


    public static boolean getProperty(String name, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean res = preferences.getBoolean(name, false);
        return res;
    }

    public static void setPreference(String name, boolean val, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, val);
        editor.commit();
    }

    public static void setPreference(String name, String val, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, val);
        editor.commit();
    }

    public static void setPreference(String name, int val, Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, val);
        editor.commit();
    }


    //--------------

    public enum api {
        LOCAL,
        QA,
        DEV,
    }

    public static String getAPIURL() {
        switch (ambiente) {
            case LOCAL:
                return API_REST_URL_LOCAL;
            case QA:
                return API_REST_URL_QA;
            case DEV:
                return API_REST_URL_DEV;
            default:
                return API_REST_URL_DEV;
        }
    }

    public static String getAPIURLName() {
        switch (ambiente) {
            case LOCAL:
                return "local";
            case QA:
                return "qa";
            case DEV:
                return "dev";
            default:
                return "dev";
        }
    }
}
