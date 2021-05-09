package ua.kpi.comsys.IO8216;


public class Details {
    private String title;
    private int year;
    private String imdbID;
    private String type;
    private String poster;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String imdbRating;
    private String imdbVotes;
    private String production;

    public Details(String title, int year, String imdbID, String type, String poster, String rated,
             String released,
             String runtime,
             String genre,
             String director,
             String actors,
             String plot,
             String language,
             String country,
             String awards,
             String imdbRating,
             String imdbVotes,
             String production){
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
        this.actors = actors;
        this.awards = awards;
        this.language = language;
        this.plot = plot;
        this.country = country;
        this.director = director;
        this.rated = rated;
        this.imdbRating = imdbRating;
        this.genre = genre;
        this.imdbVotes = imdbVotes;
        this.released = released;
        this.runtime = runtime;
        this.production = production;
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

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getProduction() {
        return production;
    }
}
