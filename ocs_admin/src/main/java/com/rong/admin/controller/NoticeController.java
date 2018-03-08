package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.NoticeService;
import com.rong.business.service.NoticeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Notice;

public class NoticeController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private NoticeService service = new NoticeServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		service.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		String content = getPara("content");
		String softwareCode = getPara("softwareCode");
		String softwareName = getPara("softwareName");
		service.save(content, softwareCode, softwareName, false);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功:" + softwareCode+"-"+softwareName+"-"+content);
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			Notice model = service.findById(id);
			setAttr("item", model);
		}
		render("/views/notice/edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		String content = getPara("content");
		String softwareCode = getPara("softwareCode");
		String softwareName = getPara("softwareName");
		Notice model = service.findById(id);
		model.setSoftwareCode(softwareCode);
		model.setSoftwareName(softwareName);
		model.setContent(content);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		String softwareCode = getPara("softwareCode");
		String content = getPara("content");
		Kv param = Kv.by("softwareCode",softwareCode).set("content", content);
		Page<Notice> returnPage = service.page(page, pageSize, param);
		keepPara();
		setAttr("page", returnPage);
		render("/views/notice/list.jsp");
	}
	
	public void enable(){
		Long id = getParaToLong("id");
		Boolean enable = getParaToBoolean("enable");
		Notice model = service.findById(id);
		model.setEnable(enable);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
	}
}
