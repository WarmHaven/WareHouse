/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse_test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JComboBox;


/**
 *
 * @author haven
 */
public class MSSql {
    
    
    private Connection con = null; //Database objects 
    private String ServerIP =page.Setting.ServerIP;
    
    
    
    
    
    public MSSql() throws ClassNotFoundException, SQLException {
        try {
        
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connUrl = "jdbc:sqlserver://www.ledway.com.tw:11433;databaseName=test";
            Connection con = DriverManager.getConnection(connUrl, "sa", "ledway");
            Statement stmt = con.createStatement();
            System.out.println("OK");
//            //取得connection
//
//            //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
//            //localhost是主機名,test是database名
//            //useUnicode=true&characterEncoding=Big5使用的編碼 
        } catch (ClassNotFoundException e) {
            System.out.println("DriverClassNotFound :" + e.toString());
        }//有可能會產生sqlexception 
        catch (SQLException x) {
            System.out.println("Exception :" + x.toString());
        }







    }
    
    
}
