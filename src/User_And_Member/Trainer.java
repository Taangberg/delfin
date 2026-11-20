package User_And_Member;
import Disciplines.*;
import java.time.LocalDate;

public class Trainer extends User {

    public Trainer(String email, String password){
        super(email, password);
    }

    public void changeCompetitorStatus(Member member, Boolean choice){
        if(choice == true){
            member.setMemberType(MemberType.COMPETITOR);
        } else{
            member.setMemberType(MemberType.EXERCISER);
        }
    }

    public Result createResult(Disciplines disciplines, int resultHours, int resultMinutes, int resultSeconds, LocalDate resultDate, int memberNumber){
        return new Result(resultHours,resultMinutes,resultSeconds,resultDate,disciplines,memberNumber);
    }

    /*
    Menu punkter;
    * View tider
    * Opret tider
    * Select competitors
    * Deselect competitors
    Juniors / Seniors?
    Gennemgå tider i hver disciplin
    Tider menu;
    *Backcrawl
    *Breastswimming
    *Butterfly
    *Crawl
     */
}
