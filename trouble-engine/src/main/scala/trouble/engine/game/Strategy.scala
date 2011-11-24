package trouble.engine.game

import trouble.engine.board.{GameMove, PieceColor, GameBoard}

trait Strategy {
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

  def scoreMove(move: GameMove, colors: Array[PieceColor.Value]): Int;
}