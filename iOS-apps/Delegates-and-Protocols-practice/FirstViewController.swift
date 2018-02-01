//
//  FirstViewController.swift
//  Delegates and Protocols
//
//  Created by Dana Huget on 2017-12-05.
//  Copyright © 2017 Dana Huget. All rights reserved.
//

import UIKit

// MARK: STEP 2: in the VC that is recieving the data, conform to the protocol at the class declaration line, this class will be the delegate
class FirstViewController: UIViewController, CanRecieve {

    
    @IBOutlet weak var label: UILabel!
    @IBOutlet weak var textField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //label.text = dataPassedBack

    }

    @IBAction func sendButtonPressed(_ sender: Any) {
        // trigger segue
        performSegue(withIdentifier: "sendDataForwards", sender: self) // sender is self, the origin of the segue
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "sendDataForwards" {
            
            let secondVC = segue.destination as! SecondViewController
            
            secondVC.data = textField.text!
            
            // MARK: Step 5: set the second view controller's delegate as this current view controller
            secondVC.delegate = self
            
        }
    }
    
    // MARK: STEP 3: implement the required delegate method
    func dataReceived(data: String) {
        //do something with the data that is recieved
        label.text = data
    }

}

