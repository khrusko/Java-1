/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.viewModel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import cinema.model.Director;
/**
 *
 * @author karlo_6zwakzv
 */
public class DirectorTableModel extends AbstractTableModel{

    private static final String[] COLUMN_NAMES = {"Id","FullName"};

    private List<Director> directors;

    public DirectorTableModel(List<Director> directors) {
        this.directors = directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {

        return directors.size();
    }

    @Override
    public int getColumnCount() {

        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch(columnIndex){
            case 0:
                return directors.get(rowIndex).getId();
            case 1:
                return directors.get(rowIndex).getFullName();

        }
        throw new RuntimeException("No such column");


     }

    @Override
    public String getColumnName(int column) {

        return COLUMN_NAMES[column];
     }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch(columnIndex){
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
    }




}
