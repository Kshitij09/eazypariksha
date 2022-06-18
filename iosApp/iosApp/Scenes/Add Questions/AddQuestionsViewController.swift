//
//  AddQuestionsViewController.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit

class AddQuestionsViewController: UIViewController {
    
    private var questionView: QuestionView?

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        title = "Add Question"
        questionView = QuestionView.fromNib()
        questionView?.frame = CGRect(x: 0, y: 0, width: view.frame.width, height: UIScreen.main.bounds.height - 100)
        view.addSubview(questionView!)
    }
    
    @IBAction func saveAction(_ button: UIButton) {
           
        
    }
}
