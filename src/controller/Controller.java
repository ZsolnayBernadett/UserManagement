
package controller;

public class Controller {

    public Controller() {
        
        DatabaseController dbCtr = new DatabaseController();
        dbCtr.setDatabase();
        ViewController viewController = new ViewController(dbCtr);
    }
}
