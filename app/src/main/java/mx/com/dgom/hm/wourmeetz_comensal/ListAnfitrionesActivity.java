package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.adapter.ListAnfitrionesAdapter;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;

public class ListAnfitrionesActivity extends App2GomActivity implements AdapterView.OnItemClickListener {

    ListAnfitrionesAdapter adapter;
    private ArrayList<AnfitrionTO> anfitrionArray;
    private ListView listAnfitriones;
    private EditText txtSearch;
    private ImageView btnUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_anfitriones);

        btnUsuario = findViewById(R.id.btnUsuario);


        anfitrionArray = new ArrayList<>();
        listAnfitriones = findViewById(R.id.list_anfitriones);
        txtSearch = findViewById(R.id.search);

        adapter = new ListAnfitrionesAdapter(this, anfitrionArray);
        listAnfitriones.setAdapter(adapter);
        listAnfitriones.setOnItemClickListener(this);


        setUpSearch();
        inicializarMenu();
        setupAnfitriones();
    }


    public void inicializarMenu() {
        inicializarMenu(getResources().getString(R.string.anfitriones), View.INVISIBLE, null);
    }

    private void setUpSearch() {
        this.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(txtSearch.getText().toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.txtSearch.clearFocus();
    }

    public void setupAnfitriones() {
        addCover();
        controller.obtenerAnfitriones(this, new LatLng(0, 0), new LatLng(0, 0), new MessageListResponseInterface<AnfitrionTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, ListResponse<AnfitrionTO> responseMessage) {
                if (!validateResponse(noInternetError, errorResponse)) {
                    return;
                }
                anfitrionArray = responseMessage.getResults();
                adapter.setDatos(anfitrionArray);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AnfitrionTO to = adapter.getItem(position);
        Intent intent = new Intent(this, DetalleAnfitrionActivity.class);
        intent.putExtra(AppConstantes.ANFITRION, to);
        startActivity(intent);
    }

}
