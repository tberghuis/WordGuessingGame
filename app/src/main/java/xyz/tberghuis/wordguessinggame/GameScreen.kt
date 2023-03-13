package xyz.tberghuis.wordguessinggame

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.composables.SnackbarContainer
import xyz.tberghuis.wordguessinggame.composables.ThemeSwitcher
import xyz.tberghuis.wordguessinggame.state.LetterMatchState
import xyz.tberghuis.wordguessinggame.ui.theme.ConstantsWggColors.wggColorsMap
import xyz.tberghuis.wordguessinggame.util.logd

@Composable
fun GameScreen() {
  val viewModel: WordleViewModel = hiltViewModel()

  if (!viewModel.initialised.value) {
    return
  }


  val wordleState = viewModel.wordleState.value

  val configuration = LocalConfiguration.current
  val screenHeight = configuration.screenHeightDp.dp
//  var size by remember { mutableStateOf(Size.Unspecified) }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    ThemeSwitcher(darkTheme = viewModel.isDarkTheme.value,
      size = 40.dp,
      padding = 5.dp,
      onClick = {
        viewModel.toggleDarkTheme()
      })

//    Row {
//      Button(onClick = {
//        logd("toggle isDark")
//        viewModel.isDarkTheme.value = !viewModel.isDarkTheme.value
//      }) {
//        if (viewModel.isDarkTheme.value) {
//          Icon(Icons.Filled.LightMode, "light mode")
//        } else {
//          Icon(Icons.Filled.DarkMode, "dark mode")
//        }
//      }
//    }

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
  val isDarkTheme = vm.isDarkTheme.value
  val wggColorPalette = wggColorsMap.getValue(isDarkTheme)

  val cellBackground = wggColorPalette.cellBackground.getValue(cellState)

  val textColor =
    if (!isDarkTheme && cellState == LetterMatchState.Unchecked) Color.Black else Color.White

  Box(modifier = Modifier
    .padding(2.dp)
//      .size(62.dp)
    .weight(1f)
    .aspectRatio(1f)
    .let {
      if (cellState == LetterMatchState.Unchecked) {
        it.border(BorderStroke(2.dp, wggColorPalette.cellBorder))
      } else {
        it
      }
    }
    .background(cellBackground), contentAlignment = Alignment.Center) {
    Text(
      renderString,
//      modifier = Modifier.padding(16.dp)
      color = textColor
    )
  }
}

fun calcCellState(
  letter: String, row: Int, col: Int, cursorRow: Int, solution: String
): LetterMatchState {
  if (letter == "") {
    return LetterMatchState.Unchecked
  }
  if (row >= cursorRow) {
    return LetterMatchState.Unchecked
  }
  if (!solution.contains(letter)) {
    return LetterMatchState.NoMatch
  }
  if (solution[col] == letter[0]) {
    return LetterMatchState.ExactMatch
  }
  return LetterMatchState.Match
}


@Composable
fun RenderKeyboard() {
  val vm: WordleViewModel = hiltViewModel()

  // no need to use a lambda, should use fun
  val renderKeysInRow: @Composable RowScope.(row: List<String>) -> Unit = { row ->
    for (k in row) RenderKey(
      k, calcKeyState(k[0], vm.wordleState.value)
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
    RenderKey("⌫", LetterMatchState.Unchecked, 1.5f) {
      println("on click backspace")
      vm.removeLetter()
    }
    Spacer(Modifier.weight(.5f))
  }
  Row(Modifier.padding(vertical = 5.dp)) {
    Spacer(Modifier.weight(1f))
    RenderKey("Check", LetterMatchState.Unchecked) {
      println("on click enter")
      vm.onKeyUpEnter()
    }
    Spacer(Modifier.weight(1f))
  }

}

@Composable
fun RowScope.RenderKey(
  k: String, keyState: LetterMatchState, weight: Float = 1f, onClick: () -> Unit
) {
  // todo change font color to white if backgroundColor = (gray, green or yellow)

  val vm = hiltViewModel<WordleViewModel>()
  val backgroundColor = wggColorsMap.getValue(vm.isDarkTheme.value).keyBackground.getValue(keyState)

  val textColor = if (!vm.isDarkTheme.value && keyState == LetterMatchState.NoMatch) Color.White
  else Color.Unspecified

  Box(modifier = Modifier
    .padding(1.dp)
    .weight(weight)
    .clickable {
      onClick()
    }
    .background(backgroundColor), contentAlignment = Alignment.Center) {
    Text(k, modifier = Modifier.padding(vertical = 12.dp), color = textColor)
  }
}

fun calcKeyState(key: Char, wordleState: WordleState): LetterMatchState {
  if (wordleState.cursorRow == 0) {
    return LetterMatchState.Unchecked
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    for (i in 0..4) {
      if (key == wordleState.solution[i] && key == word[i]) {
        return LetterMatchState.ExactMatch
      }
    }
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    if (word.contains(key) && wordleState.solution.contains(key)) {
      return LetterMatchState.Match
    }
  }

  for (row in 0 until wordleState.cursorRow) {
    val word = wordleState.wordList[row]
    if (word.contains(key)) {
      return LetterMatchState.NoMatch
    }
  }

  return LetterMatchState.Unchecked
}

