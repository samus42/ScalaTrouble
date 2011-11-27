package trouble

import engine.board.PieceColor
import engine.game.ai.{BalancedGoalOrientedStrategy, NoBrainStrategy}
import engine.game.{GameClient, ConsoleMessageClient, GameEngine}
import org.apache.commons.cli.{HelpFormatter, UnrecognizedOptionException, PosixParser, Options}

object ConsoleMain {
  def main(args: Array[String]) {
    val options: Options = initOptions()
    try {
      val parser = new PosixParser();

      val cmd = parser.parse(options, args);

      val numCPUPlayers = cmd.getOptionValue("cpu", "1").toInt min 4;
      val numHumanPlayers = if (numCPUPlayers == 4) 0 else 1;
      val twoColorsPerPlayer = cmd.hasOption("2c") && (numCPUPlayers == 1)
      println("Num Human Players: %s, Num CPU Players: %s, 2 colors per player: %s".format(numHumanPlayers, numCPUPlayers, twoColorsPerPlayer));
      val players = new Array[GameClient](4);
      for (i <- 0 until 4) {
        if (i < numHumanPlayers) {
          players(i) = new GameClient("human %s".format(i), new NoBrainStrategy(), PieceColor.orderedList(i));
        } else if (i <= numCPUPlayers + numHumanPlayers ) {
          players(i) = new GameClient("cpu %s".format(i), new BalancedGoalOrientedStrategy(), PieceColor.orderedList(i));
        }
      }
      val engine = new GameEngine(new ConsoleMessageClient());
      engine.startGame(players.filter(_ != null): _*)
    } catch {
      case ex: UnrecognizedOptionException => {
        println(ex.getMessage);
        new HelpFormatter().printHelp("args", options);
      };
    }
  }

  private def initOptions(): Options = {
    val options = new Options();
    options.addOption("cpu", true, "Number of CPU players");
    options.addOption("2c", false, "2 Colors per person");
    options;
  }
}

