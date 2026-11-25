import User_And_Member.Administrator;
import User_And_Member.Member;
import User_And_Member.MemberType;
import User_And_Member.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Administrator_test {

    @Test
    public void test(){
        List<User> members = new ArrayList<>();
        Member daniel = new Member("Daniel", "Oxbøll", "njasdbksa", "ndasjdn", LocalDate.of(2005,2,23), 1234, MemberType.EXERCISER);
        members.add(0,daniel);
        Administrator administrator = new Administrator("Test", "test");
        Member member2 = administrator.createNewMember(members, "Lucas", "Christensen", "dsnado", "fjdsno", LocalDate.of(2006,05,23), MemberType.COMPETITOR);
        IO.println(member2.getMemberNumber());
    }
}
