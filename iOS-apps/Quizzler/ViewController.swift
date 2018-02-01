//
//  ViewController.swift
//  Quizzler
//
//  Created by Angela Yu on 25/08/2015.
//  Copyright (c) 2015 London App Brewery. All rights reserved.
//
//
//  Modified by Dana Huget 11/22/2017 (Udemy Course)

import UIKit

class ViewController: UIViewController {
    
    //Place your instance variables here
    
    // creating a QuestionBank object
    let allQuestions = QuestionBank()
    var pickedAnswer : Bool = false
    var questionNumber : Int = 0
    var score : Int = 0
    
    
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet var progressBar: UIView!
    @IBOutlet weak var progressLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // creating first Question using list property of QuestionBank
        let firstQuestion = allQuestions.list[0]
        questionLabel.text = firstQuestion.questionText
        updateUI()
        
    }


    @IBAction func answerPressed(_ sender: AnyObject) {
        
        if sender.tag == 1 {
            // true button pressed
            pickedAnswer = true
        } else if sender.tag == 2 {
            // false button pressed
            pickedAnswer = false
        }
        
        checkAnswer()
        
        questionNumber += 1
        nextQuestion()
  
    }
    
    
    func updateUI() {
        
        scoreLabel.text = "Score: \(score)"
        progressLabel.text = "\(questionNumber + 1) / 13"
        
        progressBar.frame.size.width = (view.frame.size.width / 13) * CGFloat(questionNumber + 1)
      
    }
    

    func nextQuestion() {
        
        if questionNumber <= 12 {
            
            questionLabel.text = allQuestions.list[questionNumber].questionText
            
            updateUI()
        }
        else {
            
            // create Alert
            let alert = UIAlertController(title: "Awesome", message: "You've finished all the questions, do you want to start over?", preferredStyle: .alert)
            // create Alert Action
            let restartAction = UIAlertAction(title: "Restart", style: .default, handler: { (UIAlertAction) in
                self.startOver()
            })
            
            alert.addAction(restartAction)
            
            present(alert, animated: true, completion: nil)
        }
        
    }
    
    
    func checkAnswer() {
        
        let correctAnswer = allQuestions.list[questionNumber].answer
        if correctAnswer == pickedAnswer {
            
            // use third party library to show a Heads Up Display to the UI
            ProgressHUD.showSuccess("Correct!")
            
            score += 1
        } else {
            
            ProgressHUD.showError("Wrong :(")
            
        }
        
    }
    
    
    func startOver() {
        
        score = 0
        questionNumber = 0
        nextQuestion()
       
    }
    

    
}
