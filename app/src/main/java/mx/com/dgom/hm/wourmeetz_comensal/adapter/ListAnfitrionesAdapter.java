package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;

public class ListAnfitrionesAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<AnfitrionTO> datos;
    private ArrayList<AnfitrionTO> searchDatos = new ArrayList<>();


    public int getCount() {
        return this.datos.size();
    }

    public AnfitrionTO getItem(int i) {
        return this.datos.get(i);
    }

    public void setDatos(ArrayList<AnfitrionTO> arrayList) {
        this.datos = arrayList;
        this.searchDatos.addAll(arrayList);
        notifyDataSetChanged();
    }

    public ListAnfitrionesAdapter(Context context, ArrayList<AnfitrionTO> arrayList) {
        super(context, R.layout.anfitrion_item, arrayList);
        this.context = context;
        this.datos = arrayList;
        this.searchDatos.addAll(arrayList);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        AnfitrionTO to = (AnfitrionTO) this.datos.get(i);
        view = View.inflate(this.context, R.layout.anfitrion_item, null);

        TextView txtNombreEmpres = (TextView) view.findViewById(R.id.txt_nom_empresa);
        TextView txtDesc = (TextView) view.findViewById(R.id.txt_desc_corta);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_anf);

        txtNombreEmpres.setText(to.getNombre_empresa());
        txtDesc.setText(to.getDescripcion_corta());
        ratingBar.setRating(to.getRating());

        return view;
    }

    /**
     * Filtrado de la lista de clientes
     * @param str
     */
    public void filter(String str) {
        str = str.toLowerCase().trim();
        this.datos.clear();


        if (str.length() == 0) {
            this.datos.addAll(this.searchDatos);
        } else {
            for (int i = 0; i < this.searchDatos.size(); i++) {
                AnfitrionTO anfitrionTO = (AnfitrionTO) this.searchDatos.get(i);

                if(anfitrionTO != null){
                    //Verifica si tiene el nombre
                    if(anfitrionTO.compareNameTo(str) || anfitrionTO.compareDescCortaTo(str) || anfitrionTO.compareDescTo(str)){
                        this.datos.add(anfitrionTO);
                        continue;
                    }

                }
            }
        }
        notifyDataSetChanged();
    }
}

