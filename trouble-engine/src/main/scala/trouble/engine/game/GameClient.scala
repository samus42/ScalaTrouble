package trouble.engine.game

import trouble.engine.board.{GameMove, PieceColor, GameBoard}

case class GameClient (player: String, strategy: Strategy, color: PieceColor.Value) {
  def makeMove(possibleMoves: List[GameMove]) : GameMove = {
    strategy.makeMove(possibleMoves, color);
  }

  override def toString = "%s (%s)".format(player, color);
}