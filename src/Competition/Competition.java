package Competition;


import Disciplines.Result;

import java.util.ArrayList;
import java.util.List;

public class Competition {
     private String arrangement;
     private String location;
     private Result result;
     List<Result> results = new ArrayList<>();

     public Competition(String arrangement, String location, Result result) {
         this.arrangement = arrangement;
         this.location = location;
         this.result = result;
     }

     public void addResult(Result result){
         results.add(result);
     }

 }
