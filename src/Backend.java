import User_And_Member.*;

import java.util.ArrayList;
import java.util.List;


public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members = new ArrayList<>();

    public Backend(List<User> users, List<Member> members){
        this.users = users;
        this.members = members;
    }



}
