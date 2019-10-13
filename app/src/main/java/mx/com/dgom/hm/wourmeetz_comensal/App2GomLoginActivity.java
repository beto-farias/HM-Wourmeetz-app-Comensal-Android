package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;

import com.firebase.ui.auth.data.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.DataTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class App2GomLoginActivity extends App2GomActivity {

    public void loginUser(UserTO user){
        addCover();
        controller.login(getApplicationContext(), user, new MessageResponseInterface<DataTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<DataTO> responseMessage) {
                removeCover();

                //Valida si hay error o si puede continuar con el negocio
                if(!validateResponse(noInternetError,errorResponse)){
                    return;
                }

                //Proceso de negocio
                DataTO to = (DataTO) responseMessage.getData();

                UserTO user = (UserTO) to.getUsuario();


                AppConstantes.setUser(user, getApplicationContext());
                AppConstantes.setToken(to.getToken(), getApplicationContext());


                Intent intent;

                intent = new Intent(App2GomLoginActivity.this, MainActivity.class);

                startActivity(intent);

                finish();


            }
        });
    }





    public void loginUser(String name, String email, String url, String uuid){
        addCover();
        controller.login(getApplicationContext(), name, email, uuid, url, new MessageResponseInterface<DataTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<DataTO> responseMessage) {
                removeCover();

                //Valida si hay error o si puede continuar con el negocio
                if(!validateResponse(noInternetError,errorResponse)){
                    return;
                }

                //Proceso de negocio
                DataTO to = (DataTO) responseMessage.getData();

                UserTO user = (UserTO) to.getUsuario();


                AppConstantes.setUser(user, getApplicationContext());
                AppConstantes.setToken(to.getToken(), getApplicationContext());


                Intent intent;

                intent = new Intent(App2GomLoginActivity.this, MainActivity.class);

                startActivity(intent);

                finish();


            }
        });
    }

}
