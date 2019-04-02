package com.example.android.newsapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            settingFragmentTransaction(item.getItemId());
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        listeningView();
        settingBottomNavigationStartItem();
    }

    private void listeningView() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void settingBottomNavigationStartItem() {
        navigation.getMenu().getItem(0).setChecked(true);
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0));
    }

    private void settingFragmentTransaction(int menuItemSelected) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        switch (menuItemSelected){
            case R.id.navigation_technology:
                navigation.getMenu().getItem(0).setChecked(true);
                fragmentTransaction.replace(R.id.linearlayout_container, new TechnologyFragment());
                break;
            case R.id.navigation_entertainment:
                navigation.getMenu().getItem(1).setChecked(true);
                fragmentTransaction.replace(R.id.linearlayout_container, new GamesFragment());
                break;
            default:
                navigation.getMenu().getItem(0).setChecked(true);
                fragmentTransaction.replace(R.id.linearlayout_container, new TechnologyFragment());
                break;
        }

        fragmentTransaction.commit();
    }

}
