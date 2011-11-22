package trouble.engine.game

import util.Random
import trouble.engine.board.{PieceColor, MoveGenerator, GameBoard}
import collection.mutable.Queue

class GameEngine {
  def startGame(players: GameClient*) {
    require(players.length > 0 && players.length <= 4);

    var gameBoard = new GameBoard();

    val playerQueue = Queue[GameClient]();
    players.foreach(p => playerQueue.enqueue(p));
    val turnEngine = new TurnEngine();
    while (gameBoard.finishedTeams.isEmpty) {
      val roll: Int = Random.nextInt(6) + 1
      gameBoard = turnEngine.executeTurn(gameBoard, playerQueue, roll);
    }
    println(gameBoard.finishedTeams + " wins!");
  }
}