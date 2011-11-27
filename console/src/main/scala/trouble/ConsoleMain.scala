package trouble

import org.apache.commons.cli.{HelpFormatter, UnrecognizedOptionException, PosixParser, Options}

object ConsoleMain {
  def main(args: Array[String]) {
    val options: Options = initOptions()
    try {
      val parser = new PosixParser();

      val cmd = parser.parse(options, args);

    val numCPUPlayers = cmd.getOptionValue("cpu", "1").toInt;
    val twoColorsPerPlayer = cmd.hasOption("2c") && (numCPUPlayers == 1)
    println("Num CPU Players: %s, 2 colors per player: %s".format(numCPUPlayers, twoColorsPerPlayer));
    } catch {
      case ex: UnrecognizedOptionException => {
        println(ex.getMessage());
        new HelpFormatter().printHelp("args", options);
      };
    }
  }

  private def initOptions() : Options = {
    val options = new Options();
    options.addOption("cpu", true, "Number of CPU players");
    options.addOption("2c", false, "2 Colors per person");
    options;
  }
}

