import CSV.CSVMapper;
import CSV.FileHandler;
import Competition.Competition;
import Disciplines.CompetitionResult;
import Disciplines.Disciplines;
import Disciplines.Result;
import User_And_Member.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members;
    private final List<Result> trainingResults = new ArrayList<>();
    private final List<Competition> competitions = new ArrayList<>();
    private final FileHandler fileHandler;
    private final String filePath;
    ConsoleUI ui;
    private final MembershipFeeService membershipFeeService = new MembershipFeeService();

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
                    ui.treasurerRun();
                }
            } else if (user instanceof Member) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.memberRun((Member)user, email);
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

    public void editMember(int memberNumber, EditMember editChoice) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                if (editChoice == EditMember.FIRSTNAME) {
                    member.setName(ui.firstName());
                } else if (editChoice == EditMember.LASTNAME) {
                    member.setLastName(ui.lastName());
                } else if (editChoice == EditMember.EMAIL) {
                    member.setEmail(ui.email());
                } else if (editChoice == EditMember.MEMBER_TYPE) {
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

    public ArrayList<Result> viewTimes(Age chosenAge, int input) {
        ArrayList<Result> backCrawl = new ArrayList<>();
        ArrayList<Result> breastSwimming = new ArrayList<>();
        ArrayList<Result> butterfly = new ArrayList<>();
        ArrayList<Result> crawl = new ArrayList<>();

        if (trainingResults.isEmpty()) {
            return null;
        }
        for (Result result : trainingResults) {
            switch (chosenAge) {
                case UNDER_AGE -> {
                    if (getAge(result.getMemberNumber()) < 18) {
                        switch (result.getDiscipline()) {
                            case BACKCRAWL -> {
                                backCrawl.add(result);
                            }
                            case BREASTSWIMMING -> {
                                breastSwimming.add(result);
                            }
                            case BUTTERFLY -> {
                                butterfly.add(result);
                            }
                            case CRAWL -> {
                                crawl.add(result);
                            }
                        }
                    }
                }
                case ADULT_AGE -> {
                    if (getAge(result.getMemberNumber()) >= 18) {
                        switch (result.getDiscipline()) {
                            case BACKCRAWL -> {
                                backCrawl.add(result);
                            }
                            case BREASTSWIMMING -> {
                                breastSwimming.add(result);
                            }
                            case BUTTERFLY -> {
                                butterfly.add(result);
                            }
                            case CRAWL -> {
                                crawl.add(result);
                            }
                        }
                    }
                }
            }
            switch (input) {
                case 1 -> {
                    return backCrawl;
                }
                case 2 -> {
                    return breastSwimming;
                }
                case 3 -> {
                    return butterfly;
                }
                case 4 -> {
                    return crawl;
                }
            }
        }
        return null;
    }

    public ArrayList<Result> viewCompTimes(Age chosenAge, int input) {
        ArrayList<Result> backCrawl = new ArrayList<>();
        ArrayList<Result> breastSwimming = new ArrayList<>();
        ArrayList<Result> butterfly = new ArrayList<>();
        ArrayList<Result> crawl = new ArrayList<>();

        if (competitions.isEmpty()) {
            return null;
        }
        for (int i = 0; i < competitions.size(); i++) {
            for (Result result : competitions.get(i).getResults()) {
                switch (chosenAge) {
                    case UNDER_AGE -> {
                        if (getAge(result.getMemberNumber()) < 18) {
                            switch (result.getDiscipline()) {
                                case BACKCRAWL -> {
                                    backCrawl.add(result);
                                }
                                case BREASTSWIMMING -> {
                                    breastSwimming.add(result);
                                }
                                case BUTTERFLY -> {
                                    butterfly.add(result);
                                }
                                case CRAWL -> {
                                    crawl.add(result);
                                }
                            }
                        }
                    }
                    case ADULT_AGE -> {
                        if (getAge(result.getMemberNumber()) >= 18) {
                            switch (result.getDiscipline()) {
                                case BACKCRAWL -> {
                                    backCrawl.add(result);
                                }
                                case BREASTSWIMMING -> {
                                    breastSwimming.add(result);
                                }
                                case BUTTERFLY -> {
                                    butterfly.add(result);
                                }
                                case CRAWL -> {
                                    crawl.add(result);
                                }
                            }
                        }
                    }
                }
            }
        }
        switch (input) {
            case 1 -> {
                return backCrawl;
            }
            case 2 -> {
                return breastSwimming;
            }
            case 3 -> {
                return butterfly;
            }
            case 4 -> {
                return crawl;
            }
        }
        return null;
    }

    public void createResult(String trainerEmail, String trainerPassword, int resultHour, int resultMinute, int resultSecond, LocalDate resultDate, Disciplines disciplines, int memberNumber) {
        Trainer trainer = new Trainer(trainerEmail, trainerPassword);
        Result result = trainer.createResult(disciplines, resultHour, resultMinute, resultSecond, resultDate, memberNumber);
        trainingResults.add(result);
    }

    public void createCompetition(String trainerEmail, String trainerPassword, String arrangement, String location, LocalDate date) {
        Trainer trainer = new Trainer(trainerEmail, trainerPassword);
        Competition competition = trainer.createCompetition(competitions, arrangement, location, date);
        competitions.add(competition);
    }

    public void createCompResult(String trainerEmail, String trainerPassword, int competitionID, int resultHour, int resultMinute, int resultSecond, LocalDate resultDate, Disciplines disciplines, int memberNumber) {
        Trainer trainer = new Trainer(trainerEmail, trainerPassword);
        Result result = trainer.createResult(disciplines, resultHour, resultMinute, resultSecond, resultDate, memberNumber);
        for (Competition competition : competitions) {
            if (competition.getCompetitionID() == competitionID) {
                competition.getResults().add(result);
            }
        }
    }

    public ArrayList<Result> viewMembertimes(int choice, int input, String email) {
        ArrayList<Result> backCrawl = new ArrayList<>();
        ArrayList<Result> breastSwimming = new ArrayList<>();
        ArrayList<Result> butterfly = new ArrayList<>();
        ArrayList<Result> crawl = new ArrayList<>();

        if (trainingResults.isEmpty() || competitions.isEmpty()) {
            return null;
        }

        int memberNumber = getMembernumberByEmail(email);

        switch (choice) {
            case 1 -> {
                for (Result result : trainingResults) {
                    if (memberNumber == result.getMemberNumber()) {
                        switch (result.getDiscipline()) {
                            case BACKCRAWL -> {
                                backCrawl.add(result);
                            }
                            case BREASTSWIMMING -> {
                                breastSwimming.add(result);
                            }
                            case BUTTERFLY -> {
                                butterfly.add(result);
                            }
                            case CRAWL -> {
                                crawl.add(result);
                            }
                        }
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < competitions.size(); i++) {
                    for (Result result : competitions.get(i).getResults()) {
                        if (memberNumber == result.getMemberNumber()) {
                            switch (result.getDiscipline()) {
                                case BACKCRAWL -> {
                                    backCrawl.add(result);
                                }
                                case BREASTSWIMMING -> {
                                    breastSwimming.add(result);
                                }
                                case BUTTERFLY -> {
                                    butterfly.add(result);
                                }
                                case CRAWL -> {
                                    crawl.add(result);
                                }
                            }
                        }
                    }
                }


                switch (input) {
                    case 1 -> {
                        return backCrawl;
                    }
                    case 2 -> {
                        return breastSwimming;
                    }
                    case 3 -> {
                        return butterfly;
                    }
                    case 4 -> {
                        return crawl;
                    }
                }
            }
        }
        return null;
    }


    public void changeCompetitorStatus(String email, String password, int memberNumber, boolean choice) {
        Trainer trainer = new Trainer(email, password);
        trainer.changeCompetitorStatus(printMember(memberNumber), choice);
    }

    public void printMembers() {
        for (Member member : members) {
            if (!members.isEmpty()) {
                ui.printMembers(member);
            }
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

    public void printCompetitions() {
        for (Competition competition : competitions) {
            if (!competitions.isEmpty()) {
                ui.printCompetitions(competition);
            }
        }
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
        List<Competition> juniorCompetitions = fileHandler.loadCompetitionsWithResults("src/CSV/Competitions_U18.CSV", "src/CSV/CompetitionResults_U18.CSV");
        List<Competition> seniorCompetitions = fileHandler.loadCompetitionsWithResults("src/CSV/Competitions_O18.CSV", "src/CSV/CompetitionResults_O18.CSV");

        competitions.clear();
        competitions.addAll(juniorCompetitions);

        for (Competition senior : seniorCompetitions) { //løkken tjekker om der allerede er blevet tilføjet et stævne med samme id fra juniors
            Competition existingCompetition = null;

            for (Competition competition : competitions) {
                if (competition.getCompetitionID() == senior.getCompetitionID()) {
                    existingCompetition = competition;
                    break;
                }
            }

            if (existingCompetition != null) {
                existingCompetition.getResults().addAll(senior.getResults());
            } else {
                competitions.add(senior);
            }
        }
    }

    public int getMembernumberByEmail(String email) {
        for (Member member : members) {
            if (member.getEmail().equalsIgnoreCase(email)) {
                return member.getMemberNumber();
            }
        }
        return 0;
    }

    public int getAge(int memberNumber) {
        return printMember(memberNumber).getAge();
    }

    public int calculateTotalExpectedIncome() {
        return membershipFeeService.calculateTotalExpectedIncome(members);
    }

    public void createPaymentsForAllMembers(int year) {
        membershipFeeService.createPaymentsForAllMembers(members, year);
    }

    public int calculateTotalPaidIncome(int year) {
        return membershipFeeService.calculateTotalPaidIncome(year);
    }

    public int calculateTotalUnpaidIncome(int year) {
        return membershipFeeService.calculateTotalUnpaidIncome(year);
    }

    /* Funktion længere ned gør det samme, men det her kan måske bruges andet sted.(ikke slet endnu)
    public List<MembershipPayment> getUnpaidPayments(int year) {
        return membershipFeeService.getUnpaidPayments(year);
    }

    public List<MembershipPayment> getPaidPayments(int year) {
        return membershipFeeService.getPaidPayments(year);
    }

     */

    public boolean registerMembershipPayment(int memberNumber, int year) {
        return membershipFeeService.registerPayment(memberNumber, year);
    }

    public void printMembersWithUnpaidMembership(int year) {
        List<MembershipPayment> unpaidPayments = membershipFeeService.getUnpaidPayments(year);

        for (MembershipPayment payment : unpaidPayments) {
            Member member = printMember(payment.getMemberNumber());

            if (member != null) {
                ui.printMembers(member);
                IO.println("Payment year: " + payment.getYear());
                IO.println("Amount: " + payment.getAmount());
                IO.println("Status: Unpaid");
                IO.println("--------------------");
            }
        }
    }

    public void printMembersWithPaidMembership(int year) {
        List<MembershipPayment> paidPayments = membershipFeeService.getPaidPayments(year);

        for (MembershipPayment payment : paidPayments) {
            Member member = printMember(payment.getMemberNumber());

            if (member != null) {
                ui.printMembers(member);
                IO.println("Payment year: " + payment.getYear());
                IO.println("Amount: " + payment.getAmount());
                IO.println("Status: Paid");
                IO.println("--------------------");
            }
        }
    }

    public int calculateMembershipFee(Member member){
        return membershipFeeService.calculateMembershipFee(member);
    }
    public boolean payMembershipFee(Member member, int year){
        return membershipFeeService.payMembershipFee(member, year);
    }
    public MembershipPayment getMembershipPayment(int memberNumber, int year){
        return membershipFeeService.getMembershipPayment(memberNumber, year);
    }
}

