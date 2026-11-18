package Disciplines;

import java.time.LocalDate;

public class
Result {
    int resultHours;
    int resultMinutes;
    int resultSeconds;
    LocalDate resultDate;
    Disciplines discipline;
    int memberNumber;

    public Result(int resultHours, int resultMinutes, int resultSeconds, LocalDate resultDate, Disciplines discipline, int memberNumber){
        this.resultHours = resultHours;
        this.resultMinutes = resultMinutes;
        this.resultSeconds = resultSeconds;
        this.resultDate = resultDate;
        this.discipline = discipline;
        this.memberNumber = memberNumber;
    }

    public Disciplines getDiscipline() {
        return discipline;
    }

    public int getMemberNumber() {
        return memberNumber;
    }
}