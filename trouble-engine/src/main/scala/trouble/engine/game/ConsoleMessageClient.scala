package trouble.engine.game


class ConsoleMessageClient extends MessageClient {
  def displayMessage(msg: String) {
    println(msg);
  }
}