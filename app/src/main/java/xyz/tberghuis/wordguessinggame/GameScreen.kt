package xyz.tberghuis.wordguessinggame

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.composables.SnackbarContainer
import xyz.tberghuis.wordguessinggame.state.CellState
import xyz.tberghuis.wordguessinggame.ui.theme.ConstantsWggColors.wggColorsMap
import xyz.tberghuis.wordguessinggame.util.logd

@Composable
fun GameScreen() {
  val viewModel: WordleViewModel = hiltViewModel()
  val wordleState = viewModel.wordleState.value

  val configuration = LocalConfiguration.current
  val screenHeight = configuration.screenHeightDp.dp
//  var size by remember { mutableStateOf(Size.Unspecified) }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {

    Row {
      Button(onClick = {
        logd("toggle isDark")
        viewModel.isDarkTheme.value = !viewModel.isDarkTheme.value
      }) {
        Text("toggle isDark")
      }
    }

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
          wordleState.solution, modifier = Modifier.padding(15.dp)
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

@Composable
fun RenderGameBoard(wordleState: WordleState, screenHeight: Dp) {

  val cursorRow = wordleState.cursorRow
  val wordList = wordleState.wordList
  val solution = wordleState.solution

  val maxSize = minOf(screenHeight * 0.45f, 500.dp)


  Column(
    Modifier
      .padding(horizontal = 2.dp)
      .widthIn(0.dp, maxSize)
//      .heightIn(0.dp, screenHeight * 0.3f)
//      .sizeIn(0.dp, 0.dp, maxSize, maxSize)
  ) {
    for (i in 0..5) {
      Row {
        for (j in 0..4) {
          if (j >= wordList[i].length) {
            RenderChar(null, i, j, cursorRow, solution)
          } else {
            RenderChar(wordList[i][j], i, j, cursorRow, solution)
          }
        }
      }
    }
  }
}


// may not need to pass shit down...
// who cares
@Composable
fun RowScope.RenderChar(c: Char?, row: Int, col: Int, cursorRow: Int, solution: String) {
  val renderString = if (c == null) "" else "$c".uppercase()

  val cellState = calcCellState(renderString, row, col, cursorRow, solution)

//  val backgroundColor = calcBackgroundColor(renderString, row, col, cursorRow, solution)

  val vm = hiltViewModel<WordleViewModel>()
  val isDarkThene = vm.isDarkTheme.value
  val wggColorPalette = wggColorsMap.getValue(isDarkThene)

  val cellBackground = wggColorPalette.cellBackground.getValue(cellState)

  Box(
    modifier = Modifier.padding(2.dp)
//      .size(62.dp)
      .weight(1f).aspectRatio(1f).let {
        if (cellState == CellState.Unchecked) {
          it.border(BorderStroke(2.dp, wggColorPalette.cellBorder))
        } else {
          it
        }
      }.background(cellBackground), contentAlignment = Alignment.Center
  ) {
    Text(
      renderString,
//      modifier = Modifier.padding(16.dp)
    )
  }
}

fun calcCellState(
  letter: String, row: Int, col: Int, cursorRow: Int, solution: String
): CellState {
  if (letter == "") {
    return CellState.Unchecked
  }
  if (row >= cursorRow) {
    return CellState.Unchecked
  }
  if (!solution.contains(letter)) {
    return CellState.NoMatch
  }
  if (solution[col] == letter[0]) {
    return CellState.ExactMatch
  }
  return CellState.Match
}


@Composable
fun RenderKeyboard() {
  val vm: WordleViewModel = hiltViewModel()

  // no need to use a lambda, should use fun
  val renderKeysInRow: @Composable RowScope.(row: List<String>) -> Unit = { row ->
    for (k in row) RenderKey(
      k, deriveKeyBackgroundColor(k[0], vm.wordleState.value)
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
    RenderKey("Check", COLORS.LightGray) {
      println("on click enter")
      vm.onKeyUpEnter()
    }
    Spacer(Modifier.weight(1f))
  }

}

@Composable
fun RowScope.RenderKey(k: String, backgroundColor: Color, weight: Float = 1f, onClick: () -> Unit) {
  // todo change font color to white if backgroundColor = (gray, green or yellow)

  Box(modifier = Modifier
    .padding(1.dp)
    .weight(weight)
    .clickable {
      onClick()
    }
    .background(backgroundColor), contentAlignment = Alignment.Center) {
    Text(
      k, modifier = Modifier.padding(vertical = 12.dp)
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