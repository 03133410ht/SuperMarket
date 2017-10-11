package com.view.soldNote;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.dao.SalesDao;
import com.dao.imp.SalesDaoImp;
import com.entity.Goods;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.service.GoodsService;
import com.service.MemberService;
import com.service.imp.GoodsServiceImp;
import com.service.imp.MemberServiceImp;
import com.util.OutExcel;
import com.view.DateField;

/**
 * ��ӡ����Jpanel
 * 
 * @author Goddard
 *
 */
public class StatementJpanel extends JPanel {
	private JComboBox statementComboBox;
	private DateField statementDateField1;
	private DateField statementDateField2;
	private ChartPanel statement;
	private DecimalFormat df = new DecimalFormat("0.00");
	private SalesDao salesDao = new SalesDaoImp();
	private Map<Sales, List<SalesInfo>> mapSales;
	private Goods goods;
	private GoodsService goodsService = new GoodsServiceImp();
	private MemberService memberService = new MemberServiceImp();
	private Map<Integer, Integer> midMap;
	private Map<String, Integer> categoryMap;
	private JFreeChart chart;
	private Map<String, Integer> gradeMap;
	private Logger log = Logger.getLogger(StatementJpanel.class);

	public StatementJpanel() {
		df.setRoundingMode(RoundingMode.HALF_UP);
		statement();
	}

