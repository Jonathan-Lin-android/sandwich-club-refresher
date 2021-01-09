package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
/*
Sandwich
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
 */

    /*
    {
    "name":
        {
        "mainName":"Gua bao",
        "alsoKnownAs": ["Steamed bao","Pork belly bun"]
        },
    "placeOfOrigin":"Taiwan",
    "description":"Gua bao is a Taiwanese snack food consisting of a slice of stewed meat and other condiments sandwiched between flat steamed bread. The steamed bread is typically 6–8 centimetres (2.4–3.1 in) in size,
                semi-circular and flat in form, with a horizontal fold that, when opened, gives the
                appearance that it has been sliced. The traditional filling for gua bao is a slice of
                red-cooked porkbelly, typically dressed with stir-fried suan cai (pickled mustard
                greens), cilantro, and ground peanuts.",

     "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Steamed_Sandwich%2Ctaken_by_LeoAlmighty.jpg/600px-Steamed_Sandwich%2Ctaken_by_LeoAlmighty.jpg",

    "ingredients":["Steamed bread","Stewed meat","Condiments"]}

     */
//String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        /*
        static final strings
        JSONObject = optional jsonObject
         */
        final String strNameJSONObject = "name";
        final String strMainName = "mainName";
        final String strAlsoKnownAs = "alsoKnownAs";
        final String strDescription = "description";
        final String strImage = "image";
        final String strPlaceOfOrigin = "placeOfOrigin";
        final String strIngredients = "ingredients";

        Sandwich model = new Sandwich();

        JSONObject root = new JSONObject(json);

        JSONObject name = root.optJSONObject(strNameJSONObject);
        model.setMainName(name.optString(strMainName));

        // populate also known as list
        List<String> listAlsoKnownAs = new ArrayList<String>();
        JSONArray jsonArrAlsoKnownAs =  name.optJSONArray(strAlsoKnownAs);
        for(int i = 0; i < jsonArrAlsoKnownAs.length(); i++)
        {
            listAlsoKnownAs.add(jsonArrAlsoKnownAs.optString(i));
        }
        model.setAlsoKnownAs(listAlsoKnownAs);

        model.setDescription(root.optString(strDescription));
        model.setImage(root.optString(strImage));
        model.setPlaceOfOrigin(root.optString(strPlaceOfOrigin));

        // populate ingredient list
        JSONArray jsonArrIngredients = root.optJSONArray(strIngredients);
        List<String> listIngredients = new ArrayList<>();
        for(int i = 0; i < jsonArrIngredients.length(); i++)
            listIngredients.add(jsonArrIngredients.optString(i));

        model.setIngredients(listIngredients);
        return model;
    }
}
