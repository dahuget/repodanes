//
//  ViewController.swift
//  
//  Example Sound Player
//

import UIKit
import AVFoundation

class ViewController: UIViewController, AVAudioPlayerDelegate{
    
    var player: AVAudioPlayer!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func notePressed(_ sender: UIButton) {
        
        let url = Bundle.main.url(forResource: "soundFileName", withExtension: "wav")
        
        do {
            player = try AVAudioPlayer(contentsOf: url!)
        }
        catch {
            print(error)
        }
        
        player.play()
        
    }

}