	private void statement() {

		JButton CreateButton = new JButton("\u751F\u6210\u62A5\u8868");
		CreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creatrActionPerformed();
			}
		});
		CreateButton.setFont(new Font("����", Font.BOLD, 15));
		CreateButton.setForeground(Color.BLACK);
		CreateButton.setBounds(984, 33, 109, 35);

		JLabel statementDateLabel = new JLabel("\u9500\u552E\u65F6\u95F4");
		statementDateLabel.setForeground(Color.BLACK);
		statementDateLabel.setBounds(391, 33, 81, 33);
		statementDateLabel.setFont(new Font("����", Font.BOLD, 17));

		statementDateField1 = new DateField();
		statementDateField1.setBounds(486, 33, 189, 35);
		statementDateField1.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel arriveLabel = new JLabel("\u81F3");
		arriveLabel.setForeground(Color.BLACK);
		arriveLabel.setBounds(702, 31, 18, 35);
		arriveLabel.setFont(new Font("����", Font.BOLD, 17));

		statementDateField2 = new DateField();
		statementDateField2.setBounds(746, 33, 190, 35);
		statementDateField2.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		statement = new ChartPanel(null);
		statement.setBounds(43, 81, 1229, 443);
		statement.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel statementLabel = new JLabel("\u62A5\u8868\u7C7B\u578B");
		statementLabel.setForeground(Color.BLACK);
		statementLabel.setFont(new Font("����", Font.BOLD, 17));
		statementLabel.setBounds(43, 33, 81, 31);

		String[] strarr = { "�鿴ÿ��ҵ����״ͼ", "�鿴ÿ��ҵ������ͼ", "�鿴��Ʒ����ǰʮ������״ͼ", "�鿴��Ʒ�����״ͼ", "�鿴��Ա�ȼ���״ͼ", };
		statementComboBox = new JComboBox(strarr);
		statementComboBox.setFont(new Font("����", Font.PLAIN, 17));
		statementComboBox.setBounds(138, 35, 225, 28);

		JButton statementSavePicButton = new JButton("\u4FDD\u5B58jpg\u6587\u4EF6");
		statementSavePicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJpegActionPerformed();
			}
		});
		statementSavePicButton.setForeground(Color.BLACK);
		statementSavePicButton.setFont(new Font("����", Font.BOLD, 15));
		statementSavePicButton.setBounds(1140, 33, 121, 34);
		setLayout(null);
		add(CreateButton);
		add(statementLabel);
		add(statementComboBox);
		add(statementDateLabel);
		add(statementDateField1);
		add(arriveLabel);
		add(statementDateField2);
		add(statementSavePicButton);
		add(statement);
		statement.setLayout(null);
	}

	/**
	 * ����ͼƬ�¼�����
	 */
	private  void saveJpegActionPerformed() {
		File f = FileChooser();
		if(f != null) {
			if(chart == null) {
				JOptionPane.showMessageDialog(null, "�����ɱ�����ٵ��");
				return;
			}else {
				try {
					ChartUtilities.saveChartAsJPEG(f, chart, 800, 600);
					JOptionPane.showMessageDialog(null, "�����ɹ�");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "����ʧ��");
					log.error(e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * ���ɱ����¼�����
	 */
	private void creatrActionPerformed() {
		String choose = statementComboBox.getSelectedItem().toString();
		Date date1 = new Date(statementDateField1.getDate().getTime());
		Date date2 = new Date(statementDateField2.getDate().getTime());
		int dates = date2.getDate() - date1.getDate() + 1;
		double price = 0;
		double costPrice = 0;
		double profit = 0;
		Date date = date1;
		if (choose.equals("�鿴ÿ��ҵ����״ͼ")) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < dates; i++) {
				mapSales = salesDao.getALLByDate(date);
				for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
					for (SalesInfo salesInfo : entry.getValue()) {
						int num = salesInfo.getSnum();
						goods = goodsService.queryGoodsById(salesInfo.getGid());
						price += Double.parseDouble(df.format(goods.getGprice() * num));
						costPrice += Double.parseDouble(df.format(goods.getGcost_price() * num));
					}
				}
				profit = Double.parseDouble(df.format(price - costPrice));
				dataset.addValue(price, "�ۼ�", date);
				dataset.addValue(costPrice, "�ɱ���", date);
				dataset.addValue(profit, "����", date);
				date = new Date(date.getTime() + 86400000);
				price = 0;
				costPrice = 0;
				profit = 0;
			}
			chart = ChartFactory.createBarChart3D("ÿ��ҵ��ͳ��ͼ", "����", // X��ı�ǩ
					"������", // Y��ı�ǩ
					dataset, // ͼ����ʾ�����ݼ���
					PlotOrientation.VERTICAL, // ͼ�����ʾ��ʽ��ˮƽ���ߴ�ֱ��
					true, // �Ƿ���ʾ�ӱ���
					true, // �Ƿ�������ʾ�ı�ǩ
					true); // �Ƿ�����URL����
			// ����ͼ���ϵ�����
			// ���������������
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
			// �����ӱ�������
			chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
			// ��ȡͼ���������
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			// ��ȡX��Ķ���
			CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();
			// ��ȡY��Ķ���
			NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
			// ����X���ϵ�����
			categoryAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 15));
			// ����X���������
			categoryAxis3D.setLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y���ϵ�����
			numberAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y���������
			numberAxis3D.setLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y������ʾ�Ŀ̶ȣ���10��Ϊ1��
			numberAxis3D.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(10000.00);
			numberAxis3D.setTickUnit(unit);
			// ��ȡ��ͼ�������
			BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot.getRenderer();
			// ��������ͼ�Ŀ��
			barRenderer3D.setMaximumBarWidth(0.07);
			// ��ͼ������ʾ����
			barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barRenderer3D.setBaseItemLabelsVisible(true);
			barRenderer3D.setBaseItemLabelFont(new Font("����", Font.BOLD, 10));
			statement.removeAll();
			statement.setChart(chart);

		} else if (choose.equals("�鿴ÿ��ҵ������ͼ")) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < dates; i++) {
				mapSales = salesDao.getALLByDate(date);
				for (Map.Entry<Sales, List<SalesInfo>> entry : mapSales.entrySet()) {
					for (SalesInfo salesInfo : entry.getValue()) {
						int num = salesInfo.getSnum();
						goods = goodsService.queryGoodsById(salesInfo.getGid());
						price += Double.parseDouble(df.format(goods.getGprice() * num));
						costPrice += Double.parseDouble(df.format(goods.getGcost_price() * num));
					}
				}
				profit = Double.parseDouble(df.format(price - costPrice));
				dataset.addValue(price, "�ۼ�", date);
				dataset.addValue(costPrice, "�ɱ���", date);
				dataset.addValue(profit, "����", date);
				date = new Date(date.getTime() + 86400000);
				price = 0;
				costPrice = 0;
				profit = 0;
			}
			chart = ChartFactory.createLineChart("ÿ��ҵ������ͼ", "����", "������", dataset, PlotOrientation.VERTICAL,
					true, true, true);
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
			chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
			categoryAxis.setTickLabelFont(new Font("����", Font.BOLD, 15));
			categoryAxis.setLabelFont(new Font("����", Font.BOLD, 15));
			numberAxis.setTickLabelFont(new Font("����", Font.BOLD, 15));
			numberAxis.setLabelFont(new Font("����", Font.BOLD, 15));
			numberAxis.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(10000);
			numberAxis.setTickUnit(unit);
			LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineAndShapeRenderer.setBaseItemLabelsVisible(true);
			lineAndShapeRenderer.setBaseItemLabelFont(new Font("����", Font.BOLD, 15));
			Rectangle shape = new Rectangle(10, 10);
			lineAndShapeRenderer.setSeriesShape(0, shape);
			lineAndShapeRenderer.setSeriesShape(1, shape);
			lineAndShapeRenderer.setSeriesShape(2, shape);
			lineAndShapeRenderer.setSeriesShapesVisible(0, true);
			lineAndShapeRenderer.setSeriesShapesVisible(1, true);
			lineAndShapeRenderer.setSeriesShapesVisible(2, true);
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("�鿴��Ʒ����ǰʮ������״ͼ")) {
			int i = 0;
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			midMap = salesDao.queryGid(date1 ,date2);
			for (Map.Entry<Integer, Integer> entry : midMap.entrySet()) {
				dataset.addValue(entry.getValue(), "��Ʒ", goodsService.queryGoodsById(entry.getKey()).getGname());
				++i;
				if (i > 9) {
					break;
				}
			}
			chart = ChartFactory.createBarChart3D("��Ʒ����ǰʮ������״ͼ", "��Ʒ", // X��ı�ǩ
					"����", // Y��ı�ǩ
					dataset, // ͼ����ʾ�����ݼ���
					PlotOrientation.VERTICAL, // ͼ�����ʾ��ʽ��ˮƽ���ߴ�ֱ��
					true, // �Ƿ���ʾ�ӱ���
					true, // �Ƿ�������ʾ�ı�ǩ
					true); // �Ƿ�����URL����
			// ����ͼ���ϵ�����
			// ���������������
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
			// �����ӱ�������
			chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
			// ��ȡͼ���������
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			// ��ȡX��Ķ���
			CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();
			categoryAxis3D.setMaximumCategoryLabelLines(4);//����
			// ��ȡY��Ķ���
			NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
			// ����X���ϵ�����
			categoryAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 15));
			// ����X���������
			categoryAxis3D.setLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y���ϵ�����
			numberAxis3D.setTickLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y���������
			numberAxis3D.setLabelFont(new Font("����", Font.BOLD, 15));
			// ����Y������ʾ�Ŀ̶ȣ���10��Ϊ1��
			numberAxis3D.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(2);
			numberAxis3D.setTickUnit(unit);
			// ��ȡ��ͼ�������
			BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot.getRenderer();
			// ��������ͼ�Ŀ��
			barRenderer3D.setMaximumBarWidth(0.07);
			// ��ͼ������ʾ����
			barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barRenderer3D.setBaseItemLabelsVisible(true);
			barRenderer3D.setBaseItemLabelFont(new Font("����", Font.BOLD, 15));
		
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("�鿴��Ʒ�����״ͼ")) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			categoryMap = goodsService.queryCategoryNum();
			for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
				dataset.setValue(entry.getKey(), entry.getValue());
			}
			chart = ChartFactory.createPieChart3D("��Ʒ�����״ͼ", // �����������
					dataset, // ͼ����ʾ�����ݼ���
					true, // �Ƿ���ʾ�ӱ���
					true, // �Ƿ�������ʾ�ı�ǩ
					true); // �Ƿ�����URL����
			// ����ͼ���ϵ�����
			// ���������������
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
			// �����ӱ�������
			chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
			// ��ȡͼ���������
			PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
			// ����ͼ���ϵ�����
			categoryPlot.setLabelFont(new Font("����", Font.BOLD, 15));
			String format = "{0} {1} ({2})";
			categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("�鿴��Ա�ȼ���״ͼ")) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			gradeMap = memberService.queryGradeNum();
			for(Map.Entry<String, Integer> entry : gradeMap.entrySet()) {
				dataset.setValue(entry.getKey(), entry.getValue());
			}
			chart = ChartFactory.createPieChart3D("��Ա�ȼ���״ͼ", // �����������
					dataset, // ͼ����ʾ�����ݼ���
					true, // �Ƿ���ʾ�ӱ���
					true, // �Ƿ�������ʾ�ı�ǩ
					true); // �Ƿ�����URL����
			// ����ͼ���ϵ�����
			// ���������������
			chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
			// �����ӱ�������
			chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
			// ��ȡͼ���������
			PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
			// ����ͼ���ϵ�����
			categoryPlot.setLabelFont(new Font("����", Font.BOLD, 15));
			String format = "{0} {1} ({2})";
			categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));
			statement.removeAll();
			statement.setChart(chart);
			
		}

	}
	/**
	 * ��ȡ�ļ�
	 * @return
	 */
	private File FileChooser() {
		JFileChooserDemo fc = new JFileChooserDemo("C:");
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileHidingEnabled(true);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("jpeg�ļ�(*.jpeg)","jpeg"));
		int returnValue = fc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String ftype = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4);
			if (!ftype.equals(".jpeg")) {
				file = new File(file.getAbsolutePath() + ".jpeg");
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

	public void approveSelection() {
		File file = new File(getSelectedFile() + ".jpeg");
		if (file.exists()) {
			JOptionPane.showMessageDialog(getParent(), "�ļ����ѱ�ռ��");
			return;
		} else {
			super.approveSelection();
		}
	}
}
