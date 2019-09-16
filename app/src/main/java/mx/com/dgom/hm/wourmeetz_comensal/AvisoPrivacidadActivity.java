package mx.com.dgom.hm.wourmeetz_comensal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.lang.reflect.Type;

import mx.com.dgom.hm.wourmeetz_comensal.to.TerminosCondicionesTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class AvisoPrivacidadActivity extends App2GomActivity {

    HtmlTextView htmlAviso;
    ImageView btnUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_privacidad);



        htmlAviso = findViewById(R.id.htmlAviso);
        setup();
        inicializarMenu();
    }

    public void inicializarMenu(){
        btnUsuario = findViewById(R.id.btnUsuario);
        btnUsuario.setVisibility(View.INVISIBLE);
        inicializarMenu(getResources().getString(R.string.aviso_privacidad), View.INVISIBLE, null);
    }


    private void setup(){
        addCover();

        controller.obtenerAvisoPrivacidad(this, new MessageResponseInterface<TerminosCondicionesTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<TerminosCondicionesTO> responseMessage) {
                removeCover();
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }
                htmlAviso.setHtml(responseMessage.getData().getHtml());
            }
        });

    }


    public void btnBackAction(View v){
        finish();
    }
}
