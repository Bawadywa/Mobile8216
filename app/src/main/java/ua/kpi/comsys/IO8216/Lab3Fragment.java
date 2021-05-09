package ua.kpi.comsys.IO8216;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lab3Fragment extends Fragment{
    private View root;
    private LinearLayout main_layout;
    private AssetManager am;
    private Data data;
    private ArrayList<Movie> movies;
    private ArrayList<FrameLayout> children;
    private TextInputEditText search_input;
    private TextView no_movies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_lab3, container, false);
        am = getContext().getAssets();
        makeTable();

        search_input = (TextInputEditText) root.findViewById(R.id.search_input);
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(search_input.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        TextInputLayout textInputLayout = (TextInputLayout) root.findViewById(R.id.textInputLayout);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMovieActivity.class);
                startActivity(intent);
            }
        });


        no_movies = (TextView) root.findViewById(R.id.no_movies_found);
        no_movies.setVisibility(View.GONE);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        data = (Data) getActivity().getApplication();
        if (data.flag){
            addMovie();
            data.flag = false;
        }

    }

    public void search(String string){
        if (string.equals("")){
            for (int i = 0; i < children.size(); i++) {
                children.get(i).setVisibility(View.VISIBLE);
            }
            no_movies.setVisibility(View.GONE);
        }else{
            Movie movie;
            int count = 0;
            for (int i = 0; i < movies.size(); i++) {
                movie = movies.get(i);
                if (!(movie.getTitle().toLowerCase().contains(string.toLowerCase()) || String.valueOf(movie.getYear()).contains(string)
                || movie.getType().toLowerCase().contains(string.toLowerCase()))){
                    children.get(i).setVisibility(View.GONE);
                    count += 1;
                }else{
                    children.get(i).setVisibility(View.VISIBLE);
                }
            }
            if (count == movies.size()){
                no_movies.setVisibility(View.VISIBLE);
            }else{
                no_movies.setVisibility(View.GONE);
            }
        }

    }

    public void addMovie(){
        Movie movie = new Movie(data.title, data.year, "noid", data.type, "");
        movies.add(movie);
        LinearLayout table_row;
        FrameLayout wrapper;
        table_row = createTableRow();
        table_row.addView(createContentRow(movie, 25));
        table_row.addView(createUnderline(0));
        wrapper = createWrapper(movie);
        children.add(wrapper);
        wrapper.addView(table_row);
        main_layout.addView(wrapper);
    }

    public void createMoviesArr() {
        String text = readFile("MoviesList.txt");

        JSONArray json = new JSONArray();
        movies = new ArrayList<>();

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
        main_layout = root.findViewById(R.id.main_layout);
        createMoviesArr();
        LinearLayout table_row;
        FrameLayout wrapper;
        children = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++){
            table_row = createTableRow();
            table_row.addView(createContentRow(movies.get(i), 25));
            table_row.addView(createUnderline(0));
            wrapper = createWrapper(movies.get(i));
            wrapper.addView(table_row);
            children.add(wrapper);
            main_layout.addView(wrapper);
        }
    }

    public LinearLayout createContentRow(Movie movie, int margin){
        LinearLayout layout = createContainer(margin);
        ImageView image = createImage(movie.getPoster());
        LinearLayout content_box = createContentBox(movie);
        layout.addView(image);
        layout.addView(content_box);
        return layout;
    }

    public ImageView createImage(String file_name){
        ImageView image = new ImageView(getContext());
        image.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f));
        image.setAdjustViewBounds(true);
        image.setMaxHeight(convertDpToPx(300));

        InputStream inputStream = null;

        if (file_name.equals("")){
            image.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.75f));
            return image;
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

    public FrameLayout createWrapper(Movie movie){
        FrameLayout layout = new FrameLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            boolean flag = true;
            Button button = null;
            @Override
            public boolean onLongClick(View v) {
                if(flag) {
                    button = createDeleteButton();
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            main_layout.removeView(layout);
                            children.remove(layout);
                            movies.remove(movie);
                        }
                    });
                    layout.addView(button);

                    final Animation translate = AnimationUtils.loadAnimation(getContext(), R.anim.transform);
                    button.startAnimation(translate);
                    flag = false;
                }else{
                    if(button != null){
                        final Animation disappear = AnimationUtils.loadAnimation(getContext(), R.anim.disappear);
                        button.startAnimation(disappear);
                        layout.removeView(button);
                        flag = true;
                    }
                }

                return true;
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieActivity.class);
                String value = movie.getImdbID();
                intent.putExtra("id", value);
                startActivity(intent);
            }
        });
        return layout;
    }

    public Button createDeleteButton(){
        Button button = new Button(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(convertDpToPx(150), FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.END;
        layoutParams.setMargins(0, 0, 0, 0);
        button.setLayoutParams(layoutParams);
        button.setPadding(convertDpToPx(15), convertDpToPx(10), convertDpToPx(15), convertDpToPx(10));
        button.setTextSize(25);
        button.setText("DELETE");
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setBackgroundColor(Color.parseColor("#000000"));
        button.setAlpha(0.7f);
        return button;

    }

    public LinearLayout createTableRow(){
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    public LinearLayout createContainer(int margin){
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, convertDpToPx(margin));
        layout.setPadding(convertDpToPx(5), convertDpToPx(25), convertDpToPx(5), 0);
        layout.setMinimumHeight(convertDpToPx(150));
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(5);
        return layout;
    }


    public LinearLayout createContentBox(Movie movie){
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3.25f);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(convertDpToPx(15), convertDpToPx(10), 0, 0);

        TextView text;
        text = createText(movie.getTitle(), 0, 18);
        text.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        layout.addView(text);

        String[] movie_info;
        if (movie.getYear() == 0){
            movie_info = new String[]{movie.getType()};
        }else{
            movie_info = new String[]{String.valueOf(movie.getYear()), movie.getType()};
        }

        for (int i = 0; i < movie_info.length; i++){
            text = createText(movie_info[i], 0, 18);
            text.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            layout.addView(text);
        }
        return layout;
    }

    public TextView createText(String text, int padding, int textSize){
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, convertDpToPx(8));
        textView.setLayoutParams(layoutParams);
        textView.setPadding(convertDpToPx(padding), 0, 0, 0);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.parseColor("#000000"));

        return textView;
    }

    public View createUnderline(int margin){
        View view = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertDpToPx(1));
        layoutParams.setMargins(0, 0, 0, convertDpToPx(margin));
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(Color.parseColor("#333333"));
        return view;
    }

    private int convertDpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
