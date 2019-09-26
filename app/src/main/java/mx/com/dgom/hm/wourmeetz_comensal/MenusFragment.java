package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListMenusCalendariosAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.NonScrollListView;


public class MenusFragment extends Fragment {
    private NonScrollListView listMenus;
    private ArrayList<MenuCalendarioTO> menusArrays;
    private ListMenusCalendariosAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menus, container, false);

        listMenus = view.findViewById(R.id.list_menus);

        menusArrays = new ArrayList<>();
        adapter = new ListMenusCalendariosAdapter(getContext(), menusArrays);
        listMenus.setAdapter(adapter);

        listMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetalleMenuActivity.class);
                intent.putExtra(AppConstantes.MENU, (MenuCalendarioTO)adapter.getItem(position));
                intent.putExtra(AppConstantes.ANFITRION, ((DetalleAnfitrionActivity)getActivity()).anfitrion);
                startActivity(intent);

            }
        });

        setupMenus();
        // Inflate the layout for this fragment
        return view;
    }

    private void setupMenus(){
        ((DetalleAnfitrionActivity)getActivity()).addCover();

        ((DetalleAnfitrionActivity)getActivity()).controller.obtenerMenus(getContext(), ((DetalleAnfitrionActivity)getActivity()).anfitrion.getUuid(),false, new MessageListResponseInterface<MenuCalendarioTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, ListResponse<MenuCalendarioTO> responseMessage) {
                ((DetalleAnfitrionActivity)getActivity()).removeCover();
                if(!((DetalleAnfitrionActivity)getActivity()).validateResponse(noInternetError, errorResponse)){
                    return;
                }

                menusArrays = responseMessage.getResults();
                adapter.setDatos(menusArrays);

            }
        });

    }
}
