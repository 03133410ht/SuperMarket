package com.view.soldNote;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.entity.Goods;
import com.entity.Sales;
import com.entity.SalesInfo;
import com.service.GoodsService;
import com.service.MemberService;
import com.service.imp.GoodsServiceImp;
import com.service.imp.MemberServiceImp;
import com.util.OutExcel;

/**
 * 销售记录表格
 * 
 * @author Goddard
 *
 */
public class SoldNoteJtable extends JTable {
	private static final String[] COLUMN_NAMES = { "\u9500\u552E\u5355\u53F7", "商品条码", "\u5546\u54C1\u540D\u79F0",
			"成本价", "原价", "会员价格", "\u6570\u91CF", "\u5229\u6DA6", "会员id", "\u5907\u6CE8", "销售时间", "员工id" };
	private Object[][] o;
	private DefaultTableModel model;
	private DecimalFormat df;
	private Goods goods;
	private GoodsService goodsService = new GoodsServiceImp();
	private MemberService memberService= new MemberServiceImp();
	private int record;
	private double curPrice;
	private double curCostPrice;
	private int curNum;
	private double curProfit;
	private double memberPrice;

	public SoldNoteJtable(Map<Sales, List<SalesInfo>> salesMap, int page) {
		df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		soldNote(salesMap, page);
	}

	public int getRecord() {
		return record;
	}

	private void soldNote(Map<Sales, List<SalesInfo>> salesMap, int page) {
		record = 0;
		int i = 0;
		int y = 0;
		int maxPage = page * 10;
		int minPage = (page - 1) * 10;
		o = new Object[0][12];
		double profit = 0;
		curPrice = 0;
		curCostPrice = 0;
		curNum = 0;
		curProfit = 0;
		memberPrice = 0;
		double discount = 1;
		if (salesMap != null) {
			o = new Object[12][12];
			for (Map.Entry<Sales, List<SalesInfo>> entry : salesMap.entrySet()) {
				record += entry.getValue().size();
				for (SalesInfo salesInfo : entry.getValue()) {
					if (y >= minPage && y < maxPage) {
						//discount = Double.parseDouble(memberService.queryDiscount(memberService.queryByid(entry.getKey().getMid() + "").get(0).getMgrade()));
						String grade = memberService.queryByid(entry.getKey().getMid() + "").get(0).getMgrade();
						if (grade.equals("匿名客户")) {
							discount = 1;
						} else if (grade.equals("普通会员")) {
							discount = 0.98;
						} else if (grade.equals("黄金会员")) {
							discount = 0.95;
						} else if (grade.equals("铂金会员")) {
							discount = 0.93;
						} else if (grade.equals("钻石会员")) {
							discount = 0.9;
						} else if (grade.equals("至尊会员")) {
							discount = 0.8;
						}
						goods = goodsService.queryGoodsById(salesInfo.getGid());
						memberPrice = Double.parseDouble(df.format(goods.getGprice() * discount));
						profit = Double
								.parseDouble(df.format((memberPrice - goods.getGcost_price()) * salesInfo.getSnum()));
						curPrice += memberPrice * salesInfo.getSnum();
						curCostPrice += goods.getGcost_price() * salesInfo.getSnum();
						curNum += salesInfo.getSnum();
						curProfit += profit;
						o[i][0] = salesInfo.getSalesid();
						o[i][1] = salesInfo.getGid();
						o[i][2] = goods.getGname();
						o[i][3] = goods.getGcost_price();
						o[i][4] = goods.getGprice();
						o[i][5] = memberPrice;
						o[i][6] = salesInfo.getSnum();
						o[i][7] = profit;
						o[i][8] = entry.getKey().getMid();
						o[i][9] = entry.getKey().getRemark();
						o[i][10] = entry.getKey().getSdate();
						o[i][11] = entry.getKey().getEid();
						++i;
					}
					++y;
				}
			}
			o[11][0] = "当前页总计";
			o[11][3] = df.format(curCostPrice);
			o[11][5] = df.format(curPrice);
			o[11][6] = curNum;
			o[11][7] = df.format(curProfit);

		}
		model = new DefaultTableModel(o, COLUMN_NAMES);
		setModel(model);
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setResizable(false);
		getColumnModel().getColumn(0).setPreferredWidth(110);
		getColumnModel().getColumn(1).setResizable(false);
		getColumnModel().getColumn(2).setPreferredWidth(100);
		getColumnModel().getColumn(2).setMaxWidth(200);
		getColumnModel().getColumn(3).setResizable(false);
		getColumnModel().getColumn(4).setResizable(false);
		getColumnModel().getColumn(5).setResizable(false);
		getColumnModel().getColumn(6).setResizable(false);
		getColumnModel().getColumn(7).setResizable(false);
		getColumnModel().getColumn(8).setResizable(false);
		getColumnModel().getColumn(9).setResizable(false);
		getColumnModel().getColumn(10).setResizable(false);
	}

	public void setMap(Map<Sales, List<SalesInfo>> salesMap, int page) {
		soldNote(salesMap, page);
	}

	public void OutExcel(Map<Sales, List<SalesInfo>> salesMap) {
		int i = 0;
		curPrice = 0;
		curCostPrice = 0;
		curNum = 0;
		curProfit = 0;
		double discount = 0;
		double profit = 0;
		Object[][] o1 = new Object[this.record + 1][12];
		for (Map.Entry<Sales, List<SalesInfo>> entry : salesMap.entrySet()) {
			for (SalesInfo salesInfo : entry.getValue()) {
				String grade = memberService.queryByid(entry.getKey().getMid() + "").get(0).getMgrade();
				if (grade.equals("匿名客户")) {
					discount = 1;
				} else if (grade.equals("普通会员")) {
					discount = 0.98;
				} else if (grade.equals("黄金会员")) {
					discount = 0.95;
				} else if (grade.equals("铂金会员")) {
					discount = 0.93;
				} else if (grade.equals("钻石会员")) {
					discount = 0.9;
				} else if (grade.equals("至尊会员")) {
					discount = 0.8;
				}
				goods = goodsService.queryGoodsById(salesInfo.getGid());
				memberPrice = Double.parseDouble(df.format(goods.getGprice() * discount));
				profit = Double.parseDouble(df.format((memberPrice - goods.getGcost_price()) * salesInfo.getSnum()));
				curPrice += memberPrice * salesInfo.getSnum();
				curCostPrice += goods.getGcost_price() * salesInfo.getSnum();
				curNum += salesInfo.getSnum();
				curProfit += profit;
				o1[i][0] = salesInfo.getSalesid();
				o1[i][1] = salesInfo.getGid();
				o1[i][2] = goods.getGname();
				o1[i][3] = goods.getGcost_price();
				o1[i][4] = goods.getGprice();
				o1[i][5] = memberPrice;
				o1[i][6] = salesInfo.getSnum();
				o1[i][7] = profit;
				o1[i][8] = entry.getKey().getMid();
				o1[i][9] = entry.getKey().getRemark();
				o1[i][10] = entry.getKey().getSdate();
				o1[i][11] = entry.getKey().getEid();
				++i;
			}
		}
		o1[this.record][0] = "当前页总计";
		o1[this.record][3] = df.format(curCostPrice);
		o1[this.record][5] = df.format(curPrice);
		o1[this.record][6] = curNum;
		o1[this.record][7] = df.format(curProfit);
		new OutExcel(new JTable(new DefaultTableModel(o1, COLUMN_NAMES)));
	}
}
