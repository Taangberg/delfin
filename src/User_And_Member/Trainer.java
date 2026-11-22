package User_And_Member;
import Competition.Competition;
import Disciplines.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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

    public Competition createCompetition(List<Competition> competitions, String arrangement, String location, LocalDate date){
        Random random = new Random();
        boolean running = true;
        int count = 0;
        while(running) {
            int competitionID = random.nextInt(100000);
            if(competitionID > 9999){
                for(Competition competition : competitions){
                    if (competition.getCompetitionID()/competitionID != 0){
                        count++;
                    }
                }
                if(count == competitions.size()){
                    return new Competition(competitionID, arrangement, location, date);
                }
            }
        }
        return null;
    }
}
