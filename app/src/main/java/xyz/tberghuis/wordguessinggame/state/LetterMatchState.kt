package xyz.tberghuis.wordguessinggame.state

sealed class LetterMatchState {
  object ExactMatch: LetterMatchState()
  object Match: LetterMatchState()
  object NoMatch: LetterMatchState()
  object Unchecked: LetterMatchState()
}
