package com.github.blovemaple.mj.local.foobot;

import static com.github.blovemaple.mj.object.TileRank.NumberRank.*;
import static com.github.blovemaple.mj.object.TileSuit.*;

import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.blovemaple.mj.object.PlayerInfo;
import com.github.blovemaple.mj.object.Tile;
import com.github.blovemaple.mj.object.TileType;
import com.github.blovemaple.mj.rule.simple.NormalWinType;
import com.github.blovemaple.mj.rule.win.WinInfo;

public class NormalWinTypeTest {
  private NormalWinType winType = NormalWinType.get();
  private PlayerInfo selfInfo;
  private Collection<Tile> candidates;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    candidates = Tile.all();

    selfInfo = new PlayerInfo();
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, LIU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, SI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, LIU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, QI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, LIU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, LIU), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, QI), 2));

    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, YI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, YI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, YI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, DONG_FENG), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, NAN), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, BEI), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, XI), 1));

    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, WU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, LIU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, QI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, BA), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, NAN), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, XI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, BEI), 2));
    // selfInfo.setLastDrawedTile(Tile.of(TileType.of(WAN, LIU), 1));

    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, YI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, BA), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, ER), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, JIU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, WU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, JIU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, NAN), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, NAN), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, XI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, BEI), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(ZI, BAI), 3));
    // selfInfo.setLastDrawedTile(Tile.of(TileType.of(BING, SI), 1));

    // WIN
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, LIU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, LIU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, LIU), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, WU), 1));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, WU), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, WU), 2));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 0));
    // selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 1));

//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 0));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, JIU), 0));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, JIU), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, JIU), 2));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, QI), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, BA), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, JIU), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, YI), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, ER), 1));
//		selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SAN), 1));

    selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, YI), 0));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, ER), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(WAN, SAN), 2));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, SAN), 2));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, SI), 2));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, WU), 2));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, JIU), 2));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(TIAO, JIU), 3));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SAN), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, SI), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, WU), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, QI), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, BA), 1));
    selfInfo.getAliveTiles().add(Tile.of(TileType.of(BING, JIU), 1));
  }

  @After
  public void tearDown() throws Exception {
  }

  // @Test
  public void test() {
    // winType.changingsForWin(selfInfo, 0,
    // candidates).forEach(System.out::println);
    winType.changingsForWin(selfInfo, 0, candidates).count();
  }

  @Test
  public void testWin() {
    WinInfo winInfo = WinInfo.fromPlayerTiles(selfInfo, null, false);
    System.out.println(winType.match(winInfo));
  }

  @Test
  public void testGetDiscard() {
    winType.getDiscardCandidates(selfInfo.getAliveTiles(), candidates).forEach(System.out::println);
  }

  @Test
  public void testParse() {
    WinInfo winInfo = WinInfo.fromPlayerTiles(selfInfo, null, false);
    winType.parseWinTileUnits(winInfo).forEach(unitList -> {
      unitList.forEach(System.out::println);
      System.out.println();
    });
  }

}
