package ua.kpi.comsys.IO8216.ui.dashboard;

import java.util.Calendar;

public class TimePP {

    private int hours;
    private int minutes;
    private int seconds;

    private String am = "AM";
    private String pm = "PM";


    public static TimePP add(TimePP obj_1, TimePP obj_2){
        int hours, minutes, seconds;

        hours = (obj_1.getHours() + obj_2.getHours());
        minutes = (obj_1.getMinutes() + obj_2.getMinutes());
        seconds = (obj_1.getSeconds() + obj_2.getSeconds());

        if (seconds > 59){
            minutes += 1;
            seconds = seconds % 60;
        }

        if (minutes > 59){
            hours += 1;
            minutes = minutes % 60;
        }

        hours = hours % 24;

        return new TimePP(hours, minutes, seconds);
    }

    public static TimePP sub(TimePP obj_1, TimePP obj_2){
        int hours, minutes, seconds;

        hours = (obj_1.getHours() - obj_2.getHours());
        minutes = (obj_1.getMinutes() - obj_2.getMinutes());
        seconds = (obj_1.getSeconds() - obj_2.getSeconds());

        if (seconds < 0){
            minutes -= 1;
            seconds = 60 - (Math.abs(seconds) % 60);
        }else {
            seconds = seconds % 60;
        }

        if (minutes < 0){
            hours -= 1;
            minutes = 60 - (Math.abs(minutes) % 60);
        }else {
            minutes = minutes % 60;
        }

        if (hours < 0){
            hours = 24 - (Math.abs(hours) % 24);
        }
        else {
            hours = hours % 24;
        }

        return new TimePP(hours, minutes, seconds);
    }

    public TimePP() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;

    }

    public TimePP(int hours, int minutes, int seconds) {
        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException("Hours value is wrong. Must be in [0, 23]");
        }
        else if(minutes < 0 || minutes > 59){
            throw new IllegalArgumentException("Minutes value is wrong. Must be in [0, 59]");
        }
        else if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Seconds value is wrong. Must be in [0, 59]");
        }

        else {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    public TimePP(Calendar calendar) {
        this.hours = calendar.get(Calendar.HOUR);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
    }

    public String getTime(){
        if (this.hours < 12){
            return String.format("%02d:%02d:%02d ", this.hours, this.minutes, this.seconds) + this.am;
        }else {
            return String.format("%02d:%02d:%02d ", this.hours - 12, this.minutes, this.seconds) + this.pm;
        }
    }

    public int getHours(){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public int getSeconds(){
        return this.seconds;
    }


    public TimePP add(TimePP obj) {
        int hours, minutes, seconds;

        hours = (this.hours + obj.getHours());
        minutes = (this.minutes + obj.getMinutes());
        seconds = (this.seconds + obj.getSeconds());

        if (seconds > 59){
            minutes += 1;
            seconds = seconds % 60;
        }

        if (minutes > 59){
            hours += 1;
            minutes = minutes % 60;
        }

        hours = hours % 24;

        return new TimePP(hours, minutes, seconds);
    }

    public TimePP sub(TimePP obj) {
        int hours, minutes, seconds;

        hours = (this.hours - obj.getHours());
        minutes = (this.minutes - obj.getMinutes());
        seconds = (this.seconds - obj.getSeconds());

        if (seconds < 0){
            minutes -= 1;
            seconds = 60 - (Math.abs(seconds) % 60);
        }else {
            seconds = seconds % 60;
        }

        if (minutes < 0){
            hours -= 1;
            minutes = 60 - (Math.abs(minutes) % 60);
        }else {
            minutes = minutes % 60;
        }

        if (hours < 0){
            hours = 24 - (Math.abs(hours) % 24);
        }
        else {
            hours = hours % 24;
        }

        return new TimePP(hours, minutes, seconds);

    }


}


