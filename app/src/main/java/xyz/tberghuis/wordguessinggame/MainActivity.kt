package xyz.tberghuis.wordguessinggame

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.tmp.KeyboardDemo
import xyz.tberghuis.wordguessinggame.ui.theme.WordGuessingGameTheme
import xyz.tberghuis.wordguessinggame.util.logd

class MainActivity : ComponentActivity() {


//  val viewModel by viewModels<WordleViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WordGuessingGameTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          GameScreen()
//          KeyboardDemo()
        }
      }
    }
  }


//  override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
//    logd("KeyEvent $event")
//    return true
//  }
}

