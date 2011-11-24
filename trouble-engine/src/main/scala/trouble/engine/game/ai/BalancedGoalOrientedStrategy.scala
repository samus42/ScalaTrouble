package trouble.engine.game.ai

import trouble.engine.game.Strategy
import trouble.engine.board.{BoardPosition, PieceColor, GameMove}


class BalancedGoalOrientedStrategy extends Strategy {
  def scoreMove(move: GameMove, colors: Array[PieceColor.Value]): Int = {
    val OwnTeamStartingPosition = -200;
    val OtherTeamStartingPosition = 10;
    val OwnTeamGoalPosition = 60

    var score = 0;
    for (color <- PieceColor.orderedList) {
      val occupied = move.resultingBoard.getStartingPositions(color).filter(_.isOccupied).length;
      score += (if (colors.contains(color)) (OwnTeamStartingPosition * occupied) else (OtherTeamStartingPosition * occupied));
    }

    for (color <- colors) {
      val path: List[BoardPosition] = move.resultingBoard.getPath(color)
      for (position <- path) {
        if (position.isOccupied && position.gamePiece.color == color) {
          val index = path.indexOf(position);
          score += (if (index < path.length - 4) (index * 1) else OwnTeamGoalPosition);
        }
      }
    }
    score;
  }
}