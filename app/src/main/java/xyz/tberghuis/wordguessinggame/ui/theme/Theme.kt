package xyz.tberghuis.wordguessinggame.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.wordguessinggame.WordleViewModel
import xyz.tberghuis.wordguessinggame.state.LetterMatchState

private val DarkColorPalette = darkColorScheme(
)

private val LightColorPalette = lightColorScheme(
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
//  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
//  dynamicColor: Boolean = true,
  content: @Composable () -> Unit
) {

  val isDarkTheme = viewModel<WordleViewModel>().isDarkTheme.value
  val colors = if (isDarkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }


//  val colorScheme = when {
//    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//      val context = LocalContext.current
//      if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//    }
//
//    isDarkTheme -> DarkColorPalette
//    else -> LightColorPalette
//  }


  MaterialTheme(
    colorScheme = colors, typography = Typography, content = content
  )
}

