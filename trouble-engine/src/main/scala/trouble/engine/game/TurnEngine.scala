package trouble.engine.game

import collection.mutable.Queue
import trouble.engine.board.{GameMove, MoveGenerator, GameBoard}

class TurnEngine(messageClient: MessageClient) {
  def this() = this(new ConsoleMessageClient());
  val moveGenerator = new MoveGenerator();

  def executeTurn(board: GameBoard, playerQueue: Queue[GameClient], roll: Int): GameBoard = {
    val currentPlayer: GameClient = playerQueue.head;
    var newBoard = board;
    messageClient.displayMessage("Player %s rolled a %d".format(currentPlayer, roll));
    val possibleMoves: List[GameMove] = moveGenerator.generate(newBoard, roll, currentPlayer.colors.toArray: _*);
    messageClient.displayMessage("Roll of %d generated %d possible moves".format(roll, possibleMoves.length));
    if (!possibleMoves.isEmpty) {
      val moveTaken = currentPlayer.makeMove(possibleMoves);
      messageClient.displayMessage(moveTaken.toString)
      newBoard = moveTaken.resultingBoard;
      messageClient.displayMessage("New Board = " + newBoard);
    }
    if (roll != 6) {
      playerQueue.enqueue(playerQueue.dequeue());
    } else {
      messageClient.displayMessage("%s rolls again!".format(currentPlayer));
    }
    newBoard;
  }
}