package com.example.finalyearproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.common.Urls;
import com.example.finalyearproject.common.VolleyMultipartRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class VerifyOTPActivity extends AppCompatActivity {

    TextView tvmobileno,tvResendOTP;
    EditText etinput1,etinput2,etinput3,etinput4,etinput5,etinput6;
    AppCompatButton btnVerify;

    ProgressDialog progressDialog;

    Bitmap bitmap;

    private String strVerificationCode, strName, strMobileNo, strEmailId, strUsername, strPassword;

    double latitude, longitude;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);


        if(ActivityCompat.checkSelfPermission(VerifyOTPActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(VerifyOTPActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(VerifyOTPActivity.this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
            },199);
        }
//        else {
//        getUserCurrentLocation();
//        }


        tvmobileno = findViewById(R.id.tvverifyotpmobileno);
        tvResendOTP = findViewById(R.id.tvVerifyOTPResendOTP);
        etinput1 = findViewById(R.id.etverifyotpinputcode1);
        etinput2 = findViewById(R.id.etverifyotpinputcode2);
        etinput3 = findViewById(R.id.etverifyotpinputcode3);
        etinput4 = findViewById(R.id.etverifyotpinputcode4);
        etinput5 = findViewById(R.id.etverifyotpinputcode5);
        etinput6 = findViewById(R.id.etverifyotpinputcode6);
        btnVerify = findViewById(R.id.btnVerifyOTPverify);

        strVerificationCode = getIntent().getStringExtra("verificationcode");
        strName = getIntent().getStringExtra("name");
        strMobileNo = getIntent().getStringExtra("mobileno");
        strEmailId = getIntent().getStringExtra("email");
        strUsername = getIntent().getStringExtra("username");
        strPassword = getIntent().getStringExtra("password");

        tvmobileno.setText(strMobileNo);

        byte[] byteArray = getIntent().getByteArrayExtra("profile_photo");
        if(byteArray != null)
        {
             bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        }

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etinput1.getText().toString().trim().isEmpty() || etinput2.getText().toString().trim().isEmpty() ||
                etinput3.getText().toString().trim().isEmpty() || etinput4.getText().toString().trim().isEmpty() ||
                etinput5.getText().toString().trim().isEmpty() || etinput6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(VerifyOTPActivity.this, "Please Enter A Valid OTP", Toast.LENGTH_SHORT).show();

                }

                String otpcode = etinput1.getText().toString()+etinput2.getText().toString()+etinput3.getText().toString()+
                                 etinput4.getText().toString()+etinput5.getText().toString()+etinput6.getText().toString();

                 if (strVerificationCode!=null)
                 {
                     progressDialog = new ProgressDialog(VerifyOTPActivity.this);
                     progressDialog.setTitle("Verifying OTP");
                     progressDialog.setMessage("Please Wait...");
                     progressDialog.setCanceledOnTouchOutside(false);
                     progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                             strVerificationCode,
                             otpcode);

                     FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {

                                     if (task.isSuccessful())
                                     {
                                         progressDialog.dismiss();
                                         getUserCurrentLocation();
                                     }
                                     else
                                     {
                                         progressDialog.dismiss();
                                         Toast.makeText(VerifyOTPActivity.this, "OTP verification fail", Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             });
                 }

            }
        });

        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + strMobileNo,
                        60, TimeUnit.SECONDS, VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(VerifyOTPActivity.this,"Verification Completed",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTPActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationCode, @NonNull
                            PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                strVerificationCode = newverificationCode;
                            }
                        }
                );
            }
        });



        setupInputOTP();

    }

    private void getUserCurrentLocation() {

        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(VerifyOTPActivity.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(VerifyOTPActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                    address = addressList.get(0).getAddressLine(0);
                    userRegisterDetails(latitude,longitude,address);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VerifyOTPActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userRegisterDetails(double latitude, double longitude,String address) {
// client and server communication
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();//put the data
        params.put("name", strName);
        params.put("mobileno",strMobileNo);
        params.put("emailid",strEmailId);
        params.put("username",strUsername);
        params.put("password",strPassword);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("address",address);

        client.post(Urls.RegisterUserWebService,params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            String status = response.getString("success");
                            if (status.equals("1"))
                            {

                                uploadProfilePhoto(bitmap,response.getInt("lastinsertid"));
                            }
                            else
                            {

                                Toast.makeText(VerifyOTPActivity.this, "Already Data Present", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(VerifyOTPActivity.this, "server error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

        );
    }

    private void uploadProfilePhoto(Bitmap bitmap, int lastinsertid)
    {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                Urls.addUserRegisterImageWebService,
                new Response.Listener<NetworkResponse>() {
            @Override
                    public void onResponse(NetworkResponse response) {

                                Toast.makeText(VerifyOTPActivity.this, "Registration Successfully Done", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyOTPActivity.this, LoginActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();


            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(VerifyOTPActivity.this, ""+volleyError.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//plain data
                Map<String,String> params = new HashMap<>();
                params.put("tags",""+lastinsertid);
                return params;
            }
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() throws AuthFailureError{//file or image pass
                Map<String,VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic",new VolleyMultipartRequest.DataPart(imagename+".png",getByteArrayFromBitMap(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(VerifyOTPActivity.this).add(volleyMultipartRequest);

    }

    private byte[] getByteArrayFromBitMap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void setupInputOTP() {

        etinput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etinput2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etinput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    etinput3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etinput3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    etinput4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etinput4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    etinput5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etinput5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty())
                {
                    etinput6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }
}