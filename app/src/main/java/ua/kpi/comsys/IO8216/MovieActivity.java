package ua.kpi.comsys.IO8216;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MovieActivity extends AppCompatActivity {
    private TextView no_info;
    private TextView no_poster;
    private AssetManager am;
    private LinearLayout details_layout;
    private TextView title;
    private ImageView poster;
    private TextView year;
    private TextView genre;
    private TextView director;
    private TextView actors;
    private TextView country;
    private TextView language;
    private TextView production;
    private TextView released;
    private TextView runtime;
    private TextView awards;
    private TextView rating;
    private TextView plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        am = getAssets();
        no_info = (TextView) findViewById(R.id.no_info) ;
        no_info.setVisibility(View.INVISIBLE);
        no_poster = (TextView) findViewById(R.id.no_poster);
        no_poster.setVisibility(View.GONE);

        details_layout = (LinearLayout) findViewById(R.id.details_layout);
        title = (TextView) findViewById(R.id.title_textview);
        poster = (ImageView) findViewById(R.id.movie_poster);
        year = (TextView) findViewById(R.id.year_textview);
        genre = (TextView) findViewById(R.id.genre_textview);
        director = (TextView) findViewById(R.id.director_textview);
        actors = (TextView) findViewById(R.id.actors_textview);
        country = (TextView) findViewById(R.id.country_textview);
        language = (TextView) findViewById(R.id.language_textview);
        production = (TextView) findViewById(R.id.production_textview);
        released = (TextView) findViewById(R.id.released_textview);
        runtime = (TextView) findViewById(R.id.runtime_textview);
        awards = (TextView) findViewById(R.id.awards_textview);
        rating = (TextView) findViewById(R.id.rating_textview);
        plot = (TextView) findViewById(R.id.plot_textview);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id = extras.getString("id");
            if(check(id)){
                Details details = getData(id);
                if(details != null){
                    fillDetails(details);
                }
            }
        }



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void fillDetails(Details details){

        if (!details.getPoster().equals("")) {
            InputStream inputStream = null;
            try {
                inputStream = am.open(details.getPoster());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            poster.setImageBitmap(bitmap);
        }else{
            poster.setVisibility(View.GONE);
            no_poster.setVisibility(View.VISIBLE);
        }

        String label = title.getText().toString();
        String text = label + details.getTitle();
        title.setText(makeSpan(label, text));

        label = year.getText().toString();
        text = label + details.getYear();
        year.setText(makeSpan(label, text));

        label = genre.getText().toString();
        text = label + details.getGenre();
        genre.setText(makeSpan(label, text));


        label = director.getText().toString();
        text = label + details.getDirector();
        director.setText(makeSpan(label, text));

        label = actors.getText().toString();
        text = label + details.getActors();
        actors.setText(makeSpan(label, text));

        label = country.getText().toString();
        text = label + details.getCountry();
        country.setText(makeSpan(label, text));

        label = language.getText().toString();
        text = label + details.getLanguage();
        language.setText(makeSpan(label, text));

        label = production.getText().toString();
        text = label + details.getProduction();
        production.setText(makeSpan(label, text));


        label = released.getText().toString();
        text = label + details.getReleased();
        released.setText(makeSpan(label, text));

        label = runtime.getText().toString();
        text = label + details.getRuntime();
        runtime.setText(makeSpan(label, text));

        label = awards.getText().toString();
        text = label + details.getAwards();
        awards.setText(makeSpan(label, text));

        label = rating.getText().toString();
        text = label + details.getImdbRating() + " / 10";
        rating.setText(makeSpan(label, text));

        label = plot.getText().toString();
        text = label + details.getPlot();
        plot.setText(makeSpan(label, text));


    }

    public SpannableString makeSpan(String label, String text){
        SpannableString string = new SpannableString(text);
        string.setSpan(new ForegroundColorSpan(Color.GRAY), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new StyleSpan(Typeface.ITALIC), label.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    public boolean check(String id){
        if (id.equals("noid")){
            no_info.setVisibility(View.VISIBLE);
            details_layout.setVisibility(View.GONE);
            return false;
        }
        return true;
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

    public Details getData(String id){
        String text = readFile(id + ".txt");

        JSONObject obj = new JSONObject();

        try {
            obj = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Details details = null;

        try {
            details = new Details(obj.getString("Title"), obj.optInt("Year", 0), obj.getString("imdbID"),
                    obj.getString("Type"), obj.getString("Poster"), obj.getString("Rated"), obj.getString("Released"),
                    obj.getString("Runtime"), obj.getString("Genre"),obj.getString("Director"), obj.getString("Actors"),
                    obj.getString("Plot"), obj.getString("Language"), obj.getString("Country"), obj.getString("Awards"),
                    obj.getString("imdbRating"), obj.getString("imdbVotes"), obj.getString("Production"));

        } catch (JSONException e){
            e.printStackTrace();
        }

        return details;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private int convertDpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}