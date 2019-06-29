<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="keywords" content="${title}">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no,viewport-fit=cover">
    <meta name="referrer" content="never">
    <meta charset="utf-8">
    <title>图文预览</title>
    <style type="text/css">
        body{margin:0px; padding:0px;line-height: 1.6;
            font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;
            color: #222; font-size: 16px;}
        div#page{margin:15px; overflow: hidden}
        div#page>h1{font-size:24px; margin:5px 0}
        div#page>h3{font-size:16px; color:#888; font-weight:normal; margin:10px 0;}
        div#page-main{overflow:hidden;}
    </style>
</head>
<body>
<div id="page">
<h1>${title}</h1>
<h3>${date}<span style="margin-left:10px">${author}</span></h3>
<div id="page-main">
    ${content}
</div>
</div>
</body>
</html>
