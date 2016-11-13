package io.eidukas.calhacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by solmoms on 11/12/2016.
 */

public class RecipeActivity extends AppCompatActivity {
    ImageView mImageView;
    TextView title;
    TextView ingredients;
    TextView allergens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
