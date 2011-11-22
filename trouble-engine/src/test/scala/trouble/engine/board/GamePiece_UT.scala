package trouble.engine.board

/**
 * Created by IntelliJ IDEA.
 * User: Scott
 * Date: 9/17/11
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test
import org.scalatest.junit.{JUnitSuite, AssertionsForJUnit}
import org.junit.Assert._
import org.hamcrest.CoreMatchers._

class GamePiece_UT extends JUnitSuite {
  @Test def construction() {
    val piece = new GamePiece(PieceColor.Red, 1);
    assertThat(piece.color, is(PieceColor.Red));
    assertThat(piece.number, is(1))
  }

  @Test def testToString() {
    val piece = new GamePiece(PieceColor.Red, 1);
    assertThat(piece.toString, is("red - 1"));
  }

  @Test def testEquals() {
      val piece = new GamePiece(PieceColor.Red, 2);
      val piece2 = new GamePiece(PieceColor.Red, 2);
      val piece3 = new GamePiece(PieceColor.Green, 2);
      val piece4 = new GamePiece(PieceColor.Red, 3);
      val piece5 = null;
      assertTrue(piece.equals(piece));
      assertTrue(piece.equals(piece2));
      assertFalse(piece.equals(piece3));
      assertFalse(piece.equals(piece4));
      assertFalse(piece.equals(piece5));
      assertFalse(piece.equals("blah"));
  }
}