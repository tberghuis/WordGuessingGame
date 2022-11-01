package xyz.tberghuis.wordguessinggame.state

sealed class CellState {
  object ExactMatch: CellState()
  object Match: CellState()
  object NoMatch: CellState()
  object Unchecked: CellState()
}
