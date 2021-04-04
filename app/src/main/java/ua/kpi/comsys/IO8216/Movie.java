package ua.kpi.comsys.IO8216;


public class Movie {
    private String title;
    private int year;
    private String imdbID;
    private String type;
    private String poster;

    public Movie(String title, int year, String imdbID, String type, String poster){
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    public String getTitle(){
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType(){
        return type;
    }

    public String getPoster() {
        return poster;
    }
}
