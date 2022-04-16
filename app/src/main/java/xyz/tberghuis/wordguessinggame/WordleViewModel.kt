package xyz.tberghuis.wordguessinggame

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordleViewModel @Inject constructor(

) : ViewModel() {
  val hellovm = "hello view model"
}