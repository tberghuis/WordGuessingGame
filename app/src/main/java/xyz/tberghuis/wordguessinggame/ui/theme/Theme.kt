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
  darkTheme: Boolean = isDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
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


@Composable
fun isDarkTheme(): Boolean {
  val vm = hiltViewModel<WordleViewModel>()
  if (vm.darkTheme.value == null) {
    val isDark = isSystemInDarkTheme()

    return isDark
  }
  return vm.darkTheme.value!!
}