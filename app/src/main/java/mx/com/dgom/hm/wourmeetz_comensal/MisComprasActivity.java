package mx.com.dgom.hm.wourmeetz_comensal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListComprasAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.to.SolicitudTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;

public class MisComprasActivity extends App2GomActivity {
    private ListComprasAdapter adapter;
    private ListView listCompras;
    private ArrayList<SolicitudTO> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras);

        listCompras = findViewById(R.id.list_compras);

        adapter = new ListComprasAdapter(this, array);
        listCompras.setAdapter(adapter);

        setupDatosCompras();
        inicializarMenu();
    }

    public void inicializarMenu(){
        inicializarMenu(getResources().getString(R.string.compras), View.INVISIBLE, null);
    }

    private void setupDatosCompras(){
        addCover();
        controller.obtenerCompras(this, new MessageListResponseInterface<SolicitudTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, ListResponse<SolicitudTO> responseMessage) {
                removeCover();
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }

                array = responseMessage.getResults();
                adapter.setDatos(array);
            }
        });
    }
}
