package trouble.engine.game

import util.Random
import trouble.engine.board.{PieceColor, MoveGenerator, GameBoard}
import collection.mutable.Queue

class GameEngine {
  def startGame(players: GameClient*) : GameClient = {
    require(players.length > 0 && players.length <= 4);

    var gameBoard = new GameBoard();

    val playerQueue = Queue[GameClient]();
    players.foreach(p => playerQueue.enqueue(p));
    val turnEngine = new TurnEngine();
    while (determineWinner(players.toArray, gameBoard) == null) {
      val roll: Int = Random.nextInt(6) + 1
      gameBoard = turnEngine.executeTurn(gameBoard, playerQueue, roll);
    }
    val winner: GameClient = determineWinner(players.toArray, gameBoard)
    println(winner + " wins!");
    winner;
  }

  private def determineWinner(players: Array[GameClient], gameBoard : GameBoard) :GameClient = {
    val winners = for (player <- players if player.colors.forall(gameBoard.finishedTeams.contains(_))) yield player;
    if (winners.isEmpty) null;
    else winners(0);
  }
}