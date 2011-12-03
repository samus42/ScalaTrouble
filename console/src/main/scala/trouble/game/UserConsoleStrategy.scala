package trouble.game

import trouble.engine.game.Strategy
import trouble.engine.board.{PieceColor, GameMove}


class UserConsoleStrategy extends Strategy {
  override def makeMove(possibleMoves: List[GameMove], colors: Array[PieceColor.Value]): GameMove = {
    var move: GameMove = null;
    if (possibleMoves.size > 1) {
      move = selectMove(possibleMoves)
    } else {
      println("Only one possible move, automatically taking it.")
      move = possibleMoves(0);
    }
    move;
  }

  private def selectMove(possibleMoves: scala.List[GameMove]): GameMove = {
    var selection = -1;
    do {
      try {
        println("Please choose 1 of the possible Moves:");
        displayMoves(possibleMoves);
        selection = readLine().toInt;
        if (selection < 1 || selection > possibleMoves.size) {
          println("Invalid selection, please select again.");
          selection = -1;
        }
      } catch {
        case ex: Exception => println("Invalid selection, please select again.")
      }
    } while (selection < 1);

    possibleMoves(selection - 1);
  }

  private def displayMoves(possibleMoves: List[GameMove]) {
    var i = 1;
    for (move <- possibleMoves) {
      println("%s) %s".format(i, move));
      i += 1;
    }
  }

  def scoreMove(move: GameMove, colors: Array[PieceColor.Value]) = 0
}