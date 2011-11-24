package trouble.engine.game.ai

import trouble.engine.game.Strategy
import trouble.engine.board.{GameMove, PieceColor, GameBoard}

class NoBrainStrategy extends Strategy {

  def scoreMove(move: GameMove, colors: Array[PieceColor.Value]): Int = {
    0;
  }
}