package trouble.engine.game

import trouble.engine.board.{GameMove, PieceColor, GameBoard}

case class GameClient (player: String, strategy: Strategy, colors: PieceColor.Value*) {
  def makeMove(possibleMoves: List[GameMove]) : GameMove = {
    strategy.makeMove(possibleMoves, colors.toArray);
  }

  override def toString = "%s (%s)".format(player, colors.mkString(","));
}