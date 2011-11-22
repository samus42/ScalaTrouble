package trouble.engine.board

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._
import scala.collection.JavaConversions._

class GameBoard_UT {
  val NumberOfMainPathPositions = 28;
  val NumberOfGoalPositions = 4;
  val pieceMover = new PieceMover();

  @Test def generateBaordStartingPositionsSetupCorrectly() {
    val board = new GameBoard();
    val redStartingPositions: List[BoardPosition] = board.getStartingPositions(PieceColor.Red);
    val blueStartingPositions: List[BoardPosition] = board.getStartingPositions(PieceColor.Blue);
    val greenStartingPositions: List[BoardPosition] = board.getStartingPositions(PieceColor.Green);
    val yellowStartingPositions: List[BoardPosition] = board.getStartingPositions(PieceColor.Yellow);

    checkStartingPositionsAllPointToTheSameNextPositionAndHavePiece(redStartingPositions, PieceColor.Red)
    checkStartingPositionsAllPointToTheSameNextPositionAndHavePiece(blueStartingPositions, PieceColor.Blue)
    checkStartingPositionsAllPointToTheSameNextPositionAndHavePiece(greenStartingPositions, PieceColor.Green)
    checkStartingPositionsAllPointToTheSameNextPositionAndHavePiece(yellowStartingPositions, PieceColor.Yellow)
  }

  @Test def checkTeamPaths() {
    def validateTeamPath(team: PieceColor.Value, teamOrder: Array[PieceColor.Value]) {
      val board = new GameBoard();
      val positions = board.getPath(team);
      assertThat(positions.length, is(NumberOfMainPathPositions + NumberOfGoalPositions));

      var currentIndex = 0;
      for (teamArea <- teamOrder) {
        for (i <- 0 until 7) {
          val currentPosition = positions(currentIndex);
          assertThat(currentPosition.name, is("%s - %s".format(teamArea, i)));
          currentIndex += 1;
        }
      }

      for (i <- 0 until 4) {
        val currentPosition = positions(currentIndex + i);
        assertThat(currentPosition.name, is("%s goal %s".format(team, i)));
      }
    }
    validateTeamPath(PieceColor.Red, Array(PieceColor.Red, PieceColor.Blue, PieceColor.Green, PieceColor.Yellow))
    validateTeamPath(PieceColor.Blue, Array(PieceColor.Blue, PieceColor.Green, PieceColor.Yellow, PieceColor.Red))
    validateTeamPath(PieceColor.Green, Array(PieceColor.Green, PieceColor.Yellow, PieceColor.Red, PieceColor.Blue))
    validateTeamPath(PieceColor.Yellow, Array(PieceColor.Yellow, PieceColor.Red, PieceColor.Blue, PieceColor.Green))
  }

  @Test def createCopy() {
    val board = new GameBoard();
    val redStartingPositions = board.getStartingPositions(PieceColor.Red);
    val greenStartingPositions = board.getStartingPositions(PieceColor.Green);
    val redPath = board.getPath(PieceColor.Red);
    val greenPath = board.getPath(PieceColor.Green);
    pieceMover.movePiece(redStartingPositions(0), redPath(0));
    pieceMover.movePiece(redStartingPositions(2), redPath(6));
    pieceMover.movePiece(greenStartingPositions(1), greenPath(10));
    pieceMover.movePiece(board.getStartingPositions(PieceColor.Yellow)(0), board.getPath(PieceColor.Yellow)(31));

    val newBoard: GameBoard = board.createCopy();
    assertThat(newBoard.getPath(PieceColor.Red)(0).gamePiece, is(redPath(0).gamePiece));
    assertThat(newBoard.getPath(PieceColor.Red)(6).gamePiece, is(redPath(6).gamePiece));
    assertThat(newBoard.getStartingPositions(PieceColor.Red)(0).gamePiece, nullValue());
    assertThat(newBoard.getStartingPositions(PieceColor.Red)(2).gamePiece, nullValue());
    assertThat(newBoard.getPath(PieceColor.Green)(10).gamePiece, is(greenPath(10).gamePiece));
    assertThat(newBoard.getPath(PieceColor.Yellow)(31).gamePiece, is(board.getPath(PieceColor.Yellow)(31).gamePiece));
    assertThat(newBoard.getStartingPositions(PieceColor.Yellow)(0).gamePiece, nullValue());

    assertThat(newBoard.getStartingPositions(PieceColor.Red)(1).isOccupied, is(true));
    assertThat(newBoard.getStartingPositions(PieceColor.Red)(3).isOccupied, is(true));

    ///Test board equality to make this simpler
    assertThat(newBoard == board, is(true));

  }

