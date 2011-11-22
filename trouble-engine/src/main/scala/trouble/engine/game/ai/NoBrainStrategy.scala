package trouble.engine.game.ai

import trouble.engine.game.Strategy
import trouble.engine.board.{GameMove, PieceColor, GameBoard}

class NoBrainStrategy extends Strategy {
  def makeMove(possibleMoves: List[GameMove], colors: Array[PieceColor.Value]): GameMove = {
    possibleMoves(0);
  }
}