package com.example.travelapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.RequestModels.Login;
import com.example.travelapp.api.model.RequestModels.LoginFacebook;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserLogin;
import com.example.travelapp.api.model.ResponseModels.Sucess.UserLoginFacebook;
import com.example.travelapp.api.service.ApiUtils;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.local.UserLocal;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_GET_AUTH_CODE = 9003;
    private static final int RC_SIGN_IN = 1;

    private UserService userService;
    private CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);

        // Get service
        userService = ApiUtils.getUserService();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!isUserNameValid(username)) {
                    usernameEditText.setError(getString(R.string.invalid_username));
                } else if (!isPasswordValid(password)) {
                    passwordEditText.setError(getString(R.string.invalid_password));
                }
                loginButton.setEnabled(isUserNameValid(username) && isPasswordValid(password));
            }
        };

        usernameEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        // Facebook login
        LoginButton loginButtonFacebook = findViewById(R.id.login_facebook_button);

        callbackManager = CallbackManager.Factory.create();

        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginFacebook(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            System.out.println("ahihi " + account.getServerAuthCode());
            System.out.println("ahihi thanh cong");

        } catch (ApiException e) {
            System.out.println("ahihi that bai");
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private void login(String username, String password) {
        Call<UserLogin> call = userService.login(new Login(username, password));
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccessful()) {
                    verification(response.body());
                } else{
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
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verification(UserLogin userLogin) {
        if (true || userLogin.getPhoneVerified() && userLogin.getEmailVerified()) {
            UserLocal.setStore(getApplicationContext(), userLogin.getUserId(), userLogin.getToken());
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), VerifyActivity.class));
        }
        finish();
    }

    private void loginFacebook(String token) {
        Call<UserLoginFacebook> call = userService.loginFacebook(new LoginFacebook(token));
        call.enqueue(new Callback<UserLoginFacebook>() {
            @Override
            public void onResponse(Call<UserLoginFacebook> call, Response<UserLoginFacebook> response) {
                if (response.isSuccessful()) {
                    setUserFacebook(response.body());
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
            public void onFailure(Call<UserLoginFacebook> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserFacebook(UserLoginFacebook userFacebook) {
        UserLocal.setStore(getApplicationContext(), userFacebook.getUserId().toString(), userFacebook.getToken());
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
