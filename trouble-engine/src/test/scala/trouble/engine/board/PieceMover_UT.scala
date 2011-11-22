package trouble.engine.board

import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class PieceMover_UT {
  @Test def movePieceMutable() {
    val originalPosition = new BoardPosition("a");
    originalPosition.gamePiece = GamePiece(PieceColor.Red, 0);
    val newPosition = new BoardPosition("b");

    val mover = new PieceMover();
    mover.movePiece(originalPosition, newPosition);

    assertThat(originalPosition.isOccupied, is(false));
    assertThat(newPosition.isOccupied, is(true));
  }

  @Test def ignoresMutibleMoveToSameSpace() {
    val originalPosition = new BoardPosition("a");
    originalPosition.gamePiece = GamePiece(PieceColor.Red, 0);
    val mover = new PieceMover();
    mover.movePiece(originalPosition, originalPosition);
    assertThat(originalPosition.isOccupied, is(true));
  }

  @Test def movePieceImmutable() {
    val originalBoard: GameBoard = new GameBoard()
    val originalPosition = originalBoard.getNextAvailableStartingPosition(PieceColor.Red).get;
    val newPosition = originalBoard.getPath(PieceColor.Red)(1);
    val mover = new PieceMover();
    val newBoard : GameBoard = mover.movePiece(originalBoard, originalPosition, newPosition);
    assertThat(originalPosition.isOccupied, is(true));
    assertThat(newPosition.isOccupied, is(false));
    assertThat(newBoard.getStartingPositions(PieceColor.Red)(0).isOccupied, is(false));
    assertThat(newBoard.getPath(PieceColor.Red)(1).isOccupied, is(true));
  }

  @Test def movePiecesTupleWithMultiplePieces() {
    val mover = new PieceMover();
    val originalBoard: GameBoard = new GameBoard()
    val gameBoard = mover.movePieces(originalBoard, (PieceColor.Red, 1, "red - 2"),
      (PieceColor.Blue, 0, "green - 5"),
      (PieceColor.Green, 3, "blue - 4"));
    assertThat(gameBoard.getStartingPositions(PieceColor.Red)(1).isOccupied, is(false));
    assertThat(gameBoard.getPath(PieceColor.Red)(2).isOccupied, is(true));

    assertThat(gameBoard.getStartingPositions(PieceColor.Blue)(0).isOccupied, is(false));
    assertThat(gameBoard.getPath(PieceColor.Green)(5).isOccupied, is(true));

    assertThat(gameBoard.getStartingPositions(PieceColor.Green)(3).isOccupied, is(false));
    assertThat(gameBoard.getPath(PieceColor.Blue)(4).isOccupied, is(true));
    assertThat(originalBoard != gameBoard, is(true));
  }
}