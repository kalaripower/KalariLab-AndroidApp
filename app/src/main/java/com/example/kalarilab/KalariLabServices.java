package com.example.kalarilab;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

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
    private final static String BASE_URL = "http://192.168.0.116:8000/";
    private Activity context;
    private Map<String, String> postRequestParams = new HashMap<String, String>();
    private Map<String, String> putRequestParams = new HashMap<String, String>();
    private static String JWT_PERFIX = "JWT ";


    public SessionManagement sessionManagement;





    public KalariLabServices(Activity context) {
        this.context = context;

    }




    public void signUp(final String email, final String password,final String firstName,final String lastName, final String userName){

        addPostRequestParamsSignUp(email, password, firstName, lastName, userName);
        JSONObject jsonObj = new JSONObject(postRequestParams);
        String url = BASE_URL+"auth/users/";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                           sessionManagement = new SessionManagement(context);
                            createSession( String.valueOf(response.getInt("id")));
                            Intent myIntent = new Intent(context, ProfileInfoActivity.class);
                            context.startActivity(myIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressBar(R.layout.activity_register);
                        Log.d("GetRequestDebug", error.toString());
                        String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Auth failure...please check your information!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                error.printStackTrace();
                setAlertDialog(context, message);
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

    private void hideProgressBar(int layout) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate( layout, null, true);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }


    private void addPostRequestParamsSignUp(String email, String password, String firstName, String lastName,String userName) {
        postRequestParams.put("username", userName);
        postRequestParams.put("password", password);
        postRequestParams.put("email", email);
        postRequestParams.put("first_name", firstName);
        postRequestParams.put("last_name", lastName);

    }

    public void signIn(String userName, String password){
        addPostRequestParamsSignIn(userName, password);
        String url = BASE_URL+"auth/jwt/create/";
        addPostRequestParamsSignIn(userName, password);
        JSONObject jsonObj = new JSONObject(postRequestParams);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sessionManagement = new SessionManagement(context);

                        String refresh = null;
                        String  access = null;
                        try {
                            refresh = response.getString("refresh");
                            access = response.getString("access");


                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                            sessionManagement.saveRefreshToken(refresh);
                            sessionManagement.saveAccessToken(access);


                        GetId();
                        getCostumerId();
                        createSession(sessionManagement.returnUserId());
                        Intent myIntent = new Intent(context, MainActivity.class);
                        context.startActivity(myIntent);




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
                            message = "Auth failure...please check your information!";
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        error.printStackTrace();
                        setAlertDialog(context, message);

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



    private void addPostRequestParamsSignIn(String userName, String password ) {
        postRequestParams.put("username", userName);
        postRequestParams.put("password", password);

    }

    private void createSession(String ID) {
        sessionManagement.saveSession(ID);

    }

    private void setAlertDialog(Activity context, String message) {

        new AlertDialog.Builder(context)
                .setTitle("Connection Error.")
                .setMessage(message)

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void GetId(){


        String url = BASE_URL+"auth/users/me";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                                sessionManagement.saveUserId(response.getString("id"));

                        } catch (JSONException e) {
                           Log.d("GetRequestDebug", "failed");
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
                        setAlertDialog(context, message);
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", JWT_PERFIX + sessionManagement.returnAccessToken());
                return headers;
            };
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", JWT_PERFIX + sessionManagement.returnAccessToken());
                return headerMap;
            }
        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);

    }
    private void getCostumerId() {
        String url = BASE_URL+"store/customers/me";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            sessionManagement.saveCostumerId(response.getString("id"));
                        } catch (JSONException e) {
                            Log.d("GetRequestDebugCostumer", "failed");
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
                        setAlertDialog(context, message);
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", JWT_PERFIX + sessionManagement.returnAccessToken());
                return headers;
            };
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", JWT_PERFIX + sessionManagement.returnAccessToken());
                return headerMap;
            }
        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);
    }

    public void updateInfo(String gender, String birthDay){

        addPutRequestParams(gender, birthDay);

        JSONObject jsonObj = new JSONObject(postRequestParams);
        String url = BASE_URL+"store/customers/"+sessionManagement.returnCostumerId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,url,jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PutRequestDebug", response.toString());

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
                        setAlertDialog(context, message);
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", JWT_PERFIX + sessionManagement.ACCESS_TOKEN);
                return headers;
            };

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", JWT_PERFIX + sessionManagement.returnAccessToken());
                return headerMap;
            }
        };


        RequestQueueSinglton.getInstance(context).addToRequestQueue(request);

    }

    private void addPutRequestParams(String gender, String birthDay) {
        putRequestParams.put("gender", gender);
        putRequestParams.put("birth_date", birthDay);
    }


}

