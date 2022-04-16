package xyz.tberghuis.wordguessinggame

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.composables.SnackbarContainer

@Composable
fun GameScreen() {
  val viewModel: WordleViewModel = hiltViewModel()
  val wordleState = viewModel.wordleState.value

  val cursorRow = wordleState.cursorRow
  val wordList = wordleState.wordList
  val solution = wordleState.solution

  Column(
    modifier = Modifier
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
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
      Row(Modifier.padding(horizontal = 5.dp)) {
        for (j in 0..4) {
          if (j >= wordList[i].length) {
            RenderChar(null, i, j, cursorRow, solution)
          } else {
            RenderChar(wordList[i][j], i, j, cursorRow, solution)
          }
        }
      }
    }
    Spacer(Modifier.height(10.dp))
    RenderKeyboard()
  }
  SnackbarContainer()
}

// may not need to pass shit down...
// who cares
@Composable
fun RowScope.RenderChar(c: Char?, row: Int, col: Int, cursorRow: Int, solution: String) {
  val renderString = if (c == null) "" else "$c".uppercase()
  val backgroundColor = calcBackgroundColor(renderString, row, col, cursorRow, solution)
  Box(
    modifier = Modifier
      .padding(5.dp)
//      .size(62.dp)
      .weight(1f)
      .aspectRatio(1f)
      .let {
        if (backgroundColor == Color.White) {
          it.border(BorderStroke(2.dp, Color.LightGray))
        } else {
          it
        }
      }
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    Text(
      renderString, modifier = Modifier.padding(16.dp)
    )
  }
}

fun calcBackgroundColor(
  letter: String, row: Int, col: Int,
  cursorRow: Int, solution: String
): Color {
  if (letter == "") {
    return Color.White
  }
  if (row >= cursorRow) {
    return Color.White
  }
  if (!solution.contains(letter)) {
    return COLORS.Gray
  }
  if (solution[col] == letter[0]) {
    return COLORS.Green
  }
  return COLORS.Yellow
}

@Composable
fun RenderKeyboard() {
  val vm: WordleViewModel = hiltViewModel()

  // no need to use a lambda, should use fun
  val renderKeysInRow: @Composable RowScope.(row: List<String>) -> Unit = { row ->
    for (k in row) RenderKey(
      k,
      deriveKeyBackgroundColor(k[0], vm.wordleState.value)
    ) {
      vm.addLetter(k[0])
    }
  }

  val row1 = "QWERTYUIOP".toCharArray().map { "$it" }
  val row2 = "ASDFGHJKL".toCharArray().map { "$it" }
  val row3 = "ZXCVBNM".toCharArray().map { "$it" }

  Row {
    renderKeysInRow(row1)
  }
  Row {
    Spacer(Modifier.weight(.5f))
    renderKeysInRow(row2)
    Spacer(Modifier.weight(.5f))
  }
  Row {
    Spacer(Modifier.weight(1f))
    renderKeysInRow(row3)
    RenderKey("⌫", COLORS.LightGray, 1.5f) {
      println("on click backspace")
      vm.removeLetter()
    }
    Spacer(Modifier.weight(.5f))
  }
  Row(Modifier.padding(vertical = 5.dp)) {
    Spacer(Modifier.weight(1f))
    RenderKey("check", COLORS.LightGray) {
      println("on click enter")
      vm.onKeyUpEnter()
    }
    Spacer(Modifier.weight(1f))
  }

}

@Composable
fun RowScope.RenderKey(k: String, backgroundColor: Color, weight: Float = 1f, onClick: () -> Unit) {
  // todo change font color to white if backgroundColor = (gray, green or yellow)

  Box(
    modifier = Modifier
      .padding(1.dp)
      .weight(weight)
      .clickable {
        onClick()
      }
      .background(backgroundColor),
    contentAlignment = Alignment.Center
  ) {
    Text(
      k,
      modifier = Modifier.padding(10.dp)
    )
  }
}

fun deriveKeyBackgroundColor(key: Char, wordleState: WordleState): Color {
  if (wordleState.cursorRow == 0) {
    return COLORS.LightGray
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    for (i in 0..4) {
      if (key == wordleState.solution[i] && key == word[i]) {
        return COLORS.Green
      }
    }
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    if (word.contains(key) && wordleState.solution.contains(key)) {
      return COLORS.Yellow
    }
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    if (word.contains(key)) {
      return COLORS.Gray
    }
  }

  return COLORS.LightGray
}