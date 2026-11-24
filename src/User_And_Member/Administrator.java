package User_And_Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Administrator extends User {

    public Administrator(String email, String password) {
        super(email, password);
    }

    // Create skal have connection til backend hvilket tilføjer til arrayList og backend har forbindelse til UI, hvilket admin kan skrive i.
    public Member createNewMember(List<User> users, String firstName, String lastName, String email, String password, LocalDate birthday, MemberType memberType) {
        Random random = new Random();
        List<Member> members = new ArrayList<>();
        int memberNumber = 0;
        boolean running = true;
        int count = 0;
        while (running) {
            memberNumber = random.nextInt(10000);
            for (User user : users) {
                if (user instanceof Member member) {
                    members.add(member);
                }
            }
            if (memberNumber >= 1000) {
                for (Member member : members) {
                    if (memberNumber / member.getMemberNumber() != 0) {
                        count++;
                    }
                    if (count == members.size()) { // Jeg er stuck, jeg kan ikke huske hvorfor at denne ikke kan nåes, det samme med membernumber
                        running = false;
                    }


                }
            }
        }
        return new Member(firstName, lastName, email, password, birthday, memberNumber, memberType);
    }
}
