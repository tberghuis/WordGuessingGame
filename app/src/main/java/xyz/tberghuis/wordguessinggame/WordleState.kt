package xyz.tberghuis.wordguessinggame

enum class GameState {
  PLAYING, WON, LOST
}

data class WordleState(
  val wordList: List<String> = listOf("", "", "", "", "", ""),
  val cursorRow: Int = 0,
  val solution: String = TOP_WORDS.random(),
  val gameState: GameState = GameState.PLAYING
)