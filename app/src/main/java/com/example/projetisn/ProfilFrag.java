package com.example.projetisn;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFrag extends Fragment {

    private FirebaseAuth mAuth;
    private Button bSignOut;
    private Button bInfoUser;
    private TextView tvGreetingsText;
    private FrameLayout lUserIcon;

    public ProfilFrag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        bSignOut = v.findViewById(R.id.bSignOut_ProfilFrag);
        mAuth = FirebaseAuth.getInstance();
        bInfoUser = v.findViewById(R.id.binfoUser_ProfilFrag);
        tvGreetingsText = v.findViewById(R.id.tvGreetingsText_ProfilFrag);
        lUserIcon = v.findViewById(R.id.lIconUser_ProfilFrag);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        tvGreetingsText.setText("Bonjour " + mAuth.getCurrentUser().getDisplayName());
        lUserIcon.getLayoutParams().height = (int)(height / 9);
        lUserIcon.getLayoutParams().width = lUserIcon.getLayoutParams().height;

        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        bInfoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoUserActivity.class));
            }
        });

        return v;
    }

}
