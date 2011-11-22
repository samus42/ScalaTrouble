package trouble.engine.board

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class BoardPosition_UT extends JUnitSuite {
  @Test def construction() {
    val position = new BoardPosition("name");
    assertThat(position.name, is("name"));
    assertThat(position.isOccupied, is(false));
  }

  @Test def setGamePiece() {
    val position = new BoardPosition("name");
    val piece = GamePiece(PieceColor.Red, 1);
    position.gamePiece = piece;
    assertThat(position.gamePiece, is(piece))
    assertThat(position.isOccupied, is(true))
  }

  @Test def testEquals() {
    val position = new BoardPosition("name");
    val position2 = new BoardPosition("name");
    val position3 = new BoardPosition("name2");
    val position4 = new BoardPosition("name", new GamePiece(PieceColor.Red, 1));
    assertThat(position == position, is(true));
    assertThat(position == position2, is(true));
    assertThat(position == position3, is(false));
    assertThat(position == position4, is(true));

  }
}