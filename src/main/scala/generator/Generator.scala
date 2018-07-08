package generator

import scala.annotation.tailrec

/**
  * @see [[https://wiki.haskell.org/The_Monad.Reader/Issue5/Generating_Polyominoes]]
  */
object Generator {
  type Point = (Int, Int)
  type Polyomino = List[Point]

  def rotate90(p: Point): Point = (p._2, -p._1)

  def rotate180(p: Point): Point = (-p._1, -p._2)

  def rotate270(p: Point): Point = (-p._2, p._1)

  def reflect(p: Point): Point = (-p._1, p._2)

  def minima(polyomino: Polyomino): Point = polyomino.min

  def translateToOrigin(polyomino: Polyomino): Polyomino = {
    val m = minima(polyomino)
    polyomino.map(p => (p._1 - m._1, p._2 - m._2))
  }

  //  val rotations = Array[Point => Point](rotate90, rotate180, rotate270)
  def rotationsAndReflections(polyomino: Polyomino): List[Polyomino] = {
    val refPol = polyomino.map(reflect)
    List(
      polyomino,
      polyomino.map(rotate90),
      polyomino.map(rotate180),
      polyomino.map(rotate270),
      refPol,
      refPol.map(rotate90), // === pol
      refPol.map(rotate180),
      refPol.map(rotate270),
    )
  }

  def canonical(polyomino: Polyomino): Polyomino = {
    import Ordering.Implicits._
    rotationsAndReflections(polyomino)
      .map(translateToOrigin)
      .map(poly => poly.sorted).min
  }

//  def unique[T](a: List[T] => List[T]) = Set(a).toList

  def contiguous(p: Point): List[Point] = List(
    (p._1 - 1, p._2),
    (p._1 + 1, p._2),
    (p._1, p._2 - 1),
    (p._1, p._2 + 1),
  )

  def newPoints(polyomino: Polyomino): List[Point] = {
    polyomino.flatMap(contiguous).filterNot(polyomino.contains(_)).distinct
  }

  def newPolyominos(polyomino: Polyomino): List[Polyomino] = {
    newPoints(polyomino).map(p => canonical(p :: polyomino))
  }

  val monomino: Polyomino = List((0, 0))
  val monominos: List[Polyomino] = List(monomino)

//  @tailrec
  def rank(n: Int): List[Polyomino] = {
    require(n >= 0)
    n match {
      case 0 => Nil
      case 1 => monominos
      case _ => rank(n - 1).flatMap(newPolyominos).distinct
    }
  }
}
