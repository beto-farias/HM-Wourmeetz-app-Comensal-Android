package mx.com.dgom.hm.wourmeetz_comensal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import mx.com.dgom.hm.wourmeetz_comensal.app.AppConstantes;
import mx.com.dgom.hm.wourmeetz_comensal.to.AnfitrionTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.CompraTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.MenuCalendarioTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.PagoOpenPayTO;
import mx.com.dgom.hm.wourmeetz_comensal.to.TarjetaTO;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponse;
import mx.com.dgom.hm.wourmeetz_comensal.utils.MessageResponseInterface;
import mx.openpay.android.Openpay;
import mx.openpay.android.OperationCallBack;
import mx.openpay.android.OperationResult;
import mx.openpay.android.exceptions.OpenpayServiceException;
import mx.openpay.android.exceptions.ServiceUnavailableException;
import mx.openpay.android.model.Card;
import mx.openpay.android.model.Token;
import mx.openpay.android.validation.CardValidator;

public class PagoActivity extends App2GomActivity implements OperationCallBack {
    private final Openpay openpay = new Openpay("mawaixgtpk1fihzsgdff", "pk_2689e0a99c474548917585c0f5341881", false);

    private MenuCalendarioTO menuActual;
    private AnfitrionTO anfitrion;

    public Openpay getOpenpay() {
        return this.openpay;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pago);
        Intent intent = getIntent();
        menuActual = (MenuCalendarioTO)intent.getSerializableExtra(AppConstantes.MENU);
        anfitrion = (AnfitrionTO) getIntent().getSerializableExtra(AppConstantes.ANFITRION);

        inicializarMenu();

    }

    public void inicializarMenu(){
        inicializarMenu(getResources().getString(R.string.realizar_pago), View.INVISIBLE, null);
    }

    public void saveCard(final View view) {

        this.addToken();
    }

    public String getDeviceId() {
        Openpay openpay = getOpenpay();
        String deviceIdString = openpay.getDeviceCollectorDefaultImpl().setup(this);
        return deviceIdString;

    }

    public void getDeviceId(View v) {
        showDialog("Device Id", getDeviceId());

    }


    private void addToken() {
        Openpay openpay = getOpenpay();
        Card card = new Card();
        boolean isValid = true;

        final EditText holderNameEt = ((EditText) this.findViewById(R.id.holder_name));
        final String holderName = holderNameEt.getText().toString();
        card.holderName(holderName);

        if (!CardValidator.validateHolderName(holderName)) {
            holderNameEt.setError(this.getString(R.string.invalid_holder_name));
            isValid = false;
        }

        final EditText cardNumberEt = ((EditText) this.findViewById(R.id.card_number));
        final String cardNumber = cardNumberEt.getText().toString();
        card.cardNumber(cardNumber);
        if (!CardValidator.validateNumber(cardNumber)) {
            cardNumberEt.setError(this.getString(R.string.invalid_card_number));
            isValid = false;
        }

        EditText cvv2Et = ((EditText) this.findViewById(R.id.cvv2));
        String cvv = cvv2Et.getText().toString();
        card.cvv2(cvv);
        if (!CardValidator.validateCVV(cvv, cardNumber)) {
            cvv2Et.setError(this.getString(R.string.invalid_cvv));
            isValid = false;
        }

        Integer year = this.getInteger(((Spinner) this.findViewById(R.id.year_spinner)).getSelectedItem().toString());

        Integer month = this.getInteger(((Spinner) this.findViewById(R.id.month_spinner)).getSelectedItem().toString());

        if (!CardValidator.validateExpiryDate(month, year)) {
            showDialog(getResources().getString(R.string.error), getResources().getString(R.string.invalid_expire_date));


            isValid = false;
        }

        card.expirationMonth(month);
        card.expirationYear(year);

        if (isValid) {
            addCover();
            openpay.createToken(card, this);
        }

    }

    @Override
    public void onError(final OpenpayServiceException error) {
        removeCover();
        error.printStackTrace();
        int desc = 0;
        String msg = null;
        switch (error.getErrorCode()) {
            case 3001:
                desc = R.string.declined;
                msg = this.getString(desc);
                break;
            case 3002:
                desc = R.string.expired;
                msg = this.getString(desc);
                break;
            case 3003:
                desc = R.string.insufficient_funds;
                msg = this.getString(desc);
                break;
            case 3004:
                desc = R.string.stolen_card;
                msg = this.getString(desc);
                break;
            case 3005:
                desc = R.string.suspected_fraud;
                msg = this.getString(desc);
                break;

            case 2002:
                desc = R.string.already_exists;
                msg = this.getString(desc);
                break;
            default:
                desc = R.string.error_creating_card;
                msg = error.getDescription();
        }
        showDialog(getResources().getString(R.string.error),msg);

    }

    @Override
    public void onCommunicationError(final ServiceUnavailableException error) {
        removeCover();
        error.printStackTrace();
        showDialog(getResources().getString(R.string.error), getResources().getString(R.string.communication_error));
    }

    @Override
    public void onSuccess(final OperationResult result) {
        Token token = (Token)result.getResult();
        Log.d("TAG", token.getId());
        this.clearData();
        PagoOpenPayTO to = new PagoOpenPayTO();

        to.setNumero_platos(1);
        to.setPago_electronico(1);
        to.setUuid_anfitrion(anfitrion.getUuid());
        to.setUuid_comensal(AppConstantes.USER.getUuid());
        to.setMonto_pago(menuActual.getMonto_venta() * 100);
        to.setUuid_calendario_menu(menuActual.getUuid());
        TarjetaTO tarjeta = new TarjetaTO();
        tarjeta.setToken_id(token.getId());
        tarjeta.setName(((Card)token.getCard()).getHolderName());
        tarjeta.setLast_name(((Card)token.getCard()).getHolderName());
        tarjeta.setPhone_number(AppConstantes.USER.getTelefono());
        tarjeta.setEmail(AppConstantes.USER.getCorreo());
        tarjeta.setDevice_session_id(getDeviceId());
        to.setDatos_tarjeta_op(tarjeta);

        controller.pagarOpenPay(this, to, new MessageResponseInterface<CompraTO>() {
            @Override
            public void response(String noInternetError, MessageResponse errorResponse, MessageResponse<CompraTO> responseMessage) {
                removeCover();
                if(!validateResponse(noInternetError, errorResponse)){
                    return;
                }
                CompraTO compra = responseMessage.getData();
                slideUpDialogNotification(responseMessage.getMessage());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PagoActivity.this, MainActivity.class);
                        intent.putExtra("SHOW_COMPRAS", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                    }
                }, TIME_OUT);

            }
        });

    }

    public void showDialog(String  dialog_title, String  mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialog_title);
        builder.setMessage(mensaje);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void clearData() {
        ((EditText) this.findViewById(R.id.holder_name)).setText("");
        ((EditText) this.findViewById(R.id.card_number)).setText("");
        ((EditText) this.findViewById(R.id.cvv2)).setText("");
        ((Spinner) this.findViewById(R.id.year_spinner)).setSelection(0);
        ((Spinner) this.findViewById(R.id.month_spinner)).setSelection(0);

    }

    private Integer getInteger(final String number) {
        try {
            return Integer.valueOf(number);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public void cancelar(View v){
        finish();
    }

}
