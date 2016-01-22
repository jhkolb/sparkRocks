package edu.berkeley.ce.rockslicing

import org.scalatest._
import scala.math.sqrt

class RockSlicerSpec extends FunSuite {
  val boundingFaces = List(
    Face((-1.0, 0.0, 0.0), 0.5, phi=0, cohesion=0, processorJoint = true), // -x = 0.0
    Face((1.0, 0.0, 0.0), 0.5, phi=0, cohesion=0),  // x = 2
    Face((0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0), // -y = 0
    Face((0.0, 1.0, 0.0), 2.0, phi=0, cohesion=0),  // y = 2
    Face((0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0), // -z = 0
    Face((0.0, 0.0, 1.0), 2.0, phi=0, cohesion=0)   // z = 2
  )
  val leftCube = Block((1.5, 0.0, 0.0), boundingFaces)

  val boundingFaces2 = List(
    Face((-1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0), // -x = 0
    Face((1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0, processorJoint = true), // x = 2
    Face((0.0, -1.0, 0.0), 2.0, phi=0, cohesion=0), // -y = 0
    Face((0.0, 1.0, 0.0), 0.0, phi=0, cohesion=0), // y = 2
    Face((0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0), // -z = 0
    Face((0.0, 0.0, 1.0), 2.0, phi=0, cohesion=0) // z = 2
  )
  val rightCube = Block((1.0, 2.0, 0.0), boundingFaces2)

  val boundingFaces3 = List(
    Face((-1.0, 0.0, 0.0), 0.0, phi=0, cohesion=0), // -x = 0
    Face((1.0, 0.0, 0.0), 1.0, phi=0, cohesion=0), // x = 1
    Face((0.0, -1.0, 0.0), 0.0, phi=0, cohesion=0), // -y = 0
    Face((0.0, 1.0, 0.0), 1.0, phi=0, cohesion=0), // y = 1
    Face((0.0, 0.0, -1.0), 0.0, phi=0, cohesion=0), // -z = 0
    Face((0.0, 0.0, 1.0), 1.0, phi=0, cohesion=0) // z = 1
  )
  val unitCube = Block((0.0, 0.0, 0.0), boundingFaces3)

  val boundingFaces4 = List(
    Face((-1.0, 0.0, 0.0), 0.5, phi=0, cohesion=0),
    Face((1.0, 0.0, 0.0), 0.5, phi=0, cohesion=0), 
    Face((0.0, -1.0, 0.0), 0.5, phi=0, cohesion=0),
    Face((0.0, 1.0, 0.0), 0.5, phi=0, cohesion=0), 
    Face((0.0, 0.0, -1.0), 0.5, phi=0, cohesion=0),
    Face((0.0, 0.0, 1.0), 0.5, phi=0, cohesion=0) 
  )
  val nonOriginUnitCube = Block((0.5, 0.5, 0.5), boundingFaces4)

  test("The two cubes should share one processor face") {
    val leftCenter = (leftCube.centerX, leftCube.centerY, leftCube.centerZ)
    val updatedRightCube = Block(leftCenter, rightCube.updateFaces(leftCenter))

    val sharedFaces = RockSlicer.compareProcessorBlocks(leftCube, updatedRightCube)
    // println(sharedFaces)
    assert(sharedFaces.length == 2)
  }

  test("The two cubes should share one processor face, but with distances reversed") {
    val rightCenter = (rightCube.centerX, rightCube.centerY, rightCube.centerZ)
    val updatedLeftCube = Block(rightCenter, leftCube.updateFaces(rightCenter))
    val sharedFaces = RockSlicer.compareProcessorBlocks(rightCube, updatedLeftCube)
    // println(sharedFaces)
    assert(sharedFaces.length == 2)
  }
}