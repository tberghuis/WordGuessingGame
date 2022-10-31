package xyz.tberghuis.wordguessinggame.tmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.GameState
import xyz.tberghuis.wordguessinggame.RenderGameBoard
import xyz.tberghuis.wordguessinggame.RenderKeyboard
import xyz.tberghuis.wordguessinggame.WordleViewModel
import xyz.tberghuis.wordguessinggame.composables.SnackbarContainer

@Composable
fun TmpDarkMode() {

  val viewModel: WordleViewModel = hiltViewModel()
  val wordleState = viewModel.wordleState.value

  val configuration = LocalConfiguration.current
  val screenHeight = configuration.screenHeightDp.dp
//  var size by remember { mutableStateOf(Size.Unspecified) }

  Column(
    modifier = Modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {

    Row(
      Modifier
//        .height(70.dp)
        .weight(1f)
        .fillMaxWidth()
//        .border(2.dp, Color.Black)
      ,
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      if (wordleState.gameState == GameState.LOST) {
        Text(
          wordleState.solution,
          modifier = Modifier.padding(15.dp)
        )
      }
      if (wordleState.gameState != GameState.PLAYING) {
        Button(onClick = {
          viewModel.newGame()
        }) {
          Text("New Game")
        }
      }
    }

    // game board
    RenderGameBoard(wordleState, screenHeight)

    Column(
      Modifier
        .padding(horizontal = 5.dp)
        .padding(top = 10.dp)
    ) {
      RenderKeyboard()
    }

  }
  SnackbarContainer()

}