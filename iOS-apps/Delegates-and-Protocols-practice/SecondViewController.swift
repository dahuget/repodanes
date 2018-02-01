//
//  SecondViewController.swift
//  Delegates and Protocols
//
//  Created by Dana Huget on 2017-12-05.
//  Copyright © 2017 Dana Huget. All rights reserved.
//

import UIKit

// MARK: STEP 1: create protocol that has a name and required method
protocol CanRecieve {
    
    func dataReceived(data: String)
    
}

class SecondViewController: UIViewController {

    // MARK: Step 4: create delegate property of type CanRecieve
    var delegate : CanRecieve? //might be nil
    
    var data = ""
    
    @IBOutlet weak var label: UILabel!
    @IBOutlet weak var textField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        label.text = data

    }

    
    @IBAction func sendDataBack(_ sender: Any) {
        // if a delegate is assigned then this method get triggeted and sends text field data to whoever the delegate is via the dataReceived method
        delegate?.dataReceived(data: textField.text!) // not triggered if delegate is nil
        
        // MARK: Step 6: dismiss the second VC so we can go back to the VC that is underneath, namely the previous instance of the firstVC
        dismiss(animated: true, completion: nil)
    }
    
}
