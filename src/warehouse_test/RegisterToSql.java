/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author haven
 */
public class RegisterToSql {

    private Connection con = null;
    //連接object 
    private Statement stat = null;
    //執行,傳入之sql為完整字串 
    private ResultSet rs = null;
    //結果集 
    private PreparedStatement pst = null;

    //Insert Accounts
    private String insert_Account = "INSERT INTO account (name,password,email)"
            + "VALUES ( ?, ?, ?);";

    //查詢Accouts
    private String select_name = "SELECT * FROM account WHERE name LIKE ";

    RegisterToSql() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //註冊driver 
            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/warehouse?useUnicode=true&characterEncoding=Big5",
                    "root", "abcd1234");
            //取得connection

//jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
//localhost是主機名,test是database名
//useUnicode=true&characterEncoding=Big5使用的編碼 
        } catch (ClassNotFoundException e) {
            System.out.println("DriverClassNotFound :" + e.toString());
        }//有可能會產生sqlexception 
        catch (SQLException x) {
            System.out.println("Exception :" + x.toString());
        }

    }

    public void Insert_Value(String name, String password, String email) {

        try {
            if (!SelectCheck_Name(name)) {
                pst = con.prepareStatement(insert_Account);
                pst.setString(1, name);
                pst.setString(2, password); 
                pst.setString(3, email);

                int rowsInserted = pst.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

            }

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

    public boolean SelectCheck_Name(String item) {
        boolean i = false;
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(select_name + "\'" + item + "\'");

            while (rs.next()) {
                if (item.equals(rs.getString("name"))) {
                    i = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
        return i;
    }

    public boolean SelectCheck_Password(String name, String pass) {
        boolean i = false;
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(select_name + "\'" + name + "\'");

            while (rs.next()) {
                if (pass.equals(rs.getString("password"))) {
                    i = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
        return i;
    }
    
    
    
    public int SelectCheck_Login(String name,String pass){
        int i = 0;
        
        
        if(SelectCheck_Name(name)){  //True = 有這個帳號
            i = 1;
            if(SelectCheck_Password(name,pass)){   //True = 這個帳號的密碼是吻合的
                i = 2;
            }
        }
              
        return i;
    }
    
    

    private void Close() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stat != null) {
                stat.close();
                stat = null;
            }
            if (pst != null) {
                pst.close();
                pst = null;
            }
        } catch (SQLException e) {
            System.out.println("Close Exception :" + e.toString());
        }
    }

}
