var basePath = $("#basePath").val();
var goodsId = $("#goodsId").val();

if(taobao_app_index(goodsId,"tmall","cm_details","")==1) {
    var os_type = get_os_type();
    if(os_type.indexOf("iPhone")==0){
        $("#btn_img").attr("src",basePath+"content/img/pc2.png");
    }else{
        $("#btn_img").attr("src",basePath+"content/img/pc1.png");
    }
    $.getJSON(basePath + "m/api/" + goodsId, function (data) {
        layui.use(['carousel', 'laytpl','layer'], function () {
            layui.laytpl(appTemp.innerHTML).render(data, function (html) {
                $("#goodsDetail").html(html);
                $("#loading").hide();
                $("#goodsDetail").show();

                layui.carousel.render({
                    elem: '#goods-cover'
                    , width: '100%'
                    , height: '375px'
                    , interval: 3000
                });

                $(".d-tabbar li").click(function () {
                    $(".d-tabbar li").removeClass("sel");
                    $(".d-tabBox>div").hide();
                    $(this).addClass("sel");
                    $("#" + $(this).attr("data-id")).show();
                });

                $("#btn_close,#goodsDetail").click(function () {
                    $("#ma").hide();
                });

            });
        })
     })
}


function goBuy() {
    $("#ma").show();
}

function showCode() {
    var taocode = $("#taocode").val();
    layui.layer.open({
        type: 1
        ,title: "复制淘口令，前往淘宝打开"
        ,area: '85%;'
        ,shade: 0.7
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['一键复制']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 20px"><div style="line-height: 22px; color: #666; border:dashed 1px #999;width:100%;padding:10px;">复制框内整段文字'+taocode+'打开【手机淘宝】即可直达宝贝</div></div>'
    });

    var clipboard = new ClipboardJS('.layui-layer-btn0', {
        text: function() {
            return taocode;
        }
    });
}

