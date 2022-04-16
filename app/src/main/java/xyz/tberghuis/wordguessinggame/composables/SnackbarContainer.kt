package xyz.tberghuis.wordguessinggame.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.wordguessinggame.WordleViewModel
import kotlinx.coroutines.flow.collect
import xyz.tberghuis.wordguessinggame.util.logd


@Composable
fun SnackbarContainer() {
  val viewModel: WordleViewModel = hiltViewModel()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.snackbarSharedFlow.collect {
      logd("snackbarSharedFlow collect $it")
      snackbarHostState.showSnackbar(it)
    }
  }

  SnackbarHost(
    hostState = snackbarHostState,
    snackbar = { snackbarData: SnackbarData ->
      Box(Modifier.fillMaxSize()) {
        Card(
          Modifier
//            .padding(top = 100.dp)
            .align(Alignment.Center)
            .background(Color.Black)
            .padding(16.dp)
        ) {
          Text(
            modifier = Modifier.background(Color.Black),
            text = snackbarData.message,
            color = Color.White
          )
        }
      }
    }
  )
}