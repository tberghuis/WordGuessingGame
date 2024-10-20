package xyz.tberghuis.wordguessinggame.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.wordguessinggame.COLORS
import xyz.tberghuis.wordguessinggame.WordleViewModel
import xyz.tberghuis.wordguessinggame.state.LetterMatchState

private val DarkColorPalette = darkColors(
)

private val LightColorPalette = lightColors(
)

// doitwrong
class WggColorPalette(
  val background: Color,
  val cellBorder: Color,

  val cellBackground: Map<LetterMatchState, Color>,
  val keyBackground: Map<LetterMatchState, Color>,
)

object ConstantsWggColors {
  // mapping isDark to WggColorPalette
  val wggColorsMap = mapOf<Boolean, WggColorPalette>(
    false to WggColorPalette(
      background = Color.White, cellBorder = Color(211, 214, 218), cellBackground = mapOf(
        LetterMatchState.ExactMatch to Color(106, 170, 100),
        LetterMatchState.Match to Color(201, 180, 88),
        LetterMatchState.NoMatch to Color(120, 124, 126),
        LetterMatchState.Unchecked to Color.White,
      ), keyBackground = mapOf(
        LetterMatchState.ExactMatch to Color(106, 170, 100),
        LetterMatchState.Match to Color(201, 180, 88),
        LetterMatchState.NoMatch to Color(120, 124, 126),
        LetterMatchState.Unchecked to Color(211, 214, 218),
      )
    ),
    true to WggColorPalette(
      background = Color(0x12121300), cellBorder = Color(58, 58, 60), cellBackground = mapOf(
        LetterMatchState.ExactMatch to Color(83, 141, 78),
        LetterMatchState.Match to Color(181, 159, 59),
        LetterMatchState.NoMatch to Color(58, 58, 60),
        LetterMatchState.Unchecked to Color(0x12121300),
      ), keyBackground = mapOf(
        LetterMatchState.ExactMatch to Color(83, 141, 78),
        LetterMatchState.Match to Color(181, 159, 59),
        LetterMatchState.NoMatch to Color(58, 58, 60),
        LetterMatchState.Unchecked to Color(129, 131, 132),
      )
    ),
  )
}


@Composable
fun WordGuessingGameTheme(
  content: @Composable () -> Unit
) {

  val isDarkTheme = viewModel<WordleViewModel>().isDarkTheme.value
  val colors = if (isDarkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors, typography = Typography, shapes = Shapes, content = content
  )
}

