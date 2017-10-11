package com.service.imp;

import java.util.List;
import java.util.Map;

import com.dao.MemberDao;
import com.dao.imp.MemberDaoImp;
import com.entity.Member;
import com.entity.MemberGrade;
import com.service.MemberService;

/**
 * 操作会员Service实现类
 * 
 * @author Goddard
 *
 */
public class MemberServiceImp implements MemberService {
	private MemberDao memberDao = new MemberDaoImp();

	/**
	 * 根据会员id 进行模糊查询
	 * 
	 * @param mid
	 *            会员id
	 * @return 会员集合
	 */
	public List<Member> queryByid(String mid) {
		return memberDao.queryByid(mid);
	}

	/**
	 * 根据会员姓名 进行模糊查询
	 * 
	 * @param mname
	 *            会员姓名
	 * @return 会员集合
	 */
	public List<Member> queryByName(String mname) {
		return memberDao.queryByName(mname);
	}

	/**
	 * 根据性别 会员等级 和模糊关键词进行模糊查询
	 * 
	 * @param fuzzy
	 *            模糊关键词
	 * @param msex
	 *            会员性别
	 * @param mgrade
	 *            会员等级
	 * @return 会员集合
	 */

	public List<Member> fuzzyQuery(String fuzzy, String msex, String mgrade) {
		return memberDao.fuzzyQuery(fuzzy, msex, mgrade);
	}

	/**
	 * 根据会员手机 进行模糊查询
	 * 
	 * @param fuzzy
	 *            模糊关键词
	 * @param mname
	 *            会员手机
	 * @return 会员集合
	 */
	public List<Member> queryByTel(String mtel) {
		return memberDao.queryByTel(mtel);
	}

	/**
	 * 添加会员
	 */
	public boolean addMember(Member member) {
		return memberDao.addMember(member);
	}

	/**
	 * 删除会员
	 */
	public boolean deleteById(int mid) {
		return memberDao.deleteById(mid);
	}

	/**
	 * 更新会员信息
	 */
	public boolean updateMember(int mid, Member member) {
		return memberDao.updateMember(mid, member);
	}

	/**
	 * 会员登陆
	 * 
	 * @param member
	 * @return
	 */
	public Member queryMember(Member member) {
		Member member1 = null;
		if (memberDao.queryMember(member).size() != 0) {
			member1 = memberDao.queryMember(member).get(0);
		}
		return member1;
	}

	/**
	 * 根据会员等级查找会员折扣率
	 * 
	 * @param grade
	 * @return
	 */
	public String queryDiscount(String grade) {
		return memberDao.queryDiscount(grade).get(0);
	}

	/**
	 * 会员消费金额 在后台减去余额
	 * 
	 * @param mid
	 * @param price
	 * @return
	 */
	public boolean consume(int mid, double price, boolean isAccount) {
		return memberDao.consume(mid, price, isAccount);
	}

	/**
	 * 查询当前会员是否升级
	 * 
	 * @param member
	 * @return
	 */
	public String isUpgrade(Member member) {
		String str = null;
		List<MemberGrade> list = memberDao.queryAllGrade();
		for (MemberGrade memberGrade : list) {
			if (member.getMmonetary() > memberGrade.getMinicharge() && memberGrade.getMdiscount() < Double
					.parseDouble(memberDao.queryDiscount(member.getMgrade()).get(0))) {
				member.setMgrade(memberGrade.getMgrade());
				memberDao.updateMember(member.getMid(), member);
				str = memberGrade.getMgrade();
			}
		}
		return str;
	}

	/**
	 * 查询会员等级数量
	 * @return
	 */
	public Map<String, Integer> queryGradeNum() {
		return memberDao.queryGradeNum();
	}

	

}
