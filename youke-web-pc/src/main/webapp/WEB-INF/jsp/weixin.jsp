<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String basePath ="https://"+request.getServerName()+request.getContextPath()+"/";%>
<html>
<head>
    <title>微信公众号授权</title>
</head>
<body>
<script>
    location.href="<%=basePath%>weixin/${dykId}/goauth";
</script>
</body>
</html>
