package trouble.engine.board

class PieceMover {
  def movePiece(originalPosition: BoardPosition, newPosition: BoardPosition) {
    if (newPosition != originalPosition) {
      newPosition.gamePiece = originalPosition.gamePiece;
      originalPosition.gamePiece = null;
    }
  }

  def movePiece (board: GameBoard,  originalPosition: BoardPosition, newPosition: BoardPosition) : GameBoard = {
    val newBoard = board.createCopy();
    val newBoardFromPosition = newBoard.flattenBoardPositions().find(_ == originalPosition).get;
    val newBoardToPosition = newBoard.flattenBoardPositions().find(_ == newPosition).get;
    if (newPosition != originalPosition) {
      movePiece(newBoardFromPosition, newBoardToPosition);
    }
    newBoard;
  }

  def movePieces(board: GameBoard, positions: (PieceColor.Value, Int, String)*) : GameBoard = {
    val newBoard = board.createCopy();
    val boardPositions: List[BoardPosition] = newBoard.flattenBoardPositions()
    for (tuple <- positions) {
      val piece = new GamePiece(tuple._1, tuple._2);
      val positionName = tuple._3;
      val originalPosition: BoardPosition = boardPositions.find(p => p.isOccupied && p.gamePiece == piece).get;
      val newPosition: BoardPosition = boardPositions.find(p => p.name == positionName).get;
      movePiece(originalPosition, newPosition);
    }
    newBoard;
  }
}