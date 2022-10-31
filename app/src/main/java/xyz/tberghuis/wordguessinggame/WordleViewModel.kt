package xyz.tberghuis.wordguessinggame

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordleViewModel @Inject constructor(
  @ApplicationContext val appContext: Context
) : ViewModel() {

  //  val wordleStateFlow = MutableStateFlow(WordleState())
  val wordleState = mutableStateOf(WordleState())

  val snackbarSharedFlow = MutableSharedFlow<String>()


  val isDarkTheme = mutableStateOf(initIsDarkTheme())

  private fun initIsDarkTheme(): Boolean {
    // todo
//    https://stackoverflow.com/questions/44170028/android-how-to-detect-if-night-mode-is-on-when-using-appcompatdelegate-mode-ni

//  todo  onResume check if Configuration changed, meh user can toggle setting with button


    // doing it wrong
    val nightModeFlags: Int =
      appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
      return true
    }
    return false
  }


  fun newGame() {
    wordleState.value = WordleState()
  }

  fun addLetter(letter: Char) {
    println("addLetter $letter")
    val ws = wordleState.value

    // i should really block calling this in the UI layer
    if (ws.gameState != GameState.PLAYING) {
      return
    }

    // word being edited
    val word = ws.wordList[ws.cursorRow]
    var newWord = word

    when {
      word.length < 5 -> {
        newWord += letter
      }
      else -> {
        return
      }
    }

    println("addLetter after when $letter")

    val newWordList = ws.wordList.toMutableList()
    newWordList[ws.cursorRow] = newWord

    wordleState.value = ws.copy(
      wordList = newWordList,
    )
  }

  fun removeLetter() {
    println("removeLetter")

    val ws = wordleState.value
    if (ws.gameState != GameState.PLAYING) {
      return
    }
    val word = ws.wordList[ws.cursorRow]

    if (word.isEmpty()) {
      return
    }

    val newWord = word.substring(0, word.length - 1)
    val newWordList = ws.wordList.toMutableList()
    newWordList[ws.cursorRow] = newWord

    wordleState.value = ws.copy(
      wordList = newWordList
    )
  }

  fun onKeyUpEnter() {
    println("onKeyUpEnter")

    val ws = wordleState.value
    if (ws.gameState != GameState.PLAYING) {
      return
    }
    val word = ws.wordList[ws.cursorRow]
    if (word.length != 5) {
      return
    }

    if (!VALID_WORDS.contains(word)) {
      // is this the correct scope for compose desktop?
      viewModelScope.launch {
        snackbarSharedFlow.emit("Not in word list")
      }
      return
    }

    // check if won, add to game state
    if (word == ws.solution) {
      viewModelScope.launch {
        snackbarSharedFlow.emit("Winner")
      }
      wordleState.value = ws.copy(
        gameState = GameState.WON, cursorRow = ws.cursorRow + 1
      )
      return
    }

    // game lost
    if (ws.cursorRow == 5) {
      viewModelScope.launch {
        snackbarSharedFlow.emit("Loser")
      }
      wordleState.value = ws.copy(
        gameState = GameState.LOST, cursorRow = ws.cursorRow + 1
      )
      return
    }

    wordleState.value = ws.copy(
      cursorRow = ws.cursorRow + 1
    )
  }

}