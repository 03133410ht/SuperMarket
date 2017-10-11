package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Member;

/**
 * ������ԱService�ӿ�
 * @author Goddard
 *
 */
public interface MemberService {
	/**
	 * ���ݻ�Աid ����ģ����ѯ
	 * @param mid	��Աid
	 * @return		��Ա����
	 */
	public List<Member> queryByid(String mid);
	
	/**
	 * ���ݻ�Ա���� ����ģ����ѯ
	 * @param mname	��Ա����
	 * @return	��Ա����
	 */
	public List<Member> queryByName(String mname);
	
	/**
	 * �����Ա� ��Ա�ȼ� ��ģ���ؼ��ʽ���ģ����ѯ
	 * @param fuzzy	ģ���ؼ���
	 * @param msex	��Ա�Ա�
	 * @param mgrade	��Ա�ȼ�
	 * @return	��Ա����
	 */

	public List<Member> fuzzyQuery(String fuzzy,String msex,String mgrade);
	
	/**
	 * ���ݻ�Ա�ֻ� ����ģ����ѯ
	 * @param mtel	��Ա�ֻ�
	 * @return	��Ա����
	 */
	public List<Member> queryByTel(String mtel);
	
	
	/**
	 * ��ӻ�Ա
	 * @param member
	 * @return	�Ƿ���ӳɹ�
	 */
	public boolean addMember(Member member);
	
	
	/**
	 * ���ݻ�Աidɾ����Ա
	 * @param mid	��Աid
	 * @return
	 */
	public boolean deleteById(int mid);
	
	/**
	 * ���»�Ա��Ϣ
	 * @param mid
	 * @param member
	 * @return
	 */
	public boolean updateMember(int mid ,Member member);
	
	/**
	 * ��Ա��½	
	 * @param member
	 * @return
	 */
	public Member queryMember(Member member);
	
	
	/**
	 * ���ݻ�Ա�ȼ����һ�Ա�ۿ���
	 * @param grade
	 * @return
	 */
	public String queryDiscount(String grade);
	
	
	/**
	 * ��Ա���ѽ�� �ں�̨��ȥ���
	 * @param mid
	 * @param price
	 * @return
	 */
	public boolean consume(int mid,double price,boolean isAccount);
	
	/**
	 * ��ѯ��ǰ��Ա�Ƿ�����
	 * @param member
	 * @return
	 */
	public String isUpgrade(Member member);
	
	
	/**
	 * ��ѯ��Ա�ȼ�����
	 * @return
	 */
	public Map<String, Integer> queryGradeNum();
	
	
	
}