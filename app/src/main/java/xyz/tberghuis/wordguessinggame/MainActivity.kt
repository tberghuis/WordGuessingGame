package xyz.tberghuis.wordguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import xyz.tberghuis.wordguessinggame.ui.theme.WordGuessingGameTheme

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