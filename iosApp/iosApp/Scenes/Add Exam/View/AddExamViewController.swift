//
//  AddExamViewController.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//StringConstant.shared.easyPariksha

import UIKit
import shared

class AddExamViewController: UIViewController {
    @IBOutlet weak var datepicker: UIDatePicker!
    @IBOutlet weak var dropdownView: UIImageView!
    @IBOutlet weak var subjectTextfield: UITextField!
    private let subjectPicker = UIPickerView()
    private var subjectList: [Subject] = []
    private var selectedSubjectId: Int32 = -1

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUserInterface()
    }
    
    private func setupUserInterface() {
        title = StringConstant.shared.addExam
        subjectTextfield.inputView = subjectPicker
        subjectPicker.delegate = self
        datepicker.minimumDate = Date()
        datepicker.setDate(Date(), animated: false)
        initiateGestures()
        loadData()
    }
    
    private func initiateGestures() {
        dropdownView.isUserInteractionEnabled = true
        self.view.isUserInteractionEnabled = true
        dropdownView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(showPicker)))
        self.view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(hidePicker)))
    }
    
    @objc func showPicker() {
        subjectTextfield.becomeFirstResponder()
    }
    
    @objc func hidePicker() {
        subjectTextfield.resignFirstResponder()
    }
    
    private func loadData() {
        RepositoryModule.shared.provideExamRepository().getAllSubjects
        { [weak self] subjectList, _ in
            if let list = subjectList {
                self?.subjectList = list
                self?.subjectTextfield.text = list.first?.name ?? ""
                self?.selectedSubjectId = list.first?.id ?? -1
            }
        }
    }
    
    @IBAction func didTapNext(_ button: UIButton) {
        if selectedSubjectId != -1 {
            let identifier = String(describing: AddQuestionsViewController.self)
            navigationController?.pushController(with: identifier)
        }
    }
}

extension AddExamViewController: UIPickerViewDelegate, UIPickerViewDataSource  {
    // MARK: UIPickerView Delegation
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }

    func pickerView( _ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return subjectList.count
    }

    func pickerView( _ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return subjectList[row].name
    }

    func pickerView( _ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        subjectTextfield.text = subjectList[row].name
        selectedSubjectId = subjectList[row].id
    }
}
