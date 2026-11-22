import CSV.CSVMapper;
import CSV.FileHandler;
import Competition.Competition;
import Disciplines.CompetitionResult;
import Disciplines.Result;
import User_And_Member.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    private List<Result> trainingResults = new ArrayList<>();
    private List<Competition> juniorCompetitions = new ArrayList<>();
    private List<Competition> seniorCompetitions = new ArrayList<>();
    private final FileHandler fileHandler;
    private final String filePath;
    ConsoleUI ui;

    public Backend(String filePath) throws IOException {
        this.filePath = filePath;

        CSVMapper csvMapper = new CSVMapper();
        this.fileHandler = new FileHandler(csvMapper);

        this.members = fileHandler.loadMembers(filePath);
        loadUsers();
        loadTrainingResults();
        loadCompetitions();
    }

    public void login(String email, String password) throws IOException {
        for (User user : users) {
            if (user instanceof Administrator) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.adminRun(email, password);
                }
            } else if (user instanceof Trainer) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.trainerRun(email, password);
                }
            } else if (user instanceof Treasurer) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {

                }
            } else if (user instanceof Member) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {

                }
            }
        }
    }

    public void createMember(String adminEmail, String adminPassword, String firstName, String lastName, String email, String password, LocalDate birthday, MemberType memberType) throws IOException {
        Administrator administrator = new Administrator(adminEmail, adminPassword);
        Member member = administrator.createNewMember(members, firstName, lastName, email, password, birthday, memberType);
        members.add(member);
        fileHandler.saveMembers(members, filePath);
    }

    public void editMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                if (ui.editMember() == EditMember.FIRSTNAME) {
                    member.setName(ui.firstName());
                } else if (ui.editMember() == EditMember.LASTNAME) {
                    member.setLastName(ui.lastName());
                } else if (ui.editMember() == EditMember.EMAIL) {
                    member.setEmail(ui.email());
                } else if (ui.editMember() == EditMember.MEMBER_TYPE) {
                    MemberType newType = ui.memberType();
                    if (newType == MemberType.COMPETITOR) {
                        IO.println("User not allowed to edit competitors");
                        return;
                    }
                    member.setMemberType(newType);
                }
            }
        }
    }

    public void removeMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                members.remove(member);
            }
        }
    }


    public void changeCompetitorStatus(String email, String password, int memberNumber, boolean choice) {
        Trainer trainer = new Trainer(email, password);
        trainer.changeCompetitorStatus(printMember(memberNumber), choice);
    }

    public void printMembers() {
        for (Member member : members) {
            ui.printMembers(member);
        }
    }

    public Member printMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                return member;
            }
        }
        return null;
    }

    public void printJuniors() {
        List<Member> juniors = new ArrayList<>();

        for (Member member : members) {
            if (member.isJunior()) {
                juniors.add(member);
            }
        }
        for (Member junior : juniors) {
            ui.printMembers(junior);
        }
    }

    public void printSeniors() {
        List<Member> seniors = new ArrayList<>();

        for (Member member : members) {
            if (!member.isJunior()) {
                seniors.add(member);
            }
        }
        for (Member senior : seniors) {
            ui.printMembers(senior);
        }
    }

    public void printExercisers() {
        List<Member> exercisers = new ArrayList<>();

        for (Member member : members) {
            if (member.getMemberType() == MemberType.EXERCISER) {
                exercisers.add(member);
            }
        }
        for (Member exerciser : exercisers) {
            ui.printMembers(exerciser);
        }
    }

    public void printCompetitorsU18() {
        List<Member> u18 = new ArrayList<>();

        for (Member member : members) {
            if (member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                u18.add(member);
            }
        }
        for (Member juniors : u18) {
            ui.printMembers(juniors);
        }
    }

    public void printCompetitorsO18() {
        List<Member> o18 = new ArrayList<>();

        for (Member member : members) {
            if (!member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                o18.add(member);
            }
        }
        for (Member seniors : o18) {
            ui.printMembers(seniors);
        }
    }


    public void printPassive() {
        List<Member> passive = new ArrayList<>();

        for (Member member : members) {
            if (member.getMemberType() == MemberType.PASSIVE) {
                passive.add(member);
            }
        }
        for (Member passives : passive) {
            ui.printMembers(passives);
        }
    }

    public boolean isJuniorResult(Result result) {
        Member member = printMember(result.getMemberNumber());
        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }
        return member.isJuniorAtDate(result.getResultDate());
    }

    public List<Result> getJuniorResults(List<Result> results) {
        List<Result> juniorResults = new ArrayList<>();
        for (Result result : results) {
            if (isJuniorResult(result)) {
                juniorResults.add(result);
            }
        }
        return juniorResults;
    }

    public List<Result> getSeniorResults(List<Result> results) {
        List<Result> seniorResults = new ArrayList<>();
        for (Result result : results) {
            if (!isJuniorResult(result)) {
                seniorResults.add(result);
            }
        }
        return seniorResults;
    }

    public List<CompetitionResult> getJuniorCompetitionResults(List<CompetitionResult> competitionResults) {
        List<CompetitionResult> juniorResults = new ArrayList<>();
        for (CompetitionResult competitionResult : competitionResults) {
            if (isJuniorResult(competitionResult.result())) {
                juniorResults.add(competitionResult);
            }
        }
        return juniorResults;
    }

    public List<CompetitionResult> getSeniorCompetitionResults(List<CompetitionResult> competitionResults) {
        List<CompetitionResult> seniorResults = new ArrayList<>();
        for (CompetitionResult competitionResult : competitionResults) {
            if (!isJuniorResult(competitionResult.result())) {
                seniorResults.add(competitionResult);
            }
        }
        return seniorResults;
    }

    public void saveTrainingResults(List<Result> results) throws IOException {
        List<Result> juniorResults = getJuniorResults(results);
        List<Result> seniorResults = getSeniorResults(results);

        fileHandler.saveResults(juniorResults, "src/CSV/TrainingResults_U18.CSV");
        fileHandler.saveResults(seniorResults, "src/CSV/TrainingResults_O18.CSV");
    }

    public void saveCompetitionResults(List<CompetitionResult> competitionResults) throws IOException {
        List<CompetitionResult> juniorResults = getJuniorCompetitionResults(competitionResults);
        List<CompetitionResult> seniorResults = getSeniorCompetitionResults(competitionResults);

        fileHandler.saveCompetitionResults(juniorResults, "src/CSV/CompetitionResults_U18.CSV");
        fileHandler.saveCompetitionResults(seniorResults, "src/CSV/CompetitionResults_O18.CSV");
    }

    public void loadUsers() throws IOException {
        users.addAll(fileHandler.loadAdministrator("src/CSV/Administrator.CSV"));

        users.addAll(fileHandler.loadTrainers("src/CSV/Trainers.CSV"));

        users.addAll(fileHandler.loadTreasurers("src/CSV/Treasurers.CSV"));

        users.addAll(members);
    }

    public void loadTrainingResults() throws IOException {
        List<Result> juniorResults = fileHandler.loadResults("src/CSV/TrainingResults_U18.CSV");
        List<Result> seniorResults = fileHandler.loadResults("src/CSV/TrainingResults_O18.CSV");

        trainingResults.addAll(juniorResults);
        trainingResults.addAll(seniorResults);
    }

    public void loadCompetitions() throws IOException {
        juniorCompetitions = fileHandler.loadCompetitionsWithResults("src/CSV/Competitions_U18.CSV", "src/CSV/CompetitionResults_U18.CSV");
        seniorCompetitions = fileHandler.loadCompetitionsWithResults("src/CSV/Competitions_O18.CSV", "src/CSV/CompetitionResults_O18.CSV");
    }
}

