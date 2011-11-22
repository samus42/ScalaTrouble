package trouble.engine.game

import ai.NoBrainStrategy
import collection.mutable.Queue
import org.junit.{Before, Test}
import org.mockito.Mockito._
import org.junit.Assert._
import org.hamcrest.CoreMatchers._
import trouble.engine.board._

class TurnEngine_UT {

  val pieceMover = new PieceMover();
  val moveGenerator = new MoveGenerator();

  @Test def turnWithNoPossibleMovesChangesToNextPlayer() {
    val strategy = mock(classOf[Strategy]);

    val playerQueue = Queue[GameClient]();
    playerQueue.enqueue(new GameClient("a", strategy, PieceColor.Red));
    playerQueue.enqueue(new GameClient("b", strategy, PieceColor.Blue));
    playerQueue.enqueue(new GameClient("c", strategy, PieceColor.Green));
    playerQueue.enqueue(new GameClient("d", strategy, PieceColor.Yellow));

    val board = new GameBoard();
    verifyZeroInteractions(strategy);
    val turnEngine = new TurnEngine();
    val newBoard = turnEngine.executeTurn(board, playerQueue, 1);
    assertThat(playerQueue.head.player, is("b"));
    assertThat(newBoard == board, is(true));
  }

  @Test def turnWithPossibleMovesChangesToNextPlayer() {
    val strategy = mock(classOf[Strategy]);

    val playerQueue = Queue[GameClient]();
    playerQueue.enqueue(new GameClient("a", strategy, PieceColor.Red));
    playerQueue.enqueue(new GameClient("b", strategy, PieceColor.Blue));
    playerQueue.enqueue(new GameClient("c", strategy, PieceColor.Green));
    playerQueue.enqueue(new GameClient("d", strategy, PieceColor.Yellow));

    val board = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"));
    val expectedBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"));
    when(strategy.makeMove(moveGenerator.generate(board, 1, PieceColor.Red), PieceColor.Red)).thenReturn(new GameMove(board.getPath(PieceColor.Red)(0), board.getPath(PieceColor.Red)(1), expectedBoard));
    val turnEngine = new TurnEngine();
    val newBoard = turnEngine.executeTurn(board, playerQueue, 1);
    assertThat(playerQueue.head.player, is("b"));
    assertThat(expectedBoard == newBoard, is(true));
  }
  
  @Test def turnWithSixRolledKeepsSamePlayer() {
    val strategy = mock(classOf[Strategy]);

    val playerQueue = Queue[GameClient]();
    playerQueue.enqueue(new GameClient("a", strategy, PieceColor.Red));
    playerQueue.enqueue(new GameClient("b", strategy, PieceColor.Blue));
    playerQueue.enqueue(new GameClient("c", strategy, PieceColor.Green));
    playerQueue.enqueue(new GameClient("d", strategy, PieceColor.Yellow));

    val board = new GameBoard();
    val expectedBoard = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 0"));
    when(strategy.makeMove(moveGenerator.generate(board, 6, PieceColor.Red), PieceColor.Red)).thenReturn(new GameMove(board.getNextAvailableStartingPosition(PieceColor.Red).get, board.getPath(PieceColor.Red)(0), expectedBoard));
    val turnEngine = new TurnEngine();
    val newBoard = turnEngine.executeTurn(board, playerQueue, 6);
    assertThat(playerQueue.head.player, is("a"));
    assertThat(newBoard == expectedBoard, is(true));
  }
}