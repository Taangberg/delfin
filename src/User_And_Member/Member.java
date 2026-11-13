package User_And_Member;

public class Member extends User{
String name;
String lastName;
int age;
int memberNumber;
boolean competitor;

    public Member(String name, String lastName, String email, String password, int age, int memberNumber, boolean competitor){
        this.name = name;
        this.lastName = lastName;
        super(email, password);
        this.age = age;
        this.memberNumber = memberNumber;
        this.competitor = competitor;
    }

    public int getAge() {
        return age;
    }

    public boolean isCompetitor() {
        return competitor;
    }

    public String toString() {
        return name + " " + lastName + " " + email + " " + age + " " + memberNumber + " " + competitor;
    }
}
