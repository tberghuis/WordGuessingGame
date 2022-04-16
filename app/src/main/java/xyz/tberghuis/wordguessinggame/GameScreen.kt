package xyz.tberghuis.wordguessinggame

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GameScreen() {
  val viewModel: WordleViewModel = hiltViewModel()
  val wordleState = viewModel.wordleState.value

//  val cursorRow = wordleState.cursorRow
  val wordList = wordleState.wordList
//  val solution = wordleState.solution

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      Modifier.height(100.dp),
      verticalArrangement = Arrangement.Bottom,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (wordleState.gameState != GameState.PLAYING) {
        Button(onClick = {
          viewModel.newGame()
        }) {
          Text("New Game")
        }
      }
      if (wordleState.gameState == GameState.LOST) {
        Text(
          wordleState.solution,
          modifier = Modifier.padding(15.dp)
        )
      }
    }
    for (i in 0..5) {
      Row {
        for (j in 0..4) {
          if (j >= wordList[i].length) {
//            RenderChar(null, i, j, cursorRow, solution)
          } else {
//            RenderChar(wordList[i][j], i, j, cursorRow, solution)
          }
        }
      }
    }
    Spacer(Modifier.height(10.dp))
//    RenderKeyboard(wordleState)
  }
//  SnackbarContainer()
}