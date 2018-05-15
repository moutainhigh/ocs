package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.rong.business.service.tel.TelService;
import com.rong.business.service.tel.TelServiceImpl;
import com.rong.common.bean.MyPage;
import com.rong.common.util.GsonUtil;
import com.rong.common.util.StringUtils;
import com.rong.persist.dto.TelDTO;
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
		int pageNumber = getParaToInt("pageNumber", 1);
		int limit = getParaToInt("pageSize", pageSize);
		String tel = getPara("tel");
		String province = getPara("province");
		String city = getPara("city");
		String platform = getPara("platform");
		String unplatform = getPara("unplatform");
		String operator = getPara("operator");
		String sex = getPara("sex");
		String age = getPara("age");
		String alipayName = getPara("alipayName");
		String qqNickName = getPara("qqNickName");
		String register = getPara("register");
		Kv param = Kv.by("tel", tel).set("province",province).set("city",city).set("platform", platform).set("operator",operator);
		param.set("sex", sex).set("age", age).set("alipayName", alipayName).set("qqNickName", qqNickName).set("unplatform", unplatform).set("register",register);
		MyPage list = service.page(pageNumber, limit, tel, param);
		keepPara();
		setAttr("page", list);
		render("/views/tel/list.jsp");
	}
	
	public void detail() {
		String tel = getPara("tel");
		Tel item = service.findTel(tel);
		if(!StringUtils.isNullOrEmpty(item.getCol3())){
			setAttr("other", GsonUtil.fromJson(item.getCol3(), TelDTO.class));
		}
		setAttr("item", item);
		render("/views/tel/detail.jsp");
	}
}
