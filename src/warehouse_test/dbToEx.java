/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse_test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class dbToEx {
        
        private int no;
        private String in_name;
        private String out_name;
        
        private int money;
        private int quantity;
        private String time;
        private String department;
        private String company;
        
        
        private Connection con = null; //Database objects 
        //連接object 
        private Statement stat = null;
        private ResultSet rs = null;
        private String select_Total = "select * from total ";
    
    public dbToEx(){
        
            try { 
                
                try {
                    
                    try {
                        stat = con.createStatement();
                    }catch(Exception e){
                        
                    }
                    rs = stat.executeQuery(select_Total);
                    while(rs.next())
                    {
                        try {
                            rs.getString("no");
                            rs.getString("in_name");
                            rs.getString("out_name");
                            rs.getString("money");
                            rs.getString("quantity");
                            rs.getString("m_time");
                            rs.getString("department");
                            rs.getString("company");
                        } catch (SQLException ex) {
                            Logger.getLogger(dbToEx.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }catch(SQLException ex){
            Logger.getLogger(dbToEx.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
            }finally{
        Close();
    } 
      
    }
    
    public void setNo(int no){
        this.no = no;
    }
    
    
    public int getNo(){
        return no;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void Close() {
        try 
    { 
      if(rs!=null) 
      { 
        rs.close(); 
        rs = null; 
      } 
      if(stat!=null) 
      { 
        stat.close(); 
        stat = null; 
      } 
      
    } 
    catch(SQLException e) 
    { 
      System.out.println("Close Exception :" + e.toString()); 
    } 
    }
} 
    
    

