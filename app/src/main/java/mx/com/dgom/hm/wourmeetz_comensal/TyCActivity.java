package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.lang.reflect.Type;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppController;
import mx.com.dgom.hm.wourmeetz_comensal.to.DataTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.TerminosCondicionesTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class TyCActivity extends App2GomActivity {

    HtmlTextView html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ty_c);

        html = findViewById(R.id.html);

        html.setHtml("<h1>Cargando</h1>");
        setup();
        inicializarMenu();
    }

    public void inicializarMenu(){
        ImageView btnUsuario = findViewById(R.id.btnUsuario);
        btnUsuario.setVisibility(View.INVISIBLE);
        inicializarMenu(getResources().getString(R.string.terminos_condiciones), View.INVISIBLE, null);
    }


    private void setup(){
        addCover();

        controller.obtenerTyC(this, new MessageResponseInterface<TerminosCondicionesTO>() {
                    @Override
                    public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<TerminosCondicionesTO> responseMessage) {
                        removeCover();

                        //Valida si hay error o si puede continuar con el negocio
                        if(!validateResponse(noInternetError,errorResponse)){
                            return;
                        }
                        html.setHtml(responseMessage.getData().getHtml());
                    }
                });


    }


    public void btnBackAction(View v){
        finish();
    }
}
