//
//  HomeCellTableViewCell.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

protocol HomeCellTableViewCellInput {
    func setupData(model: HomeListModel)
}

protocol HomeCellTableViewCellType: UITableViewCell {
    var input: HomeCellTableViewCellInput { get }
}

class HomeCellTableViewCell: UITableViewCell,
                             HomeCellTableViewCellType,
                             HomeCellTableViewCellInput {
    var input: HomeCellTableViewCellInput { return self }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var imageview: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()
        
    }
    
    /// Use this function to load the data in image and title
    func setupData(model: HomeListModel) {
        titleLabel.text = model.title
        imageview.image = UIImage(systemName: model.image)
    }
}
