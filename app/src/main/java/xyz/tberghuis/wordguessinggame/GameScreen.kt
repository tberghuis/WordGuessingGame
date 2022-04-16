package xyz.tberghuis.wordguessinggame

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun GameScreen() {


  val viewModel: WordleViewModel = hiltViewModel()


  Column {
    Text("hello game screen ${viewModel.hellovm}")
  }

}