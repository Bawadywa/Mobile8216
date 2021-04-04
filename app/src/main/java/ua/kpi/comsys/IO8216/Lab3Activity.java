package ua.kpi.comsys.IO8216;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Lab3Activity extends AppCompatActivity {
    private LinearLayout main_layout;
    private Button button_back, button_next;
    private AssetManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);
        am = getAssets();
        setNavButtons();
        makeTable();
    }



    public ArrayList<Movie> createMoviesArr() {
        String text = readFile("MoviesList.txt");

        JSONArray json = new JSONArray();
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            json = new JSONObject(text).getJSONArray("Search");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < json.length(); i++){
            JSONObject obj;
            Movie movie;
            try {
                obj = json.getJSONObject(i);
                movie = new Movie(obj.getString("Title"), obj.optInt("Year", 0), obj.getString("imdbID"),
                        obj.getString("Type"), obj.getString("Poster"));
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }

    public String readFile(String name){
        String text = "";

        try{
            InputStream is = am.open(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while((line = br.readLine()) != null){
                text = text + line + "\n";
            }
            br.close();
            is.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public void makeTable(){
        main_layout = findViewById(R.id.main_layout);
        ArrayList<Movie> movies = createMoviesArr();
        for (int i = 0; i < movies.size() - 1; i++){
            main_layout.addView(createRow(movies.get(i), 50));
        }
        main_layout.addView(createRow(movies.get(movies.size() - 1), 88));
    }

    public LinearLayout createRow(Movie movie, int margin){
        LinearLayout layout = createContainer(margin);
        ImageView image = createImage(movie.getPoster());
        LinearLayout content_box = createContentBox(movie);
        layout.addView(image);
        layout.addView(content_box);
        return layout;
    }

    public ImageView createImage(String file_name){
        ImageView image = new ImageView(Lab3Activity.this);
        image.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f));
        image.setAdjustViewBounds(true);
        image.setMaxHeight(convertDpToPx(300));
        InputStream inputStream = null;

        if (file_name.equals("")){
            file_name = "no_image.png";
        }

        try {
            inputStream = am.open(file_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        image.setImageBitmap(bitmap);
        return image;
    }

    public LinearLayout createContainer(int margin){
        LinearLayout layout = new LinearLayout(Lab3Activity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, convertDpToPx(margin));
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(5);
        return layout;
    }


    public LinearLayout createContentBox(Movie movie){
        String[] labels = {"Title: ", "Year: ", "ImdbID: ", "Type: "};
        LinearLayout layout = new LinearLayout(Lab3Activity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3.25f);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(convertDpToPx(10), 0, 0, 0);

        float weightSum = 10f;

        TextView text;
        LinearLayout contentRow = createContentRow();
        text = createText(labels[0], 2.1f, 0, 18);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        contentRow.addView(text);
        HorizontalScrollView scroll = createScroll(weightSum - 2.1f);
        text = createText(movie.getTitle(), 100f, 0, 17);
        text.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        scroll.addView(text);

        contentRow.addView(scroll);
        layout.addView(contentRow);

        String year;
        if (movie.getYear() == 0){
            year = "???";
        }else{
            year = String.valueOf(movie.getYear());
        }

        String type = movie.getType();
        if (type.equals("")){
            type = "???";
        }

        float[] weights = {2.1f, 3.1f, 2.2f};
        String[] movie_info = {year, movie.getImdbID(), type};
        for (int i = 0; i < 3; i++){
            contentRow = createContentRow();
            text = createText(labels[i+1], weights[i], 0, 18);
            text.setTypeface(Typeface.DEFAULT_BOLD);
            contentRow.addView(text);
            text = createText(movie_info[i], weightSum - weights[i], 0, 17);
            text.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            contentRow.addView(text);
            layout.addView(contentRow);
        }
        return layout;
    }

    public LinearLayout createContentRow(){
        LinearLayout layout = new LinearLayout(Lab3Activity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, convertDpToPx(5));
        layout.setLayoutParams(layoutParams);
        layout.setWeightSum(10);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    public TextView createText(String text, float weight, int padding, int textSize){
        TextView textView = new TextView(Lab3Activity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);

        textView.setLayoutParams(layoutParams);
        textView.setPadding(convertDpToPx(padding), 0, 0, 0);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
    }

    public HorizontalScrollView createScroll(float weight){
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(Lab3Activity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        horizontalScrollView.setLayoutParams(layoutParams);
        return horizontalScrollView;
    }

    public void setNavButtons(){
        button_back = findViewById(R.id.button_back);
        button_next = findViewById(R.id.button_next);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLab2Activity();
            }
        });
    }

    public void openLab2Activity(){
        Intent intent = new Intent(getApplicationContext(), Lab2Activity.class);
        startActivity(intent);
    }

    private int convertDpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}