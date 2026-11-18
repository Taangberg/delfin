package Competition;


import Disciplines.Result;

import java.util.List;

public class Competition {
     private String arrangement;
     private String location;
     private List<Result> results;

     public Competition(String arrangement, String location, List<Result> results) {
         this.arrangement = arrangement;
         this.location = location;
         this.results = results;
     }

     public List<Result> getResult(){
         return results;
     }
 }
