package com.example.projetisn;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFrag extends Fragment {

    public ProfilFrag() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        TextView tvProf = v.findViewById(R.id.tvProfilAct);
        Button bSignOut = v.findViewById(R.id.bSignOut_ProfilFrag);

        mAuth = FirebaseAuth.getInstance();
        tvProf.setText(mAuth.getCurrentUser().toString());

        bSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return v;
    }

}
