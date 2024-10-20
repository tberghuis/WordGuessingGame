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
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.wordguessinggame.WordleViewModel
import xyz.tberghuis.wordguessinggame.util.logd

@Composable
fun SnackbarContainer() {
  val viewModel: WordleViewModel = viewModel()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.snackbarSharedFlow.collect {
      logd("snackbarSharedFlow collect $it")
      snackbarHostState.showSnackbar(it)
    }
  }
  val background = if (viewModel.isDarkTheme.value) Color.White else Color.Black
  val textColor = if (viewModel.isDarkTheme.value) Color.Black else Color.White

  SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData: SnackbarData ->
    Box(
      Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        modifier = Modifier
          .background(background)
          .padding(16.dp),
        text = snackbarData.message,
        color = textColor,
      )
    }
  })
}