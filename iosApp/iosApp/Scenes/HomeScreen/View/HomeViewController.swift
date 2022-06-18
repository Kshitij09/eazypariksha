//
//  ViewController.swift
//  easyPariksha
//
//  Created by Kishan Gupta on 18/06/22.
//

import UIKit
import shared

class HomeViewController: UIViewController {
    
    @IBOutlet weak var tableview: UITableView!
    private let homeList = HomeList.shared.list
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUserInterface()
        
    }
    
    private func setupUserInterface() {
        navigationController?.navigationBar.prefersLargeTitles = true
        title = StringConstant.shared.easyPariksha
        setupTableView()
    }
    
    private func setupTableView() {
        tableview.registerNib(withCellType: HomeCellTableViewCell.self)
        tableview.dataSource = self
    }
}

extension HomeViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return homeList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: HomeCellTableViewCellType = tableview.getCell(withCellType: HomeCellTableViewCell.self, indexPath: indexPath)
        cell.input.setupData(model: homeList[indexPath.row])
        return cell
    }
}

