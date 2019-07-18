/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
//import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import page.searchPage;
import warehouse_test.WareHouse_Test.*;

/**
 *
 * @author Haven
 */
public class Excel {

    public File excel = new File("./test.xls");
    public FileInputStream fis = new FileInputStream(excel);
    public HSSFWorkbook book;
    
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy.MM");
    private java.util.Date current = new java.util.Date();
    

    public Excel() throws IOException {
        this.book = new HSSFWorkbook(fis);
    }
    
    
    public void createSheet(){
//        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy.MM");
//        java.util.Date current = new java.util.Date();

        System.out.println(sdFormat.format(current));
        Sheet s = book.createSheet(sdFormat.format(current));
        
    }
    
    
    

    public void Write() throws IOException {
        try {
            
            createSheet();
            
            //setting options
            HSSFSheet sheet1 = book.getSheet(sdFormat.format(current));
            int index = book.getSheetIndex(sheet1);
            
            HSSFSheet sheet = book.getSheetAt(index);

            searchPage wt = new searchPage();

            DefaultTableModel dm;
            dm = wt.getdata();

            //write data
            Map<String, Object[]> newData = new HashMap<String, Object[]>();

            newData.put("0", new Object[]{dm.getColumnName(1), dm.getColumnName(2), dm.getColumnName(3),
                dm.getColumnName(4), dm.getColumnName(5), dm.getColumnName(6),
                dm.getColumnName(7), dm.getColumnName(8)});

            for (int i = 0; i < dm.getColumnCount(); i++) {
                newData.put(Integer.toString(i), new Object[]{
                    wt.getCellValue(i, 0), wt.getCellValue(i, 1), wt.getCellValue(i, 2),
                    wt.getCellValue(i, 3), wt.getCellValue(i, 4), wt.getCellValue(i, 5),
                    wt.getCellValue(i, 6), wt.getCellValue(i, 7), wt.getCellValue(i, 8)});

            }

            //keySet()-->有幾筆資料
            Set<String> newRows = newData.keySet();
            //getLastRowNum --> 寫入之前有幾行資料
            int rownum = sheet.getLastRowNum();

            rownum++;

            for (String key : newRows) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = newData.get(key);
                int cellnum = 0;

                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }

                }

            }

            FileOutputStream os = new FileOutputStream(excel);
            book.write(os);
            System.out.println("Writing on Excel file Finished ...");
            JOptionPane.showMessageDialog(null, "匯出Excel成功");

            os.close();
            book.close();
            fis.close();

            System.out.println("Writed End...");
        }catch(IllegalArgumentException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "匯出Excel失敗，請清除本月資料後再匯出！");
        }catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "匯出Excel失敗");
        }
    }

    public void removeSheet() throws IOException {
        try {
            
            FileInputStream fileStream = new FileInputStream(excel);
            HSSFWorkbook workbook = new HSSFWorkbook(fileStream);
            int index = 0;

            
            HSSFSheet sheet = workbook.getSheet(sdFormat.format(current));
            if (sheet != null) {
                index = workbook.getSheetIndex(sheet);
                workbook.removeSheetAt(index);
            }

            FileOutputStream output = new FileOutputStream(excel);
            workbook.write(output);
            output.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "清除本月資料失敗，找不到Excel檔"+ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "清除本月資料失敗！");
        }

    }

}
