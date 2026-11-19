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

    public void createMember(String email, String password){
        backend.createMember(email, password, firstName(), lastName(), email(), password(), birthday(), isCompetitor(), isActiveMember());
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
    public boolean isActiveMember() {
        return IO.readln("Active member? (yes/no): ").trim().equalsIgnoreCase("yes");
    }

    public void printMembers(Member member) {
        IO.println(member);
    }

    public void printCompetitions(Competition competition){
        IO.println(competition);
    }

    public void printCompetitionChoice(){
        IO.println("1. " + backend.printCompetitions());
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

    public int treasurerInput() {
        IO.println("""
            --- Treasurer Menu ---
            1. Show expected income
            2. Create membership payments
            3. Show paid income
            4. Show members with unpaid membership
            5. Show unpaid income
            6. Register membership payment
            7. Exit
            """);

        return Integer.parseInt(IO.readln("Input: "));
    }

    public void treasurerRun(){
        boolean running = true;

        while(running){
            switch (treasurerInput()){
                case 1 -> IO.println("Expected income: " + backend.calculateTotalExpectedIncome() + " DKK");
                case 2 -> {
                    int year = LocalDate.now().getYear();
                    backend.createPaymentsForAllMembers(year);
                    IO.println("Membership payments created for " + year);
                }
                case 3 -> {
                    int year = LocalDate.now().getYear();
                    IO.println("Paid income " + backend.calculateTotalPaidIncome(year) + " DKK");

                }
                case 4 -> {
                    int year = LocalDate.now().getYear();
                    backend.printMembersWithUnpaidMembership(year);
                }
                case 5 -> {
                    int year = LocalDate.now().getYear();
                    IO.println("Unpaid income " + backend.calculateTotalUnpaidIncome(year) + "DKK ");
                }
                case 6 -> {
                    int memberNumber = Integer.parseInt(IO.readln("Enter member number: "));
                    int year = LocalDate.now().getYear();

                    boolean success = backend.registerMembershipPayment(memberNumber, year);

                    if (success){
                        IO.println("Membership payment successfully registered.");
                    } else {
                        IO.println("No membership payment found for this member and year.");
                    }
                }
                case 7 -> running = false;
                default -> IO.println("Invalid input");
            }

        }
    }
}
