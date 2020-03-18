package com.example.projetisn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    private VocabFrag vocabFrag;
    private FriendsFrag friendsFrag;
    private ProfilFrag profilFrag;
    private ArrayList<Fragment> HomeMadeBackStack = new ArrayList<>();
    private boolean programaticallySelected;
    private float x1, x2, y1, y2;
    final static int MIN_DISTANCE_X = 150;
    final static int MAX_DISTANCE_Y = 150;
    private float motionX;
    private String motionDirection = null;
    boolean isAnimation = false;


    private void setFragment(Fragment fragment) {
        Fragment currentFrag = getCurrentFragment();
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (isAnimation) {
            if (motionDirection == "L") {
                //l'aniamtion ne marche pas, régler le pb: voir vidéos
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            isAnimation = false;
        }
        ft.replace(R.id.main_frame, fragment);
        if (currentFrag != null) {
            HomeMadeBackStack.add(currentFrag);
        }
        //Toast toast = Toast.makeText(getBaseContext(), "fragmentToBeSet = " + currentFrag, Toast.LENGTH_LONG);
        //toast.show();
        ft.commit();
        int i;
        for (i = 1; i < HomeMadeBackStack.size(); i++) {
            Fragment fragFromBackStack = HomeMadeBackStack.get(i);
            if (fragment == fragFromBackStack) {
                HomeMadeBackStack.remove(i);
            }
            if (HomeMadeBackStack.size() > 1) {
                if (HomeMadeBackStack.get(1) == vocabFrag) {
                    HomeMadeBackStack.remove(1);
                }
            }
        }
    }

    private void setFragmentReturn(Fragment fragment) {
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }

    private Fragment getCurrentFragment() {
        if (vocabFrag.isVisible()) {
            return vocabFrag;
        } else if (friendsFrag.isVisible()) {
            return friendsFrag;
        } else if (profilFrag.isVisible()) {
            return profilFrag;
        } else {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        int HomeMadeBackStackSize = HomeMadeBackStack.size();
        if (HomeMadeBackStackSize < 1) {
            finish();
        } else {

            Fragment fragmentToBeSet = HomeMadeBackStack.get(HomeMadeBackStackSize - 1);
            if (fragmentToBeSet != null) {
                setFragmentReturn(fragmentToBeSet);
            }
            HomeMadeBackStack.remove(HomeMadeBackStackSize - 1);
            if (fragmentToBeSet == vocabFrag) {
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_vocab);
            } else if (fragmentToBeSet == friendsFrag) {
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_friends);
            } else if (fragmentToBeSet == profilFrag) {
                programaticallySelected = true;
                navView.setSelectedItemId(R.id.navigation_profil);
            }

        }
        //super.onBackPressed();
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

                if (Math.abs(motionX) > MIN_DISTANCE_X && Math.abs(y1 - y2) < MAX_DISTANCE_Y) {
                    if (motionX > 0) {
                        motionDirection = "R";
                    } else {
                        motionDirection = "L";
                    }
                    if (motionDirection == "L") {
                        switch (navView.getSelectedItemId()) {
                            case R.id.navigation_vocab:
                                break;
                            case R.id.navigation_friends:
                                isAnimation = true;
                                navView.setSelectedItemId(R.id.navigation_vocab);
                                break;
                            case R.id.navigation_profil:
                                isAnimation = true;
                                navView.setSelectedItemId(R.id.navigation_friends);
                                break;
                            default:
                                Toast.makeText(this, "error detected: mainActivity, dispatchTouchEvent", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        switch (navView.getSelectedItemId()) {
                            case R.id.navigation_vocab:
                                isAnimation = true;
                                navView.setSelectedItemId(R.id.navigation_friends);
                                break;
                            case R.id.navigation_friends:
                                isAnimation = true;
                                navView.setSelectedItemId(R.id.navigation_profil);
                                break;
                            case R.id.navigation_profil:
                                break;
                            default:
                                Toast.makeText(this, "error detected: mainActivity, dispatchTouchEvent", Toast.LENGTH_SHORT).show();

                        }

                    }

                    return false;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        vocabFrag = new VocabFrag();
        friendsFrag = new FriendsFrag();
        profilFrag = new ProfilFrag();
        setFragment(vocabFrag);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_vocab, R.id.naviagtion_friends, R.id.navigation_profil)
                .build();*/
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_vocab:
                        if (!programaticallySelected) {
                            setFragment(vocabFrag);
                        } else {
                            programaticallySelected = false;
                        }
                        return true;
                        case R.id.navigation_friends:
                            if (!programaticallySelected) {
                                setFragment(friendsFrag);
                            } else {
                                programaticallySelected = false;
                            }
                            return true;
                            case R.id.navigation_profil:
                                if (!programaticallySelected) {
                                    setFragment(profilFrag);
                                } else {
                                    programaticallySelected = false;
                                }
                                return true;
                                default:
                                    return false;
                }

            }
        }

        );
    }

}
