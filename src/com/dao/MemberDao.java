package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Member;
import com.entity.MemberGrade;

/**
 * 会员管理数据库操作接口
 * @author Goddard
 *
 */
public interface MemberDao {
	/**
	 * 根据会员id 进行模糊查询
	 * @param mid	会员id
	 * @return		会员集合
	 */
	public List<Member> queryByid(String mid);
	
	/**
	 * 根据会员姓名 进行模糊查询
	 * @param mname	会员姓名
	 * @return	会员集合
	 */
	public List<Member> queryByName(String mname);
	
	/**
	 * 根据性别 会员等级 和模糊关键词进行模糊查询
	 * @param fuzzy	模糊关键词
	 * @param msex	会员性别
	 * @param mgrade	会员等级
	 * @return	会员集合
	 */

	public List<Member> fuzzyQuery(String fuzzy,String msex,String mgrade);
	
	/**
	 * 根据会员手机 进行模糊查询
	 * @param mtel	会员手机
	 * @return	会员集合
	 */
	public List<Member> queryByTel(String mtel);
	
	/**
	 * 添加会员
	 * @param member
	 * @return	是否添加成功
	 */
	public boolean addMember(Member member);
	
	/**
	 * 根据会员id删除会员
	 * @param mid	会员id
	 * @return
	 */
	public boolean deleteById(int mid);
	
	/**
	 * 更新会员信息
	 * @param mid
	 * @param member
	 * @return
	 */
	public boolean updateMember(int mid ,Member member);
	
	/**
	 * 会员登陆	
	 * @param member
	 * @return
	 */
	public List<Member>  queryMember(Member member);
	
	
	/**
	 * 根据会员等级查找会员折扣率
	 * @param grade
	 * @return
	 */
	public List<String> queryDiscount(String grade);
	
	/**
	 * 会员消费金额 在后台减去余额
	 * @param mid
	 * @param price
	 * @return
	 */
	public boolean consume(int mid,double price,boolean isAccount);
	
	/**
	 * 查询所有会员等级
	 * @return
	 */
	public List<MemberGrade> queryAllGrade();
	
	/**
	 * 查询会员等级数量
	 * @return
	 */
	public Map<String,Integer> queryGradeNum();
}
