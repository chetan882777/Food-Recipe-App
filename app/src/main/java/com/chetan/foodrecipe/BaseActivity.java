package com.chetan.foodrecipe;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

abstract public class BaseActivity extends AppCompatActivity {

    public  ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base , null);

        FrameLayout frameLayout = constraintLayout.findViewById(R.id.base_frameLayout);
        mProgressBar = constraintLayout.findViewById(R.id.base_progressBar);

        getLayoutInflater().inflate(layoutResID , frameLayout , true);

        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
