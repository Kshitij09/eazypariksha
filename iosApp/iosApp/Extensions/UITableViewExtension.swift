

import  UIKit

extension UITableView {
    
    func registerNib<T: UITableViewCell>(withCellType cellType:T.Type) {
        
        let name = String(describing: cellType)
        
        self.register( UINib.init(nibName: name, bundle: nil), forCellReuseIdentifier: name)
    }
    
    /**
     This method is used to get the cell Object from generic UITableViewCell Type
     
     - parameter userProfileTableView: UITableViewCell
     
     - returns :UITableViewCell
     */
    func getCell<T:UITableViewCell>(withCellType cellType:T.Type, indexPath: IndexPath) ->T {
        return self.dequeueReusableCell(withIdentifier: String(describing: cellType), for: indexPath) as! T
    }

    func getHeaderFooter<T:UITableViewHeaderFooterView>(withCellType cellType:T.Type) ->T {
        return self.dequeueReusableHeaderFooterView(withIdentifier: String(describing: cellType)) as! T
    }

    func reloadData(completion:@escaping ()->()) {
        UIView.animate(withDuration: 0, animations: {
            self.reloadData()
        }) { _ in completion() }
    }

    func scrollToBottom() {
        let sections = self.numberOfSections
        if sections <= 0 { return }

        let indexPath = IndexPath( row: self.numberOfRows(inSection: sections - 1) - 1, section: sections - 1)
        if (indexPath.row > 0) {
            self.scrollToRow(at: indexPath, at: .bottom, animated: false)
        } else {
            let indexPath = IndexPath(row: NSNotFound, section: self.numberOfSections - 1)
            self.scrollToRow(at: indexPath, at: .bottom, animated: false)
        }
    }

    func placeHolder(message: String){
        let noDataLabel = UILabel(frame: CGRect(x:0, y:0, width:self.self.bounds.size.width, height: self.self.bounds.size.height))
        noDataLabel.textColor = UIColor.black
        noDataLabel.textAlignment = NSTextAlignment.center
        noDataLabel.text = message
        self.backgroundView = noDataLabel
    }

    func placeHolder(usingImage image: UIImage?){
        let imageView = UIImageView(frame: CGRect(x:0, y:0, width:self.self.bounds.size.width, height: self.self.bounds.size.height))
        imageView.image = image
        self.backgroundView = imageView
    }

    func removePlaceholder(){
        self.backgroundView = nil
    }

    func removeFoooterView() {
        self.tableFooterView = UIView(frame: .zero)
    }

    func showLoaderAtBottom() {
        let spinner = UIActivityIndicatorView(style: UIActivityIndicatorView.Style.medium)
        spinner.startAnimating()
        spinner.frame = CGRect(x: CGFloat(0), y: CGFloat(0), width: self.bounds.width, height: CGFloat(44))
        self.tableFooterView = spinner
        self.tableFooterView?.isHidden = false
    }

    func hideLoaderAtBottom() {
        self.tableFooterView = UIView(frame: .zero)
        self.tableFooterView?.isHidden = true
    }
}
