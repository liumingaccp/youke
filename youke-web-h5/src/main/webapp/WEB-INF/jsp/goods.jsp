<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no, email=no" name="format-detection">
    <meta name="aplus-waiting" content="1">
    <meta name="data-spm" content="a1z3i">
    <title>商品详情</title>
    <link rel="stylesheet" href="${basePath}content/layui/css/layui.css?v=1.0.0" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${basePath}content/css/goods.css?v=1.0.0" type="text/css" media="screen" charset="utf-8">
    <script src="${basePath}content/js/jquery-1.9.1.min.js?v=1.0.0" type="text/javascript" charset="utf-8"></script>
    <script src="${basePath}content/layui/layui.js?v=1.0.0" type="text/javascript" charset="utf-8"></script>
    <script src="${basePath}content/js/clipboard.min.js?v=1.0.0" type="text/javascript" charset="utf-8"></script>
    <script src="${basePath}content/js/wx_app.js?v=1.0.2" type="text/javascript" charset="utf-8"></script>
    </head>
<body>
<input id="basePath" value="${basePath}" type="hidden"/>
<input id="goodsId" value="${goodsId}" type="hidden"/>
<div id="loading" style="text-align: center;"><img src="${basePath}content/img/loading.gif" style="width: 32px;margin-top: 30px"> </div>
<div id="goodsDetail" style="display: none">
    <script id="appTemp" type="text/html">
    <input type="hidden" id="taocode" value="{{ d.taocode }}"/>
    <div class="app-detail">
        <div class="layui-carousel" id="goods-cover">
            <div carousel-item="">
                {{#  layui.each(d.pics, function(index, item){ }}
                <div><img src="{{ item.src }}"></div>
                {{#  }); }}
            </div>
        </div>
        <div class="main_layout">
            <div class="title_share">
                <h1>
                    <span>{{ d.title }}</span>
                </h1>
            </div>
            <div class="d-price">
                <div>

                    <div class="present-price"><label class="price-name"></label>
                        <p class="o-t-price">
                            <span class="major" id="major">
                            {{#  if(d.promotion_price == ''){ }}
                                {{ d.price }}
                            {{#}else {}}
                                {{ d.promotion_price }}
                            {{#} }}
                            </span>
                        </p>
                        <p class="txt">
                        </p>
                        <p style="display: inline-block;color: #ff5000;font-size: 0.5rem">
                            <span class="pre-price" style="display: none;"></span>
                            <span class="custom-price" style="display: none;"></span>
                        </p>
                    </div>
                    {{#  if(d.promotion_price != ''){ }}
                    <div class="original-price">
                        <div class="">
                            <span>价格</span>
                            <span>:￥</span>
                            <del>{{ d.price }}</del>
                        </div>
                    </div>
                    {{#  } }}
                    <div class="coupon-wrap" id="coupon-wrap" style="display: none;">
                        <div class="item-coupon">
                            <span class="coupon-tag">券</span>
                            <span id="coupon-price">0</span>元
                        </div>
                        <span class="coupon-tip" id="coupon-tip"></span>
                        <span id="price-after-coupon-wrap">
                            券后价:
                            <span class="price-after-coupon" id="price-after-coupon"></span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-InfoMain">
            <div class="d-tabbar">
                <ul>
                    <li class="sel" data-id="descimg">图文详情</li>
                    <li data-id="item_props">产品参数</li>
                    <li data-id="rate_list">商品评论</li>
                </ul>
            </div>
            <div class="d-tabBox">
                <div id="descimg" class="itemPhotoDetail ">
                    {{#  layui.each(d.desc, function(index, item){ }}
                    {{#  if(item.label == 'img'){ }}
                    <div><img src="{{ item.content }}"></div>
                    {{#  } }}
                    {{#  }); }}
                </div>
                <div id="item_props" class="itemIntroduce" style="display: none">
                    {{#  layui.each(d.item_props, function(index, item){ }}
                    <dl> <dt>{{ item.name }}</dt> <dd>{{ item.value }}</dd> </dl>
                    {{#  }); }}
                </div>
                <div id="rate_list" class="shopRecommends" style="display: none;">
                    <div class="rate_single" style="padding: 0px;">
                     <ul>
                        {{#  layui.each(d.rate_list, function(index, item){ }}
                    <li style="margin-top: 0px;margin-top: 10px;background-color: white;padding: 8px;"> <div class="user-info"> <span class="img"> <img src="//gtms03.alicdn.com/tps/i3/TB1yeWeIFXXXXX5XFXXuAZJYXXX-210-210.png_80x80.jpg"> </span> <span class="name">{{ item.nick }}</span> </div> <div class="rate-list"> <p style="font-size: .35rem;">
                        {{#  if(item.feedback == ''){ }}
                        暂无评论
                        {{#  }else{ }}
                        {{ item.feedback }}
                        {{# } }}
                    </p> </div> </li>
                    {{#  }); }}
                     </ul>
                    </div>
                </div>
            </div>
        </div>
        <%--<div id="sid" style="display:none;"></div>--%>
    </div>
    </script>
</div>
<div class="bg" style="display:none"></div>
<div id="ma">
    <img id="btn_img" style="width: 80%;margin-left: 15%;" src="${basePath}content/img/pc1.png" />
    <img id="btn_close" src="${basePath}content/img/x1.png" style="position: absolute;bottom: 0px;right: 0px;height: 30px;width: 30px;" />
    <div id="qian">
        ©店有客
    </div>
</div>

<div class="botton_d">
    <%--<div class="tao-code" style="width: 50%" onclick="showCode()">淘口令获取</div>--%>
    <div class="buy-now" style="width: 100%;" onclick="goBuy()">去淘宝购买</div>
</div>

<script src="${basePath}content/js/goods.js?v=1.0.5" type="text/javascript" charset="utf-8"></script>
</body>
</html>
