package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;

public class SplashActivity extends AppCompatActivity {
    private final long SPLASH_TIME_OUT =1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Iniciamos el delay para el splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserTO to = AppConstantes.getUser(getApplicationContext());

                if(to != null){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
