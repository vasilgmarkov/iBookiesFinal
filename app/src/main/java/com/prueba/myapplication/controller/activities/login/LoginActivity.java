package com.prueba.myapplication.controller.activities.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prueba.myapplication.R;
import com.prueba.myapplication.controller.activities.main.MainActivity;
import com.prueba.myapplication.controller.activities.master_detail.UserCallBack;
import com.prueba.myapplication.controller.managers.UserLoginManager;
import com.prueba.myapplication.controller.managers.UserManager;
import com.prueba.myapplication.model.User;
import com.prueba.myapplication.model.UserToken;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback, UserCallBack {

    // UI references.
    private View mProgressView;
    private View mLoginFormView;
    private String username,userNick,passW,passW2,email;
    private EditText UserName,PassWord,UserNameR,PassWordR,PassWordR2,EmailR;
    private Button makeReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


        Button register = (Button) findViewById(R.id.reg);

        Button mEmailSignInButton = (Button) findViewById(R.id.user_log_in_button);
        UserNameR = (EditText) findViewById(R.id.nickName);
        EmailR = (EditText) findViewById(R.id.email);
        EmailR.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (EmailR.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0)
                {

                }
                else
                {

                    View focusView = null;

                        EmailR.setError(getString(R.string.error_invalid_emailValid));
                        focusView = EmailR;

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        PassWordR = (EditText) findViewById(R.id.passwordCreate);
        PassWordR2 = (EditText) findViewById(R.id.passwordCreate2);

        makeReg = (Button) findViewById(R.id.reg);

        makeReg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setTitle("Login Dialog");
                dialog.setContentView(R.layout.login_dialog);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                UserName = (EditText) dialog.findViewById(R.id.username);
                PassWord = (EditText)dialog.findViewById(R.id.password);
                Button ok = (Button) dialog.findViewById(R.id.user_log_in_buttonD);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);



                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* String UserNameS = UserName.getText().toString();
                        String PassWordS = UserName.getText().toString();
                        Toast.makeText(getApplicationContext(), "Hola " + UserNameS, Toast.LENGTH_SHORT).show();
                        // Falta hace la conexin de la base de datos tabla Usuarios.*/
                        attemptLogin();

                        //dialog.cancel();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });



        
        Button registerVis = (Button) findViewById(R.id.regVisib);
        registerVis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

             LinearLayout   registerForm = (LinearLayout) findViewById(R.id.regLay);



                if(registerForm.getVisibility() == View.VISIBLE){
                    registerForm.setVisibility(View.INVISIBLE);
                }else{
                    registerForm.setVisibility(View.VISIBLE);
                }

                registerForm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });




        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to log in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
//        mEmailView.setError(null);
      //  mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        username = UserName.getText().toString();
        String password = PassWord.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            PassWord.setError(getString(R.string.error_invalid_password));
            focusView = PassWord;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            UserName.setError(getString(R.string.error_field_required));
            focusView = UserName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
       //     showProgress(true);
            UserLoginManager.getInstance(this.getApplicationContext()).performLogin(username, password, LoginActivity.this);
            //UserLoginManager.getInstance(this.getApplicationContext()).getUserInfo(username, LoginActivity.this);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    @Override
    public void onSuccess(UserToken userToken) {
      //  showProgress(false);




        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userName",username);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(User userInfo) {




    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("LoginActivity->", "performLogin->onFailure ERROR " + t.getMessage());
       // showProgress(false);
        PassWord.setError(getString(R.string.error_incorrect_username_or_password));
        PassWord.requestFocus();
    }


    private void attemptRegister(){

        userNick =UserNameR.getText().toString();
        email = EmailR.getText().toString();
        passW = PassWordR.getText().toString();
        passW2 = PassWordR2.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passW) && TextUtils.isEmpty(passW2)) {
            PassWordR.setError(getString(R.string.error_invalid_password));
            PassWordR2.setError(getString(R.string.error_invalid_password));
            focusView = PassWordR;
            cancel = true;
        }

        if (!passW.equals(passW2) ) {
            PassWordR.setError(getString(R.string.error_invalid_passwordReg));
            focusView = PassWordR;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(userNick)) {
            UserNameR.setError(getString(R.string.error_field_required));
            focusView = UserNameR;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            EmailR.setError(getString(R.string.error_field_required));
            focusView = EmailR;
            cancel = true;
        }
        if(!isPasswordValid(passW)){
            PassWordR.setError(getString(R.string.error_invalid_passwordMin));
            focusView = PassWordR;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            User user = new User();
            user.setLogin(UserNameR.getText().toString());
            user.setPassword(PassWordR.getText().toString());
            user.setEmail(EmailR.getText().toString());
            user.setLangKey("en");


            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //     showProgress(true);
           // UserLoginManager.getInstance(this.getApplicationContext()).performLogin(username, password, LoginActivity.this);
            UserManager.getInstance(this.getApplicationContext()).newUser(LoginActivity.this, user);
        }


    }





        /**
     * Shows the progress UI and hides the login form.
     */
   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/

}

