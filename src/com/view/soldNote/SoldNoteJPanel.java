package com.view.soldNote;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.entity.Employee;
import com.entity.Goods;
import com.entity.Member;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.service.EmployeeService;
import com.service.GoodsService;
import com.service.MemberService;
import com.service.SalesService;
import com.service.imp.EmployeeServiceImp;
import com.service.imp.GoodsServiceImp;
import com.service.imp.MemberServiceImp;
import com.service.imp.SalesServiceImp;
import com.util.KeyTypeUtil;
import com.util.StringUtil;
import com.view.DateField;

/**
 * 销售记录Jpanel
 * 
 * @author Goddard
 *
 */
public class SoldNoteJPanel extends JPanel {
	private JTextField snFiltrationTextField;
	private SoldNoteJtable snJTable;
	private JTextField skipTextField;
	private DateField snDate1ComboBox;
	private DateField snDate2ComboBox;
	private SalesService salesService = new SalesServiceImp();
	private Map<Sales, List<SalesInfo>> salesMap;
	private JLabel recordLabel;
	private JLabel sumPageLabel;
	private JLabel presentPageLabel;
	private int sumPage;
	private int curPage;
	private int record;
	private GoodsService goodsService = new GoodsServiceImp();
	private DecimalFormat df = new DecimalFormat("0.00");
	private JComboBox filtrationComboBox;
	private MemberService memberService = new MemberServiceImp();
	private EmployeeService employeeService = new EmployeeServiceImp();

	public SoldNoteJPanel() {
		soldNote();
	}

