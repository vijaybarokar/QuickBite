package com.example.finalyearproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalyearproject.Restaurant.RestaurantLoginActivity;
import com.example.finalyearproject.admin.AdminHomeActivity;
import com.example.finalyearproject.common.NetworkChangeListener;
import com.example.finalyearproject.common.Urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


  //  SharedPreferences preferences1; //used to store the temp data
    //SharedPreferences.Editor editor2;//used to put or edit the temp data


    ImageView ivlogo;
    TextView tvtitle, tvforgetpassword, tvnewuser, tvRestaurantapp;
    EditText etusername, etpassword;
    CheckBox cbshowhidepassword;
    Button btnlogin;

    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    AppCompatButton btnSignInWithGoogle;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login Activity");

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        // OLD CODE: Basic one-time login check
        /*
        if (preferences.getBoolean("isLogin", false)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        */

        // ✅ NEW CODE: Now checks role also
        if (preferences.getBoolean("isLogin", false)) {
            String role = preferences.getString("role", "user");
            if (role.equals("admin")) {
                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
            finish();
        }

        ivlogo = findViewById(R.id.ivloginlogo);
        tvtitle = findViewById(R.id.tvlogintitle);
        tvnewuser = findViewById(R.id.tvloginnewuser);
        etusername = findViewById(R.id.etloginusername);
        etpassword = findViewById(R.id.etloginpassword);
        tvforgetpassword = findViewById(R.id.tvLoginForgetpassword);
        cbshowhidepassword = findViewById(R.id.cbloginshowhidepassword);
        btnlogin = findViewById(R.id.btnLoginlogin);
        btnSignInWithGoogle = findViewById(R.id.acbtnLoginsigninwithgoogle);

//        tvRestaurantapp.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, RestaurantLoginActivity.class);
//            startActivity(intent);
//        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        btnSignInWithGoogle.setOnClickListener(v -> signIn());

        cbshowhidepassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btnlogin.setOnClickListener(v -> {
            if (etusername.getText().toString().isEmpty()) {
                etusername.setError("please enter your username");
            } else if (!etusername.getText().toString().matches(".*[A-Z].*")) {
                etusername.setError("please enter at least 1 uppercase letter");
            } else if (!etusername.getText().toString().matches(".*[a-z].*")) {
                etusername.setError("please enter at least 1 lowercase letter");
            } else if (etusername.getText().toString().length() < 8) {
                etusername.setError("username must be greater than 8 characters");
            } else if (!etpassword.getText().toString().matches(".*[0-9].*")) {
                etpassword.setError("please enter at least 1 number");
            } else if (!etpassword.getText().toString().matches(".*[@,#,%,&,!].*")) {
                etpassword.setError("please enter at least 1 special character");
            } else if (etpassword.getText().toString().isEmpty()) {
                etpassword.setError("please enter your password");
            } else if (etpassword.getText().toString().length() < 8) {
                etpassword.setError("password must be greater than 8 characters");
            } else {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Login under process");
                progressDialog.show();

                // OLD CODE
                // editor.putString("username", etusername.getText().toString()).commit();
                // editor.putBoolean("isLogin", true).commit();

                userLogin(); // NEW CODE
            }
        });

        tvforgetpassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ConfirmRegisterMobileNoActivity.class);
            startActivity(intent);
        });

        tvnewuser.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(LoginActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void userLogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username", etusername.getText().toString());
        params.put("password", etpassword.getText().toString());

        client.post(Urls.loginUserWebService, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();

                try {
                    String status = response.getString("success");
                    String strUserrole = response.getString("userrole");

                    if (status.equals("1")) {
                        // ✅ NEW CODE: Save both role and login flag
                        editor.putString("username", etusername.getText().toString());
                        editor.putString("role", strUserrole); // user or admin
                        editor.putBoolean("isLogin", true);
                        editor.apply();

                        if (strUserrole.equals("admin")) {
                            startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                        } else {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }
}