package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;

public class ListMenusCalendariosAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<MenuCalendarioTO> datos;

    private SortedMap<String, ArrayList<Object>> datosMap;
    private ArrayList<Object> datosOrdenados;

    public int getCount() {
        return this.datosOrdenados.size();
    }

    public Object getItem(int i) {
        return this.datosOrdenados.get(i);
    }

    public void setDatos(ArrayList<MenuCalendarioTO> arrayList) {
        this.datos = arrayList;
        crearMapa();
        notifyDataSetChanged();
    }

    public ListMenusCalendariosAdapter(Context context, ArrayList<MenuCalendarioTO> arrayList) {
        super(context, R.layout.menu_item, arrayList);
        this.context = context;
        this.datos = arrayList;
        crearMapa();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        Object to =  this.datosOrdenados.get(i);

        if(to instanceof MenuCalendarioTO) {
            MenuCalendarioTO menuTO= (MenuCalendarioTO)to;
            view = View.inflate(this.context, R.layout.menu_item, null);
            TextView txtNombreMenu = (TextView) view.findViewById(R.id.txt_nombre_menu);
            TextView txtHorario = (TextView) view.findViewById(R.id.txt_hora);
            TextView txtCosto = (TextView) view.findViewById(R.id.txt_costo);
            TextView txtDesc = (TextView) view.findViewById(R.id.txt_desc);

            txtNombreMenu.setText(menuTO.getMenu().getNombre());
            txtHorario.setText(menuTO.getHora_inicio() + " - " + menuTO.getHora_fin());
            txtDesc.setText(menuTO.getMenu().getDescripcion());
            txtCosto.setText("$ " + menuTO.getMonto_venta());
        }else{
            view = View.inflate(this.context, R.layout.header_item, null);
            TextView txtHeader = (TextView) view.findViewById(R.id.txt_header);
            txtHeader.setText((String)to);
        }

        return view;
    }

    private void crearMapa() {

        this.datosMap = new TreeMap();
        this.datosOrdenados = new ArrayList();

        for (int i = 0; i < this.datos.size(); i++) {
            String horario = ((MenuCalendarioTO) this.datos.get(i)).getTipo_menu();
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