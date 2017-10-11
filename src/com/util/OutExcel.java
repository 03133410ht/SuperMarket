package com.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.dao.BaseDao;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * ����excel������
 * @author Goddard
 *
 */
public class OutExcel {
	private Logger log = Logger.getLogger(OutExcel.class);
	public OutExcel(JTable table) {
		TableModel tm = table.getModel();
		int row = tm.getRowCount();
		int col = tm.getColumnCount();
		if(row == 0) {
			JOptionPane.showMessageDialog(null, "��ǰ���Ϊ�գ����������ٵ���");
			return;
		}
		File f = FileChooser();
		if (f != null) {
			try {
				WritableWorkbook book = Workbook.createWorkbook(f);
				WritableSheet sheet = book.createSheet("Sheet1", 0);
				// �������壬7�������ֱ����������ƣ��ֺţ��Ƿ���壬�Ƿ�б�壬�»��ߣ���ɫ�����±�
				WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
				WritableCellFormat format = new WritableCellFormat(font);
				format.setAlignment(Alignment.CENTRE); // ���뷽
				WritableCellFormat format2 = new WritableCellFormat();
				format2.setAlignment(Alignment.CENTRE);
				for (int i = 0; i < col; i++) {
					sheet.setColumnView(i, 15);
					sheet.addCell(new Label(i, 0, tm.getColumnName(i), format));
				}
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {
						if(tm.getValueAt(i, j) == null) {
							continue;
						}
						sheet.addCell(new Label(j, i + 1, tm.getValueAt(i, j).toString(), format2));
					}
				}
				book.write();
				// �ر��ļ�
				book.close();
				JOptionPane.showMessageDialog(null, "�����ɹ�");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				log.error(ex.getMessage(), ex);
			}
		}
	}

	private File FileChooser() {
		JFileChooserDemo fc = new JFileChooserDemo("C:");
		// �Ƿ�ɶ�ѡ
		fc.setMultiSelectionEnabled(false);
		// ѡ��ģʽ����ѡ���ļ����ļ���
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// �����Ƿ���ʾ�����ļ�
		fc.setFileHidingEnabled(true);
		fc.setAcceptAllFileFilterUsed(false);
		// �����ļ�ɸѡ��
		// fc.setFileFilter(new MyFilter("java"));
		fc.setFileFilter(new FileNameExtensionFilter("Excel�ļ�(*.xls)", "xls"));
		int returnValue = fc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String ftype = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4);
			if (!ftype.equals(".xls")) {
				// ����û�û����д��չ�����Զ������չ��.xls
				file = new File(file.getAbsolutePath() + ".xls");
			}
			return file;
		}
		return null;
	}
}


/**
 * �Զ����ļ�ѡ�񹤾�
 * @author Goddard
 *
 */
class JFileChooserDemo extends javax.swing.JFileChooser {

	public JFileChooserDemo(String currentDirectoryPath) {
		super(currentDirectoryPath);
	}

	@Override
	public void approveSelection() {
		File file = new File(getSelectedFile() + ".xls");
		if (file.exists()) {
			JOptionPane.showMessageDialog(getParent(), "�ļ����ѱ�ռ��");
			return;
		} else {
			super.approveSelection();
		}
	}
}
