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

    public ViewController(DatabaseController dbCtr) {

        this.dbCtr = dbCtr;
        start();
    }

    private void initDbConnection() {

        if (dbCtr.setDatabase()) {

            userFrm.setStatusLbl("Kapcsolat OK");

        } else {

            userFrm.setStatusLbl("Nincs kapcsolat");
        }
    }

    private void initListeners() {

        userFrm.getExitBtn().addActionListener(event -> { exit(); });
        userFrm.getDelBtn().addActionListener(event -> {delete(); });
        userFrm.getEditBtn().addActionListener(event -> {edit(); });
        userFrm.getSaveBtn().addActionListener(event -> {save(); });
        userFrm.getTableTb().addChangeListener(event -> { initTables();});
    }

    private void start() {

        userFrm = new UserForm();
        userFrm.setTitle("Felhasználók kezelése");
        viewMdl = new ViewModel();
        initDbConnection();
        initListeners();
        initTables();
        userFrm.setVisible(true);
    }

    private void initTables() {

        Vector<String> columnNames = new Vector<>();
        tableData = new Vector<>();
        columnNames = viewMdl.getUserColumnNames();
        tableData = dbCtr.getUsers();
        tableData.add(null);
        TableModel tablMdl = new DefaultTableModel(tableData, columnNames);
        userFrm.getUserTb().setModel(tablMdl);
        userFrm.getUserTb().getColumnModel().getColumn(3).setWidth(0);
        userFrm.getUserTb().getColumnModel().getColumn(3).setMinWidth(0);
        userFrm.getUserTb().getColumnModel().getColumn(3).setMaxWidth(0);
    }

    private void save() {

        UserModel userModel = new UserModel();
        int row = userFrm.getUserTb().getSelectedRow();
        userModel.setName(userFrm.getUserTb().getValueAt(row, 0).toString());
        userModel.setEmail(userFrm.getUserTb().getValueAt(row, 1).toString());
        userModel.setPassword(userFrm.getUserTb().getValueAt(row, 2).toString());

        boolean success = dbCtr.setUserData(userModel);

        if (success) {

            userFrm.setStatusLbl("Sikeres kiírás");
            initTables();

        } else {

            userFrm.setStatusLbl("Hiba az írás közben");
        }
    }

    private void edit() {

        UserModel userModel = new UserModel();
        int row = userFrm.getUserTb().getSelectedRow();
        userModel.setName(userFrm.getUserTb().getValueAt(row, 0).toString());
        userModel.setEmail(userFrm.getUserTb().getValueAt(row, 1).toString());
        userModel.setPassword(userFrm.getUserTb().getValueAt(row, 2).toString());
        userModel.setId(userFrm.getUserTb().getValueAt(row, 3).toString());
        boolean success = dbCtr.editUserData(userModel);

        if (success) {

            userFrm.setStatusLbl("Sikeres módosítás");
            initTables();

        } else {

            userFrm.setStatusLbl("Hiba a módosítás közben");
        }
    }

    private void delete() {

        UserModel userModel = new UserModel();
        int row = userFrm.getUserTb().getSelectedRow();
        userModel.setEmail(userFrm.getUserTb().getValueAt(row, 1).toString());
        boolean success = dbCtr.deleteUserData(userModel);

        if (success) {

            userFrm.setStatusLbl("Sikeres törlés");
            initTables();

        } else {
            
            userFrm.setStatusLbl("Hiba a törlés közben");
        }
    }

    private void exit() {

        System.exit(0);
    }
}
