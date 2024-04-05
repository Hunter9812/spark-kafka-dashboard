package com.example

import org.scalatest.funsuite.AnyFunSuite

class SimpleTest extends AnyFunSuite {
    test("Simplest test possible") {
        assert("Scala".toLowerCase == "scala")
    }
}
