package trouble.engine.board

import org.junit.{Before, Test}
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class MoveGenerator_UT {
  val moveGenerator = new MoveGenerator();
  val pieceMover = new PieceMover();

  @Test def noMovesOnAllPiecesInStartAndNon6Rolled() {
    val moves: List[GameMove] = moveGenerator.generate(new GameBoard(), 1, PieceColor.Red);
    assertThat(moves.length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 2, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 3, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 4, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 5, PieceColor.Red).length, is(0));
  }

  @Test def movesOnAllPiecesInStartAnd6Rolled() {
    val moves: List[GameMove] = moveGenerator.generate(new GameBoard(), 6, PieceColor.Red);
    assertThat(moves.length, is(1));
    assertThat(moves(0).resultingBoard.getPath(PieceColor.Red)(0).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(0).isOccupied, is(false));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(1).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(2).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(3).isOccupied, is(true));
  }

  @Test def movesWhen6RolledButPieceInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(1));
    assertThat(moves(0).resultingBoard.getPath(PieceColor.Red)(6).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(0).isOccupied, is(false));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(1).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(2).isOccupied, is(true));
    assertThat(moves(0).resultingBoard.getStartingPositions(PieceColor.Red)(3).isOccupied, is(true));
  }

  @Test def movesWhen6RolledOnePieceInPlayNotInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "blue - 0"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(2));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
  }

  @Test def movesWhen6RolledTwoPiecesInPlayNotInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Red, 1, "red - 2"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "blue - 0"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "blue - 1"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 2, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(3));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves.exists(_.resultingBoard == expected3), is(true));

  }

  @Test def movesWhen6RolledThreePieceInPlayNotInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Red, 1, "red - 2"), (PieceColor.Red, 2, "red - 3"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "blue - 0"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "blue - 1"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 2, "blue - 2"));
    val expected4 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 3, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(4));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves.exists(_.resultingBoard == expected3), is(true));
    assertThat(moves.exists(_.resultingBoard == expected4), is(true));

  }

  @Test def movesWhen6RolledAllPiecesInPlay() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Red, 1, "red - 2"), (PieceColor.Red, 2, "red - 3"), (PieceColor.Red, 3, "red - 4"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "blue - 0"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "blue - 1"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 2, "blue - 2"));
    val expected4 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 3, "blue - 3"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(4));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves.exists(_.resultingBoard == expected3), is(true));
    assertThat(moves.exists(_.resultingBoard == expected4), is(true));

  }

  @Test def moveDoesNotAffectOriginalBoard() {
    val originalBoard = new GameBoard().createCopy();
    val moves: List[GameMove] = moveGenerator.generate(new GameBoard(), 6, PieceColor.Red);
    assertThat(originalBoard == new GameBoard(), is(true));
  }

  @Test def moveForwardCorrectNumberOfSpaces() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 2"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 3"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 4"));
    val expected4 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 5"));
    val expected5 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 6"));
    val moves1: List[GameMove] = moveGenerator.generate(initialBoard, 1, PieceColor.Red);
    val moves2: List[GameMove] = moveGenerator.generate(initialBoard, 2, PieceColor.Red);
    val moves3: List[GameMove] = moveGenerator.generate(initialBoard, 3, PieceColor.Red);
    val moves4: List[GameMove] = moveGenerator.generate(initialBoard, 4, PieceColor.Red);
    val moves5: List[GameMove] = moveGenerator.generate(initialBoard, 5, PieceColor.Red);
    assertThat(moves1.length, is(1));
    assertThat(moves1.length, is(1));
    assertThat(moves1.length, is(1));
    assertThat(moves1.length, is(1));
    assertThat(moves1.length, is(1));
    assertThat(moves1.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves2.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves3.exists(_.resultingBoard == expected3), is(true));
    assertThat(moves4.exists(_.resultingBoard == expected4), is(true));
    assertThat(moves5.exists(_.resultingBoard == expected5), is(true));
  }

  @Test def cannotMoveToSpaceOccupiedBySameTeam() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Red, 1, "red - 2"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "red - 3"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 1, PieceColor.Red);
    assertThat(moves.length, is(1));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
  }

  @Test def captureOpponent() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Blue, 0, "red - 2"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "blue start 0"), (PieceColor.Red, 0, "red - 2"));

    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 1, PieceColor.Red);
    assertThat(moves.length, is(1));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
  }

  @Test def captureOpponentOnExitingStartOn6Rolled() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Blue, 0, "red - 0"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "blue start 0"), (PieceColor.Red, 0, "red - 0"));
    val moves = moveGenerator.generate(initialBoard, 6, PieceColor.Red);
    assertThat(moves.length, is(1));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
  }

  @Test def moveToGoalPositions() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red goal 0"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red goal 1"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red goal 2"));
    val expected4 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red goal 3"));
    val moves1 = moveGenerator.generate(initialBoard, 1, PieceColor.Red);
    val moves2 = moveGenerator.generate(initialBoard, 2, PieceColor.Red);
    val moves3 = moveGenerator.generate(initialBoard, 3, PieceColor.Red);
    val moves4 = moveGenerator.generate(initialBoard, 4, PieceColor.Red);
    assertThat(moves1.length, is(1));
    assertThat(moves2.length, is(1));
    assertThat(moves3.length, is(1));
    assertThat(moves4.length, is(1));
    assertThat(moves1.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves2.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves3.exists(_.resultingBoard == expected3), is(true));
    assertThat(moves4.exists(_.resultingBoard == expected4), is(true));
  }

  @Test def cannotMovePastGoalPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"));
    val moves = moveGenerator.generate(initialBoard, 5, PieceColor.Red);
    assertThat(moves.length, is(0));
  }

  @Test def generateNoMovesBecauseNoValidMoveExistsWithPiecesOutOfStart() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"),
      (PieceColor.Red, 1, "yellow - 0"),
      (PieceColor.Red, 2, "green - 1"),
      (PieceColor.Red, 3, "blue - 2"));
    val moves = moveGenerator.generate(initialBoard, 6, PieceColor.Red)
    assertThat(moves.length, is(0));
  }
}