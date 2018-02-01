//
//  ViewController.swift
//  Xylophone
//
//  Created by Angela Yu on 27/01/2016.
//  Copyright © 2016 London App Brewery. All rights reserved.
//
//
//  Modified by Dana Huget 10/31/2017 (Udemy Course)

import UIKit
import AVFoundation

class ViewController: UIViewController, AVAudioPlayerDelegate{
    
    var player: AVAudioPlayer!
    let soundArray = ["note1", "note2", "note3", "note4", "note5", "note6", "note7"] // array of sound file names
    var selectedSoundFileName : String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func notePressed(_ sender: UIButton) {
        
        selectedSoundFileName = soundArray[sender.tag - 1]
        
        playSound(play: selectedSoundFileName)
        
    }
    
    func playSound(play soundFile: String){
        
        let url = Bundle.main.url(forResource: soundFile, withExtension: "wav")
        
        do {
            // try initializing the audio player
            try player = AVAudioPlayer(contentsOf: url!)
        }
        catch {
            print(error)
        }
        
        player.play()
        
    }

}

