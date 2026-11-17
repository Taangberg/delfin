package CSV;
import Competition.CompetitionResultsO18;
import Competition.CompetitionResultsU18;
import User_And_Member.*;

import java.time.LocalDate;

public class CsvMapper {
    String delimiter = ",";

    public Administrator fromCsvAdministrator(String line){
        String[] fields = line.split(delimiter, -1);
        return new Administrator(
                fields[0],
                fields[1]
        );
    }
    public CompetitionResultsU18 fromCsvCompetitionsU18(String line){

    }
    public CompetitionResultsO18 fromCsvCompetitionsO18(String line){

    }
    public Member fromCsvMembers(String line){
        String[] fields = line.split(delimiter, -1);
        return new Member(
                fields[0],
                fields[1],
                fields[2],
                fields[3],
                LocalDate.parse(fields[4]),
                Integer.parseInt(fields[5]),
                MemberType.valueOf(fields[6])
        );
    }
    public Trainer fromCsvTrainers(String line){
        String[] fields = line.split(delimiter, -1);
        return new Trainer(
                fields[0],
                fields[1]
        );
    }
    public fromCsvU18(String line){

    }
    public fromCsvO18(String line){

    }
    public Treasurer fromCsvTreasurers(String line){
        String[] fields = line.split(delimiter, -1);
        return new Treasurer(
                fields[0],
                fields[1]
        );
    }

    public String toCsvAdministrator(){

    }
    public String toCsvCompetitionsCompetitionsO18(){

    }
    public String toCsvCompetitionsCompetitionsU18(){

    }
    public String toCsvMembers(){

    }
    public String toCsvTrainers(){

    }
    public String toCsvU18(){

    }
    public String toCsvO18(){

    }
    public String toCsvTreasurers(){

    }
}
