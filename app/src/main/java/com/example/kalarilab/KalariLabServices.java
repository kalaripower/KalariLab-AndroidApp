package com.example.kalarilab;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class KalariLabServices {
    private final static String BASE_URL = "http://192.168.0.109:8000/";
    private Context context;
    private Map<String, String> postParam= new HashMap<String, String>();
    private UserInfoRegisterSync userInfoRegisterSync;
    public SessionManagement sessionManagement = new SessionManagement(context);





    public KalariLabServices(Context context, UserInfoRegisterSync userInfoRegisterSync) {
        this.context = context;
        this.userInfoRegisterSync = userInfoRegisterSync;
    }




    public void signUp(String email, String password, String firstName, String lastName){

        addParams(email, password, firstName, lastName);
        JSONObject jsonObj = new JSONObject(postParam);
        AtomicBoolean success = new AtomicBoolean(false);
        String url = BASE_URL+"auth/users/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ApiDebug", String.valueOf(response.getInt("id")));
                            userInfoRegisterSync.setUserID(response.getInt("id"));
                            createSession( String.valueOf(response.getInt("id")));
                            userInfoRegisterSync.changed();
                            synchronized (userInfoRegisterSync) {
                                userInfoRegisterSync.notifyAll();

                            }
                        } catch (JSONException e) {
                            Log.d("ApiDebug", "failed to fetch ID");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Auth failure...you son of a bitch!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                error.printStackTrace();
                Log.d("ApiDebug", message);
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", "nkdsfnk");
                headers.put("password", password);
                headers.put("email", email);
                headers.put("first_name", firstName);
                headers.put("last_name", lastName);

                return headers;
            };
        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);
        Log.d("ApiDebug", success.toString());

    }

    private void addParams(String email, String password, String firstName, String lastName) {
        postParam.put("username", firstName);
        postParam.put("password", password);
        postParam.put("email", email);
        postParam.put("first_name", firstName);
        postParam.put("last_name", lastName);

    }

    private void createSession(String ID) {
        sessionManagement.saveSession(ID);

    }


}

