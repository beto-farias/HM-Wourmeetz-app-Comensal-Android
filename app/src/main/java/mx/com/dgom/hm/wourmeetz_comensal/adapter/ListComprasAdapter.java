package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import mx.com.dgom.hm.wourmeetz_comensal.App2GomActivity;
import mx.com.dgom.hm.wourmeetz_comensal.MisComprasActivity;
import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.CalificacionAsignadaTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CalificacionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CompraTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.SolicitudTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ComprasInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class ListComprasAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<SolicitudTO> datos;

    private Map<String, ArrayList<Object>> datosMap;
    private ArrayList<Object> datosOrdenados;
    private ComprasInterface misComprasInterface;

    public int getCount() {
        return this.datosOrdenados.size();
    }

    public Object getItem(int i) {
        return this.datosOrdenados.get(i);
    }

    public void setDatos(ArrayList<SolicitudTO> arrayList) {
        this.datos = arrayList;
        crearMapa();
        notifyDataSetChanged();
    }

    public ListComprasAdapter(ComprasInterface misComprasInterface, Context context, ArrayList<SolicitudTO> arrayList) {
        super(context, R.layout.compra_item, arrayList);
        this.context = context;
        this.datos = arrayList;
        this.misComprasInterface = misComprasInterface;
        crearMapa();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        Object to =  this.datosOrdenados.get(i);

        if(to instanceof SolicitudTO) {
            final SolicitudTO solicitud= (SolicitudTO)to;
            view = View.inflate(this.context, R.layout.compra_item, null);
            TextView txtNombreAnfitrion = (TextView) view.findViewById(R.id.txt_nombre_anfitrion);
            TextView txtNombreMenu = (TextView) view.findViewById(R.id.txt_nombre_menu);
            TextView txtCosto = (TextView) view.findViewById(R.id.txt_costo);
            TextView txtPagoElectronico = (TextView) view.findViewById(R.id.txt_pago_efectivo);
            Button btnCalificar = (Button)view.findViewById(R.id.btn_calificar);
            RatingBar rating = (RatingBar)view.findViewById(R.id.calificacion);

            if(solicitud.getCalificado()==1){
                rating.setRating((float) solicitud.getCalificacion());
                rating.setVisibility(View.VISIBLE);
                btnCalificar.setVisibility(View.GONE);
            }else{
                btnCalificar.setVisibility(View.VISIBLE);
                rating.setVisibility(View.GONE);
            }

            txtNombreAnfitrion.setText(solicitud.getAnfitrion_nombre());
            txtNombreMenu.setText(solicitud.getMenu_nombre());
            if(solicitud.getPago_electronico()==1) {
                txtPagoElectronico.setText("Pago electrónico");
            }else{
                txtPagoElectronico.setText("Pago en efectivo");
            }
            txtCosto.setText("$ " + solicitud.getMonto_venta());

            btnCalificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    misComprasInterface.openDialog(solicitud);
                }
            }
            );
        }else{
            view = View.inflate(this.context, R.layout.header_item, null);
            TextView txtHeader = (TextView) view.findViewById(R.id.txt_header);
            txtHeader.setText((String)to);
        }

        return view;
    }

    private void crearMapa() {

        this.datosMap = new LinkedHashMap<>();
        this.datosOrdenados = new ArrayList();

        for (int i = 0; i < this.datos.size(); i++) {
            String horario = ((SolicitudTO) this.datos.get(i)).getFecha();
            if (this.datosMap.containsKey(horario)) {
                ((ArrayList) this.datosMap.get(horario)).add(this.datos.get(i));
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.datos.get(i));
                this.datosMap.put(horario, arrayList);
            }
        }


        for (String horario : datosMap.keySet()) {
            this.datosOrdenados.add(horario);
            this.datosOrdenados.addAll((ArrayList) datosMap.get(horario));
        }
    }


    public void actualizarCalificacion(CalificacionAsignadaTO to){
        for(int i=0; i<datosOrdenados.size();i++){
            if(datosOrdenados.get(i) instanceof SolicitudTO){
                SolicitudTO solicitud= (SolicitudTO)datosOrdenados.get(i);

                if(solicitud.getUuid_relacion_menu().equals(to.getUuid())){
                    solicitud.setCalificado(1);
                    solicitud.setCalificacion(to.getRaiting_anfitrion());
                    return;
                }
            }
        }

        notifyDataSetChanged();
    }



}
