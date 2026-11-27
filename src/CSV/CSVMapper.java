package CSV;

import Competition.Competition;
import Disciplines.Disciplines;
import Disciplines.Result;
import Disciplines.CompetitionResult;
import User_And_Member.*;
import Membership.MembershipPayment;

import java.time.LocalDate;

public class CSVMapper {
    private final String delimiter = ",";

    public Member fromCSVMember(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Member(
                tokens[0],
                tokens[1],
                tokens[2],
                tokens[3],
                LocalDate.parse(tokens[4]),
                Integer.parseInt(tokens[5]),
                MemberType.valueOf(tokens[6]));
    }

    public String toCSVMember(Member member) {
        return member.getName() + delimiter
                + member.getLastName() + delimiter
                + member.getEmail() + delimiter
                + member.getPassword() + delimiter
                + member.getBirthday() + delimiter
                + member.getMemberNumber() + delimiter
                + member.getMemberType().name();
    }

    public Administrator fromCSVAdministrator(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Administrator(
                tokens[0],
                tokens[1]);
    }

    public String toCSVAdministrator(Administrator administrator) {
        return administrator.getEmail() + delimiter
                + administrator.getPassword();
    }

    public Trainer fromCSVTrainer(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Trainer(
                tokens[0],
                tokens[1]);
    }

    public String toCSVTrainer(Trainer trainer) {
        return trainer.getEmail() + delimiter
                + trainer.getPassword();
    }

    public Treasurer fromCSVTreasurer(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Treasurer(
                tokens[0],
                tokens[1]);
    }

    public String toCSVTreasurer(Treasurer treasurer) {
        return treasurer.getEmail() + delimiter
                + treasurer.getPassword();
    }

    public Result fromCSVResults(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Result(
                Integer.parseInt(tokens[0]),
                Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2]),
                LocalDate.parse(tokens[3]),
                Disciplines.valueOf(tokens[4]),
                Integer.parseInt(tokens[5]));
    }

    public String toCSVResults(Result result) {
        return result.getResultHours() + delimiter
                + result.getResultMinutes() + delimiter
                + result.getResultSeconds() + delimiter
                + result.getResultDate() + delimiter
                + result.getDiscipline() + delimiter
                + result.getMemberNumber();
    }

    public Competition fromCSVCompetition(String line) {
        String[] tokens = line.split(delimiter, -1);

        return new Competition(
                Integer.parseInt(tokens[0]),
                tokens[1],
                tokens[2],
                LocalDate.parse(tokens[3]));
    }

    public String toCSVCompetition(Competition competition) {
        return competition.getCompetitionID() + delimiter
                + competition.getArrangement() + delimiter
                + competition.getLocation() + delimiter
                + competition.getDate();
    }

    public CompetitionResult fromCSVCompetitionResult(String line) {
        String[] tokens = line.split(delimiter, -1);

        int competitionID = Integer.parseInt(tokens[0]);

        String resultData = line.substring(line.indexOf(delimiter) + 1);

        Result result = fromCSVResults(resultData);

        return new CompetitionResult(competitionID, result);
    }

    public String toCSVCompetitionResult(CompetitionResult competitionResult) {
        return competitionResult.competitionID() + delimiter
                + toCSVResults(competitionResult.result());
    }

    public MembershipPayment fromCSVMembershipPayment(String line) {
        String[] token = line.split(delimiter);

        int memberNumber = Integer.parseInt(token[0]);
        int year = Integer.parseInt(token[1]);
        int amount = Integer.parseInt(token[2]);
        boolean paid = Boolean.parseBoolean(token[3]);

        return new MembershipPayment(memberNumber, year, amount, paid);
    }

    public String toCSVMembershipPayment(MembershipPayment payment) {
        return payment.getMemberNumber() + delimiter + payment.getYear() + delimiter + payment.getAmount() + delimiter + payment.isPaid();

    }
}

