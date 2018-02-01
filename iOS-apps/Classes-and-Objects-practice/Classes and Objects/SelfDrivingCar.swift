//
//  SelfDrivingCar.swift
//  Classes and Objects
//
//  Created by Dana Huget on 2017-11-22.
//  Copyright © 2017 Dana Huget. All rights reserved.
//

import Foundation

class SelfDrivingCar : Car { //inheirits all the capabilities & properties of the Car superclass
    
    var destination : String? //means this may contain a nil value
    
    override func drive(){
        super.drive()
        
        //custom drive to destination functionality
        /*if destination != nil {
            print("driving towards " + destination!)
        }*/
        //optional binding for SAFE code
        if let userSetDestination = destination {
            print("driving towards " + userSetDestination)
        }
    }
    
}
