package User_And_Member;

import java.time.LocalDate;
import java.time.Period;

public class Member extends User {
    String name;
    String lastName;
    LocalDate birthday;
    int memberNumber;
    MemberType memberType;

    public Member(String name, String lastName, String email, String password, LocalDate birthday, int memberNumber, MemberType memberType) {
        this.name = name;
        this.lastName = lastName;
        super(email, password);
        this.birthday = birthday;
        this.memberNumber = memberNumber;
        this.memberType = memberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public boolean isJunior() {
        return getAge() < 18;
    }

    public boolean isJuniorAtDate(LocalDate resultDate) {
        return Period.between(birthday, resultDate).getYears() < 18;
    }

    public boolean isOld() {
        return getAge() >= 60;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType newMemberType) {
        memberType = newMemberType;
    }

    public String toString() {
        return name + " " + lastName + " " + email + " " + birthday + " " + memberNumber + " " + memberType;
    }
}
