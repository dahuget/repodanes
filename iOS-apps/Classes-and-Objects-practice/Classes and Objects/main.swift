//
//  main.swift
//  Classes and Objects
//
//  Created by Dana Huget on 2017-11-22.
//  Copyright © 2017 Dana Huget. All rights reserved.
//

import Foundation

let myCar = Car()

let myCar2 = Car(custmerChosenColour: "Red")

myCar.drive()

let mySelfDrivingCar = SelfDrivingCar()

mySelfDrivingCar.destination = "1 Infinite Loop"
print(mySelfDrivingCar.colour)
mySelfDrivingCar.drive()


