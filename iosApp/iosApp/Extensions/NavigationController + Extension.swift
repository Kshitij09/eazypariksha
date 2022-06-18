//
//  NavigationController + extension.swift
//  iosApp
//
//  Created by Kishan Gupta on 18/06/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit

extension UINavigationController {
    func pushController(with identifier: String) {
        if let controller = storyboard?.instantiateViewController(withIdentifier: identifier) {
            self.navigationBar.tintColor = UIColor.black
            pushViewController(controller, animated: true)
        }
    }
}
