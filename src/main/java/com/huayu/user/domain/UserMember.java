package com.huayu.user.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：用户会员表
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：12:27
 */
@Table(name="user_member")
public class UserMember extends BaseDomain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = 2978901655954889883L;
	private Long userId;
	private Long growthValue;
	private Long score;
	private Long expenseScore;
	private Short type;
	private Long relationId;

	// Constructors

	/** default constructor */
	public UserMember() {
	}

	/** full constructor */
	public UserMember(Long userId, Long growthValue, Long score,
			Long expenseScore, Short type, Long relationId) {
		this.userId = userId;
		this.growthValue = growthValue;
		this.score = score;
		this.expenseScore = expenseScore;
		this.type = type;
		this.relationId = relationId;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGrowthValue() {
		return this.growthValue;
	}

	public void setGrowthValue(Long growthValue) {
		this.growthValue = growthValue;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getExpenseScore() {
		return this.expenseScore;
	}

	public void setExpenseScore(Long expenseScore) {
		this.expenseScore = expenseScore;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getRelationId() {
		return this.relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

}