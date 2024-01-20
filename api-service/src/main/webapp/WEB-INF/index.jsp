<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Devices World</title>
</head>
<body>

<script>
function redirectBrand(event) {
    window.location.href = '?brandName=' + event.target.getAttribute('value');
};
function redirectModel(event) {
    window.location.href = '?brandName=' + event.target.getAttribute('name')
    + '&modelName=' + event.target.getAttribute('value');
};
</script>

<c:forEach var="brand" items="${brands}">
    <button value="${brand}" onclick="redirectBrand(event)">${brand}</button>
</c:forEach>

<br>
<br>

<c:forEach var="model" items="${models}">
    <button value="${model}" name="${brandSelected}" onclick="redirectModel(event)">${model}</button>
</c:forEach>

<br>
<br>

<table>
    <tr>
        <th>Category</th>
        <th>Property</th>
        <th>Values</th>
    </tr>
    <c:forEach items="${itemDetails}" var="item" varStatus="status">
        <tr>
            <td>${item.category}</td>
            <td>${item.property}</td>
            <td>${item.values}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>