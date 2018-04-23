package AgentBot;

import com.github.blovemaple.mj.object.TileSuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WinJudge {
  private HandFactory factory= HandFactory.getHandFactory();

  public WinJudge(){}

  public boolean isWin(Map<TileSuit, int[]> hand){
    int completeNum = 0;
    int inCompleteNum = 0;
    int tuple = 0;
    int single = 0;
    for(Map.Entry<TileSuit, int[]> entry:hand.entrySet()){
      int[] suit = entry.getValue();
      List<List<String>> combinations;
      if(entry.getKey().equals(TileSuit.ZI)){
        combinations = new ArrayList<>(factory.combinationForWindDragon(suit));
      }else {
        combinations = new ArrayList(factory.getCombination(suit));
      }
      Map<String, List<Integer>> helper = factory.gradeHelper(combinations
              .get(0),suit);
      List<Integer> complete = helper.get(HandFactory.COMPLETE);
      completeNum += complete.get(0) + complete.get(1);
      inCompleteNum += helper.get(HandFactory.INCOMPLETE).get(0);
      tuple += helper.get(HandFactory.TUPLE).get(0);
      single += helper.get(HandFactory.SINGLE).size();
    }
    return inCompleteNum == 0 && tuple ==1 && single == 0;
  }
}
