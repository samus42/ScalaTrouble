package trouble.engine.game.ai

import trouble.engine.game.Strategy
import trouble.engine.board.{BoardPosition, PieceColor, GameMove}

class GoalOrientedStrategy extends Strategy {
  val OwnTeamGoalPosition = 100;

  def makeMove(possibleMoves: List[GameMove], colors: Array[PieceColor.Value]): GameMove = {
    var currentMove: GameMove = null;
    var currentScore = -10000;
    for (move <- possibleMoves) {
      val score = scoreMove(move, colors);
      if (score > currentScore) {
        currentScore = score;
        currentMove = move;
      }
    }
    currentMove;
  }

  private def scoreMove(move: GameMove, colors: Array[PieceColor.Value]): Int = {
    var score = 0;

    for (color <- colors) {
      val path: List[BoardPosition] = move.resultingBoard.getPath(color)
      for(position <- path) {
        if (position.isOccupied && position.gamePiece.color == color) {
          val index = path.indexOf(position);
          score += (if (index < path.length - 4) (index * 1) else OwnTeamGoalPosition);
        }
      }
    }
    score;
  }
}