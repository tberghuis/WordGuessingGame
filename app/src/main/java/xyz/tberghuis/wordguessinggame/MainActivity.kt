package xyz.tberghuis.wordguessinggame

import android.os.Bundle
import android.view.KeyEvent.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.ui.theme.WordGuessingGameTheme
import xyz.tberghuis.wordguessinggame.util.logd

class MainActivity : ComponentActivity() {

//  val viewModel by viewModels<WordleViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WordGuessingGameTheme {

        // https://stackoverflow.com/questions/70838476/onkeyevent-without-focus-in-jetpack-compose
        // feels hacky
        val focusRequester = remember { FocusRequester() }
        var hasFocus by remember { mutableStateOf(false) }
        if (!hasFocus) {
          LaunchedEffect(Unit) {
            focusRequester.requestFocus()
          }
        }

        val viewModel: WordleViewModel = hiltViewModel()

        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .onFocusChanged {
              hasFocus = it.hasFocus
            }
            .focusable()
            .onPreviewKeyEvent { onPreviewKeyEvent(it, viewModel) },
          color = MaterialTheme.colors.background
        ) {
          GameScreen()
//          KeyboardDemo()
        }
      }
    }
  }
}

fun onPreviewKeyEvent(keyEvent: KeyEvent, viewModel: WordleViewModel): Boolean {
  if (keyEvent.nativeKeyEvent.action != ACTION_UP) {
    return false
  }
  logd("onPreviewKeyEvent $keyEvent")
  when (keyEvent.nativeKeyEvent.keyCode) {
    KEYCODE_DEL -> {
      viewModel.removeLetter()
      return true
    }
    KEYCODE_ENTER -> {
      viewModel.onKeyUpEnter()
      return true
    }
  }
  val c = keyEvent.nativeKeyEvent.unicodeChar.toChar()
  logd("onPreviewKeyEvent c $c")
  if (c.isLetter()) {
    viewModel.addLetter(c.uppercaseChar())
    return true
  }
  return false
}

