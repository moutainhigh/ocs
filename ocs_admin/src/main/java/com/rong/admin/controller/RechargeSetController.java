package com.rong.admin.controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.RechargeSetService;
import com.rong.business.service.RechargeSetServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.RechargeSet;

public class RechargeSetController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private RechargeSetService rechargeSetService = new RechargeSetServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		rechargeSetService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		Integer rechargeMoney = getParaToInt("rechargeMoney");
		Integer give = getParaToInt("give");
		String remark = getPara("remark");
		rechargeSetService.save(rechargeMoney, give, remark);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功");
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			RechargeSet model = rechargeSetService.findById(id);
			setAttr("item", model);
		}
		render("/views/recharge/set_edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		Integer rechargeMoney = getParaToInt("rechargeMoney");
		Integer give = getParaToInt("give");
		String remark = getPara("remark");
		RechargeSet model = rechargeSetService.findById(id);
		model.setGive(give);
		model.setRechargeMoney(rechargeMoney);
		model.setGiveRemark(remark);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		Page<RechargeSet> list = rechargeSetService.page(page, pageSize);
		keepPara();
		setAttr("page", list);
		render("/views/recharge/set_list.jsp");
	}
	
	public void updateState() {
		Long id = getParaToLong("id");
		Boolean enable = getParaToBoolean("enable");
		rechargeSetService.setEnable(id, enable);
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]禁用/启用成功,id：" + id);
	}
}
