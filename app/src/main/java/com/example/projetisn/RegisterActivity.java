package com.example.projetisn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout lLogo;
    private EditText etUsernameRegister;
    private EditText etEmailRegister;
    private EditText etPwRegister;
    private EditText etPwConfRegister;
    String errorMessage = "";
    private FirebaseAuth mAuth;

    float x1, x2, y1, y2, motionX;
    final static int MIN_DISTANCE_X = 150;
    final static int MAX_DISTANCE_Y = 150;

    //checking the validity of the infos
    private boolean checkEditTextRegister(){
        errorMessage = "";
        boolean bool1;
        boolean bool2 = checkEmailValidity();
        boolean bool3;
        boolean bool4;

        if (etUsernameRegister.length() > 3 && validUsername()){
            bool1 = true;
        }else{
            errorMessage = errorMessage + "\nveuillez entrer un username valide";
            bool1 = false;
        }
        if (etPwRegister.length() > 4){
            bool3 = true;
        }else {
            errorMessage = errorMessage + "\nveuillez entrer un mot de passe valide";
            bool3 = false;
        }
        if (etPwRegister.getText().toString().equals(etPwConfRegister.getText().toString())){
            bool4 = true;
        }else{
            errorMessage = errorMessage + "\nveuiller ressaisir votre mot de passe et sa confirmation";
            bool4 = false;
        }

        if(bool1&&bool2&&bool3&&bool4){
            Toast.makeText(RegisterActivity.this, "youpi", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }

    private boolean checkEmailValidity(){
        boolean bool1 = false;
        boolean bool2 = false;
        char c;
        for (int i = 0; i < etEmailRegister.length(); i++){
            c = etEmailRegister.getText().charAt(i);
            if (c == '@') {
                bool1 = true;
            }
            else if(c == '.' && bool1){
                bool2 = true;
            }
        }
        if (bool1 && bool2) {
            return true;
        }else{
            errorMessage = errorMessage + "veuillez entrer une adresse email valide";
            return false;
        }
    }

    private Boolean validUsername(){
        char c;
        boolean bool = false;
        for (int i = 0; i < etUsernameRegister.length(); i++){
            c = etUsernameRegister.getText().charAt(i);
            if (c == '@') {
                bool = true;
            }
        }
        if(bool){
            errorMessage = errorMessage + "\n '@' interdit dans l'username";
            return false;
        }else{
            return true;
        }
    }

    //onClick methods

    public void onClickRegisterButton(View v){
        if(!checkEditTextRegister()){
            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }else {
            String email = etEmailRegister.getText().toString();
            String password = etPwRegister.getText().toString();
            String username = etUsernameRegister.getText().toString();

            final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "registered, bienvenue" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.updateProfile(profileUpdates);
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed. \n" + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        /*Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                motionX = x1 - x2;

                if (motionX < 0 && Math.abs(motionX) > MIN_DISTANCE_X && Math.abs(y1 - y2) < MAX_DISTANCE_Y) {
                    finish();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //mAuth est l'appareil sur lequel touurne l'app, est à la base d'une majorité de foncction
        mAuth = FirebaseAuth.getInstance();

        lLogo = findViewById(R.id.lAppThem_FrameLayout_RegisterActivity);
        etUsernameRegister = findViewById(R.id.et_register_username);
        etEmailRegister = findViewById(R.id.et_register_emailAdress);
        etPwRegister = findViewById(R.id.et_register_password);
        etPwConfRegister = findViewById(R.id.et_register_confirmPassword);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            lLogo.getLayoutParams().height = height / 3;
        }else{
            lLogo.getLayoutParams().height = height /6;
        }
    }
}