  @Test def testEquals() {
    val board = new GameBoard();
    val board2 = new GameBoard();
    val board4 = new GameBoard();
    pieceMover.movePiece(board4.getStartingPositions(PieceColor.Red)(0), board4.getPath(PieceColor.Red)(2));
    pieceMover.movePiece(board4.getStartingPositions(PieceColor.Blue)(0), board4.getPath(PieceColor.Blue)(2));
    val board5 = board4.createCopy();
    val tmp = board5.getPath(PieceColor.Red)(2).gamePiece;
    board5.getPath(PieceColor.Red)(2).gamePiece = board5.getPath(PieceColor.Blue)(2).gamePiece;
    board5.getPath(PieceColor.Blue)(2).gamePiece = tmp;

    assertThat(board == board, is(true));
    assertThat(board == board2, is(true));
    assertThat(board == board4, is(false));
    assertThat(board4 == board5, is(false));
    assertThat(board == new GamePiece(PieceColor.Red, 1), is(false));
  }

  @Test def regenerateMissingPieces() {
    val board = new GameBoard();
    for (position <- board.flattenBoardPositions()) {
      position.gamePiece = null;
    }

    board.regenerateMissingPieces();
    assertThat(board == new GameBoard(), is(true));
  }


  @Test def finishedTeams() {

    var board = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red goal 0"), (PieceColor.Red, 1, "red goal 1"), (PieceColor.Red, 2, "red goal 2"), (PieceColor.Red, 3, "red goal 3"));
    assertThat(board.finishedTeams.length, is(1));
    assertThat(board.finishedTeams(0), is(PieceColor.Red));
    board = pieceMover.movePieces(board, (PieceColor.Blue, 0, "blue goal 0"), (PieceColor.Blue, 1, "blue goal 1"), (PieceColor.Blue, 2, "blue goal 2"), (PieceColor.Blue, 3, "blue goal 3"));
    assertThat(board.finishedTeams.length, is(2));
    assertThat(board.finishedTeams.contains(PieceColor.Red), is(true));
    assertThat(board.finishedTeams.contains(PieceColor.Blue), is(true));
    board = pieceMover.movePieces(board, (PieceColor.Green, 0, "green goal 0"), (PieceColor.Green, 1, "green goal 1"), (PieceColor.Green, 2, "green goal 2"));
    assertThat(board.finishedTeams.length, is(2));
    assertThat(board.finishedTeams.contains(PieceColor.Red), is(true));
    assertThat(board.finishedTeams.contains(PieceColor.Blue), is(true));
  }

  private def checkStartingPositionsAllPointToTheSameNextPositionAndHavePiece(startingPositions: List[BoardPosition], team: PieceColor.Value){
    assertThat(startingPositions.size, is(4))
    var pieceNumber = 0
    for (position <- startingPositions) {
      assertThat(position.isOccupied, is(true))
      assertThat(position.gamePiece.number, is(pieceNumber))
      assertThat(position.gamePiece.color, is(team))
      assertThat(position.name, is(team + " start " + (pieceNumber)))
      pieceNumber = pieceNumber + 1;
    }
  }
}

