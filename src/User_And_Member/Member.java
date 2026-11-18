package User_And_Member;

import java.time.LocalDate;

public class Member extends User{
String name;
String lastName;
LocalDate birthday;
int memberNumber;
boolean competitor;
boolean activeMember;

    public Member(String name, String lastName, String email, String password, LocalDate birthday, int memberNumber, boolean competitor, boolean activeMember){
        this.name = name;
        this.lastName = lastName;
        super(email, password);
        this.birthday = birthday;
        this.memberNumber = memberNumber;
        this.competitor = competitor;
        this.activeMember = activeMember;
    }

    public boolean isCompetitor() {
        return competitor;
    }
    public String getName(){
        return name;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setLastName(String newLastName){
        lastName = newLastName;
    }
    public void setEmail(String newEmail){
        email = newEmail;
    }


    public int getMemberNumber(){
        return memberNumber;
    }

    public boolean isActiveMember(){
        return activeMember;
    }

    public LocalDate getBirthday(){
        return birthday;
    }

    public String toString() {
        return name + " " + lastName + " " + email + " " + birthday + " " + memberNumber + " " + competitor + " " + activeMember;
    }
}
