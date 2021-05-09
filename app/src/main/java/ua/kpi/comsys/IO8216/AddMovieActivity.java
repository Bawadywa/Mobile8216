package ua.kpi.comsys.IO8216;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddMovieActivity extends AppCompatActivity {
    private Button button_add;
    private Data data;
    private String title;
    private int year;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextInputEditText title_input = findViewById(R.id.title_input);
        TextInputEditText year_input = findViewById(R.id.year_input);
        TextInputEditText type_input = findViewById(R.id.type_input);

        data = (Data) getApplication();

        button_add = (Button)findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                String error_message = "Error. ";

                title = title_input.getText().toString();
                String year_str = year_input.getText().toString();
                type = type_input.getText().toString();

                if (title.equals("")){
                    check = false;
                    error_message += "Field 'Title' is required";
                }else{
                    if(!year_str.equals("")){
                        if(year_str.contains(".")){
                            check = false;
                            error_message += "Year must be decimal";
                        }else if (year_str.startsWith("0")){
                            check = false;
                            error_message += "Year mustn't start with zero";
                        }else{
                            year = Integer.parseInt(year_str);
                            if (year > 2021){
                                check = false;
                                error_message += "Year is more than 2021";
                            }
                        }
                    }else{
                        year = 0;
                    }
                }


                if (check) {
                    data.title = title;
                    data.year = year;
                    data.type = type;
                    data.flag = true;
                    finish();
                }else{
                    Toast.makeText(AddMovieActivity.this, error_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        data.flag = false;
        finish();
        return true;
    }




}