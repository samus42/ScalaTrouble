package trouble.engine.game

import trouble.engine.board.{GameMove, PieceColor, GameBoard}

trait Strategy {
  def makeMove(possibleMoves: List[GameMove], colors: PieceColor.Value*) : GameMove
}