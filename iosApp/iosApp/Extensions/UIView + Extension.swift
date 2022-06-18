//
//  UIView + Extension.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit

extension UIView {
    class func fromNib<T: UIView>() -> T {
        let nibName = String(describing: self)
        return Bundle.main.loadNibNamed(nibName, owner: nil, options: nil)![0] as! T
    }
}
