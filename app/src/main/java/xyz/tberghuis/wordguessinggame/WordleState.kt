package xyz.tberghuis.wordguessinggame

import kotlin.random.Random

enum class GameState {
  PLAYING, WON, LOST
}

val random = Random(System.currentTimeMillis())
fun getRandomWord(): String {
  return TOP_WORDS[random.nextInt(TOP_WORDS.size)]
}

data class WordleState(
  val wordList: List<String> = listOf("", "", "", "", "", ""),
  val cursorRow: Int = 0,
  val solution: String = getRandomWord(),
  val gameState: GameState = GameState.PLAYING
)