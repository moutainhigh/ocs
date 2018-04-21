package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.rong.business.service.tel.TelService;
import com.rong.business.service.tel.TelServiceImpl;
import com.rong.common.bean.MyPage;
import com.rong.common.util.StringUtils;
import com.rong.persist.model.Tel;

public class TelController extends BaseController{
	private TelService service = new TelServiceImpl();
	
	public void toView() {
		String tel = getPara("id");
		if(!StringUtils.isNullOrEmpty(tel)){
			Tel item = service.findTel(tel);
			setAttr("item", item);
		}
		render("/views/tel/edit.jsp");
	}
	
	public void list() {
		int page = getParaToInt("page", 1);
		String tel = getPara("tel","1300");
		String province = getPara("province");
		String city = getPara("city");
		String platform = getPara("platform");
		String operator = getPara("operator");
		Kv param = Kv.by("tel", tel).set("province",province).set("city",city).set("platform", platform).set("operator",operator);
		MyPage list = service.page(page, pageSize, tel, param);
		keepPara();
		setAttr("page", list);
		render("/views/tel/list.jsp");
	}
}
