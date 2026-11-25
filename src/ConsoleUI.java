import Competition.Competition;
import Disciplines.Disciplines;
import User_And_Member.*;
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
                case 0 -> backend.saveAll();
                case 1 -> login();
                case 2 -> {
                    backend.saveAll();
                    running = false;
                }
                default -> IO.println("Invalid input");
            }
        }
    }

    private void showStartMenu() {
        IO.println("""
                --- Welcome! ---
                1. | Log in
                2. | Exit
                """);
    }

    public void login() throws IOException {
        IO.println("--- Login Page ---");
        String email = IO.readln("Input Email: ");
        String password = IO.readln("Input Password: ");
        backend.login(email, password);
    }

    public int adminInput() {
        IO.println("""
                --- Admin Menu ---
                1. Create new member.
                2. Edit existing member.
                3. Remove member.
                4. Go back
                """);
        return Integer.parseInt(IO.readln("Input..."));
    }

    public void adminRun(String email, String password) throws IOException {
        boolean running = true;

        while (running) {
            switch (adminInput()) {
                case 1 -> createMember(email, password);
                case 2 -> editMember();
                case 3 -> removeMember();
                case 4 -> running = false;
                default -> IO.println("Give the right input...");
            }
        }
    }

    public int trainermenu() {
        IO.println("""
                --- Trainer Menu ---
                1. View Times
                2. Update member status
                3. Create normal Training-time
                4. Create Competition / Result time
                5. Go back
                """);
        return Integer.parseInt(IO.readln("Input..."));
    }

    public void trainerRun(String email, String password) {
        boolean running = true;

        while (running) {
            switch (trainermenu()) {
                case 1 -> {
                    whichTimes();
                }
                case 2 -> {
                    int userInput = Integer.parseInt(IO.readln("Which membernumber: "));
                    IO.println("Write true or false, to select or deselect competitor status");
                    backend.changeCompetitorStatus(email, password, userInput, Boolean.parseBoolean(IO.readln("true/false ")));
                }
                case 3 -> {
                    createResult(email, password);
                }
                case 4 -> {
                    IO.println("""
                            1. View existing competitions...
                            2. Create competition
                            """);
                    switch (Integer.parseInt(IO.readln("Input... "))) {
                        case 1 -> {
                            IO.println("To create a new time in a given competition, input the competitionID");
                            backend.printCompetitions();
                            createCompResult(email, password);
                        }
                        case 2 -> {
                            backend.createCompetition(email, password, IO.readln("Provide arrangement name: "), IO.readln("Provide location name: "), LocalDate.parse(IO.readln("Provide Date for competition: ")));
                        }
                    }

                }
                case 5 -> running = false;
                default -> IO.println("Give the right input...");
            }
        }
    }

    public int treasurerInput() {
        IO.println("""
            --- Treasurer Menu ---
            1. View expected income
            2. View paid income
            3. View unpaid income
            4. View members with unpaid membership
            5. Register membership payment
            6. Create payments for all members
            7. View members with paid membership
            8. Exit
            """);

        return Integer.parseInt(IO.readln("Input..."));
    }

    public void treasurerRun() {
        boolean running = true;
        while (running) {
            switch (treasurerInput()) {
                case 1 -> IO.println(
                        "Expected income: " + backend.calculateTotalExpectedIncome()
                );
                case 2 -> {
                    int year = Integer.parseInt(IO.readln("Input year: "));
                    IO.println("Paid income: " + backend.calculateTotalPaidIncome(year));
                }
                case 3 -> {
                    int year = Integer.parseInt(IO.readln("Input year: "));
                    IO.println("Unpaid income: " + backend.calculateTotalUnpaidIncome(year));
                }
                case 4 -> {
                    int year = Integer.parseInt(IO.readln("Input year: "));
                    backend.printMembersWithUnpaidMembership(year);
                }
                case 5 -> {
                    int memberNumber = Integer.parseInt(IO.readln("Input member number: "));
                    int year = Integer.parseInt(IO.readln("Input year: "));
                    if (backend.registerMembershipPayment(memberNumber, year)) {
                        IO.println("Payment registered.");
                    } else {
                        IO.println("Payment not found.");
                    }
                }
                case 6 -> {
                    int year = Integer.parseInt(IO.readln("Input year: "));
                    backend.createPaymentsForAllMembers(year);
                    IO.println("Payments created.");
                }
                case 7 -> {
                    int year = Integer.parseInt(IO.readln("Input year"));
                    backend.printMembersWithPaidMembership(year);
                }
                case 8 -> running = false;
                default -> IO.println("Invalid input.");
            }
        }
    }

    public int memberMenu(Member member){
        IO.println(String.format("""
                --- Hello %s ---
                1. View Results
                2. Pay Fee
                3. Go back
                """, member.getName()));
        return Integer.parseInt(IO.readln("Input... "));
    }

    public void memberRun(Member member, String email){
        boolean running = true;
        while (running) {
            switch (memberMenu(member)) {
                case 1 -> {
                    IO.println("""
                            1. Training times
                            2. Competition times
                            """);
                    int choice = Integer.parseInt(IO.readln("Input..."));
                    IO.println(backend.viewMembertimes(choice, chooseDisciplines(), member));

                }
                case 2 -> payMembershipFee(member);
                case 3 -> running = false;
                default -> IO.println("Give the right input...");
            }
        }
    }

    public void whichTimes() {
        IO.println("""
                1. Normal excercises
                2.Competition times
                """);
        if (Integer.parseInt(IO.readln("Input...")) == 1) {
            IO.println(backend.viewTimes(printChooseMemberAgeGroup(), chooseDisciplines()));
        } else if(Integer.parseInt(IO.readln("Input..."))==2){
            IO.println(backend.viewCompTimes(printChooseMemberAgeGroup(), chooseDisciplines()));
        }
    }

    public void createResult(String email, String password){
        int memberNumber = Integer.parseInt(IO.readln("Which membernumber: "));
        backend.createResult(email, password,chooseDisciplines(), inputTimes(1), inputTimes(2), inputTimes(3), createResultDate(), memberNumber);
    }

    public void createCompResult(String email, String password){
        int competitionID = Integer.parseInt(IO.readln("Input..."));
        int memberNumber = Integer.parseInt(IO.readln("Which membernumber: "));
        backend.createCompResult(email, password,chooseDisciplines(), competitionID, inputTimes(1), inputTimes(2), inputTimes(3), createResultDate(), memberNumber);
    }

    public void createMember(String email, String password) throws IOException {
        backend.createMember(email, password, firstName(), lastName(), email(), password(), LocalDate.parse(IO.readln("Input birthday: YYYY,MM,DD ")), memberType());
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
                default -> IO.println("Give the right input...");
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

    public MemberType memberType() {
        return MemberType.valueOf(IO.readln("Input member type (EXERCISER/COMPETITOR/PASSIVE): ").trim().toUpperCase());
    }

    public int inputTimes(int i){
        switch(i){
            case 1 -> {
                return Integer.parseInt(IO.readln("Input amount of hours: "));
            }
            case 2 -> {
                return Integer.parseInt(IO.readln("Input amount of minutes: "));
            }
            case 3 -> {
                return Integer.parseInt(IO.readln("Input amount of seconds: "));
            }
        }
        return 0;
    }

    public LocalDate createResultDate(){
        return LocalDate.parse(IO.readln("Input Result Date in format (year,month,day): "));
    }

    public void printCompetitions(Competition competition){
        IO.println(competition);
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
            default -> IO.println("Give the right input...");
        }
        return null;
    }

    public Disciplines chooseDisciplines(){
        IO.println("""
                1. Backcrawl
                2. Breast Swimming
                3. Butterfly
                4. Crawl
                """);

        switch (Integer.parseInt(IO.readln("Input..."))){
            case 1 -> {
                return Disciplines.BACKCRAWL;
            }
            case 2 -> {
                return Disciplines.BREASTSWIMMING;
            }
            case 3 -> {
                return Disciplines.BUTTERFLY;
            }
            case 4 -> {
                return Disciplines.CRAWL;
            }
            default -> IO.println("Give the right input...");
        }
        return null;
    }

    public void printMembershipReceipt(Member member, int year){
        MembershipPayment payment = backend.getMembershipPayment(member.getMemberNumber(), year);

        if (payment != null && payment.isPaid()){
            IO.println("""
                --- Membership Receipt ---
                Name: %s %s
                Member number: %d
                Year: %d
                Amount: %d kr.
                --------------------------
                """.formatted(
                    member.getName(),
                    member.getLastName(),
                    member.getMemberNumber(),
                    payment.getYear(),
                    payment.getAmount()
            ));
        }
    }

    public void payMembershipFee(Member member){
        int year = LocalDate.now().getYear();
        int amount = backend.calculateMembershipFee(member);

        IO.println("Your membership fee is: " + amount + " kr.");
        String confirmation = IO.readln("Do you want to pay? (yes/no)");

        if (confirmation.equalsIgnoreCase("Yes")){
            boolean paid = backend.payMembershipFee(member, year);

            if (paid) {
                IO.println("Payment successfull");
                printMembershipReceipt(member, year);
            } else {
                IO.println("Membership fee has already been paid.");
            }
        } else {
            IO.println("Payment cancelled.");
        }
    }

}
