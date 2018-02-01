$(function() {

        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function() {
            $.AMUI.fullscreen.toggle();
        });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
            $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        });

		var dataType = $('body').attr('data-type');
        for (key in pageData) {
            if (key == dataType) {
                pageData[key]();
            }
        }
    })
    // ==========================
    // 侧边导航下拉列表
    // ==========================

$('.tpl-left-nav-link-list').on('click', function() {
			$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80).end().find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
			$('.am-breadcrumb').children('li').eq(1).children('a').eq(0).text( $(this).children('span').eq(0).text());//设置有则导航栏值
			//点开一个菜单，将其它菜单栏隐藏
			var obj = $(this).siblings('.tpl-left-nav-sub-menu');
			$('.tpl-left-nav-sub-menu').each(function(){
				if(!$(this).is(obj)){
					$(this).hide(500);
				}
			});
    })
    // ==========================
    // 头部导航隐藏菜单
    // ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
    $('.tpl-left-nav').toggle();
    $('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
})

/**
 * 给所有a标签添加事件,点击添加选中效果，并将其他选中的效果删除
 */
$("a").click(function(){
	$('a').each(function(){
	   if($(this).attr('href')){
		   $(this).removeClass("active");
	   }
	});
	$(this).addClass("active");
});

var pageData = {
	// ===============================================
    // 后台首页-报表数据
    // ===============================================
		
    'index': function indexData() {

    }
	
}