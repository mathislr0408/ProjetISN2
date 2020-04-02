package com.example.projetisn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class InfoUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LinearLayout lActionBar;
    private TextView tvUsername;
    private Button bChangeUsername;
    private EditText etNewUsername;
    private TextView tvEmail;
    private String errorMessage ="";

    float x1, x2, y1, y2, motionX;
    String motionDirection;
    final static int MIN_DISTANCE_X = 150;
    final static int MAX_DISTANCE_Y = 150;

    private Boolean validLength(){
        if (etNewUsername.length() > 3){
            return true;
        }else {
            errorMessage = errorMessage + "longueur username < 4";
            return false;
        }
    }

    private Boolean validUsername(){
        char c;
        boolean bool = false;
        for (int i = 0; i < etNewUsername.length(); i++){
            c = etNewUsername.getText().charAt(i);
            if (c == '@') {
                bool = true;
            }
        }
        if(bool){
            errorMessage = errorMessage + "\n @ interdit dans l'username";
            return false;
        }else{
            return true;
        }
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
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        mAuth = FirebaseAuth.getInstance();
        lActionBar = findViewById(R.id.lActionbar_InfoUserActivity);
        tvUsername = findViewById(R.id.tvUsername_InfoUserActivity);
        bChangeUsername = findViewById(R.id.bChangeUsername_InfoUserActivity);
        etNewUsername = findViewById(R.id.etNewUsername_InfoUserActivity);
        etNewUsername.setText("");
        tvEmail = findViewById(R.id.tvEmail_InfoUserActivity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        lActionBar.getLayoutParams().height = (int)(height / 8.5);
        etNewUsername.setWidth((int)(width/1.75));
        tvUsername.setText(mAuth.getCurrentUser().getDisplayName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

        bChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validLength() && validUsername()) {
                    final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(etNewUsername.getText().toString())
                            .build();
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            tvUsername.setText(mAuth.getCurrentUser().getDisplayName());

                        }
                    });
                    etNewUsername.setText("");
                }else{
                Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
                    errorMessage = "";
                }
            }
        });

    }
}
