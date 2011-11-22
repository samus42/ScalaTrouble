package trouble.engine.board

import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._


class GameMove_UT {
  @Test def toStringNormalMove() {
    val move = new GameMove(new BoardPosition("a", new GamePiece(PieceColor.Red, 1)), new BoardPosition("b"), new GameBoard());
    assertThat(move.toString, is("red moves from a to b"));
  }
  
  @Test def toStringCapture() {
    val move = new GameMove(new BoardPosition("a", new GamePiece(PieceColor.Red, 1)), new BoardPosition("b", new GamePiece(PieceColor.Blue, 1)), new GameBoard());
    assertThat(move.toString, is("red moves from a to b (Captures blue)"));
  }
}