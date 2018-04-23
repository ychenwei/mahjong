package AgentTest;

import com.github.blovemaple.mj.object.TileSuit;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import AgentBot.WinJudge;

public class WinTest {
  private WinJudge judge = new WinJudge();

  @Test
  public void test(){
    int[] numberSuit = {1,1,1,0,0,3,0,0,3};
    int[] stickSuit = {0,0,0,0,0,0,0,0,0};
    int[] ballSuit = {0,1,1,1,0,0,0,0,0};
    int[] windsAndDragons = {0,0,2,0,0,0,0};

    Map<TileSuit, int[]> map = new HashMap<>();
    map.put(TileSuit.WAN,numberSuit);
    map.put(TileSuit.TIAO, stickSuit);
    map.put(TileSuit.BING, ballSuit);
    map.put(TileSuit.ZI, windsAndDragons);

    System.out.println(judge.isWin(map));
  }

}
