
package controller;

public class Controller {
    
    public Controller() {
        
        DatabaseController dbCtr = new DatabaseController();
        new ViewController( dbCtr );
        
    }
}
