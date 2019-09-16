package mx.com.dgom.hm.wourmeetz_comensal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class RecuperarPasswordActivity extends App2GomActivity {


    private EditText txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);


        txtUserName = findViewById(R.id.txtUserName);
    }



    public void btnRecoverPasswordAction(View v){
        String userName = txtUserName.getText().toString();
        if(userName.isEmpty()){
            showAlert("Debe indicar el correo electrónico",MessageType.ERROR);
            return;
        }

        recoverPassword();
    }


    private void recoverPassword() {
        String userName = txtUserName.getText().toString();

        addCover();

        controller.recoverPassword(this, userName, new MessageResponseInterface() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse responseMessage) {
                removeCover();

                //Valida si hay error o si puede continuar con el negocio
                if (!validateResponse(noInternetError, errorResponse)) {
                    return;
                }

                showAlert(responseMessage.getMessage(), MessageType.SUCCESS);


            }
        });
    }
}
