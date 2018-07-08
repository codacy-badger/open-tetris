package generator

import generator.Generator.Polyomino
import org.scalatest.{FlatSpec, Matchers}

class GeneratorSpec extends FlatSpec with Matchers {
  "Generator" should "generate Monominos correctly" in {
    Generator.rank(1) should be (List(List((0,0))))
  }

  "Generator" should "generate Dominos correctly" in {
    Generator.rank(2) should be (List(List((0,0), (0,1))))
  }

  "Generator" should "generate Pentominos correctly" in {
    val expected = List[Polyomino](
      List((0,0), (0,1), (1,1), (1,2), (2,1)),
      List((0,0), (0,1), (0,2), (1,0), (1,1)),
      List((0,0), (0,1), (0,2), (0,3), (1,1)),
      List((0,1), (1,0), (1,1), (1,2), (2,1)),
      List((0,0), (0,1), (0,2), (1,1), (2,1)),
      List((0,0), (0,1), (1,1), (1,2), (2,2)),
      List((0,0), (0,1), (0,2), (1,2), (1,3)),
      List((0,0), (0,1), (1,1), (2,1), (2,2)),
      List((0,0), (0,1), (0,2), (1,0), (1,2)),
      List((0,0), (0,1), (0,2), (0,3), (1,0)),
      List((0,0), (0,1), (0,2), (1,0), (2,0)),
      List((0,0), (0,1), (0,2), (0,3), (0,4)))

    val tetronimos = Generator.rank(5)
    tetronimos.foreach(t => println(t.mkString(" ")))
    tetronimos should be (expected)
  }
}
