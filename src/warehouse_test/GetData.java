/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Haven
 */
public class GetData {
    String conString = "jdbc:mysql://127.0.0.1:3306/warehouse?useUnicode=true&characterEncoding=Big5";
    String username = "root";
    String password = "abcd1234";
    public void getData(DefaultTableModel dm){
        
         String sql = "SELECT * FROM total;";

        try {
            Connection con = DriverManager.getConnection(conString, username, password);

            Statement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
//            jTable1.invalidate();
            while (rs.next()) {
                String no = rs.getString("no");
                String inname = rs.getString("in_name");
                String outname = rs.getString("out_name");
                String money = rs.getString("money");
                String quantity = rs.getString("quantity");
                String type = rs.getString("type");
                String time = rs.getString("m_time");
                String department = rs.getString("department");
                String company = rs.getString("company");

                String[] rowdata = {no, inname, outname, money, quantity, type, time, department, company};
                dm.addRow(rowdata);
                rowdata = new String[0];
            }
            

        } catch (Exception ex) {

        }

        
    }
    
    public DefaultTableModel getTableModel(DefaultTableModel dm){
        return dm;
    }
    
    
}
