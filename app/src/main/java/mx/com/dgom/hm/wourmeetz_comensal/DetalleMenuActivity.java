package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListPlatillosAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CompraTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.ListPlatillosTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.PlatilloTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.ReservacionTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class DetalleMenuActivity extends App2GomActivity {

    DecimalFormat df = new DecimalFormat("#,##0.00");

    private TextView txtDescripcion;
    private TextView txtHorario;
    private TextView txtHorarioFin;
    private TextView txtPrecio;
    private Button btnReservar;
    private ListView recyclerView;
    private MenuCalendarioTO to;
    private AnfitrionTO anfitrion;
    private ListPlatillosAdapter adapter;
    private ArrayList<PlatilloTO> platillosArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_menu);

        to = (MenuCalendarioTO) getIntent().getSerializableExtra(AppConstantes.MENU);
        anfitrion = (AnfitrionTO) getIntent().getSerializableExtra(AppConstantes.ANFITRION);
        txtDescripcion = findViewById(R.id.txt_desc);
        txtHorario = findViewById(R.id.txt_hora);
        txtHorarioFin = findViewById(R.id.txt_hora_fin);
        txtPrecio = findViewById(R.id.txt_precio);
        recyclerView = findViewById(R.id.recyclerView);
        btnReservar = findViewById(R.id.btn_reservar);

        if(to.getAcepta_efectivo()==1) {
            btnReservar.setVisibility(View.VISIBLE);
        }else{
            btnReservar.setVisibility(View.INVISIBLE);
        }

        setupMenu();
        inicializarMenu(to.getMenu().getNombre());
    }

    public void inicializarMenu(String nombre){
        inicializarMenu(nombre, View.INVISIBLE, null);
    }

    private void setupMenu(){
        txtDescripcion.setText(to.getMenu().getDescripcion());
        txtHorario.setText(to.getHora_inicio());
        txtPrecio.setText("$ " + df.format( to.getMonto_venta()));
        txtHorarioFin.setText(to.getHora_fin());
        ArrayList<ListPlatillosTO> array = to.getMenu().getPlatillos();

        for(int i = 0; i<array.size();i++){
            platillosArray.add(array.get(i).getPlatillo());
        }

        adapter = new ListPlatillosAdapter(this, platillosArray);
        recyclerView.setAdapter(adapter);

    }

    public void comprar(View view){
        Intent intent = new Intent(this, PagoActivity.class);
        intent.putExtra(AppConstantes.MENU, to);
        intent.putExtra(AppConstantes.ANFITRION, anfitrion);
        startActivity(intent);
        finish();
    }

    public void reservar(View view){
        ReservacionTO reservacion = new ReservacionTO();
        reservacion.setMonto_pago(to.getMonto_venta()*100);
        reservacion.setNumero_platos(1);
        reservacion.setPago_electronico(0);
        reservacion.setUuid_anfitrion(anfitrion.getUuid());
        reservacion.setUuid_calendario_menu(to.getUuid());
        reservacion.setUuid_comensal(AppConstantes.USER.getUuid());

        addCover();
        controller.reservar(this, reservacion, new MessageResponseInterface<CompraTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<CompraTO> responseMessage) {
                removeCover();
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }
                CompraTO compra = responseMessage.getData();
                slideUpDialogNotification(responseMessage.getMessage());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, TIME_OUT);

            }
        });
    }


}
