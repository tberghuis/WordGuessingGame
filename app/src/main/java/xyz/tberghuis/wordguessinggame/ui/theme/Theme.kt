package xyz.tberghuis.wordguessinggame.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.COLORS
import xyz.tberghuis.wordguessinggame.WordleViewModel
import xyz.tberghuis.wordguessinggame.state.CellState

private val DarkColorPalette = darkColors(
)

private val LightColorPalette = lightColors(
)

// doitwrong
class WggColorPalette(
  val background: Color,
  val cellBorder: Color,

  val cellBackground: Map<CellState, Color>,
  val keyBackground: Map<CellState, Color>,
)

object ConstantsWggColors {
  // mapping isDark to WggColorPalette
  val wggColorsMap = mapOf<Boolean, WggColorPalette>(
    false to WggColorPalette(
      background = Color.White, cellBorder = Color(0x3a3a3c00), cellBackground = mapOf(
        CellState.ExactMatch to COLORS.Green,
        CellState.Match to COLORS.Yellow,
        CellState.NoMatch to COLORS.Gray,
        CellState.Unchecked to Color(0x12121300),
      ), keyBackground = mapOf(
        CellState.ExactMatch to Color(83, 141, 78),
        CellState.Match to Color(181, 159, 59),
        CellState.NoMatch to Color(58, 58, 60),
        CellState.Unchecked to Color(129, 131, 132),
      )
    ),
    true to WggColorPalette(
      background = Color(0x12121300), cellBorder = Color(58, 58, 60), cellBackground = mapOf(
        CellState.ExactMatch to Color(83, 141, 78),
        CellState.Match to Color(181, 159, 59),
        CellState.NoMatch to Color(58, 58, 60),
        CellState.Unchecked to Color(0x12121300),
      ), keyBackground = mapOf(
        CellState.ExactMatch to Color(83, 141, 78),
        CellState.Match to Color(181, 159, 59),
        CellState.NoMatch to Color(58, 58, 60),
        CellState.Unchecked to Color(129, 131, 132),
      )
    ),
  )
}


@Composable
fun WordGuessingGameTheme(
  content: @Composable () -> Unit
) {

  val isDarkTheme = hiltViewModel<WordleViewModel>().isDarkTheme.value
  val colors = if (isDarkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors, typography = Typography, shapes = Shapes, content = content
  )
}

