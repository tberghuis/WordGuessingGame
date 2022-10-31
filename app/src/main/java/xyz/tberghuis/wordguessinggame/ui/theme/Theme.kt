package xyz.tberghuis.wordguessinggame.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.WordleViewModel

private val DarkColorPalette = darkColors(
)

private val LightColorPalette = lightColors(
)

// doitwrong
class WggColorPalette(val background: Color)

object ConstantsWggColors {
  // mapping isDark to WggColorPalette
  val wggColorsMap = mapOf(
    false to WggColorPalette(Color.White),
    true to WggColorPalette(Color.Black),
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
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}

