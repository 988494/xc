<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>     
    <meta charset="utf‐8">     
    <title>Hello World!</title>
</head>
<body>
---------------</br>
遍历list中的学习信息stus
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td><年龄/td>
        <td>金额</td>
        <td>出生日期</td>
    </tr>
    <#if stus??>
        <#list stus as stu>
         <td>${stu_index+1}</td>
        <td <#if stu.name=="小明">style="background: aqua" </#if>> ${stu.name}</td>
        <td>${stu.age}</td>
        <td <#if (stu.mondy > 300)>style="background: bisque" </#if>>${stu.mondy}</td>
        <td>${(stu.birthday?time)!''}</td>
        <td>${(stu.birthday?date)!''}</td>
        <td>${(stu.birthday?datetime)!''}</td>
        <td>${(stu.birthday?string('yyy-MM'))!''}</td>
        </#list>
       学生个数：${stus?size}</br>
    </#if>
</table>
-----------------------------------------------------</br>
map遍历stuMap<br/>
姓名:${(stuMap['stu1'].name)!''}<br/>
年龄:${(stuMap['stu1'].age)!''}</br>
姓名:${(stuMap.stu1.name)!''}<br/>
年龄:${(stuMap.stu1.age)!''}</br>
<#list stuMap?keys as k>
     ${(stuMap[k].name)!''}</br>
    ${(stuMap[k].age)!''}</br>
    ${(stuMap[k].mondy)!''}</br>
</#list>
-------------------------</br>
</body>
</html