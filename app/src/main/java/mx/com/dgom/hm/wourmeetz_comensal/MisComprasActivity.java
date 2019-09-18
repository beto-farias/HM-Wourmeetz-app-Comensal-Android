package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListComprasAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.CalificacionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.SolicitudTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

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


        listCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final SolicitudTO solicitud = (SolicitudTO) adapter.getItem(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MisComprasActivity.this, R.style.Dialog);
                // Get the layout inflater
                LayoutInflater inflater = MisComprasActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                final View v= inflater.inflate(R.layout.califica_anfitrion, null);
                builder.setView(v)
                        // Add action buttons
                        .setPositiveButton(R.string.califica, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                CalificacionTO to = new CalificacionTO();
                                to.setUuid_comensal(AppConstantes.USER.getUuid());
                                //TODO poner uuid del anfitrion / menu.
                                to.setUuid_anfitrion(solicitud.getAnfitrion_nombre());
                                to.setUuid_menu_calendario_comensal("");
                                RatingBar rating = v.findViewById(R.id.califica_anf);
                                TextView comentarios = v.findViewById(R.id.txt_notas_anfitrion);
                                to.setComentarios(comentarios.getText().toString());
                                to.setRating(rating.getRating());

                                addCover();
                                controller.calificar(MisComprasActivity.this, to, new MessageResponseInterface() {
                                    @Override
                                    public void response(String noInternetError, MessageResponse errorResponse, MessageResponse responseMessage) {
                                        removeCover();

                                        if(!validateResponse(noInternetError,errorResponse)){
                                            return;
                                        }

                                        slideUpDialogNotification(responseMessage.getMessage());
                                    }
                                });

                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
               AlertDialog dialog = builder.create();
                dialog.show();
              //  dialog.getWindow().setLayout(900,900);*/
             }
        });
    }
}
