package trouble.engine.board

import collection.immutable.{Iterable, HashMap}
import collection.Iterable

class GameBoard() {
  private val startingPositions = Map(
    PieceColor.Red -> setupStartingPositions(PieceColor.Red),
    PieceColor.Blue -> setupStartingPositions(PieceColor.Blue),
    PieceColor.Green -> setupStartingPositions(PieceColor.Green),
    PieceColor.Yellow -> setupStartingPositions(PieceColor.Yellow)
  );

  private val goalPositions = Map(
    PieceColor.Red -> setupGoalPositions(PieceColor.Red),
    PieceColor.Blue -> setupGoalPositions(PieceColor.Blue),
    PieceColor.Green -> setupGoalPositions(PieceColor.Green),
    PieceColor.Yellow -> setupGoalPositions(PieceColor.Yellow)
  );

  private val mainPath = setupMainPath();
  private val pieceMover = new PieceMover();

  def getPath(team: PieceColor.Value): List[BoardPosition] = {
    val startIndex: Int = mainPath.indexOf(new BoardPosition("%s - 0".format(team)))
    val split: (List[BoardPosition], List[BoardPosition]) = mainPath.splitAt(startIndex)
    split._2 ::: split._1 ::: goalPositions(team)
  }

  def getStartingPositions(team: PieceColor.Value) = startingPositions(team);

  def getNextAvailableStartingPosition(team: PieceColor.Value): Option[BoardPosition] = {
    getStartingPositions(team).find(_.gamePiece != null);
  }

  private def setupStartingPositions(team: PieceColor.Value): List[BoardPosition] = {
    var startingPositions = List[BoardPosition]();
    for (i <- 0 until 4) {
      val startingPosition: BoardPosition = new BoardPosition("%s start %s".format(team, i))
      val piece: GamePiece = new GamePiece(team, i)
      startingPosition.gamePiece = piece
      startingPositions = startingPosition :: startingPositions;
    }
    startingPositions.reverse
  }

  private def setupMainPath(): List[BoardPosition] = {
    var mainPathPositions = List[BoardPosition]();

    for (currentTeamArea <- PieceColor.orderedList) {
      for (i <- 0 until 7) {
        mainPathPositions = new BoardPosition("%s - %s".format(currentTeamArea, i)) :: mainPathPositions;
      }
    }
    mainPathPositions.reverse;
  }

  private def setupGoalPositions(team: PieceColor.Value): List[BoardPosition] = {
    var goalPositions = List[BoardPosition]();
    for (i <- 0 until 4) {
      val goalPosition: BoardPosition = new BoardPosition("%s goal %s".format(team, i))
      goalPositions = goalPosition :: goalPositions;
    }
    goalPositions.reverse
  }

  def flattenBoardPositions(): List[BoardPosition] = {
    var flatList: List[BoardPosition] = List[BoardPosition]();
    for (key <- startingPositions.keys) {
      flatList = flatList ::: startingPositions(key);
    }
    flatList = flatList ::: mainPath;
    for (key <- goalPositions.keys) {
      flatList = flatList ::: goalPositions(key);
    }
    flatList
  }

  def occupiedPositions: List[BoardPosition] = {
    flattenBoardPositions().filter(p => p.isOccupied);
  }

  def createCopy(): GameBoard = {
    val newBoard = new GameBoard();
    val newPositions: List[BoardPosition] = newBoard.flattenBoardPositions();
    for (position <- occupiedPositions) {
      val newPosition: BoardPosition = newPositions.find(_ == position).get;
      val optionalOriginalPosition: Option[BoardPosition] = newPositions.find(_.gamePiece == position.gamePiece);
      if (optionalOriginalPosition.isEmpty) {
        println("Could not find position holding: " + position.gamePiece + " position = " + position);
      }
      val originalPosition: BoardPosition = optionalOriginalPosition.get;
      pieceMover.movePiece(originalPosition, newPosition);
    }
    newBoard;
  }

  def regenerateMissingPieces() {
    val boardPositions: List[BoardPosition] = flattenBoardPositions()
    for (team <- PieceColor.orderedList) {
      for (i <- 0 until 4) {
        val gamePiece: GamePiece = new GamePiece(team, i)
        if (!boardPositions.exists(_.gamePiece == gamePiece)) {
          getStartingPositions(team)(i).gamePiece = gamePiece;
        }
      }
    }
  }

  def finishedTeams: List[PieceColor.Value] = {
    val finished = for (team <- goalPositions.keys if goalPositions(team).filter(!_.isOccupied).length == 0) yield team
    finished.toList;
  }

  override def equals(obj: Any) = obj match {
    case that: GameBoard =>
      val thatOccupiedPositions: List[BoardPosition] = that.occupiedPositions
      val thisOccupiedPositions: List[BoardPosition] = this.occupiedPositions
      var positionsEqual = that.flattenBoardPositions() == this.flattenBoardPositions() &&
        thatOccupiedPositions == thisOccupiedPositions;
      if (positionsEqual) {
        for (i <- 0 until thisOccupiedPositions.length) {
          if (thisOccupiedPositions(i).gamePiece != thatOccupiedPositions(i).gamePiece) {
            positionsEqual = false;
          }
        }
      }
      positionsEqual;
    case _ => false;
  }

  override def toString = {
    val startingList: List[BoardPosition] = startingPositions.values.flatten.toList;
    val sb: StringBuilder = new StringBuilder();
    sb.append("{");
    for (team <- startingPositions.keys) {
      val count = getStartingPositions(team).filter(_.isOccupied).length;
      sb.append("\n\t%s has %d pieces in the starting position".format(team, count));
    }
    sb.append("\n\t----In Play----");
    sb.append(occupiedPositions.filter(!startingList.contains(_)).mkString("\n\t", "\n\t", "\n}"));
    sb.toString();
  }
}