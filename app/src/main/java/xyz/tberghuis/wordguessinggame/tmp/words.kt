package xyz.tberghuis.wordguessinggame.tmp

import xyz.tberghuis.wordguessinggame.TOP_WORDS
import xyz.tberghuis.wordguessinggame.VALID_WORDS
import java.io.File

fun main() {
  val _allwords = VALID_WORDS + TOP_WORDS

//  println(_allwords.size)

  val allwords = _allwords.toSet().sorted().toList()
//  println(allwords.size)
//  print(allwords)

  var s = ""


  val itr = allwords.listIterator()
  while (itr.hasNext()) {
    s += "${itr.next()}\n"
  }

  File("/home/tom/tmp/allwords.txt").writeText(s)

}