import User_And_Member.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Backend {
    List<User> users = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    ConsoleUI ui;

    public Backend(List<User> users, List<Member> members) {
        this.users = users;
        this.members = members;
    }

    public void login(String email, String password) {
        for (User user : users) {
            if (user instanceof Administrator) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.adminRun(email, password);
                }
            } else if (user instanceof Trainer) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    ui.trainerRun(email, password);
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

    public void createMember(String adminEmail, String adminPassword, String firstName, String lastName, String email, String password, LocalDate birthday, MemberType memberType) {
        Administrator administrator = new Administrator(adminEmail, adminPassword);
        Member member = administrator.createNewMember(members, firstName, lastName, email, password, birthday, memberType);
        members.add(member);
    }

    public void editMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                if (ui.editMember() == EditMember.FIRSTNAME) {
                    member.setName(ui.firstName());
                } else if (ui.editMember() == EditMember.LASTNAME) {
                    member.setLastName(ui.lastName());
                } else if (ui.editMember() == EditMember.EMAIL) {
                    member.setEmail(ui.email());
                } else if (ui.editMember() == EditMember.MEMBER_TYPE) {
                   MemberType newType = ui.memberType();
                   if(newType == MemberType.COMPETITOR) {
                       IO.println("User not allowed to edit competitors");
                       return;
                   }
                   member.setMemberType(newType);
                }
            }
        }
    }

    public void removeMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                members.remove(member);
            }
        }
    }


    public void changeCompetitorStatus(String email, String password, int memberNumber, boolean choice){
        Trainer trainer = new Trainer(email, password);
        trainer.changeCompetitorStatus(printMember(memberNumber), choice);
    }

    public void printMembers() {
        for (Member member : members) {
            ui.printMembers(member);
        }
    }

    public Member printMember(int memberNumber) {
        for (Member member : members) {
            if (member.getMemberNumber() == memberNumber) {
                return member;
            }
        }
        return null;
    }

    public void printJuniors() {
        List<Member> juniors = new ArrayList<>();

        for(Member member : members) {
            if(member.isJunior()) {
                juniors.add(member);
            }
        }
        for(Member junior : juniors) {
            ui.printMembers(junior);
        }
    }

    public void printSeniors() {
        List<Member> seniors = new ArrayList<>();

        for(Member member : members) {
            if(!member.isJunior()) {
                seniors.add(member);
            }
        }
        for(Member senior : seniors) {
            ui.printMembers(senior);
        }
    }

    public void printExercisers() {
        List<Member> exercisers = new ArrayList<>();

        for(Member member : members) {
            if(member.getMemberType() == MemberType.EXERCISER) {
                exercisers.add(member);
            }
        }
        for (Member exerciser : exercisers) {
            ui.printMembers(exerciser);
        }
    }

    public void printCompetitorsU18() {
        List<Member> u18 = new ArrayList<>();

        for(Member member : members) {
            if(member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                u18.add(member);
            }
        }
        for(Member juniors : u18) {
            ui.printMembers(juniors);
        }
    }

    public void printCompetitorsO18() {
        List<Member> o18 = new ArrayList<>();

        for(Member member : members) {
            if(!member.isJunior() && member.getMemberType() == MemberType.COMPETITOR) {
                o18.add(member);
            }
        }
        for(Member seniors : o18) {
            ui.printMembers(seniors);
        }
    }


    public void printPassive() {
        List<Member> passive = new ArrayList<>();

        for(Member member : members) {
            if(member.getMemberType() == MemberType.PASSIVE) {
                passive.add(member);
            }
        }
        for(Member passives : passive) {
            ui.printMembers(passives);
        }
    }
}

