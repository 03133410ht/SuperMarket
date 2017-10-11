package com.util;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *����excel������
 * @author goddard
 */
public class ImportExcel {

    private Object[][] obj;

    public ImportExcel() throws IOException, BiffException {
        File excelFile = FileChooser();
       if(excelFile == null ) {
    	   return;
       }
        Workbook workBook = Workbook.getWorkbook(excelFile);
        // �õ����������еĹ��������
        Sheet sheet = workBook.getSheet(0);//Ĭ��ѡ���һ����,�ɸ�������������б�
        // ����������
        int rowsCount = sheet.getRows();
        int columnsCount = sheet.getRow(0).length;
        obj = new Object[rowsCount - 1][columnsCount];
        for (int i = 1; i < sheet.getRows(); i++) {
            // �õ������У���������е�����
            Cell[] cells = sheet.getRow(i);
            for (int j = 0; j < cells.length; j++) {
                obj[i - 1][j] = cells[j].getContents() + "";
            }
        }
        workBook.close();
    }

    private File FileChooser() {
        JFileChooserDemo fc = new JFileChooserDemo("C:");
        //�Ƿ�ɶ�ѡ  
        fc.setMultiSelectionEnabled(false);
        //ѡ��ģʽ����ѡ���ļ����ļ���  
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //�����Ƿ���ʾ�����ļ�  
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        //�����ļ�ɸѡ��  
        fc.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls)", "xls"));
        int returnValue = fc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            file = new File(file.getAbsolutePath());
            return file;
        }
        return null;
    }

    /**
     * @return the obj
     */
    public Object[][] getObj() {
        return obj;
    }
}
