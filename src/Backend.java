import Competition.Competition;
import Disciplines.*;
import CSV.CSVMapper;
import CSV.FileHandler;
import User_And_Member.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    private final FileHandler fileHandler;
    private final String filePath;
    ConsoleUI ui;
    MembershipFeeService membershipFeeService = new MembershipFeeService();

    public Backend(String filePath) throws IOException {
        this.filePath = filePath;

        CSVMapper csvMapper = new CSVMapper();
        this.fileHandler = new FileHandler(csvMapper);

        this.members = fileHandler.loadMembers(filePath);
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
                   if(newType == MemberType.COMPETITOR) {
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

    public ArrayList<Result> viewTimes(Age chosenAge,int input) {
        ArrayList<Result> backCrawl = new ArrayList<>();
        ArrayList<Result> breastSwimming = new ArrayList<>();
        ArrayList<Result> butterfly = new ArrayList<>();
        ArrayList<Result> crawl = new ArrayList<>();

        if (results.isEmpty()) {
            return null;
        }
        for (Result result : results) {
            switch (chosenAge) {
                case UNDER_AGE -> {
                    if (getAge(result.getMemberNumber()) < 18) {
                        if (result.getDiscipline() == Disciplines.BACKCRAWL) {
                            backCrawl.add(result);
                        } else if (result.getDiscipline() == Disciplines.BREASTSWIMMING) {
                            breastSwimming.add(result);
                        } else if (result.getDiscipline() == Disciplines.BUTTERFLY) {
                            butterfly.add(result);
                        } else if (result.getDiscipline() == Disciplines.CRAWL) {
                            crawl.add(result);
                        }
                    }
                }
                case ADULT_AGE -> {
                    if (getAge(result.getMemberNumber()) >= 18) {
                        if (result.getDiscipline() == Disciplines.BACKCRAWL) {
                            backCrawl.add(result);
                        } else if (result.getDiscipline() == Disciplines.BREASTSWIMMING) {
                            breastSwimming.add(result);
                        } else if (result.getDiscipline() == Disciplines.BUTTERFLY) {
                            butterfly.add(result);
                        } else if (result.getDiscipline() == Disciplines.CRAWL) {
                            crawl.add(result);
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

    public ArrayList<Result> viewCompTimes(Age chosenAge, int input){
        switch (input) {
            case 1 ->
            case 2 ->
            case 3 ->
            case 4 ->
        }
    }


    public void changeCompetitorStatus(String email, String password, int memberNumber, boolean choice){
        Trainer trainer = new Trainer(email, password);
        trainer.changeCompetitorStatus(printMember(memberNumber), choice);
    }

    public void printMembers() {
        for (Member member : members) {
            if(!members.isEmpty()) {
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

    public int calculateTotalExpectedIncome() {
        return membershipFeeService.calculateTotalExpectedIncome(members);
    }
    public void createPaymentsForAllMembers(int year) {
        membershipFeeService.createPaymentsForAllMembers(members, year);
    }
    public int calculateTotalPaidIncome(int year){
        return membershipFeeService.calculateTotalPaidIncome(year);
    }
    public int calculateTotalUnpaidIncome(int year){
        return membershipFeeService.calculateTotalUnpaidIncome(year);
    }
    public List<MembershipPayment> getUnpaidPayments(int year) {
        return membershipFeeService.getUnpaidPayments(year);
    }
    public boolean registerMembershipPayment(int memberNumber, int year){
        return membershipFeeService.registerPayment(memberNumber, year);
    }

    public void printMembersWithUnpaidMembership(int year){
        for (MembershipPayment payment : getUnpaidPayments(year)){
            Member member = printMember(payment.getMemberNumber());

            if (member != null){
                IO.println(member.getName() + " " + member.getLastName() + " - " + payment.getAmount() + " DKK");
            }
        }
    public void printCompetitions(){
        for(Competition competition : competitions){
            if(!competitions.isEmpty()){
                ui.printCompetitions(competition);
            }
        }
    }

    public void printJuniors() {
        List<Member> juniors = new ArrayList<>();

        for(Member member : members) {
            if(member.isJunior()) {
                juniors.add(member);
            }
        }
        for(Member junior : juniors) {
            ui.printMembers(junior);
        }
    }

    public void printSeniors() {
        List<Member> seniors = new ArrayList<>();

        for(Member member : members) {
            if(!member.isJunior()) {
                seniors.add(member);
            }
        }
        for(Member senior : seniors) {
            ui.printMembers(senior);
        }
    }

    public void printExercisers() {
        List<Member> exercisers = new ArrayList<>();

        for(Member member : members) {
            if(member.getMemberType() == MemberType.EXERCISER) {
                exercisers.add(member);
            }
        }
        for (Member exerciser : exercisers) {
            ui.printMembers(exerciser);
        }
    }

    public void printCompetitorsU18() {
        List<Member> u18 = new ArrayList<>();

        for(Member member : members) {
            if(member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                u18.add(member);
            }
        }
        for(Member juniors : u18) {
            ui.printMembers(juniors);
        }
    }

    public void printCompetitorsO18() {
        List<Member> o18 = new ArrayList<>();

        for(Member member : members) {
            if(!member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                o18.add(member);
            }
        }
        for(Member seniors : o18) {
            ui.printMembers(seniors);
        }
    }


    public void printPassive() {
        List<Member> passive = new ArrayList<>();

        for(Member member : members) {
            if(member.getMemberType() == MemberType.PASSIVE) {
                passive.add(member);
            }
        }
        for(Member passives : passive) {
            ui.printMembers(passives);
        }
    }

    public int getAge(int memberNumber){
        return printMember(memberNumber).getAge();
    }
}

