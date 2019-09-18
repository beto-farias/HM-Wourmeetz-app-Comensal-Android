package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.PlatilloTO;

public class ListPlatillosAdapter extends ArrayAdapter {

    private static final String TAG = "ListPlatillosRecyclerVA";

    private ArrayList<PlatilloTO> platillos = new ArrayList<PlatilloTO>();
    private Context context;
    private int idMenu;


    public ListPlatillosAdapter(Context context, ArrayList<PlatilloTO> platillos) {
        super(context, R.layout.platillo_cardview, platillos);
        this.platillos = platillos;
        this.context = context;
        this.idMenu = idMenu;
    }

    public void setDatos(ArrayList<PlatilloTO> arrayList) {
        this.platillos = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = View.inflate(this.context, R.layout.platillo_cardview, null);

        String url = AppConstantes.API_URL_IMAGES + "" + platillos.get(i).getUrl();
        Log.d(TAG, url);
        ImageView imageView = view.findViewById(R.id.image_platillo);
        Glide.with(context).asBitmap().load(url).into(imageView);

        TextView name = view.findViewById(R.id.nombre_platillo);
        TextView desc = view.findViewById(R.id.desc);

        name.setText(platillos.get(i).getNombre());
        desc.setText(platillos.get(i).getDescripcion());


        return  view;
    }

    @Override
    public int getCount() {
        return platillos.size();
    }


}
