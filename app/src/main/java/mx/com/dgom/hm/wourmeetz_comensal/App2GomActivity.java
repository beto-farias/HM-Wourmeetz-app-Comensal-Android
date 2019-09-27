package mx.com.dgom.hm.wourmeetz_comensal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppController;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;

public class App2GomActivity extends AppCompatActivity {

    private static final String TAG = "App2GomActivity";

    private static final int XInitial = 0;
    private static final int XFinal = 0;
    private static final int YFinal = 0;
    private static final int duration = 700;
    public final long TIME_OUT =2000;

    public AppController controller;
    public UserTO user;
    public String token;
    private LinearLayout contenedor;

    private int coversCount = 0;
    private ProgressDialog pd;

    public void addCover() {
        this.coversCount++;
        if (this.pd == null) {
            this.pd = new ProgressDialog(this);
            this.pd.setMessage(getString(R.string.espere_un_momento));
            this.pd.setCancelable(false);
        }
        if (!this.pd.isShowing()) {
            this.pd.show();
        }
    }

    public void removeCover() {
        this.coversCount--;
        Log.d(TAG,"Remove cover " + coversCount);
        if (this.coversCount < 0) {
            this.coversCount = 0;
        }
        if (this.pd != null && this.coversCount == 0) {
            this.pd.dismiss();
        }
    }

    protected boolean isWorking() {
        return this.coversCount > 0;
    }

    protected App2GomActivity() {
        controller = new AppController();

    }

    public AppController getController() {
        return controller;
    }

    public void setController(AppController controller) {
        this.controller = controller;
    }


    public LinearLayout getLayout() {

        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    private LinearLayout layout;


    enum MessageType{
        SUCCESS,WARNING,ERROR
    }

    public void showAlert(String msg,MessageType type){
        slideUpDialogNotification( msg);
    }

    public void slideUpDialogNotification(String msg){

        contenedor = new LinearLayout(getApplicationContext());

        contenedor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        contenedor.setOrientation(LinearLayout.HORIZONTAL);
        contenedor.setGravity(Gravity.BOTTOM);
        contenedor.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPurple, getTheme()));

        TextView txt = new TextView(getApplicationContext());
        //Agrega propiedades al TextView.
        txt.setText(msg);
        txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(20);
        txt.setTextColor(getApplicationContext().getResources().getColor(R.color.colorWhite, getTheme()));
        txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        contenedor.addView(txt);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)getApplicationContext().getResources().getDimension(R.dimen.notification_height), Gravity.BOTTOM);
    addContentView(contenedor, params);

        TranslateAnimation animate = new TranslateAnimation(
                XInitial,
                XFinal,
                contenedor.getHeight(),
                YFinal);
        animate.setDuration(duration);
        //animate.setFillAfter(true);
        contenedor.startAnimation(animate);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slideDownDialogNotification(contenedor);
            }
        }, TIME_OUT);


    }

    public void slideDownDialogNotification(LinearLayout contenedor){

        TranslateAnimation animate = new TranslateAnimation(
                XInitial,
                XFinal,
                YFinal,
                contenedor.getHeight());
        animate.setDuration(duration);
        contenedor.startAnimation(animate);

        ((ViewGroup) contenedor.getParent()).removeView(contenedor);

    }

    public void closeSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void inicializarMenu(String title,int  visibility,  final Class<?> next){
        ImageButton btn =(ImageButton)findViewById(R.id.btn_menu_agregar);
        btn.setVisibility(visibility);
        if(next != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(App2GomActivity.this, next);
                    startActivity(intent);
                }
            });
        }

        TextView txtTitle =(TextView) findViewById(R.id.txt_title_menu);
        txtTitle.setText(title);

    }

    public void notInternetErrorManager(String msg){
        removeCover();
        //TODO
    }



    protected boolean validateResponse(String noInternetError, MessageResponse errorResponse){
        removeCover();
        if(noInternetError!=null){
            slideUpDialogNotification(noInternetError);
            return false;
        }

        if(errorResponse!=null){
            slideUpDialogNotification(errorResponse.getMessage());
            return false;
        }

        return true;
    }

}
