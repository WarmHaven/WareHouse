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
import java.sql.ResultSet;
import javax.swing.JComboBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Sql {

    private Connection con = null; //Database objects 
    //連接object 
    private Statement stat = null;
    private Statement stat2 = null;
    private Statement stat3 = null;

    //執行,傳入之sql為完整字串 
    private ResultSet rs = null;
    //結果集 
    private PreparedStatement pst = null;
    //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
    //先利用?來做標示 
    private String dropdbSQL = "TRUNCATE TABLE user";
    private String dropdbSQL2 = "TRUNCATE TABLE message";
    //建立DB
    private String createdbSQL = "CREATE TABLE User ("
            + "    id     INTEGER "
            + "  , iP    TEXT "
            + "  , port  INTEGER(4)"
            + "  , time  TEXT)";

    private String createdbSQL2 = "CREATE TABLE Message ("
            + "    no     INTEGER "
            + "  , message    TEXT(50))";
    //儲存DB
    private String insertdbSQL = "INSERT INTO Item (item,type)"
            + "VALUES ( ?, ?);";
    private String insertBigitem = "INSERT INTO bigitem (bigitem)"
            + "VALUES ( ?);";
    private String insert_MaterialItem = "INSERT INTO material (m_item,type)"
            + "VALUES ( ?, ?);";
    private String insert_inTotal = "INSERT INTO total (in_name,out_name,money,quantity,type,m_time,department,company)"
            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
    private String insert_outTotal = "INSERT INTO total (in_name,out_name,money,quantity,type,m_time,department,company)"
            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

    //查詢DB
    private String selectSQL = "select * from Item ";
    private String select_Total = "select * from total ";
    private String select_MaterialItem = "SELECT * FROM material";
    private String selectcheck_Material_type = "SELECT * FROM material WHERE type LIKE ";
    private String selectcheck_Item_type = "SELECT * FROM item WHERE type LIKE ";
    private String selectcheck_Item = "SELECT * FROM item WHERE item LIKE ";
    private String select_BigItem = "select * from bigitem ";
    private String selectcheck_Total = "select count(*) from total";
    private String selectcheck_BigItem = "SELECT * FROM bigitem WHERE bigitem LIKE ";
    

    //查詢Accouts
    private String select_name = "SELECT * FROM account WHERE name LIKE ";

    //=========================================================================    
    Sql() {
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
//=============================================================================
    //新增資料  

    /**
     *
     * @param item
     * @param type
     *
     */
    public void inser_Item(String item, int type) {
        int check = SelectCheck_Item(item);

        try {
            if (check == 0) {
                pst = con.prepareStatement(insertdbSQL);
                pst.setString(1, item);
                pst.setInt(2, type);
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

    //寫入Message資料庫
    public void inser_BigItem(String bigitem) {
        try {
            pst = con.prepareStatement(insertBigitem);
            pst.setString(1, bigitem);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

    public void insert_Material(String m_item, int type) {

        try {

            pst = con.prepareStatement(insert_MaterialItem);
            pst.setString(1, m_item);
            pst.setInt(2, type);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

    public void insert_inTotal(String in, int money, int quantity, String type, String time, String department, String company) {
        String out = "";
        try {

            pst = con.prepareStatement(insert_inTotal);
            pst.setString(1, in);
            pst.setString(2, out);
            pst.setInt(3, money);
            pst.setInt(4, quantity);
            pst.setString(5, type);
            pst.setString(6, time);
            pst.setString(7, department);
            pst.setString(8, company);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

    public void insert_outTotal(String out, int quantity, String type, String time, String department, String company) {
        String in = "";
        int money = 0;
        try {

            pst = con.prepareStatement(insert_outTotal);
            pst.setString(1, in);
            pst.setString(2, out);
            pst.setInt(3, money);
            pst.setInt(4, quantity);
            pst.setString(5, type);
            pst.setString(6, time);
            pst.setString(7, department);
            pst.setString(8, company);

            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("InsertDB Exception:" + e.toString());
        } finally {
            Close();
        }
    }

//==============================================================================
    //查詢資料 
    //可以看看回傳結果集及取得資料方式 
    public void Select_BigItem(JComboBox box) {

        try {
            // Your database connections 
            stat = con.createStatement();
            rs = stat.executeQuery(select_BigItem);
            while (rs.next()) {
                box.addItem(rs.getString(1) + ".   " + rs.getString(2));

            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            Close();
        }
    }
//==============================================================================

    public void Select_MaterialItem(JComboBox box) {

        try {
            // Your database connections 
            stat = con.createStatement();

            rs = stat.executeQuery(select_MaterialItem);
            while (rs.next()) {
                box.addItem(rs.getString(1) + ".   " + rs.getString(2));

            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            Close();
        }
    }
    //=================================================================================

    public void loadcombo(JComboBox box) {

        try {
            // Your database connections 
            stat = con.createStatement();
            rs = stat.executeQuery(selectSQL);
            while (rs.next()) {
                box.addItem(rs.getString(1) + ".   " + rs.getString(2));

            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        } finally {
            Close();
        }

    }

    //===================================================================================
    //查詢Message資料庫
    public void Select_Total() {
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(select_Total);
            while (rs.next()) {
                rs.getString("no");
                rs.getString("in_name");
                rs.getString("out_name");
                rs.getString("money");
                rs.getString("quantity");
                rs.getString("m_time");
                rs.getString("department");
                rs.getString("company");
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }

    }

    public void SelectCheck_Material_type(JComboBox box, int type) {

        try {

            stat = con.createStatement();
            rs = stat.executeQuery(selectcheck_Material_type + "\'" + type + "\'");
            box.removeAllItems();
            while (rs.next()) {

                box.addItem(rs.getString(1) + ".   " + rs.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception hello2 :" + e.toString());
        } finally {
            Close();
        }

    }

    public void SelectCheck_Item_type(JComboBox box, int type) {

        try {

            stat = con.createStatement();
            rs = stat.executeQuery(selectcheck_Item_type + "\'" + type + "\'");
            box.removeAllItems();
            while (rs.next()) {

                box.addItem(rs.getString(1) + ".   " + rs.getString(2));
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception hello2 :" + e.toString());
        } finally {
            Close();
        }

    }

    public int SelectCheck_Item(String item) {
        int i = 0;
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(selectcheck_Item + "\'" + item + "\'");

            while (rs.next()) {
                if (item.equals(rs.getString("item"))) {
                    i = 1;
                }
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
        return i;
    }
    
    
    
    public int SelectCheck_BigItem(String item) {
        int i = 0;
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(selectcheck_BigItem + "\'" + item + "\'");

            while (rs.next()) {
                if (item.equals(rs.getString("bigitem"))) {
                    i = 1;
                }
            }

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
        return i;
    }
    
    
    

    public int SelectCheck_Total() throws SQLException {
        int i = 0;
        try {

            stat = con.createStatement();
            rs = stat.executeQuery(selectcheck_Total);
            return rs.getRow();

        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
        return rs.getRow();
    }

    //=============================================================================
    //清空Message資料庫 
    public void dropTable_User() {
        try {
            stat = con.createStatement();
            stat.executeUpdate(dropdbSQL);
        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
    }

    public void dropTable_Message() {
        try {
            stat2 = con.createStatement();
            stat2.executeUpdate(dropdbSQL2);
        } catch (SQLException e) {
            System.out.println("DropDB Exception :" + e.toString());
        } finally {
            Close();
        }
    }
//==============================================================================
    //Close全部

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

    /**
     *
     * @return
     */
}
