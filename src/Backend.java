import User_And_Member.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    ConsoleUI ui;

    public Backend(List<User> users, List<Member> members, ConsoleUI ui) {
        this.users = users;
        this.members = members;
        this.ui = ui;
    }

    public void login(String email, String password) {
        for (User user : users) {
            if (user instanceof Administrator) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.adminRun(email, password);
                }
            } else if (user instanceof Trainer) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {

                }
            } else if (user instanceof Treasurer) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {

                }
            } else if (user instanceof Member) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {

                }
            }
        }
    }
    public void createMember(String adminEmail, String adminPassword, String firstName, String lastName, String email, String password, LocalDate birthday, boolean competitor){
        Administrator administrator = new Administrator(adminEmail, adminPassword);
        Member member = administrator.createNewMember(members, firstName, lastName, email, password, birthday, competitor);
        members.add(member);
    }

    public void editMember(int memberNumber){
        for(Member member : members){
            if(member.getMemberNumber() == memberNumber){
                if(ui.editMember() == EditMember.FIRSTNAME){
                    member.setName(ui.firstName());
                } else if (ui.editMember() == EditMember.LASTNAME) {
                    member.setLastName(ui.lastName());
                } else if (ui.editMember() == EditMember.EMAIL) {
                    member.setEmail(ui.email());
                }
            }
        }
    }
    public void removeMember(int memberNumber){
        for(Member member : members){
            if(member.getMemberNumber() == memberNumber){
                members.remove(member);
            }
        }
    }


    public void printMembers(){
        for (Member member : members){
            ui.printMembers(member);
        }
    }
    public Member printMember(int memberNumber){
        for(Member member : members){
            if(member.getMemberNumber() == memberNumber){
                return member;
            }
        }
        return null;
    }
}

