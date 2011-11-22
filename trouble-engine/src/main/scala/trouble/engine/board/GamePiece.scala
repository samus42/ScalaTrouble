package trouble.engine.board

object PieceColor extends Enumeration {
  type PieceColor = Value;
  val Red = Value("red");
  val Blue = Value("blue");
  val Green = Value("green");
  val Yellow = Value("yellow");

  def orderedList : List[PieceColor.Value] = List(PieceColor.Red, PieceColor.Blue, PieceColor.Green, PieceColor.Yellow);
}

case class GamePiece (color: PieceColor.Value, number: Int) {
  override def toString = color + " - " + number;
}