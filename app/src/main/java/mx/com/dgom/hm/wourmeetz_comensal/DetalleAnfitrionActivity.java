package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListMenusCalendariosAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.NonScrollListView;

public class DetalleAnfitrionActivity extends App2GomActivity {
    private TextView txtDescripcion;
    private TextView txtNumAsientos;
    private TextView txtDescCorta;

    private NonScrollListView listMenus;
    private ArrayList<MenuCalendarioTO> menusArrays;
    private ListMenusCalendariosAdapter adapter;

    private AnfitrionTO anfitrion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_anfitrion);

        txtDescripcion = findViewById(R.id.txt_desc_completa);
        txtDescCorta = findViewById(R.id.txt_desc_corta);
        txtNumAsientos = findViewById(R.id.txt_num_asientos);
        listMenus = findViewById(R.id.list_menus);

        Intent i= getIntent();

        anfitrion =(AnfitrionTO) i.getSerializableExtra(AppConstantes.ANFITRION);
        menusArrays = new ArrayList<>();
        adapter = new ListMenusCalendariosAdapter(this, menusArrays);
        listMenus.setAdapter(adapter);

        listMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetalleMenuActivity.class);
                intent.putExtra(AppConstantes.MENU, (MenuCalendarioTO)adapter.getItem(position));
                intent.putExtra(AppConstantes.ANFITRION, anfitrion);
                startActivity(intent);

            }
        });


        inicializarMenu(anfitrion.getNombre_empresa(), View.INVISIBLE , null);
        setupAnfitrion();

    }

    private void setupAnfitrion(){
        txtDescCorta.setText(anfitrion.getDescripcion_corta());
        txtDescripcion.setText(anfitrion.getDescripcion());
        txtNumAsientos.setText("" + anfitrion.getNum_asientos());

        setupMenus();
    }

    private void setupMenus(){
        addCover();

        controller.obtenerMenus(this, anfitrion.getUuid(), new MessageListResponseInterface<MenuCalendarioTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, ListResponse<MenuCalendarioTO> responseMessage) {
                removeCover();
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }

                menusArrays = responseMessage.getResults();
                adapter.setDatos(menusArrays);

            }
        });

    }
}
