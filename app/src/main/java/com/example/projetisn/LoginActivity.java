package com.example.projetisn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button bLogin;
    private FrameLayout lAppNameLayout;
    private EditText etEmailUsername_LoginActivity;
    private EditText etPw_LoginActivity;
    private FirebaseAuth mAuth;
    private String email;
    private String username;
    private String password;

    //checking login infos methods

    private Boolean isEmail(){
        boolean bool1 = false;
        boolean bool2 = false;
        char c;
        for (int i = 0; i < etEmailUsername_LoginActivity.length(); i++){
            c = etEmailUsername_LoginActivity.getText().charAt(i);
            if (c == '@') {
                bool1 = true;
            }
            else if(bool1 && c == '.'){
                bool2 = true;
            }
        }
        if (bool1 && bool2) {
            return true;
        }else{
            return false;
        }
    }

    private Boolean validUsername(){
        if (etEmailUsername_LoginActivity.length()>3){
            return true;
        }else {
            return false;
        }
    }

    private Boolean validPw(){
        if (etPw_LoginActivity.length()>4){
            return true;
        }else {
            return false;
        }
    }

    //onClick methods

    public void onClickbLogin(View v){
        /*Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);*/

        if (isEmail()) {
            email = etEmailUsername_LoginActivity.getText().toString();
        }else{
            username = etEmailUsername_LoginActivity.getText().toString();
        }
        password = etPw_LoginActivity.getText().toString();

        if (isEmail() && validPw()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Bienvenue " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Veuillez entrer des informations valides \n" +
                                                "Pas de compte? Créez en un!",
                                        Toast.LENGTH_LONG).show();
                                //updateUI(null);
                                // ...
                            }

                            // ...
                        }
                    });
        }else if (validPw() && validUsername()){
            Toast.makeText(LoginActivity.this, "J'ai pas encore activé le login avec username wsh", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this, "Veuillez entrer des informations valides \n" +
                            "Pas de compte? Créez en un!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickTvRegister(View v){
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //mAuth est l'appareil sur lequel touurne l'app
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        bLogin = findViewById(R.id.bLogin);
        lAppNameLayout = findViewById(R.id.lAppThem_FrameLayout_LoginActivity);
        etEmailUsername_LoginActivity = findViewById(R.id.et_login_username);
        etPw_LoginActivity = findViewById(R.id.et_login_password);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        lAppNameLayout.getLayoutParams().height = height/3;

    }
}
