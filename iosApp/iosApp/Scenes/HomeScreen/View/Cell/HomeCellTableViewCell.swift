//
//  HomeCellTableViewCell.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit

protocol HomeCellTableViewCellInput {
    func setupData()
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
    func setupData() {
        titleLabel.text = "Hello world"
        imageview.image = UIImage(systemName: "plus")
    }
}
