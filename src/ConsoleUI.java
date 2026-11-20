import Competition.Competition;
import Disciplines.Disciplines;
import User_And_Member.*;

import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;

public class ConsoleUI {
    Backend backend;

    public ConsoleUI(Backend backend) {
        this.backend = backend;
    }

    public void start() throws IOException{
        boolean running = true;

        while (running) {
            showStartMenu();
            int choice = Integer.parseInt(IO.readln("Choose a option, to continue "));
            switch (choice) {
                case 1 -> login();
                case 2 -> running = false;
                default -> IO.println("Invalid input");
            }
        }
    }

    private void showStartMenu() {
        IO.println();
        IO.println(" 1. | Log in");
        IO.println(" 2. | Exit");
        IO.println();
    }

    public void login() throws IOException {
        String email = IO.readln("Input Email: ");
        String password = IO.readln("Input Password: ");
        backend.login(email, password);
    }

    public int adminInput() {
        IO.println("""
                1. Create new member.
                2. Edit existing member.
                3. Remove member.
                """);
        return Integer.parseInt(IO.readln("Input..."));
    }

    public void adminRun(String email, String password) throws IOException {
        switch (adminInput()) {
            case 1 -> createMember(email, password);
            case 2 -> editMember();
            case 3 -> removeMember();
        }
    }

    public int trainermenu() {
        IO.println("""
                1. View Times
                2. Update member status
                """);
        return Integer.parseInt(IO.readln("Input..."));
    }

    public void trainerRun(String email, String password) {
        switch (trainermenu()) {
            case 1 -> {
                whichTimes();
            }
            case 2 -> {
                int userInput = Integer.parseInt(IO.readln("Which membernumber: "));
                IO.println("Write true or false, to select or deselect competitor status");
                backend.changeCompetitorStatus(email, password, userInput, Boolean.parseBoolean(IO.readln("true/false ")));
            }
            default -> IO.println("Give the right input...");
        }
    }

    public void whichTimes() {
        IO.println("""
                1. Normal excercises
                2.Competition times
                """);
        if (Integer.parseInt(IO.readln("Input...")) == 1) {
            IO.println(backend.viewTimes(printChooseMemberAgeGroup(), printDiscplines()));
        } else if(Integer.parseInt(IO.readln("Input..."))==2){
            IO.println(backend.viewCompTimes(printChooseMemberAgeGroup(), printDiscplines()));
        }
    }


    public void createMember(String email, String password) throws IOException {
        backend.createMember(email, password, firstName(), lastName(), email(), password(), birthday(), memberType());
    }

    public void editMember() {
        backend.printMembers();
        int memberNumber = Integer.parseInt(IO.readln("Input membernumber: "));
        boolean running = true;
        EditMember editChoice = null;
        while (running) {
            IO.println(String.format("""
                    1. %s
                    2. %s
                    3. %s
                    4. %s
                    5. End
                    """, backend.printMember(memberNumber).getName(), backend.printMember(memberNumber).getLastName(), backend.printMember(memberNumber).getEmail(), backend.printMember(memberNumber).getMemberType()));
            switch (Integer.parseInt(IO.readln("Input "))) {
                case 1 -> {
                    editChoice = EditMember.FIRSTNAME;
                }
                case 2 -> {
                    editChoice = EditMember.LASTNAME;
                }
                case 3 -> {
                    editChoice = EditMember.EMAIL;
                }
                case 4 -> {
                    editChoice = EditMember.MEMBER_TYPE;
                }
                case 5 -> running = false;
            }
            backend.editMember(memberNumber, editChoice);
        }
    }

    public void removeMember() {
        backend.printMembers();
        int memberNumber = Integer.parseInt(IO.readln("Input membernumber: "));
        IO.println("Removed \n" + backend.printMember(memberNumber));
        backend.removeMember(memberNumber);
    }


    public String firstName() {
        return IO.readln("Input first name: ");
    }

    public String lastName() {
        return IO.readln("Input last name: ");
    }

    public String email() {
        return IO.readln("Input Email: ");
    }

    public String password() {
        return IO.readln("Input new password: ");
    }

    public LocalDate birthday() {
        return LocalDate.parse(IO.readln("Input birthday: YYYY.MM.DD "));
    }

    public MemberType memberType() {
        return MemberType.valueOf(IO.readln("Input member type (EXERCISER/COMPETITOR/PASSIVE): ").trim().toUpperCase());
    }

    public void printMembers(Member member) {
        IO.println(member);
    }

    public void printCompetitions(Competition competition){
        IO.println(competition);
    }

    public void printCompetitionChoice(){

    }

    public Age printChooseMemberAgeGroup(){
        IO.println("""
                1. Under 18.
                2. Over 18.
                """);
        switch(Integer.parseInt(IO.readln("Input..."))){
            case 1 -> {
                return Age.UNDER_AGE;
            }
            case 2 -> {
                return Age.ADULT_AGE;
            }
        }
        return null;
    }

    public int printDiscplines(){
        IO.println("""
                1. Backcrawl
                2. Breast Swimming
                3. Butterfly
                4. Crawl
                """);

        return Integer.parseInt(IO.readln("Input..."));
    }
}
