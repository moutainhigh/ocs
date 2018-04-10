package com.rong.business.service;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.MyConst;
import com.rong.common.util.CommonUtil;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.ConsumeDao;
import com.rong.persist.dao.MealDao;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.User;

/**
 * 会员用户业务实现类
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	private UserDao dao = new UserDao();
	private MealDao mealDao = new MealDao();
	private AccountDao accountDao = new AccountDao();
	private ConsumeDao consumeDao = new ConsumeDao();
	@Override
	public Page<Record> getUserList(int page,int pagesize,Kv param) {
		return dao.page(page,pagesize,param);
	}
	
	@Override
	public boolean setEnable(long id, boolean isEnable){
		return dao.updateField(id, "state", isEnable ? MyConst.USERSTATUS_ENABLE : MyConst.USERSTATUS_DISABLE);
	}
	
	@Override
	public boolean resetPwd(long id, String pwd) {
		return dao.updateField(id, "user_pwd", pwd);
	}

	@Override
	public User findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public User findByUserNameAndPwd(String userName, String pwd) {
		pwd = CommonUtil.getMD5(pwd);
		return dao.findByUserNameAndPwd(userName, pwd);
	}

	@Override
	public boolean editExpirDate(long id, String date) {
		return dao.updateField(id, "expir_date", date);
	}

	@Override
	public boolean saveUserMeal(String userName, Long mealId) {
		return mealDao.saveUserMeal(userName, mealId);
	}

	@Override
	public boolean openMeal(String userName, Long mealId,BigDecimal account,BigDecimal mealMoney) {
		// 扣除金额
		boolean result = accountDao.consumed(userName, account, mealMoney);
		// 保存消费记录
		consumeDao.save(mealMoney, userName, mealId, "开通套餐");
		if(result){
			return mealDao.saveUserMeal(userName, mealId);
		}
		return false;
	}

	@Override
	public boolean haveValidMealProject(String userName, Long projectId) {
		List<Long> proList = mealDao.userValidProjectList(userName);
		return proList.contains(projectId);
	}

	@Override
	public int countLoginToday() {
		return dao.countLoginToday();
	}
}
