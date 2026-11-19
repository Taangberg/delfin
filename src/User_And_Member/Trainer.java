package User_And_Member;
import Disciplines.*;
import java.time.LocalDate;

public class Trainer extends User {

    public Trainer(String email, String password){
        super(email, password);
    }

    public Result createResult(int resultHour, int resultMinute, int resultSeconds, LocalDate resultDate, Disciplines discipline, int memberNumber){
        return new Result(resultHour, resultMinute, resultSeconds, resultDate, discipline, memberNumber);
    }

    public void changeCompetitorStatus(Member member, Boolean choice){
        if(choice == true){
            member.setMemberType(MemberType.COMPETITOR);
        } else{
            member.setMemberType(MemberType.EXERCISER);
        }
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
