package trouble.engine.board


case class BoardPosition (name: String, var gamePiece: GamePiece) {
  def this(name: String) = this(name, null);

  def isOccupied = gamePiece != null;

  override def toString = name + (if (gamePiece != null) " (" + gamePiece.toString + ")" else "");

  override def equals(obj: Any) = obj match {
    case that: BoardPosition => this.name == that.name;
    case _ => false;
  }
}