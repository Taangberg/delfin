package Competition;


import Disciplines.Result;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competition {
    private int competitionID;
    private String arrangement;
    private String location;
    private LocalDate date;
    private List<Result> results;

    public Competition(int competitionID, String arrangement, String location, LocalDate date) {
        this.competitionID = competitionID;
        this.arrangement = arrangement;
        this.location = location;
        this.date = date;
        this.results = new ArrayList<>();
    }

    public void addResult(Result result) {
        results.add(result);
    }

    public int getCompetitionID() {
        return competitionID;
    }

    public String getArrangement() {
        return arrangement;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Result> getResults() {
        return results;
    }
}
