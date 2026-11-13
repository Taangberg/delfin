package Activities;
import User_And_Member.Member;
import java.util.ArrayList;
import java.util.List;

public class Activities {

    private final List<Member> juniors = new ArrayList<>();
    private final List<Member> seniors = new ArrayList<>();
    private final List<Member> exercisers = new ArrayList<>();
    private final List<Member> juniorCompetitors = new ArrayList<>();
    private final List<Member> seniorCompetitors = new ArrayList<>();

    public List<Member> getJuniors() {
        return juniors;
    }

    public List<Member> getSeniors() {
        return seniors;
    }

    public List<Member> getExercisers() {
        return exercisers;
    }

    public List<Member> getJuniorCompetitors() {
        return juniorCompetitors;
    }

    public List<Member> getSeniorCompetitors() {
        return seniorCompetitors;
    }

    public void addMember(Member member) { //i UI skal der være en metode som laver et member, og den metoder bliver givet som member
        boolean junior = member.getAge() < 18;

        if (junior) {
            juniors.add(member);
        } else {
            seniors.add(member);
        }

        if (!member.isCompetitor()) {
            exercisers.add(member);
        } else if (junior) {
            juniorCompetitors.add(member);
        } else {
            seniorCompetitors.add(member);
        }
    }

}
