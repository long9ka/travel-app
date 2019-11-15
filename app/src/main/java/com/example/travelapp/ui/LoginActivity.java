package com.example.travelapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.ReqFacebookLogin;
import com.example.travelapp.api.model.request.ReqLogin;
import com.example.travelapp.api.model.response.ResFacebookLogin;
import com.example.travelapp.api.model.response.ResLogin;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.store.model.User;
import com.example.travelapp.ui.valid.Validation;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserStore userStore;

    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Load userStore
        userStore = new UserStore(this);

        // Login
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.loginButton);
        final TextView registerButton = findViewById(R.id.registerButton);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!Validation.isUserNameValid(username)) {
                    usernameEditText.setError(getString(R.string.notification_invalid_username));
                } else if (!Validation.isPasswordValid(password)) {
                    passwordEditText.setError(getString(R.string.notification_invalid_password));
                }
                loginButton.setEnabled(Validation.isUserNameValid(username) && Validation.isPasswordValid(password));
            }
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                execLogin(new ReqLogin(username, password));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        // Facebook login
        final LoginButton loginFacebookButton = findViewById(R.id.login_facebook_button);
        callbackManager = CallbackManager.Factory.create();
        loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginManager.getInstance().logOut();
                execFacebookLogin(new ReqFacebookLogin(loginResult.getAccessToken().getToken()));
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.notification_login_failed, Toast.LENGTH_LONG).show();
            }
        });

        // Google login
        String serverClientId = getString(R.string.client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.login_google_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    // ExecLogin
    private void execLogin(ReqLogin reqLogin) {
        Call<ResLogin> call = RetrofitClient.getUserService().login(reqLogin);
        call.enqueue(new Callback<ResLogin>() {
            @Override
            public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                if (response.isSuccessful()) {
                    redirectLogin(response.body());
                } else {

                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.notification_login_failed, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void redirectLogin(ResLogin resLogin) {
        if (true || resLogin.getEmailVerified() || resLogin.getPhoneVerified()) {
            userStore.setUser(new User(resLogin.getUserId(), resLogin.getToken()));
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, VerifyActivity.class));
        }
        finish();
    }

    // ExecFacebookLogin
    private void execFacebookLogin(ReqFacebookLogin reqFacebookLogin) {
        Call<ResFacebookLogin> call = RetrofitClient.getUserService().loginFacebook(reqFacebookLogin);
        call.enqueue(new Callback<ResFacebookLogin>() {
            @Override
            public void onResponse(Call<ResFacebookLogin> call, Response<ResFacebookLogin> response) {
                if (response.isSuccessful()) {
                    redirectFacebook(response.body());
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResFacebookLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.notification_login_failed, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void redirectFacebook(ResFacebookLogin userFacebook) {
        userStore.setUser(new User(userFacebook.getUserId().toString(), userFacebook.getToken()));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // ExecGoogleLogin
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        } catch (ApiException e) {
            Log.w("GoogleLogin", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
