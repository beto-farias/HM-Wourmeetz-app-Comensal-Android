package mx.com.dgom.hm.wourmeetz_comensal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.R;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.PlatilloTO;

public class ListPlatillosAdapter extends RecyclerView.Adapter<ListPlatillosAdapter.ViewHolder>{

    private static final String TAG = "ListPlatillosRecyclerVA";

    private ArrayList<PlatilloTO> platillos = new ArrayList<PlatilloTO>();
    private Context context;
    private int idMenu;

    private boolean canBeSelected;

    public ListPlatillosAdapter(Context context, ArrayList<PlatilloTO> platillos){
        this.platillos = platillos;
        this.context= context;
        this.idMenu = idMenu;
        this.canBeSelected = canBeSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.platillo_cardview, viewGroup, false);
        return new ViewHolder(view);
    }
    public void setDatos(ArrayList<PlatilloTO> arrayList) {
        this.platillos = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final  int i) {

        String url = AppConstantes.API_URL_IMAGES + "" + platillos.get(i).getUrl();
        Log.d(TAG, url);
        Glide.with(context).asBitmap().load(url).into(viewHolder.imageView);

        viewHolder.name.setText(platillos.get(i).getNombre());
        viewHolder.uuid = platillos.get(i).getUuid();

        viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite, context.getTheme()));
        viewHolder.name.setTextColor(context.getResources().getColor(R.color.colorBackGroundGreen, context.getTheme()));

    }


    @Override
    public int getItemCount() {
        return platillos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        LinearLayout layout;
        String uuid;


        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_platillo);
            name = itemView.findViewById(R.id.nombre_platillo);
            layout = itemView.findViewById(R.id.layout_container);
        }
    }

}
