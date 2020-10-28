<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<table border="1">


    <tr>
        <td>ID</td>
        <td>name</td>
        <td>kana</td>
        <td>Dept.</td>
        <td>Is Admin</td>

    </tr>
    <c:forEach var="data" items="${datas}">
        <tr>
            <td>${data.userId}</td>
            <td>${data.fullName}</td>
            <td>${data.fullNameInKata}</td>
            <td>${data.department}</td>
            <td>${data.admin}</td>
        </tr>
    </c:forEach>
</table>