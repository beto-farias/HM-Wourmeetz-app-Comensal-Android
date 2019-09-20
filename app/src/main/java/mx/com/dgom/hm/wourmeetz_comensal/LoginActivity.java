package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.AppVersionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.DataTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class LoginActivity extends App2GomLoginActivity {

    private static final String TAG = "LOGIN_ACTIVITY";
    private TextView txtUser;
    private TextView txtPwd;
    private LinearLayout layout;
    private TextView txtVersionApp;


    //FIREBASE & FB LOGIN

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = (TextView)findViewById(R.id.txtUsername);
        txtPwd = (TextView) findViewById(R.id.txtPwd);
        txtUser.setText("notei@hotmail.com");
        txtPwd.setText("12345678");

        txtVersionApp = findViewById(R.id.txtVersionApp);


        // Initialize Facebook Login button

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btnFacebook);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            loginFacebook(user);
        }


        setupVersionUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupVersionAndroid();
    }

    private void setupVersionUI(){
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;

            txtVersionApp.setText("Versión: " + verCode + " " + AppConstantes.getAPIURLName());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }



    //---------- BOTONES ------------------

    public void login(View view) {

        String userName = txtUser.getText().toString();
        String pass = txtPwd.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            this.txtUser.setError("Indica tu correo");

        } else if (TextUtils.isEmpty(pass)) {
            this.txtPwd.setError("Indica tu contraseña");

        }  else {
            UserTO user = new UserTO();
            user.setPassword(pass);
            user.setCorreo(userName);

            loginUser(user);

        }

    }


    public void crearCuenta(View view){
        Intent intent = new Intent(LoginActivity.this, CrearCuentaActivity.class);
        startActivity(intent);
    }


    public void btnRecuperarPasswordAction(View v){
        Intent intent = new Intent(LoginActivity.this,RecuperarPasswordActivity.class);
        startActivity(intent);
    }


    //------------------ FIREBASE --------------------------

    // LOGIN && FIREBASE
    private void loginFacebook(FirebaseUser user){
        Log.d(TAG,user.toString());

        String email    = user.getEmail();
        String name     = user.getDisplayName();
        String photoUrl = user.getPhotoUrl().toString();
        String uuid     = user.getUid();
        loginUser(name, email, photoUrl, uuid);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this,"" + user.getEmail(), Toast.LENGTH_LONG).show();


                            //LOGIN 2 BACKEND
                            loginFacebook(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }



    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }


    private void updateUI(FirebaseUser user) {
       /* hideProgressDialog();
        if (user != null) {
             mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
             mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.btnFacebook).setVisibility(View.GONE);
            findViewById(R.id.buttonFacebookSignout).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.btnFacebook).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonFacebookSignout).setVisibility(View.GONE);
        }*/
    }


    private void setupVersionAndroid(){

        controller.getVersion(getApplicationContext(), new MessageResponseInterface<AppVersionTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<AppVersionTO> responseMessage) {
                removeCover();

                //Valida si hay error o si puede continuar con el negocio
                if(!validateResponse(noInternetError,errorResponse)){
                    return;
                }

                //Proceso de negocio
                int versionInt = responseMessage.getData().getVersion();

                try{
                    PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                    int verCode = pInfo.versionCode;
                    if(verCode < versionInt){
                        //show update app
                        showUpdateAlert();
                    }

                }catch(Exception ex){

                }

            }
        });
    }


    private void showUpdateAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Nueva versión");
        builder.setMessage("Hay una nueva versión de la aplicación, desea actualizar?");
        builder.setCancelable(false);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
