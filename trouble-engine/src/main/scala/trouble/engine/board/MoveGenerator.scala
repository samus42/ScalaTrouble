package trouble.engine.board


class MoveGenerator {
  private val pieceMover = new PieceMover();

  def generate(board: GameBoard, roll: Int, teams: PieceColor.Value*): List[GameMove] = {
    var newMoves = List[GameMove]();
    for (team <- teams) {
      val path: List[BoardPosition] = board.getPath(team)
      if (roll == 6) {
        if (!(path(0).isOccupied && teams.exists(_ == (path(0).gamePiece.color)))) {
          val availableStartingPosition = board.getNextAvailableStartingPosition(team)
          if (availableStartingPosition.isDefined) {
            val fromPosition: BoardPosition = availableStartingPosition.get
            val toPosition: BoardPosition = board.getPath(team)(0)
            val newBoard = pieceMover.movePiece(board, fromPosition, toPosition);
            newMoves = new GameMove(fromPosition, toPosition, newBoard) :: newMoves;
          }
        }
      }
      val teamPositions: List[BoardPosition] = path.filter(p => p.isOccupied && p.gamePiece.color == team);
      for (teamPosition <- teamPositions) {
        if (isValidMove(teamPosition, path, roll, teams.toArray)) {
          val pathIndex: Int = path.indexOf(teamPosition)
          val fromPosition: BoardPosition = board.getPath(team)(pathIndex)
          val toPosition: BoardPosition = board.getPath(team)(pathIndex + roll)
          val newBoard = pieceMover.movePiece(board, fromPosition, toPosition);
          newMoves = new GameMove(fromPosition, toPosition, newBoard) :: newMoves;
        }
      }
    }

    newMoves.foreach(_.resultingBoard.regenerateMissingPieces());
    newMoves;
  }

  private def isValidMove(originalPosition: BoardPosition, path: List[BoardPosition], roll: Int, teams: Array[PieceColor.Value]): Boolean = {
    val pathIndex: Int = path.indexOf(originalPosition)

    if (pathIndex + roll >= path.length) false;
    else if (path(pathIndex + roll).isOccupied &&  teams.exists(_ == path(pathIndex + roll).gamePiece.color)) false;
    else true;
  }
}