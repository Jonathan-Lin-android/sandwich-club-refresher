package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // list view position represent array position in string array
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich model) {
        TextView itemName = findViewById(R.id.tv_item_name);
        itemName.setText(getModelStringData(model.getMainName()));

        TextView itemDescription = findViewById(R.id.tv_description);
        itemDescription.setText(getModelStringData(model.getDescription()));

        TextView itemOrigin = findViewById(R.id.tv_place_of_origin);
        itemOrigin.setText(" " + getModelStringData(model.getPlaceOfOrigin()));

        // populating also known as
        List<String> listAlsoKnownAs = model.getAlsoKnownAs();
        if(listAlsoKnownAs != null && !listAlsoKnownAs.isEmpty()) {
            TextView alsoKnownAsTextView = findViewById(R.id.tv_also_known_as);
            alsoKnownAsTextView.setText(model.getAlsoKnownAs().toString().replace("[", "(").replace("]", ")"));
        }

        // populating ingredients
        List<String> listIngredients = model.getIngredients();
        TextView ingredientsTextView = findViewById(R.id.tv_ingredients);
        if(listIngredients != null && !listIngredients.isEmpty()) {
            ingredientsTextView.setText(model.getIngredients().toString().replace("[", "").replace("]", ""));
        }
        else
            ingredientsTextView.setText("Data not available");
    }

    private String getModelStringData(String s)
    {
        return s == null || s.isEmpty() ? "Data not available" : s;
    }
}
