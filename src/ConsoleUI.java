import User_And_Member.*;
import Activities.*;

import java.time.LocalDate;
import java.util.List;

public class ConsoleUI {
    private final Activities activities = new Activities();
    Backend backend;

    public ConsoleUI(Backend backend){
        this.backend = backend;
    }

    public void start() {
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

    public void login(){
        String email = IO.readln("Input Email: ");
        String password = IO.readln("Input Password: ");
        backend.login(email, password);
    }

    public int adminInput(){
        IO.println("""
                1. Create new member.
                2. Edit existing member.
                3. Remove member.
                """);
        return Integer.parseInt(IO.readln("Input..."));
    }

    public void adminRun(String email, String password){
        switch(adminInput()){
            case 1 -> createMember(email, password);
            case 2 -> editMember();
            case 3 -> removeMember();
        }
    }


    public void createMember(String email, String password){
        backend.createMember(email, password, firstName(), lastName(), email(), password(), birthday(), isCompetitor(), isActiveMember());
    }

    public EditMember editMember(){
        backend.printMembers();
        int memberNumber = Integer.parseInt(IO.readln("Input membernumber: "));
        boolean running = true;
        while (running) {
            IO.println(String.format("""
                    1. %s
                    2. %s
                    3. %s
                    4. End
                    """, backend.printMember(memberNumber).getName(), backend.printMember(memberNumber).getLastName(), backend.printMember(memberNumber).getEmail()));
            switch (Integer.parseInt(IO.readln("Input "))) {
                case 1 -> {
                    return EditMember.FIRSTNAME;
                }
                case 2 -> {
                    return EditMember.LASTNAME;
                }
                case 3 -> {
                    return EditMember.EMAIL;
                }
                case 4 -> running = false;
            }
            backend.editMember(memberNumber);
        }
        return null;
    }

    public void removeMember(){
        backend.printMembers();
        int memberNumber = Integer.parseInt(IO.readln("Input membernumber: "));
        IO.println("Removed \n" + backend.printMember(memberNumber));
        backend.removeMember(memberNumber);
    }


    public String firstName(){
        return IO.readln("Input first name: ");
    }
    public String lastName(){
        return IO.readln("Input last name: ");
    }
    public String email(){
        return IO.readln("Input Email: ");
    }
    public String password(){
        return IO.readln("Input new password: ");
    }
    public LocalDate birthday(){
        return LocalDate.parse(IO.readln("Input birthday: YYYY.MM.DD "));
    }
    public boolean isCompetitor() {
        return IO.readln("Competitor? (yes/no): ").trim().equalsIgnoreCase("yes");
    }
    public boolean isActiveMember() {
        return IO.readln("Active member? (yes/no): ").trim().equalsIgnoreCase("yes");
    }

    public void printMembers(Member member) {
        IO.println(member);
    }

    private void showMembersMenu() {
        IO.println();
        IO.println(" 1. | Juniors");
        IO.println(" 2. | Seniors");
        IO.println(" 3. | Exercisers");
        IO.println(" 4. | Junior Competitors");
        IO.println(" 5. | Senior Competitors");
        IO.println();
    }

    private void showJuniors() {
        showList("Juniors", activities.getJuniors());
    }

    private void showSeniors() {
        showList("Seniors", activities.getSeniors());
    }

    private void showExercisers() {
        showList("Exercisers", activities.getExercisers());
    }

    private void showJuniorCompetitors() {
        showList("Junior Competitors", activities.getJuniorCompetitors());
    }

    private void showSeniorCompetitors() {
        showList("Senior Competitors", activities.getSeniorCompetitors());
    }

    private void showList(String title, List<Member> list) {
        IO.println("\n--- " + title + " ---");
        if (list.isEmpty()) {
            IO.println("(empty)");
        }
        for (Member member : list) {
            IO.println(member);
        }
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
