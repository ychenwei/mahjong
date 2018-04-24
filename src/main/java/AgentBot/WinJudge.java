package AgentBot;

import com.github.blovemaple.mj.object.TileSuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * For CS5100 Final Project
 * @author Luyao Wang, Chenwei Yin
 */

public class WinJudge {
  private HandFactory factory = HandFactory.getHandFactory();

  public WinJudge() {
  }

  public boolean isWin(Map<TileSuit, int[]> hand) {
//    System.out.println("enter win judge");
    int completeNum = 0;
    int inCompleteNum = 0;
    int tuple = 0;
    int single = 0;
    for (Map.Entry<TileSuit, int[]> entry : hand.entrySet()) {
//      System.out.println("examine suit " + entry.getKey().toString() + " ");
      int[] suit = entry.getValue();
      for (int i : suit) {
//        System.out.print(i + " ");
      }
//      System.out.println();
      List<List<String>> combinations;
      if (entry.getKey().equals(TileSuit.ZI)) {
//        System.out.println("enter zi");
        combinations = new ArrayList<>(factory.combinationForWindDragon(suit));
      } else {
//        System.out.println("enter not zi");
        combinations = new ArrayList<>(factory.getCombination(suit));
//        System.out.println("leave not zi");
      }
//      System.out.println("enter gradeHelper");
      Map<String, List<Integer>> helper = factory.gradeHelper(combinations
              .get(0), suit);
//      System.out.println("leave gradeHelper");
      List<Integer> complete = helper.get(HandFactory.COMPLETE);
      completeNum += complete.get(0) + complete.get(1);
      inCompleteNum += helper.get(HandFactory.INCOMPLETE).get(0);
      tuple += helper.get(HandFactory.TUPLE).get(0);
      single += helper.get(HandFactory.SINGLE).size();
    }
//    System.out.println("leave win judge");
    return inCompleteNum == 0 && tuple == 1 && single == 0;
  }
}
