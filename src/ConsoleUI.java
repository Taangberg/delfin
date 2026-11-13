import java.util.List;

public class ConsoleUI {
    private final Activities activities = new Activities();

    public void start() {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = Integer.parseInt(IO.readln("Choose a option, to continue "));
            switch (choice) {
                case 1 -> activities.addMember(createMember());
                case 2 -> showMembers();
                case 3 -> running = false;
                default -> IO.println("Invalid input");
            }
        }
    }

    private void showMenu() {
        IO.println();
        IO.println(" 1. | Create member");
        IO.println(" 2. | Show");
        IO.println(" 3. | Exit");
        IO.println();
    }

    private Member createMember() {
        String firstName = IO.readln("Input first name: ");
        String lastName = IO.readln("Input last name: ");
        String email = IO.readln("Input Email: ");
        String password = IO.readln("Input new password: ");
        int age = Integer.parseInt(IO.readln("Input age: "));
        int memberNumber = Integer.parseInt(IO.readln("Input new member number: "));
        boolean competitor = IO.readln("Competitor? (yes/no): ").trim().equalsIgnoreCase("yes");

        Member member = new Member(firstName, lastName, email, password, age, memberNumber, competitor);
        IO.println("Created: " + member);
        return member;
    }

    private void showMembers() {
        showMembersMenu();
        int choice = Integer.parseInt(IO.readln("Choose a option, to continue "));
        switch (choice) {
            case 1 -> showJuniors();
            case 2 -> showSeniors();
            case 3 -> showExercisers();
            case 4 -> showJuniorCompetitors();
            case 5 -> showSeniorCompetitors();
            default -> IO.println("Invalid input");
        }
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
}
