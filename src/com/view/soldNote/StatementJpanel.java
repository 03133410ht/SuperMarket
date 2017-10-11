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
 * 打印报表Jpanel
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
		CreateButton.setFont(new Font("宋体", Font.BOLD, 15));
		CreateButton.setForeground(Color.BLACK);
		CreateButton.setBounds(984, 33, 109, 35);

		JLabel statementDateLabel = new JLabel("\u9500\u552E\u65F6\u95F4");
		statementDateLabel.setForeground(Color.BLACK);
		statementDateLabel.setBounds(391, 33, 81, 33);
		statementDateLabel.setFont(new Font("宋体", Font.BOLD, 17));

		statementDateField1 = new DateField();
		statementDateField1.setBounds(486, 33, 189, 35);
		statementDateField1.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JLabel arriveLabel = new JLabel("\u81F3");
		arriveLabel.setForeground(Color.BLACK);
		arriveLabel.setBounds(702, 31, 18, 35);
		arriveLabel.setFont(new Font("宋体", Font.BOLD, 17));

		statementDateField2 = new DateField();
		statementDateField2.setBounds(746, 33, 190, 35);
		statementDateField2.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		statement = new ChartPanel(null);
		statement.setBounds(43, 81, 1229, 443);
		statement.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel statementLabel = new JLabel("\u62A5\u8868\u7C7B\u578B");
		statementLabel.setForeground(Color.BLACK);
		statementLabel.setFont(new Font("宋体", Font.BOLD, 17));
		statementLabel.setBounds(43, 33, 81, 31);

		String[] strarr = { "查看每日业绩柱状图", "查看每日业绩折线图", "查看商品销量前十排行柱状图", "查看商品种类饼状图", "查看会员等级饼状图", };
		statementComboBox = new JComboBox(strarr);
		statementComboBox.setFont(new Font("宋体", Font.PLAIN, 17));
		statementComboBox.setBounds(138, 35, 225, 28);

		JButton statementSavePicButton = new JButton("\u4FDD\u5B58jpg\u6587\u4EF6");
		statementSavePicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJpegActionPerformed();
			}
		});
		statementSavePicButton.setForeground(Color.BLACK);
		statementSavePicButton.setFont(new Font("宋体", Font.BOLD, 15));
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
	 * 保存图片事件处理
	 */
	private  void saveJpegActionPerformed() {
		File f = FileChooser();
		if(f != null) {
			if(chart == null) {
				JOptionPane.showMessageDialog(null, "请生成报表后再点击");
				return;
			}else {
				try {
					ChartUtilities.saveChartAsJPEG(f, chart, 800, 600);
					JOptionPane.showMessageDialog(null, "导出成功");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "导出失败");
					log.error(e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * 生成报表事件处理
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
		if (choose.equals("查看每日业绩柱状图")) {
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
				dataset.addValue(price, "售价", date);
				dataset.addValue(costPrice, "成本价", date);
				dataset.addValue(profit, "利润", date);
				date = new Date(date.getTime() + 86400000);
				price = 0;
				costPrice = 0;
				profit = 0;
			}
			chart = ChartFactory.createBarChart3D("每日业绩统计图", "日期", // X轴的标签
					"人名币", // Y轴的标签
					dataset, // 图标显示的数据集合
					PlotOrientation.VERTICAL, // 图像的显示形式（水平或者垂直）
					true, // 是否显示子标题
					true, // 是否生成提示的标签
					true); // 是否生成URL链接
			// 处理图形上的乱码
			// 处理主标题的乱码
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
			// 处理子标题乱码
			chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
			// 获取图表区域对象
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			// 获取X轴的对象
			CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();
			// 获取Y轴的对象
			NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
			// 处理X轴上的乱码
			categoryAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理X轴外的乱码
			categoryAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴上的乱码
			numberAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴外的乱码
			numberAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴上显示的刻度，以10作为1格
			numberAxis3D.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(10000.00);
			numberAxis3D.setTickUnit(unit);
			// 获取绘图区域对象
			BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot.getRenderer();
			// 设置柱形图的宽度
			barRenderer3D.setMaximumBarWidth(0.07);
			// 在图形上显示数字
			barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barRenderer3D.setBaseItemLabelsVisible(true);
			barRenderer3D.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 10));
			statement.removeAll();
			statement.setChart(chart);

		} else if (choose.equals("查看每日业绩折线图")) {
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
				dataset.addValue(price, "售价", date);
				dataset.addValue(costPrice, "成本价", date);
				dataset.addValue(profit, "利润", date);
				date = new Date(date.getTime() + 86400000);
				price = 0;
				costPrice = 0;
				profit = 0;
			}
			chart = ChartFactory.createLineChart("每日业绩折线图", "日期", "人名币", dataset, PlotOrientation.VERTICAL,
					true, true, true);
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
			chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
			NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
			categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			categoryAxis.setLabelFont(new Font("宋体", Font.BOLD, 15));
			numberAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			numberAxis.setLabelFont(new Font("宋体", Font.BOLD, 15));
			numberAxis.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(10000);
			numberAxis.setTickUnit(unit);
			LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
			lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			lineAndShapeRenderer.setBaseItemLabelsVisible(true);
			lineAndShapeRenderer.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 15));
			Rectangle shape = new Rectangle(10, 10);
			lineAndShapeRenderer.setSeriesShape(0, shape);
			lineAndShapeRenderer.setSeriesShape(1, shape);
			lineAndShapeRenderer.setSeriesShape(2, shape);
			lineAndShapeRenderer.setSeriesShapesVisible(0, true);
			lineAndShapeRenderer.setSeriesShapesVisible(1, true);
			lineAndShapeRenderer.setSeriesShapesVisible(2, true);
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("查看商品销量前十排行柱状图")) {
			int i = 0;
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			midMap = salesDao.queryGid(date1 ,date2);
			for (Map.Entry<Integer, Integer> entry : midMap.entrySet()) {
				dataset.addValue(entry.getValue(), "商品", goodsService.queryGoodsById(entry.getKey()).getGname());
				++i;
				if (i > 9) {
					break;
				}
			}
			chart = ChartFactory.createBarChart3D("商品销量前十排行柱状图", "商品", // X轴的标签
					"数量", // Y轴的标签
					dataset, // 图标显示的数据集合
					PlotOrientation.VERTICAL, // 图像的显示形式（水平或者垂直）
					true, // 是否显示子标题
					true, // 是否生成提示的标签
					true); // 是否生成URL链接
			// 处理图形上的乱码
			// 处理主标题的乱码
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
			// 处理子标题乱码
			chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
			// 获取图表区域对象
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
			// 获取X轴的对象
			CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();
			categoryAxis3D.setMaximumCategoryLabelLines(4);//行数
			// 获取Y轴的对象
			NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
			// 处理X轴上的乱码
			categoryAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理X轴外的乱码
			categoryAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴上的乱码
			numberAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴外的乱码
			numberAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));
			// 处理Y轴上显示的刻度，以10作为1格
			numberAxis3D.setAutoTickUnitSelection(false);
			NumberTickUnit unit = new NumberTickUnit(2);
			numberAxis3D.setTickUnit(unit);
			// 获取绘图区域对象
			BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot.getRenderer();
			// 设置柱形图的宽度
			barRenderer3D.setMaximumBarWidth(0.07);
			// 在图形上显示数字
			barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			barRenderer3D.setBaseItemLabelsVisible(true);
			barRenderer3D.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 15));
		
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("查看商品种类饼状图")) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			categoryMap = goodsService.queryCategoryNum();
			for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
				dataset.setValue(entry.getKey(), entry.getValue());
			}
			chart = ChartFactory.createPieChart3D("商品种类饼状图", // 主标题的名称
					dataset, // 图标显示的数据集合
					true, // 是否显示子标题
					true, // 是否生成提示的标签
					true); // 是否生成URL链接
			// 处理图形上的乱码
			// 处理主标题的乱码
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
			// 处理子标题乱码
			chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
			// 获取图表区域对象
			PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
			// 处理图像上的乱码
			categoryPlot.setLabelFont(new Font("宋体", Font.BOLD, 15));
			String format = "{0} {1} ({2})";
			categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));
			statement.removeAll();
			statement.setChart(chart);
		} else if (choose.equals("查看会员等级饼状图")) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			gradeMap = memberService.queryGradeNum();
			for(Map.Entry<String, Integer> entry : gradeMap.entrySet()) {
				dataset.setValue(entry.getKey(), entry.getValue());
			}
			chart = ChartFactory.createPieChart3D("会员等级饼状图", // 主标题的名称
					dataset, // 图标显示的数据集合
					true, // 是否显示子标题
					true, // 是否生成提示的标签
					true); // 是否生成URL链接
			// 处理图形上的乱码
			// 处理主标题的乱码
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
			// 处理子标题乱码
			chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
			// 获取图表区域对象
			PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
			// 处理图像上的乱码
			categoryPlot.setLabelFont(new Font("宋体", Font.BOLD, 15));
			String format = "{0} {1} ({2})";
			categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));
			statement.removeAll();
			statement.setChart(chart);
			
		}

	}
	/**
	 * 获取文件
	 * @return
	 */
	private File FileChooser() {
		JFileChooserDemo fc = new JFileChooserDemo("C:");
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileHidingEnabled(true);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("jpeg文件(*.jpeg)","jpeg"));
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
 * 自定义文件选择工具
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
			JOptionPane.showMessageDialog(getParent(), "文件名已被占用");
			return;
		} else {
			super.approveSelection();
		}
	}
}
