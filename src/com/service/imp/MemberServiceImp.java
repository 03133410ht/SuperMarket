package com.service.imp;

import java.util.List;
import java.util.Map;

import com.dao.MemberDao;
import com.dao.imp.MemberDaoImp;
import com.entity.Member;
import com.entity.MemberGrade;
import com.service.MemberService;

/**
 * ������ԱServiceʵ����
 * 
 * @author Goddard
 *
 */
public class MemberServiceImp implements MemberService {
	private MemberDao memberDao = new MemberDaoImp();

	/**
	 * ���ݻ�Աid ����ģ����ѯ
	 * 
	 * @param mid
	 *            ��Աid
	 * @return ��Ա����
	 */
	public List<Member> queryByid(String mid) {
		return memberDao.queryByid(mid);
	}

	/**
	 * ���ݻ�Ա���� ����ģ����ѯ
	 * 
	 * @param mname
	 *            ��Ա����
	 * @return ��Ա����
	 */
	public List<Member> queryByName(String mname) {
		return memberDao.queryByName(mname);
	}

	/**
	 * �����Ա� ��Ա�ȼ� ��ģ���ؼ��ʽ���ģ����ѯ
	 * 
	 * @param fuzzy
	 *            ģ���ؼ���
	 * @param msex
	 *            ��Ա�Ա�
	 * @param mgrade
	 *            ��Ա�ȼ�
	 * @return ��Ա����
	 */

	public List<Member> fuzzyQuery(String fuzzy, String msex, String mgrade) {
		return memberDao.fuzzyQuery(fuzzy, msex, mgrade);
	}

	/**
	 * ���ݻ�Ա�ֻ� ����ģ����ѯ
	 * 
	 * @param fuzzy
	 *            ģ���ؼ���
	 * @param mname
	 *            ��Ա�ֻ�
	 * @return ��Ա����
	 */
	public List<Member> queryByTel(String mtel) {
		return memberDao.queryByTel(mtel);
	}

	/**
	 * ��ӻ�Ա
	 */
	public boolean addMember(Member member) {
		return memberDao.addMember(member);
	}

	/**
	 * ɾ����Ա
	 */
	public boolean deleteById(int mid) {
		return memberDao.deleteById(mid);
	}

	/**
	 * ���»�Ա��Ϣ
	 */
	public boolean updateMember(int mid, Member member) {
		return memberDao.updateMember(mid, member);
	}

	/**
	 * ��Ա��½
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
	 * ���ݻ�Ա�ȼ����һ�Ա�ۿ���
	 * 
	 * @param grade
	 * @return
	 */
	public String queryDiscount(String grade) {
		return memberDao.queryDiscount(grade).get(0);
	}

	/**
	 * ��Ա���ѽ�� �ں�̨��ȥ���
	 * 
	 * @param mid
	 * @param price
	 * @return
	 */
	public boolean consume(int mid, double price, boolean isAccount) {
		return memberDao.consume(mid, price, isAccount);
	}

	/**
	 * ��ѯ��ǰ��Ա�Ƿ�����
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
	 * ��ѯ��Ա�ȼ�����
	 * @return
	 */
	public Map<String, Integer> queryGradeNum() {
		return memberDao.queryGradeNum();
	}

	

}
