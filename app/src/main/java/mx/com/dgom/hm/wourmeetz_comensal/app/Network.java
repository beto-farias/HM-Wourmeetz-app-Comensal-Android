package mx.com.dgom.hm.wourmeetz_comensal.app;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mx.com.dgom.hm.wourmeetz_comensal.utils.NetworkResponseInterface;

public class Network {
    private static final String TAG = "NETWORK";
    private String response;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void jsonObjectRequest(String url, JSONObject data, final NetworkResponseInterface netInterface, Context context, final String headerValue) {

        //Log.d(TAG, url);

        final JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                 netInterface.networkResponse( response.toString(), null);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, error.toString());
                 netInterface.networkResponse(null, error.getMessage());
            }
        }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("api-key", "key");
                headers.put("api-secret", "secret");
                headers.put("Content-Type", "application/json");

                if (headerValue != null)
                    headers.put("Authentication-Token", headerValue);
                return headers;
            }

        };

        jsonObjectReq.setRetryPolicy(new DefaultRetryPolicy(0, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(context).addToRequestQueue(jsonObjectReq);
    }

}
