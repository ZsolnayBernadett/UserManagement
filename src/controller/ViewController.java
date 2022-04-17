package controller;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.UserModel;
import model.ViewModel;
import view.UserForm;

public class ViewController {
    
    private UserForm userFrm;
    private ViewModel viewMdl;
    private Vector<Vector<Object>> tableData;
    private DatabaseController dbCtr;
    
    public ViewController( DatabaseController dbCtr ) {

        this.dbCtr = dbCtr;
        userFrm = new UserForm();
        viewMdl = new ViewModel();
        start();
        initComponents();
        initListeners();
    }
    
    private void initComponents() {
        
        if( dbCtr.setDatabase() ){
            userFrm.setStatusLbl( "Kapcsolat OK" );
        }
    }
    
    private void initListeners() {
        
        userFrm.getExitBtn().addActionListener( event -> { exit(); });
        userFrm.getDelBtn().addActionListener( event -> { delete(); });
        userFrm.getEditBtn().addActionListener( event -> { edit(); });
        userFrm.getSaveBtn().addActionListener( event -> { save(); });
        userFrm.getTableTb().addChangeListener( event -> { initTables(); });
    }
    
    private void start() {
        
        initTables();
        userFrm.setVisible( true );
    }
    
    private void initTables() {
        
        Vector<String> columnNames = new Vector<>();
        
        if( userFrm.getTableTb().getSelectedIndex() == 1 ) {
            
            columnNames = viewMdl.getUserColumnNames();
            tableData = dbCtr.getUsers();
            tableData.add( null );
            TableModel tablMdl = new DefaultTableModel( tableData, columnNames);
            userFrm.getUserTb().setModel( tablMdl );
        }
    }
    
    private void save() {
        
        if( userFrm.getTableTb().getSelectedIndex() == 0 ) {
            
            UserModel usModel = new UserModel();
            int row = userFrm.getUserTb().getSelectedRow();
            usModel.setName(userFrm.getUserTb().getValueAt( row, 0 ).toString());
            usModel.setEmail(userFrm.getUserTb().getValueAt( row, 1 ).toString());
            usModel.setPassword(userFrm.getUserTb().getValueAt( row, 2 ).toString());
            usModel.setStatus(userFrm.getUserTb().getValueAt( row, 3 ).toString());
            
            boolean success = dbCtr.setUserData( usModel );
            
            if( success ) {
                
                userFrm.setStatusLbl( "Sikeres kiírás" );
                initTables();
                
            }else {
                
                userFrm.setStatusLbl( "Írási hiba" );
            }
        }
    }
    
    
    private void edit() {
        
        System.out.println( "edit gomb" );
    }
    
    private void delete() {
        
        System.out.println( "delete gomb" );
    }
    
    private void exit() {
        
        System.exit( 0 );
    }
}
