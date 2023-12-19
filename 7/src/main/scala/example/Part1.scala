package example

import scala.io.Source

case class Hand(cards: String, power: Int, bid: Int)

object Part1 extends App {
  implicit val handOrdering: Ordering[Hand] = new Ordering[Hand] {
    private def cardToNum(c: Char): Int =
      c match {
        case 'A' => 14
        case 'K' => 13
        case 'Q' => 12
        case 'J' => 11
        case 'T' => 10
        case num => num.toInt - 48
      }

    private def compareHighetsCard(x: String, y: String): Int = {
      if (x.size == 0 || y.size == 0) 0
      else if (x.head == y.head) compareHighetsCard(x.tail, y.tail)
      else Ordering[Int].compare(cardToNum(x.head), cardToNum(y.head))
    }

    override def compare(x: Hand, y: Hand): Int = {
      val powerResult = Ordering[Int].compare(x.power, y.power)
      if (powerResult != 0) powerResult
      else compareHighetsCard(x.cards, y.cards)
    }

  }

  def getPower(cards: String): Int = {
    val sameCardOccurence = cards
      .groupBy(identity)
      .map(x => x._2.size)
      .toList
      .sorted
      .reverse

    sameCardOccurence match {
      case 5 :: Nil                => 7
      case 4 :: 1 :: Nil           => 6
      case 3 :: 2 :: Nil           => 5
      case 3 :: 1 :: 1 :: Nil      => 4
      case 2 :: 2 :: 1 :: Nil      => 3
      case 2 :: 1 :: 1 :: 1 :: Nil => 2
      case _                       => 1
    }
  }

  val input = Source.fromResource("input.txt").mkString
  val orderedHands = input
    .split("\n")
    .map(line => {
      val parts = line.split(" ")
      val cards = parts(0)
      val bid = parts(1).toInt

      Hand(cards, getPower(cards), bid)
    })
    .toList
    .sorted

  val result = orderedHands.zipWithIndex
    .foldLeft(0) { case (sum, (hand, idx)) =>
      sum + (hand.bid * (idx + 1))
    }

  println(orderedHands)
  println(result)
}
