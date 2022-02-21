package com.example.kalarilab;

import android.content.Context;
import android.content.Intent;
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

public class KalariLabServices {
    private final static String BASE_URL = "http://192.168.31.89:8000/";
    private Context context;
    private Map<String, String> postRequestParam = new HashMap<String, String>();

    public SessionManagement sessionManagement;





    public KalariLabServices(Context context) {
        this.context = context;
    }




    public void signUp(final String email, final String password,final String firstName,final String lastName){

        addPostRequestParamsSignUp(email, password, firstName, lastName);



        JSONObject jsonObj = new JSONObject(postRequestParam);
        String url = BASE_URL+"auth/users/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("ApiDebug",   response.toString());
                            sessionManagement = new SessionManagement(context);
                            createSession( String.valueOf(response.getInt("id")));
                            Intent myIntent = new Intent(context, MainActivity.class);
                            context.startActivity(myIntent);

                        } catch (JSONException e) {
                            Log.d("ApiDebug", "failed to fetch ID");
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ApiDebug", error.toString());

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
                headers.put("Content-Type", "application/json");
                return headers;
            };
        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);

    }

    private void addPostRequestParamsSignUp(String email, String password, String firstName, String lastName) {
        postRequestParam.put("username", firstName);
        postRequestParam.put("password", password);
        postRequestParam.put("email", email);
        postRequestParam.put("first_name", firstName);
        postRequestParam.put("last_name", lastName);

    }

    public void signIn(String email, String password){
        addPostRequestParamsSignIn(email, password);
        String url = BASE_URL+"auth/jwt/create/";
        addPostRequestParamsSignIn(email, password);
        JSONObject jsonObj = new JSONObject(postRequestParam);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        sessionManagement = new SessionManagement(context);
                        Log.d("LoginDebug", response.toString());

                        String refresh = null;
                        String  access = null;
                        try {
                            refresh = response.getString("refresh");
                            access = response.getString("password");

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                            sessionManagement.saveRefreshToken(refresh);
                            sessionManagement.saveAccessToken(access);
                            Log.d("ApiDebug", refresh+" "+ access);

                            createSession("1232");
                            Intent myIntent = new Intent(context, MainActivity.class);
                            context.startActivity(myIntent);
                            Log.d("ApiDebug", response.toString());




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ApiDebug", error.toString());

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
                headers.put("Content-Type", "application/json");
                return headers;
            };


        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);

    }
    private void addPostRequestParamsSignIn(String email, String password ) {
        postRequestParam.put("username", email);
        postRequestParam.put("password", password);

    }

    private void createSession(String ID) {
        sessionManagement.saveSession(ID);

    }



}

