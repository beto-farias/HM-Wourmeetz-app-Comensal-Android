package mx.com.dgom.hm.wourmeetz_comensal;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppController;
import mx.com.dgom.hm.wourmeetz_comensal.to.UserTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;

public class CrearCuentaActivity extends App2GomLoginActivity {
    private static final String TAG = "CREAR_CUENTA_ACTIVITY";
    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtCorreo;
    private TextView txtCorreoConfirm;
    private TextView txtPwd;
    private TextView txtPwdConfirm;
    private TextView txtTelefono;
    private CheckBox cbTerminos;
    private CheckBox cbPrivacidad;

    private String nombre;
    private String apellido;
    private String correo;
    private String correoConfirm;
    private String pwd;
    private String pwdConfirm;
    private String telefono;
    UserTO user = new UserTO();

    private AppController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        txtNombre = (TextView)findViewById(R.id.txt_nombre_usuario);
        txtApellido = (TextView)findViewById(R.id.txt_apellido);
        txtCorreo = (TextView)findViewById(R.id.txt_correo);
        txtCorreoConfirm = (TextView)findViewById(R.id.txt_correo_confirm);
        txtPwd = (TextView)findViewById(R.id.txt_password);
        txtPwdConfirm = (TextView)findViewById(R.id.txt_pwd_confirm);
        cbTerminos = (CheckBox) findViewById(R.id.cb_terminos);
        cbPrivacidad = (CheckBox)findViewById(R.id.cb_privacidad);
        txtTelefono =(TextView)findViewById(R.id.txt_telefono);

        controller = new AppController();
        inicializarMenu();


    }

    public void inicializarMenu(){
        ImageView btnUsuario = findViewById(R.id.btnUsuario);
        btnUsuario.setVisibility(View.INVISIBLE);
        inicializarMenu(getResources().getString(R.string.menu_crear_cuenta), View.INVISIBLE, null);
    }


    //------------ BOTONES

    public void btnVerTerminosAction(View v){
        Intent intent = new Intent(CrearCuentaActivity.this, TyCActivity.class);
        startActivity(intent);
    }

    public void btnVerPrivacidadAction(View v){
        Intent intent = new Intent(CrearCuentaActivity.this, AvisoPrivacidadActivity.class);
        startActivity(intent);
    }

    public void regresarLogin(View view){
        Intent intent = new Intent(CrearCuentaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void registrar(View view){
        if(validar()){

            user.setNombre(nombre);
            user.setCorreo(correo);
            user.setNombre_completo(nombre + " " + apellido);
            user.setCorreo(correo);
            user.setPassword(pwd);
            user.setTelefono(telefono);


            addCover();

            controller.registrar(getApplicationContext(), user, new MessageResponseInterface<UserTO>() {
                @Override
                public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<UserTO> responseMessage) {
                    removeCover();

                    //Valida si hay error o si puede continuar con el negocio
                    if(!validateResponse(noInternetError,errorResponse)){
                        return;
                    }

                    //Proceso de negocio
                    final UserTO to= (UserTO) responseMessage.getData();

                    to.setPassword(user.getPassword().toString());
                    Log.d(TAG, "usuario " + to.toString());

                    removeCover();

                    slideUpDialogNotification(responseMessage.getMessage());

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginUser(to);
                        }
                    }, TIME_OUT);



                }
            });

        }
    }

    private boolean validar() {
        nombre = txtNombre.getText().toString();
        apellido = txtApellido.getText().toString();
        correo = txtCorreo.getText().toString();
        correoConfirm = txtCorreoConfirm.getText().toString();
        pwd = txtPwd.getText().toString();
        pwdConfirm = txtPwdConfirm.getText().toString();
        telefono = txtTelefono.getText().toString();

        boolean isCorreoEmpty=false;
        boolean isPwdEmpty=false;

        boolean isValid=true;

        if (TextUtils.isEmpty(nombre)) {
            this.txtNombre.setError(getResources().getString(R.string.error_nombre));
            isValid=false;

        }

        if (TextUtils.isEmpty(apellido)) {
            this.txtApellido.setError(getResources().getString(R.string.error_apellido));
            isValid=false;

        }

        if (TextUtils.isEmpty(correo)) {
            this.txtCorreo.setError(getResources().getString(R.string.error_correo));
            isValid=false;
            isCorreoEmpty = true;

        }

        if (TextUtils.isEmpty(correoConfirm)) {
            this.txtCorreoConfirm.setError(getResources().getString(R.string.error_correo_confirm));
            isValid=false;
            isCorreoEmpty = true;

        }

        if (TextUtils.isEmpty(pwd)) {
            this.txtPwd.setError(getResources().getString(R.string.error_pwd));
            isValid=false;
            isPwdEmpty = true;

        }

        if (TextUtils.isEmpty(pwdConfirm)) {
            this.txtPwdConfirm.setError(getResources().getString(R.string.error_pwd_confirm));
            isValid=false;
            isPwdEmpty = true;

        }

        if(!isCorreoEmpty && !TextUtils.equals(correo, correoConfirm)){
            this.txtCorreoConfirm.setError(getResources().getString(R.string.error_correo_equals));
            isValid = false;
        }

        if(!isPwdEmpty && !TextUtils.equals(pwd, pwdConfirm)){
            this.txtPwdConfirm.setError(getResources().getString(R.string.error_pwd_equals));
            isValid = false;
        }

        if (TextUtils.isEmpty(telefono)) {
            this.txtTelefono.setError(getResources().getString(R.string.error_telefono));
            isValid=false;

        }

        if(!cbPrivacidad.isChecked()){
            cbPrivacidad.setError("Debe aceptar los términos de privacidad");
            isValid = false;
        }

        if(!cbTerminos.isChecked()){
            cbTerminos.setError("Debe aceptar los términos y condiciones");
            isValid = false;
        }


        return isValid;
    }
}
