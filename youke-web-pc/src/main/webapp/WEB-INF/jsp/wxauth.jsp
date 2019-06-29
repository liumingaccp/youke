<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公众号授权结果</title>
    <style type="text/css">
        body{font: 14px/1.6 "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","\9ED1\4F53",Arial,sans-serif;min-width: 1200px;}
        h1{text-align: center; font-size:40px; margin:100px auto;}
        div.bottom{text-align: center;}
        div.bottom a{display: inline-block; background-color:#63b504; color:#fff; font-size:18px; width:150px; line-height:45px; text-align: center; text-decoration: none; border-radius:5px;}
    </style>
    <script type="text/javascript">
        function closePage(){
            var userAgent = navigator.userAgent;
            if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Presto") != -1) {
                window.location.replace("about:blank");
            } else {
                window.location.href="about:blank";
                window.close();
            }
        }
    </script>
</head>
<body>
<h1>${message}</h1>
<div class="bottom"><a onclick="closePage()">关闭窗口</a></div>
</body>
</html>
