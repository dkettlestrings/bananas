package whatever

import org.scalatest.{FlatSpec, Matchers}

class TestFoo extends FlatSpec with Matchers {

  "Foo" should "foo" in {

    Foo.foo() should be (7)
  }

}