	private void soldNote() {
		df.setRoundingMode(RoundingMode.HALF_UP);
		JLabel snFiltrationLabel = new JLabel("\u67E5\u8BE2\u6761\u4EF6");
		snFiltrationLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		snFiltrationLabel.setBounds(34, 37, 74, 34);

		snFiltrationTextField = new JTextField();
		snFiltrationTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchPerformed();
				}
			}
		});

		snFiltrationTextField.setFont(new Font("宋体", Font.PLAIN, 17));
		snFiltrationTextField.setBounds(284, 37, 114, 34);
		snFiltrationTextField.setColumns(10);

		JButton snSearchButton = new JButton("\u641C\u7D22");
		snSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPerformed();
			}
		});

		snSearchButton.setFont(new Font("宋体", Font.PLAIN, 17));
		snSearchButton.setBounds(425, 36, 74, 37);

		JLabel snDateLabel = new JLabel("\u9500\u552E\u65F6\u95F4");
		snDateLabel.setBounds(529, 38, 74, 33);
		snDateLabel.setFont(new Font("宋体", Font.PLAIN, 17));

		snDate1ComboBox = new DateField();
		snDate1ComboBox.setBounds(617, 37, 189, 35);
		snDate1ComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));

		JButton snExcelButton = new JButton("\u5BFC\u51FAexcel");
		snExcelButton.setFont(new Font("宋体", Font.PLAIN, 17));
		snExcelButton.setBounds(1075, 37, 114, 33);
		snExcelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salesMap == null) {
					JOptionPane.showMessageDialog(null, "请搜索后再导出");
					return;
				} else {
					snJTable.OutExcel(salesMap);
				}
			}
		});

		JScrollPane snScrollPane = new JScrollPane();
		snScrollPane.setBounds(34, 92, 1237, 351);

		snJTable = new SoldNoteJtable(salesMap, 1) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		snScrollPane.setViewportView(snJTable);

		JLabel snArriveLabel = new JLabel("\u81F3");
		snArriveLabel.setBounds(820, 39, 18, 28);
		snArriveLabel.setFont(new Font("宋体", Font.PLAIN, 18));

		snDate2ComboBox = new DateField();
		snDate2ComboBox.setBounds(860, 37, 190, 35);
		snDate2ComboBox.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		setLayout(null);
		add(snScrollPane);
		add(snFiltrationLabel);
		add(snFiltrationTextField);
		add(snSearchButton);
		add(snDateLabel);
		add(snDate1ComboBox);
		add(snArriveLabel);
		add(snDate2ComboBox);
		add(snExcelButton);
		String[] filtArr = { "全部记录", "根据会员查看", "根据员工查看", "根据单号查看", "根据商品查看" };
		filtrationComboBox = new JComboBox(filtArr);
		filtrationComboBox.setBounds(122, 38, 134, 34);
		add(filtrationComboBox);

		JLabel page1Label = new JLabel("\u5F53\u524D\u4E3A\u7B2C");
		page1Label.setFont(new Font("宋体", Font.BOLD, 17));
		page1Label.setForeground(Color.BLACK);
		page1Label.setBounds(390, 456, 80, 34);
		add(page1Label);

		presentPageLabel = new JLabel("0");
		presentPageLabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		presentPageLabel.setBounds(474, 457, 33, 30);
		add(presentPageLabel);

		JLabel page2Label = new JLabel("\u9875\uFF0C\u5171");
		page2Label.setForeground(Color.BLACK);
		page2Label.setFont(new Font("宋体", Font.BOLD, 17));
		page2Label.setBounds(501, 456, 72, 34);
		add(page2Label);

		sumPageLabel = new JLabel("0");
		sumPageLabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		sumPageLabel.setBounds(565, 456, 33, 34);
		add(sumPageLabel);

		JLabel page3Label = new JLabel("\u9875\uFF0C\u5171");
		page3Label.setForeground(Color.BLACK);
		page3Label.setFont(new Font("宋体", Font.BOLD, 17));
		page3Label.setBounds(600, 456, 54, 34);
		add(page3Label);

		JButton homePageButton = new JButton("\u9996\u9875");
		homePageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salesMap == null) {
					JOptionPane.showMessageDialog(null, "请搜索后再点击");
					return;
				} else {
					curPage = 1;
					if (sumPage == 0) {
						curPage = 0;
					}
					snJTable.setMap(salesMap, curPage);
					presentPageLabel.setText(curPage + "");
				}
			}
		});
		homePageButton.setFont(new Font("宋体", Font.BOLD, 15));
		homePageButton.setBounds(310, 503, 113, 27);
		add(homePageButton);

		JButton previousPageButton = new JButton("\u4E0A\u4E00\u9875");
		previousPageButton.setFont(new Font("宋体", Font.BOLD, 15));
		previousPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salesMap == null) {
					JOptionPane.showMessageDialog(null, "请搜索后再点击");
					return;
				}
				if (curPage == 1 || curPage == 0) {
					JOptionPane.showMessageDialog(null, "当前页已经是首页!!");
				} else {
					curPage = curPage - 1;
					snJTable.setMap(salesMap, curPage);
					presentPageLabel.setText(curPage + "");
				}
			}
		});
		previousPageButton.setBounds(463, 503, 113, 27);
		add(previousPageButton);

		JButton nextPageButton = new JButton("\u4E0B\u4E00\u9875");
		nextPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salesMap == null) {
					JOptionPane.showMessageDialog(null, "请搜索后再点击");
					return;
				}
				if (curPage == sumPage || sumPage == 0) {
					JOptionPane.showMessageDialog(null, "已经到最后一页！！");
				} else {
					curPage = curPage + 1;
					snJTable.setMap(salesMap, curPage);
					presentPageLabel.setText(curPage + "");
				}
			}
		});
		nextPageButton.setFont(new Font("宋体", Font.BOLD, 15));
		nextPageButton.setBounds(613, 503, 113, 27);
		add(nextPageButton);

		JButton trailerPageButton = new JButton("\u5C3E\u9875");
		trailerPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (salesMap == null) {
					JOptionPane.showMessageDialog(null, "请搜索后再点击");
					return;
				} else {
					curPage = sumPage;
					snJTable.setMap(salesMap, curPage);
					presentPageLabel.setText(curPage + "");
				}
			}
		});
		trailerPageButton.setFont(new Font("宋体", Font.BOLD, 15));
		trailerPageButton.setBounds(774, 504, 113, 27);
		add(trailerPageButton);

		recordLabel = new JLabel("0");
		recordLabel.setFont(new Font("微软雅黑", Font.BOLD, 17));
		recordLabel.setBounds(668, 456, 72, 34);
		add(recordLabel);

		JLabel page4Label = new JLabel("\u6761\u8BB0\u5F55");
		page4Label.setForeground(Color.BLACK);
		page4Label.setFont(new Font("宋体", Font.BOLD, 17));
		page4Label.setBounds(733, 456, 72, 34);
		add(page4Label);

		JLabel skipLabel = new JLabel("\u8DF3\u8F6C\u81F3");
		skipLabel.setForeground(Color.BLACK);
		skipLabel.setFont(new Font("宋体", Font.BOLD, 17));
		skipLabel.setBounds(946, 499, 72, 33);
		add(skipLabel);

		skipTextField = new JTextField();
		skipTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				KeyTypeUtil.numType(e);
			}

			public void keyPressed(KeyEvent e) {
				if (KeyTypeUtil.pressEnter(e)) {
					skipPageActionPerformed();
				}
			}
		});
		skipTextField.setFont(new Font("微软雅黑", Font.BOLD, 17));
		skipTextField.setBounds(1026, 498, 86, 34);
		add(skipTextField);
		skipTextField.setColumns(10);

		JButton skipButton = new JButton("\u8DF3\u8F6C");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skipPageActionPerformed();
			}
		});
		skipButton.setForeground(Color.BLACK);
		skipButton.setFont(new Font("宋体", Font.BOLD, 17));
		skipButton.setBounds(1126, 498, 92, 34);
		add(skipButton);
	}

	/**
	 * 跳转事件处理
	 */
	private void skipPageActionPerformed() {
		if (salesMap == null) {
			JOptionPane.showMessageDialog(null, "请搜索后再点击");
			return;
		} else if (skipTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入数字后再点击");
			return;
		} else {
			int page = Integer.parseInt(skipTextField.getText().toString());
			if (page > sumPage) {
				JOptionPane.showMessageDialog(null, "超过最大页数，最大页数为" + sumPage);
				return;
			}
			curPage = page;
			snJTable.setMap(salesMap, curPage);
			presentPageLabel.setText(curPage + "");
		}
	}

	/**
	 * 搜索事件处理
	 */
	private void searchPerformed() {
		curPage = 1;
		skipTextField.setText("");
		record = 0;
		recordLabel.setText("");
		sumPageLabel.setText("");
		presentPageLabel.setText("");
		double sumProfit = 0;
		double discount = 0;
		Goods goods = null;
		List<Member> memberList;
		List<Goods> goodsList;
		List<String> stringList;
		List<Employee> employeeList;
		Date date1 = new Date(snDate1ComboBox.getDate().getTime());
		Date date2 = new Date(snDate2ComboBox.getDate().getTime());
		String fuzzy = snFiltrationTextField.getText().trim();

		if (fuzzy.equals("")) {
			salesMap = salesService.getALLByMid(null, date1, date2);

		} else {
			if (filtrationComboBox.getSelectedItem().equals("根据会员查看")) {
				memberList = memberService.fuzzyQuery(fuzzy, "", "");
				if (memberList.size() == 0) {
					salesMap = new LinkedHashMap<Sales, List<SalesInfo>>();
				} else {
					salesMap = salesService.getALLByMid(memberList, date1, date2);
				}
			} else if (filtrationComboBox.getSelectedItem().equals("根据单号查看")) {
				stringList = new ArrayList<String>();
				stringList.add(fuzzy);
				if (stringList.size() == 0) {
					salesMap = new LinkedHashMap<Sales, List<SalesInfo>>();
				} else {
					if (fuzzy.matches("\\d{1,18}")) {
						salesMap = salesService.getALLByMid(stringList, date1, date2);
					} else {
						salesMap = new LinkedHashMap<Sales, List<SalesInfo>>();
					}
				}
			} else if (filtrationComboBox.getSelectedItem().equals("根据商品查看")) {
				goodsList = goodsService.fuzzyQuery(fuzzy, "", " > -1");
				if (goodsList.size() == 0) {
					salesMap = new LinkedHashMap<Sales, List<SalesInfo>>();
				} else {
					salesMap = salesService.getALLByMid(goodsList, date1, date2);
				}
			} else if (filtrationComboBox.getSelectedItem().equals("根据员工查看")) {
				employeeList = employeeService.fuzzyQueryEmployee(fuzzy);
				if (employeeList.size() == 0) {
					salesMap = new LinkedHashMap<Sales, List<SalesInfo>>();
				} else {
					salesMap = salesService.getALLByMid(employeeList, date1, date2);
				}
			}
		}

		snJTable.setMap(salesMap, curPage);
		record = snJTable.getRecord();
		recordLabel.setText(record + "");
		sumPage = (int) Math.ceil((double) record / 10);
		if (salesMap == null||salesMap.size() == 0) {
			curPage = 0;
			JOptionPane.showMessageDialog(null, "没有找到任何记录");
		}
		sumPageLabel.setText(sumPage + "");
		presentPageLabel.setText(curPage + "");

	}
}
