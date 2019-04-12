package com.chetan.foodrecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Button button = findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProgressBar.getVisibility() == View.VISIBLE){
                    showProgressBar(false);
                }else{
                    showProgressBar(true);
                }
            }
        });
    }
}
