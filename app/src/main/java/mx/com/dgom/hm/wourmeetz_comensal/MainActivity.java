package mx.com.dgom.hm.wourmeetz_comensal;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.app.AppController;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.GeocodingLocation;
import mx.com.dgom.hm.wourmeetz_comensal.utils.ListResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageListResponseInterface;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener
{

    private static final String TAG = "MainActivity";
    private GoogleMap gMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;
    int DEFAULT_ZOOM = 16;
    LatLng mDefaultLocation = new LatLng(23.3145044, -111.6363048);

    AppController controller = new AppController();
    ArrayList<AnfitrionTO> anfitrionArray;
    AnfitrionTO anfitrionSeleccionado;

    private EditText txtAddress;
    private TextView txtNombreAnfitrion;
    private TextView txtDireccionAnfitrion;
    private TextView txtDescripcionAnfitrion;
    private ConstraintLayout layoutDetails;

    private Address address;
    private double lat;
    private double lon;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private boolean mLocationPermissionGranted;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtAddress = findViewById(R.id.txtAddress);
        txtNombreAnfitrion = findViewById(R.id.txtNombreAnfitrion);
        txtDireccionAnfitrion = findViewById(R.id.txtDireccionAnfitrion);
        txtDescripcionAnfitrion = findViewById(R.id.txtDescripcionAnfitrion);
        layoutDetails = findViewById(R.id.layoutDetails);
        layoutDetails.setVisibility(View.GONE);


        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.abrir,R.string.cerrar);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.inflateHeaderView(R.layout.menu_main_activity);

        TextView txtNombreComensal = headerView.findViewById(R.id.navNombreComensal);
        ImageView imageViewLogo = headerView.findViewById(R.id.navLogo);

        Glide.with(this).asBitmap().load(R.drawable.logo).into(imageViewLogo);

        txtNombreComensal.setText(AppConstantes.USER.getNombre_completo());
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a FusedLocationProviderClient.

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showMisCompras();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mis_compras:
                showMisCompras();
                return true;
            case R.id.menu_lista:
                showListaAnfitriones();
                return true;
            case R.id.menu_logout:
                logoutApp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //MainActivity.super.onBackPressed();
                        finishAndRemoveTask();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // --------------- BOTONES ------------------------

    public void btnSideBarAction(View v){
        toggleDrawer();
    }


    public void btnSearchAddressAction(View v){
        String searchStr = txtAddress.getText().toString().trim();
        if(searchStr.isEmpty()){
            return;
        }

        searchAddress(searchStr);
    }

    public void btnVerAnfitrionAction(View v){
        Intent intent = new Intent(MainActivity.this, DetalleAnfitrionActivity.class);
        intent.putExtra(AppConstantes.ANFITRION, anfitrionSeleccionado);
        startActivity(intent);
        layoutDetails.setVisibility(View.GONE);
    }


    //------------------ FUNCIONES ----------------------------

    private void logoutApp(){

        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
            LoginManager.getInstance().logOut();
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            FirebaseAuth.getInstance().signOut(); //signout firebase
        }

        finish();
    }

    private void showMisCompras(){
        Intent intent = new Intent(MainActivity.this,MisComprasActivity.class);
        startActivity(intent);
    }

    private void showListaAnfitriones(){
        Intent intent = new Intent(MainActivity.this,ListAnfitrionesActivity.class);
        startActivity(intent);
    }

    private AnfitrionTO getAnfitrionByUuid(String uuid){
        for (AnfitrionTO item: anfitrionArray) {
            if(item.getUuid().equals(uuid)){
                return item;
            }
        }

        return null;
    }

    private void searchAddress(String address){
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeocoderHandler());
    }


    /**
     *
     * @param lat
     * @param lon
     */
    private void centerMap(double lat,double lon ){
        LatLng center = new LatLng(lat, lon);
        //gMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15));

        this.lat = lat;
        this.lon = lon;
    }


    private void setupAnfitrionesMap(){
        for (AnfitrionTO item :anfitrionArray) {
            MarkerOptions options = new MarkerOptions().position(item.getPosicion()).title(item.getNombre_empresa());
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_marker));
            Marker marker = gMap.addMarker(options);
            marker.setTag(item.getUuid());
        }
    }

    private void findNegocios(){
        LatLng fl = gMap.getProjection().getVisibleRegion().farLeft;
        LatLng fr = gMap.getProjection().getVisibleRegion().farRight;

        findNegociosEn(fl,fr);
    }

    //------------------ MAPAS --------------------------

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMinZoomPreference(3.0f);
        gMap.setMaxZoomPreference(18.0f);
        if(mLocationPermissionGranted) {
            gMap.setMyLocationEnabled(true);
        }

        LatLng center = new LatLng(23.3145044, -111.6363048);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(center));

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(layoutDetails.getVisibility() == View.VISIBLE) {
                    txtNombreAnfitrion.setText("");
                    txtDescripcionAnfitrion.setText("");
                    txtDireccionAnfitrion.setText("");
                    layoutDetails.setVisibility(View.GONE);
                }
            }
        });

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String uuid = (String)(marker.getTag());
                //Toast.makeText(MainActivity.this,"Marker " + uuid, Toast.LENGTH_LONG).show();

                AnfitrionTO to = getAnfitrionByUuid(uuid);
                if(to == null){
                    return false;
                }

                anfitrionSeleccionado = to;
                txtNombreAnfitrion.setText(to.getNombre_empresa());
                txtDescripcionAnfitrion.setText(to.getDescripcion());
                txtDireccionAnfitrion.setText(to.getDireccion());

                layoutDetails.setVisibility(View.VISIBLE);

                return false;
            }
        });


        getDeviceLocation();
        findNegocios();
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            gMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    // ------------------- DRAWER ----------------------

    private void toggleDrawer(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }else{
            openDrawer();
        }
    }

    private void openDrawer(){
        drawer.openDrawer(Gravity.LEFT);
    }

    private void closeDrawer(){
        drawer.closeDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.menu_mis_compras:
                closeDrawer();
                showMisCompras();
                return true;
            case R.id.menu_lista:
                closeDrawer();
                showListaAnfitriones();
                return true;
            case R.id.menu_logout:
                closeDrawer();
                logoutApp();
                return true;
        }
        closeDrawer();
        return false;
    }


    //------------------- PERMISOS ----------------
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

            getDeviceLocation();

        } else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //updateLocationUI();
    }

    //-------------------- NETWORK --------------------------------





    private void findNegociosEn(LatLng init, LatLng end){
        controller.obtenerAnfitriones(this, AppConstantes.USER.getUuid(), init, end, new MessageListResponseInterface<AnfitrionTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, ListResponse<AnfitrionTO> responseMessage) {
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }
                anfitrionArray = responseMessage.getResults();
                setupAnfitrionesMap();
            }
        });
    }

    protected boolean validateResponse(String noInternetError, MessageResponse errorResponse){

        if(noInternetError!=null){

            return false;
        }

        if(errorResponse!=null){

            return false;
        }

        return true;
    }


    //----------------------------- CLASES --------------------

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    address = bundle.getParcelable("address");
                    break;
                default:
                    address = null;
            }

            if(address != null){
                centerMap(address.getLatitude(),address.getLongitude());
            }
        }
    }
}
