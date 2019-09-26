package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.to.CompraTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.SolicitudTO;

public class ListComprasAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<SolicitudTO> datos;

    private Map<String, ArrayList<Object>> datosMap;
    private ArrayList<Object> datosOrdenados;

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

    public ListComprasAdapter(Context context, ArrayList<SolicitudTO> arrayList) {
        super(context, R.layout.compra_item, arrayList);
        this.context = context;
        this.datos = arrayList;
        crearMapa();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        Object to =  this.datosOrdenados.get(i);

        if(to instanceof SolicitudTO) {
            SolicitudTO solicitud= (SolicitudTO)to;
            view = View.inflate(this.context, R.layout.compra_item, null);
            TextView txtNombreAnfitrion = (TextView) view.findViewById(R.id.txt_nombre_anfitrion);
            TextView txtNombreMenu = (TextView) view.findViewById(R.id.txt_nombre_menu);
            TextView txtCosto = (TextView) view.findViewById(R.id.txt_costo);
            TextView txtPagoElectronico = (TextView) view.findViewById(R.id.txt_pago_efectivo);

            txtNombreAnfitrion.setText(solicitud.getAnfitrion_nombre());
            txtNombreMenu.setText(solicitud.getMenu_nombre());
            if(solicitud.getPago_electronico()==1) {
                txtPagoElectronico.setText("Pago electrónico");
            }else{
                txtPagoElectronico.setText("Pago en efectivo");
            }
            txtCosto.setText("$ " + solicitud.getMonto_venta());
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



}
