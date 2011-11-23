package trouble.engine.game.ai

import org.junit.Test
import org.junit.Assert._
import org.hamcrest.CoreMatchers._
import trouble.engine.game.Strategy
import trouble.engine.board._

class SuperAggressiveStrategy_UT {
  val strategy: Strategy = new SuperAggressiveStrategy();
  val pieceMover = new PieceMover();
  val moveGenerator = new MoveGenerator();

  @Test def prefersCaptureOverGoal() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"), (PieceColor.Red, 1, "red - 0"), (PieceColor.Green, 0, "red - 1"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(true));
    assertThat(resultingMove.fromPosition.name, is("red - 0"));
    assertThat(resultingMove.toPosition.name, is("red - 1"));
  }

  @Test def prefersCaptureOverGoalMultiColorPlayer() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"), (PieceColor.Blue, 1, "red - 0"), (PieceColor.Green, 0, "red - 1"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(true));
    assertThat(resultingMove.fromPosition.name, is("red - 0"));
    assertThat(resultingMove.toPosition.name, is("red - 1"));
  }

  @Test def prefersCaptureOverExitingStartingPosition() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Green, 0, "blue - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(true));
    assertThat(resultingMove.fromPosition.name, is("red - 1"));
    assertThat(resultingMove.toPosition.name, is("blue - 0"));
  }

  @Test def prefersCaptureOverExitingStartingPositionMultiColorPlayer() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Blue, 0, "blue - 1"), (PieceColor.Green, 0, "green - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(true));
    assertThat(resultingMove.fromPosition.name, is("blue - 1"));
    assertThat(resultingMove.toPosition.name, is("green - 0"));
  }

  @Test def prefersExitingStartingPositionOverNormalMove() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("red start 1"));
    assertThat(resultingMove.toPosition.name, is("red - 0"));

  }

  @Test def prefersExitingStartingPositionOverNormalMoveMultiColorPlayer() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red - 1"), (PieceColor.Red, 1, "red - 2"), (PieceColor.Red, 2, "red - 3"), (PieceColor.Red, 3, "red - 4"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("blue start 0"));
    assertThat(resultingMove.toPosition.name, is("blue - 0"));
  }

  @Test def prefersGoalOverNormalMove() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 6"), (PieceColor.Red, 1, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("yellow - 6"));
    assertThat(resultingMove.toPosition.name, is("red goal 0"));

  }

  @Test def prefersGoalOverNormalMoveMultiColorPlayer() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Blue, 0, "red - 6"), (PieceColor.Red, 1, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("red - 6"));
    assertThat(resultingMove.toPosition.name, is("blue goal 0"));
  }

  @Test def prefersGoalOverExiting() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "yellow - 1"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("yellow - 1"));
    assertThat(resultingMove.toPosition.name, is("red goal 0"));
  }

  @Test def prefersGoalOverExitingMultiColorPlayer() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Blue, 0, "red - 1"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 6, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("red - 1"));
    assertThat(resultingMove.toPosition.name, is("blue goal 0"));
  }

  @Test def prefersNormalMoveOverPieceAlreadyInGoal() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red goal 0"), (PieceColor.Red, 1, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("red - 0"));
    assertThat(resultingMove.toPosition.name, is("red - 1"));
  }

  @Test def prefersNormalMoveOverPieceAlreadyInGoalMulticolored() {
    val initialState = pieceMover.movePieces(new GameBoard(), (PieceColor.Red, 0, "red goal 0"), (PieceColor.Blue, 1, "red - 0"));
    val moves: List[GameMove] = moveGenerator.generate(initialState, 1, PieceColor.Red, PieceColor.Blue);
    println("Possible Moves: " + moves);
    val resultingMove: GameMove = strategy.makeMove(moves, Array(PieceColor.Red, PieceColor.Blue));
    assertThat(resultingMove.isCapture, is(false));
    assertThat(resultingMove.fromPosition.name, is("red - 0"));
    assertThat(resultingMove.toPosition.name, is("red - 1"));
  }
}