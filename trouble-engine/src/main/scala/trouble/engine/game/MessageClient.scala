package trouble.engine.game

import actors.Actor


trait MessageClient extends Actor {
  def act() {
    receive {
      case msg: String => displayMessage(msg);
    }
  }

  def displayMessage(msg: String);
}