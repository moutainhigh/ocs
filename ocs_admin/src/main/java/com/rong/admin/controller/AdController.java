package com.rong.admin.controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.AdService;
import com.rong.business.service.AdServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Ad;

public class AdController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private AdService adService = new AdServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		adService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		String content = getPara("content");
		adService.save(null, content);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功");
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			Ad model = adService.findById(id);
			setAttr("item", model);
		}
		render("/views/Ad/edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		String content = getPara("content");
		Ad model = adService.findById(id);
		model.setContent(content);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		Page<Ad> list = adService.page(page, pageSize, null);
		keepPara();
		setAttr("page", list);
		render("/views/ad/list.jsp");
	}
}
