//
//  ViewController.swift
//  easyPariksha
//
//  Created by Kishan Gupta on 18/06/22.
//

import UIKit
import shared

class ViewController: UIViewController {
    
    @IBOutlet weak var tableview: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUserInterface()
        
    }
    
    private func setupUserInterface() {
        navigationController?.navigationBar.prefersLargeTitles = true
        title = StringConstant.eazyPariksha
        setupTableView()
    }
    
    private func setupTableView() {
        tableview.registerNib(withCellType: HomeCellTableViewCell.self)
        tableview.dataSource = self
    }
}

extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: HomeCellTableViewCellType = tableview.getCell(withCellType: HomeCellTableViewCell.self, indexPath: indexPath)
        cell.input.setupData()
        return cell
    }
}

