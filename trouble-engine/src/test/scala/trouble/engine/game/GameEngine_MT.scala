package trouble.engine.game

import ai.NoBrainStrategy
import org.junit.{Ignore, Test}
import trouble.engine.board.PieceColor

class GameEngine_MT {
  @Test def runTwoPersonGame() {
    val engine = new GameEngine();
    engine.startGame(new GameClient("Scott", new NoBrainStrategy(), PieceColor.Red), new GameClient("Gavin", new NoBrainStrategy(), PieceColor.Green));
  }

  @Test def runFourPersonGame() {
    val engine = new GameEngine();
    engine.startGame(new GameClient("Scott", new NoBrainStrategy(), PieceColor.Red),
      new GameClient("Pam", new NoBrainStrategy(), PieceColor.Blue),
      new GameClient("Ian", new NoBrainStrategy(), PieceColor.Yellow),
      new GameClient("Gavin", new NoBrainStrategy(), PieceColor.Green));
  }

  @Test def runTwoPersonGameTwoColorsEach() {
    for (i <- (0 to 100)) {
      val engine = new GameEngine();
      engine.startGame(new GameClient("Scott", new NoBrainStrategy(), PieceColor.Red, PieceColor.Blue), new GameClient("Gavin", new NoBrainStrategy(), PieceColor.Green, PieceColor.Yellow));
    }
  }
}