package com.example.projetisn;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class VocabFrag extends Fragment {

    private Button bListePrincipale;
    private Button bAddList;
    private LayoutInflater inflater;
    private PopupWindow popupWindow;
    private FrameLayout lVocabFrag_FrameLyout;
    private LinearLayout lLinearLayoutTransparent_PopUpWindow;
    private TextView tvCrossClose_PopUpWindow;

    public VocabFrag() {
        // Required empty public constructor
    }

    public void onClicktvClosePopUp(){
        this.popupWindow.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vocab, container, false);
        View w = inflater.inflate(R.layout.l_popupwindow_addlist_vocabfrag, container, false);
        this.bListePrincipale = v.findViewById(R.id.bListePrincipale);
        this.bAddList = v.findViewById(R.id.bAddList);
        this.lVocabFrag_FrameLyout = (FrameLayout) v.findViewById(R.id.lFragmentVocab_FraeLayout);
        this.lLinearLayoutTransparent_PopUpWindow = (LinearLayout) w.findViewById(R.id.lLayoutTransparent_PopUpWindow);
        lLinearLayoutTransparent_PopUpWindow.setAlpha((float)(0.1));
        this.tvCrossClose_PopUpWindow = w.findViewById(R.id.tvCross_close_PopUpWindow);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int width = dm.widthPixels;
        final int height = dm.heightPixels;
        this.bAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity pop up
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) inflater.inflate(R.layout.l_popupwindow_addlist_vocabfrag, null);
                popupWindow = new PopupWindow(container, (int) (width), (int) (height), true);
                lVocabFrag_FrameLyout.setAlpha((float)(0.1));
                popupWindow.showAtLocation(lVocabFrag_FrameLyout, Gravity.CENTER, 0, -100);
                Button bClose_PopUpWindow = container.findViewById(R.id.bClose_PopUpWindow);
                bClose_PopUpWindow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        lVocabFrag_FrameLyout.setAlpha(1);
                    }
                });
            }
        });

       }
}
