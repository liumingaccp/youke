function taobao_app_index(shopUrl, shop_type, small_shop_type, tbk_url) {
  var ua = navigator.userAgent.toLowerCase();
  ua = ua.toLowerCase();
  if (is_weixin(ua)) {
    //微信中
    return 1;
  } else {
    var os_type = get_os_type();
    //非微信中
    if (os_type != "android") {
      $("body").html("<div style='color:#000000;display: block;font-size: 22px;height: 1000px;margin-left:10px; margin-top:50px;text-align:center;'>正在跳转.....</div> ");
    }
    if (tbk_url != "") {
      shop_type = "tao_bao_ke";
      small_shop_type = "tao_bao_ke";
      shopUrl = tbk_url;
    }

    url = url(shopUrl, shop_type, small_shop_type);

    if (url != false) {
      if (os_type == "iPhone_ios_9") {
        openIphoneApp_ios_9(url);
      } else if (os_type == "android") {
        return openApp_android(url);
      } else if (os_type == "iPhone") {
        openApp_ios(url);
      } else {
        window.location = url;
      }
    }

  }
}

function get_os_type() {
    var ua = navigator.userAgent.toLowerCase();
    ua = ua.toLowerCase();
    var os_type = 'android';
    if (ua.indexOf("iphone") != -1) {

        if (ua.indexOf("iphone os 9") != -1 || ua.indexOf("iphone os 10") != -1) {
            os_type = 'iPhone_ios_9';
        } else {
            os_type = 'iPhone';
        }
    }
    return os_type;
}

function is_weixin(ua) {

  if (ua.indexOf("micromessenger") != -1 || ua.indexOf("qiange") != -1) {
    return true;
  } else {
    return false;
  }

}

function GetQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) return r[2];
  return null;
}

function url(shopUrl, shop_type, small_shop_type) {
  if (small_shop_type == "cm_details") {
    //单品
    if (shop_type == 'taobao') {
      shopUrl = "https://h5.m.taobao.com/awp/core/detail.htm?id=" + shopUrl;
      return shopUrl;
    } else if (shop_type == 'tmall' || shop_type == 'alitrip' || shop_type == 'ju_hf') {
      shopUrl = "https://detail.m.tmall.com/item.htm?id=" + shopUrl;
      return shopUrl;
    }
  } else if (small_shop_type == "tao_bao_ke") {
    return shopUrl;
  }
  return false;

}

function openIphoneApp_ios_9(url) {

  var tb_url = url.replace("http://", "").replace("https://", "");
  window.location = "taobao://" + tb_url;
  window.setTimeout(function() {
    window.location = url;
  }, 3000)
}

function openApp_android(url) {
  window.location.href = url;
  // var ua = navigator.userAgent.toLowerCase();
  // if (ua.match(/tb/i) == "tb") {
  //   window.location.replace(url);
  //   return 2;
  // }
  // var tb_url = url.replace("http://", "").replace("https://", "");
  // window.location = "taobao://" + tb_url;
  return 2;

}

function openApp_ios(url) {
  // 通过iframe的方式试图打开APP，如果能正常打开，会直接切换到APP，并自动阻止a标签的默认行为
  // 否则打开a标签的href链接
  var tb_url = url.replace("http://", "").replace("https://", "");
  var ifr = document.createElement('iframe');
  ifr.src = 'taobao://' + tb_url;
  ifr.style.display = 'none';
  document.body.appendChild(ifr);
  window.location = url;
}