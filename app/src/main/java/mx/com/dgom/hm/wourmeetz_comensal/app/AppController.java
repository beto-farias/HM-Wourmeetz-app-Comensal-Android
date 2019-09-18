package mx.com.dgom.hm.wourmeetz_comensal.app;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.AppVersionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CalificacionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CompraTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.DataTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.PagoOpenPayTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.ReservacionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.SolicitudTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.TerminosCondicionesTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.NetworkResponseInterface;

public class AppController {

    private static final String TAG="AppController";
    private Gson gson = new Gson();
    Network net = new Network();


    //---------- METODOS DE NEGOCIO ---------------------

    /**
     *
     * @param ctx
     * @param to
     * @param respInterface
     */
    public void login(Context ctx,UserTO to,  final MessageResponseInterface<DataTO> respInterface){
        try{
            JSONObject data = new JSONObject(gson.toJson(to));
            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_LOGIN, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse<DataTO>>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }





    public void login(Context ctx, String nombre, String userName, String fb_uuid, String url, final MessageResponseInterface<DataTO> respInterface){
        try{
            JSONObject data = new JSONObject();
            data.put("nombre_usuario", nombre);
            data.put("user_name", userName);
            data.put("fb_uuid", fb_uuid);
            data.put("txt_url_profile", url);
            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_LOGIN_FIREBASE, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse<DataTO>>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getVersion( Context ctx, final MessageResponseInterface<AppVersionTO> respInterface){
        net.jsonObjectRequest(AppConstantes.API_REST_VERSION_ANDROID, null, new NetworkResponseInterface() {

            @Override
            public void networkResponse(String response, String error) {
                Type stringType = new TypeToken<MessageResponse<AppVersionTO>>(){}.getType();
                processResponse(response,error,respInterface,stringType);

            }
        }, ctx, null);

    }

    public void recoverPassword(Context ctx,String userName, final MessageResponseInterface respInterface){
        try{
            JSONObject data = new JSONObject();
            data.put("user_name", userName);

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_RECOVER_PASSWORD, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
          } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void registrar(Context ctx,UserTO to, final MessageResponseInterface<UserTO> respInterface){
        try{
            JSONObject data = new JSONObject(gson.toJson(to));
            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_CREAR_CUENTA, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse<UserTO>>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void obtenerTyC(Context ctx , final MessageResponseInterface<TerminosCondicionesTO>  respInterface){
        String token = AppConstantes.TOKEN;
        JSONObject data = new JSONObject();

        net.jsonObjectRequest(AppConstantes.API_TYC, data, new NetworkResponseInterface() {

            @Override
            public void networkResponse(String response, String error) {
                Type stringType = new TypeToken<MessageResponse<TerminosCondicionesTO>>(){}.getType();
                processResponse(response,error,respInterface,stringType);

            }
        }, ctx, null);

        }

    public void obtenerAvisoPrivacidad(Context ctx , final MessageResponseInterface<TerminosCondicionesTO>  respInterface){
        String token = AppConstantes.TOKEN;
        JSONObject data = new JSONObject();
        net.jsonObjectRequest(AppConstantes.API_AVISO_PRIVACIDAD, data, new NetworkResponseInterface() {

            @Override
            public void networkResponse(String response, String error) {
                Type stringType = new TypeToken<MessageResponse<TerminosCondicionesTO>>(){}.getType();
                processResponse(response,error,respInterface,stringType);

            }
        }, ctx, null);
    }

    //------LISTADO DE ANFITRIONES----

    public void obtenerAnfitriones(Context ctx , String uuid_comensal, LatLng inicio, LatLng fin, final MessageListResponseInterface<AnfitrionTO> respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject();
            data.put("uuid_comensal",uuid_comensal );
            data.put("lat_inicio", inicio.latitude);
            data.put("lon_inicio",inicio.longitude );
            data.put("lat_fin", fin.latitude);
            data.put("lon_fin",fin.longitude );

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_RECUPERA_ANFITRIONES, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<ListResponse<AnfitrionTO>>(){}.getType();
                    processListResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
        e.printStackTrace();
        }
    }

    //------LISTADO DE DISPONIBILIDAD----

    public void obtenerMenus(Context ctx , String uuid_anfitrion,  final MessageListResponseInterface<MenuCalendarioTO> respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject();
            data.put("uuid_comensal",AppConstantes.USER.getUuid() );
            data.put("uuid_anfitrion", uuid_anfitrion);

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_GET_DISPONIBILIDAD_ANF, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<ListResponse<MenuCalendarioTO>>(){}.getType();
                    processListResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    //---------PAGOS-----------

    public void pagarOpenPay(Context ctx , PagoOpenPayTO to, final MessageResponseInterface<CompraTO> respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject(gson.toJson(to));

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_COMPRA_MENU, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse<CompraTO>>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void reservar(Context ctx , ReservacionTO to, final MessageResponseInterface<CompraTO> respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject(gson.toJson(to));

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_COMPRA_MENU, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse<CompraTO>>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    //------Mi Compras---------------
    public void obtenerCompras(Context ctx ,final MessageListResponseInterface<SolicitudTO> respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject();
            data.put("uuid_comensal",AppConstantes.USER.getUuid() );

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_LISTA_SOLICITUDES, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<ListResponse<SolicitudTO>>(){}.getType();
                    processListResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //---------CALIFICACION ANFITRION

    public void calificar(Context ctx , CalificacionTO to, final MessageResponseInterface respInterface){
        String token = AppConstantes.TOKEN;
        try{
            JSONObject data = new JSONObject(gson.toJson(to));

            net.jsonObjectRequest(AppConstantes.getAPIURL() + "" + AppConstantes.API_CALIFICA_ANFITRION, data, new NetworkResponseInterface() {

                @Override
                public void networkResponse(String response, String error) {
                    Type stringType = new TypeToken<MessageResponse>(){}.getType();
                    processResponse(response,error,respInterface,stringType);

                }
            }, ctx, null);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }


    /// ---------- METODOS DE UTILIDAD ------------------------------------

    /**
     * Método que procesa la respuesta
     * @param response
     * @param error
     * @param respInterface
     * @param stringType
     */
    private void processResponse(String response, String error,MessageResponseInterface respInterface, Type stringType){

        if(error!=null){
            respInterface.response(error, null, null);
            return ;
        }
        Log.d(TAG, response);
        MessageResponse resp = gson.fromJson(response, MessageResponse.class);
        if(resp.getResponseCode()<0){
            respInterface.response(null, resp, null);
            return ;
        }

        respInterface.response(null, null, (MessageResponse) gson.fromJson(response, stringType));

    }

    /**
     * Método que procesa la respuesta
     * @param response
     * @param error
     * @param respInterface
     * @param stringType
     */
    private void processListResponse(String response, String error,MessageListResponseInterface respInterface, Type stringType){
        if(error!=null){
            respInterface.response(error, null, null);
            return ;
        }
        Log.d(TAG, response);
        MessageResponse resp = gson.fromJson(response, MessageResponse.class);
        if(resp.getResponseCode()<0){
            respInterface.response(null, resp, null);
            return ;
        }

        respInterface.response(null, null, (ListResponse) gson.fromJson(response, stringType));

    }

}
