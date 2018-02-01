//
//  Car.swift
//  Classes and Objects
//
//  Created by Dana Huget on 2017-11-22.
//  Copyright © 2017 Dana Huget. All rights reserved.
//

import Foundation

// create enumerations (custom data type) at top of code

enum CarType {
    case Sedan
    case Coupe
    case Hatchback
}

class Car {
    
    // properties
    var colour = "Black"
    var numberOfSeats = 5
    var typeOfCar: CarType = .Coupe
    
    // designatied initializer
    init() {
        
    }
    
    //convenience initializer
    convenience init (custmerChosenColour : String) {
        self.init()
        colour = custmerChosenColour
    }
    
    //methods
    func drive(){
        print("vroom vroom")
    }
    
}
