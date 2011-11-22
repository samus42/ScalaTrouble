package trouble.engine.board


case class GameMove(_fromPosition: BoardPosition, _toPosition: BoardPosition, _resultingBoard: GameBoard) {
  require(_fromPosition != null);
  require(_toPosition != null);
  require(_resultingBoard != null);
  require(_fromPosition.isOccupied, "From Position must be occupied!");
  
  def toPosition = _toPosition.copy();
  def fromPosition = _fromPosition.copy();
  def resultingBoard = _resultingBoard;

  def isCapture = toPosition.isOccupied;
  override def toString = {
    "%s moves from %s to %s%s".format(fromPosition.gamePiece.color,
      fromPosition.name,
      toPosition.name,
      if (this.isCapture) " (Captures %s)".format(toPosition.gamePiece.color) else "");
  };
}