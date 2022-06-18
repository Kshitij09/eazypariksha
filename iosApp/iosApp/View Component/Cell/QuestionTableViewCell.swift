//
//  QuestionTableViewCell.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

protocol QuestionTableViewCellProtocol: AnyObject {
    func updateData(text: String?, index: Int)
}

class QuestionTableViewCell: UITableViewCell {
    
    @IBOutlet weak var textfield: UITextField!
    @IBOutlet weak var imageview: UIImageView!
    weak var delegate: QuestionTableViewCellProtocol?
    var index: Int!

    override func awakeFromNib() {
        super.awakeFromNib()

    }
}

extension QuestionTableViewCell: UITextFieldDelegate {
    func textFieldDidEndEditing(_ textField: UITextField) {
        delegate?.updateData(text: textField.text, index: index)
        textField.resignFirstResponder()
    }
}
