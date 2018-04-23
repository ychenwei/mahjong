package AgentTest;

import com.github.blovemaple.mj.object.TileSuit;
import com.github.blovemaple.mj.object.TileType;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import AgentBot.HandGenerator;

public class HandGeneratorTest {
  private HandGenerator generator = new HandGenerator();

  TileType[] numbers = new TileType[9];
  TileType[] sticks = new TileType[9];
  TileType[] balls = new TileType[9];
  TileType[] windsAndDragons = new TileType[7];

  @Before
  public void setup(){
    for(int i =0; i<9; i++){
      numbers[i] = TileType.of(TileSuit.WAN,i+1);
      sticks[i] = TileType.of(TileSuit.TIAO, i+1);
      balls[i] = TileType.of(TileSuit.BING,i+1);
      if(i<7){
        windsAndDragons[i] = TileType.of(TileSuit.ZI,i);
      }
    }
  }

  @Test
  public void test(){
    int[] numberSuit = {1,3,2,1,2,2,1,2,1};
//    int[] stickSuit = {4,0,1,2,3,0,0,0,1};
//    int[] ballSuit = {2,1,1,4,0,1,2,1,0};
//    int[] windsAndDragons = {2,1,2,0,0,0,3};

//    System.out.println(sum(numberSuit) + sum(stickSuit)
//    +sum(ballSuit) + sum(windsAndDragons));
    Map<TileSuit, int[]> map = new HashMap<>();
    map.put(TileSuit.WAN,numberSuit);
//    map.put(TileSuit.TIAO, stickSuit);
//    map.put(TileSuit.BING, ballSuit);
//    map.put(TileSuit.ZI, windsAndDragons);
//    List<List<TileType>> set3 = generator.genereate3Sets(map);
//    System.out.println(set3.size() + ":" + set3.toString());
////    System.out.println(generator.genereate2Sets(map).toString());
//    System.out.println(generator.generateRandom(map));

    System.out.println(generator.randomHandsFor3Players(map,new int[]{4, 4,
            4}));
  }

  private int sum(int[] array){
    int sum = 0;
    for(int i=0; i<array.length;i++){
      sum += array[i];
    }
    return sum;
  }





}
