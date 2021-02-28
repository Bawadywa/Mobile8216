package ua.kpi.comsys.IO8216;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Lab2Activity extends AppCompatActivity {
    private Button button_back, button_next;
    private Button pie_chart_button, function_button;
    private LinearLayout linear;
    private PieChartView pieChartView;
    private GradientDrawable pie_chart_button_bg, function_button_bg;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);
        linear = (LinearLayout) findViewById(R.id.linear);
        createGraph();
        createPieChart();
        setNavButtons();
        setFunctionButton();
        setPieChartButton();
        pie_chart_button_bg = (GradientDrawable) pie_chart_button.getBackground();
        function_button_bg = (GradientDrawable) function_button.getBackground();
        function_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 255, 0));
        pie_chart_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 0, 0));
        pieChartView.setVisibility(View.GONE);
    }

    public void setPieChartButton(){
        pie_chart_button = (Button) findViewById(R.id.button_pie_chart);
        pie_chart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graph.setVisibility(View.GONE);
                pieChartView.setVisibility(View.VISIBLE);
                pie_chart_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 255, 0));
                function_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 0, 0));
            }
        });
    }

    public void createPieChart(){
        pieChartView = findViewById(R.id.chart);

        List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(10, Color.rgb(255, 255, 0)));
        pieData.add(new SliceValue(20, Color.rgb(0, 255, 0)));
        pieData.add(new SliceValue(25, Color.rgb(0, 0, 255)));
        pieData.add(new SliceValue(5, Color.rgb(255, 0, 0)));
        pieData.add(new SliceValue(40, Color.rgb(0,191,255)));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true);
        pieChartView.setPieChartData(pieChartData);
    }

    private void setFunctionButton(){
        function_button = (Button) findViewById(R.id.button_func);
        function_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChartView.setVisibility(View.GONE);
                graph.setVisibility(View.VISIBLE);
                function_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 255, 0));
                pie_chart_button_bg.setStroke(convertDpToPx(2), Color.rgb(0, 0, 0));
            }
        });
    }

    public void createGraph(){
        double y, x;
        x = -4.0;
        graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        double step = 0.0001;
        int count = (int)(8 / step);
        for (int i = 0; i < count; i ++){
            x = x + step;
            y = Math.log(x);
            if (x <= 0){
                continue;
            }
            series.appendData(new DataPoint(x, y), true, count);
        }
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-4.0);
        graph.getViewport().setMaxX(4.0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-4.0);
        graph.getViewport().setMaxY(4.0);
    }

    public void setNavButtons(){
        button_back = (Button) findViewById(R.id.button_back);
        button_next = (Button) findViewById(R.id.button_next);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmainactivity();
            }
        });
    }

    public void openmainactivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private int convertDpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}