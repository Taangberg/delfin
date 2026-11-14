package User_And_Member;

import java.time.LocalDate;

public class Member extends User{
String name;
String lastName;
LocalDate birthday;
int memberNumber;
boolean competitor;

    public Member(String name, String lastName, String email, String password, LocalDate birthday, int memberNumber, boolean competitor){
        this.name = name;
        this.lastName = lastName;
        super(email, password);
        this.birthday = birthday;
        this.memberNumber = memberNumber;
        this.competitor = competitor;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public boolean isCompetitor() {
//        return competitor;
//    }
//
    public int getMemberNumber(){
        return memberNumber;
    }
//
//    public String toString() {
//        return name + " " + lastName + " " + email + " " + age + " " + memberNumber + " " + competitor;
//    }
}
