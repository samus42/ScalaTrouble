package trouble.engine.board

import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._


class MoveGeneratorTwoColors_UT {
val moveGenerator = new MoveGenerator();
  val pieceMover = new PieceMover();
  val teams = Array(PieceColor.Red, PieceColor.Blue);
  @Test def noMovesOnAllPiecesInStartAndNon6Rolled() {
    val moves: List[GameMove] = moveGenerator.generate(new GameBoard(), 1, PieceColor.Red, PieceColor.Blue);
    assertThat(moves.length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 2, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 3, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 4, PieceColor.Red).length, is(0));
    assertThat(moveGenerator.generate(new GameBoard(), 5, PieceColor.Red).length, is(0));
  }

  @Test def movesOnAllPiecesInStartAnd6Rolled() {
    val expected1 = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"));
    val expected2 = pieceMover.movePieces(new GameBoard(), (PieceColor.Blue, 0, "blue - 0"));
    val moves: List[GameMove] = moveGenerator.generate(new GameBoard(), 6, teams: _*);
    assertThat(moves.length, is(2));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
  }

  @Test def movesWhen6RolledButPieceInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"), (PieceColor.Blue, 0, "blue - 0"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 6"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "blue - 6"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, teams: _*);
    assertThat(moves.length, is(2));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
  }

  @Test def movesWhen6RolledButPieceOfAlliedColorInExitPosition() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "blue - 0"), (PieceColor.Blue, 0, "red - 0"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "red - 6"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "blue - 6"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 6, teams: _*);
    assertThat(moves.length, is(2));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
  }

  @Test def doesNotCaptureColorPassedIn() {
    val initialBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"), (PieceColor.Blue, 0, "red - 1"));
    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "red - 2"));
    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 1, teams: _*);
    assertThat(moves.length, is(1));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
  }

  @Test def movesWhenAllPiecesInPlay() {
    val initialBoard = pieceMover.movePieces(new GameBoard(),
      (PieceColor.Red, 0, "red - 0"), (PieceColor.Red, 1, "red - 2"), (PieceColor.Red, 2, "red - 4"), (PieceColor.Red, 3, "red - 6"),
      (PieceColor.Blue, 0, "green - 0"), (PieceColor.Blue, 1, "green - 2"), (PieceColor.Blue, 2, "green - 4"), (PieceColor.Blue, 3, "green - 6"));

    val expected1 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 0, "red - 1"));
    val expected2 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 1, "red - 3"));
    val expected3 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 2, "red - 5"));
    val expected4 = pieceMover.movePieces(initialBoard, (PieceColor.Red, 3, "blue - 0"));
    val expected5 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 0, "green - 1"));
    val expected6 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 1, "green - 3"));
    val expected7 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 2, "green - 5"));
    val expected8 = pieceMover.movePieces(initialBoard, (PieceColor.Blue, 3, "yellow - 0"));

    val moves: List[GameMove] = moveGenerator.generate(initialBoard, 1, teams: _*);
    assertThat(moves.length, is(8));
    assertThat(moves.exists(_.resultingBoard == expected1), is(true));
    assertThat(moves.exists(_.resultingBoard == expected2), is(true));
    assertThat(moves.exists(_.resultingBoard == expected3), is(true));
    assertThat(moves.exists(_.resultingBoard == expected4), is(true));
    assertThat(moves.exists(_.resultingBoard == expected5), is(true));
    assertThat(moves.exists(_.resultingBoard == expected6), is(true));
    assertThat(moves.exists(_.resultingBoard == expected7), is(true));
    assertThat(moves.exists(_.resultingBoard == expected8), is(true));

  }
}