package ua.kpi.comsys.IO8216;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DashboardFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText(time());
        return root;
    }


    public String time() {
        String main_str = "";
//        INIT
        TimePP time1 = new TimePP();
        TimePP time2 = new TimePP(22, 20, 30);
        Calendar calendar = new GregorianCalendar(2017, 0 , 25);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 28);
        TimePP time3 = new TimePP(calendar);

        main_str += time1.getTime() + "\n";
        main_str += time2.getTime() + "\n";
        main_str += time3.getTime() + "\n";

        try {
            TimePP time4 = new TimePP(40, 50, 90);
        }catch (IllegalArgumentException e) {
            main_str += e.getMessage() + "\n\n";
        }

//        OPERATIONS
        TimePP time5 = new TimePP(10, 6, 40);
        main_str += String.format("%s + %s = %s", time2.getTime(), time5.getTime(),
                time2.add(time5).getTime()) + "\n";
        main_str += String.format("%s - %s = %s", time2.getTime(), time3.getTime(),
                time2.sub(time3).getTime()) + "\n\n";

        TimePP time6 = new TimePP(23, 59, 59);
        TimePP time7 = new TimePP(12, 0, 1);
        main_str += String.format("%s + %s = %s", time6.getTime(), time7.getTime(),
                TimePP.add(time6, time7).getTime()) + "\n";

        TimePP time8 = new TimePP(0, 0, 0);
        TimePP time9 = new TimePP(0, 0, 1);
        main_str += String.format("%s - %s = %s", time8.getTime(), time9.getTime(),
                TimePP.sub(time8, time9).getTime());

        return main_str;
    }

}
