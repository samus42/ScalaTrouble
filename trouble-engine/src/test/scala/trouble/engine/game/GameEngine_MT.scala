package trouble.engine.game

import ai.{GoalOrientedStrategy, SuperAggressiveStrategy, NoBrainStrategy}
import trouble.engine.board.PieceColor
import org.junit.{Before, Ignore, Test}
import collection.mutable.{HashMap, Map}
;

class GameEngine_MT {
  val Scott = "Scott";
  val Pam = "Pam";
  val Gavin = "Gavin";
  val Ian = "Ian";
  val MaxGames = 100;
  val engine = new GameEngine(new QuietMessageClient());

  private def initScoreMap(): Map[String, Int] = {
    val map = new HashMap[String, Int]();
    map += ("Scott" -> 0);
    map += ("Pam" -> 0);
    map += ("Gavin" -> 0);
    map += ("Ian" -> 0);
  }

  @Test def runTwoPersonGame() {
    val map = initScoreMap();
    for (i <- 0 until MaxGames) {
      recordResult(map, engine.startGame(new GameClient(Scott, new GoalOrientedStrategy(), PieceColor.Red), new GameClient(Gavin, new SuperAggressiveStrategy(), PieceColor.Green)));
    }
    printResults(map);
  }

  @Test def runFourPersonGame() {
    val map = initScoreMap();
    for (i <- 0 until MaxGames) {
      recordResult(map, engine.startGame(new GameClient(Scott, new GoalOrientedStrategy(), PieceColor.Red),
        new GameClient(Pam, new NoBrainStrategy(), PieceColor.Blue),
        new GameClient(Ian, new NoBrainStrategy(), PieceColor.Yellow),
        new GameClient(Gavin, new SuperAggressiveStrategy(), PieceColor.Green)));
    }
    printResults(map);
  }

  @Test def runTwoPersonGameTwoColorsEach() {
    val map = initScoreMap();
    for (i <- 0 until MaxGames) {
      recordResult(map, engine.startGame(new GameClient(Scott, new GoalOrientedStrategy(), PieceColor.Red, PieceColor.Blue), new GameClient(Gavin, new SuperAggressiveStrategy(), PieceColor.Green, PieceColor.Yellow)));
    }
    printResults(map);
  }


  private def recordResult(map: Map[String, Int], winner: GameClient) {
    map(winner.player) = map(winner.player) + 1;
  }

  private def printResults(map: Map[String, Int]) {
    for (key <- map.keys) {
      if (map(key) > 0) println("%s won %s times".format(key, map(key)));
    }
  }
}