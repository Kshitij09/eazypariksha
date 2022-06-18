//
//  QuestionView.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

class QuestionView: UIView {
    @IBOutlet weak var textview: UITextView!
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var pointTextfield: UITextField!
    @IBOutlet weak var tableview: UITableView!
    
    private var optionList:[String] = ["","","",""]
    
    override func awakeFromNib() {
        super.awakeFromNib()
        tableview.registerNib(withCellType: QuestionTableViewCell.self)
        tableview.dataSource = self
        tableview.delegate = self
        pointTextfield.delegate = self
        textview.layer.cornerRadius = 5
        textview.layer.borderWidth = 1
        textview.layer.borderColor = UIColor.lightText.cgColor
    }
    
    func set(model: Question) {
        title.text = model.title
        pointTextfield.text = "0"
    }
}

extension QuestionView: UITableViewDataSource, UITableViewDelegate {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableview.getCell(withCellType: QuestionTableViewCell.self, indexPath: indexPath)
        cell.delegate = self
        cell.index = indexPath.row
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableview.cellForRow(at: indexPath)
        cell?.contentView.backgroundColor = UIColor.white
    }
}

extension QuestionView: UITextFieldDelegate {
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.endEditing(true)
    }
}

extension QuestionView: QuestionTableViewCellProtocol {
    func updateData(text: String?, index: Int) {
        optionList[index] = text ?? ""
    }
}
