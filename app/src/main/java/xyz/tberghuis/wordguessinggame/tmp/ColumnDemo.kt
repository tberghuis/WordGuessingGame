package xyz.tberghuis.wordguessinggame.tmp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ColumnDemo() {
  Box(Modifier.size(250.dp, 500.dp)) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
//      Text("hello column demo")
      Box(
        Modifier
          .size(100.dp, 100.dp)
          .border(2.dp, Color.Black)
      )
      Box(
        Modifier
          .size(100.dp, 100.dp)
          .border(2.dp, Color.Black)
      )
      Box(
        Modifier
          .size(100.dp, 100.dp)
          .border(2.dp, Color.Black)
      )
    }
  }

}