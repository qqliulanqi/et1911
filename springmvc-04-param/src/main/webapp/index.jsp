<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>测试数组传参</h2>
	<form action="${pageContext.request.contextPath}/complex/array" method="post">
		爱好:
		<input type="checkbox" name="hobby" value="踢球">踢球
		<input type="checkbox" name="hobby" value="听音乐">听音乐
		<input type="checkbox" name="hobby" value="看书">看书
		<br>
		<input type="submit" value="测试数组传参">
	</form>
	<h2>测试List传参</h2>
	<form action="${pageContext.request.contextPath}/complex/list" method="post">
		爱好:
		<input type="checkbox" name="hobbyList" value="踢球">踢球
		<input type="checkbox" name="hobbyList" value="听音乐">听音乐
		<input type="checkbox" name="hobbyList" value="看书">看书 
		<br>
		<input type="submit" value="测试数组传参">
	</form>
	<button id="testMap">测试Map传参</button>
	<button onclick="jsonToMap()">测试json传参</button>
	<button onclick="jsonToList()">测试json转List传参</button>
	<hr>
	<button onclick="jsonToBean()">测试json转Bean</button>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-3.4.1.js"></script>
	<script type="text/javascript">
		const path ="${pageContext.request.contextPath}"
		$(function() {
			$('#testMap').click(()=>{
				$.ajax({
					url:path+'/complex/map',
					type:'post',
					dataType:'text',
					data:"stuMap['id']=1&stuMap['name']=zhangsan",
					success:function(data){
						alert(data);
					}
				})
			})
		})
		function jsonToMap() {
			let obj={id:1,name:"et1911"}
			$.ajax({
				url:path+'/json/jsonToMap',
				type:'post',
				data:JSON.stringify(obj),
				dataType:'json',
				contentType:'application/json;charset=UTF-8',
				success:function(data){
					alert(data.msg);
				}
			})
		}
		
		/* function jsonToList() {
			let array={id:2,name:"et1910",age:22};
			let user = {id:1,name:"et1911",age:20};
			array.push(user)
			
			$.ajax({
				url:path+'/json/jsonToList',
				type:'post',
				data:JSON.stringify(array),
				dataType:'json',
				contentType:'application/json;charset=UTF-8',
				success:function(data){
					alert(data.code+"-"+data.msg);
				}
			})
		} */
		function jsonToBean() {
			let obj={id:1,name:"et1911",age:20,hobbyList:['看书','踢球'],stuMap:{id:2,score:200}}
			$.ajax({
				url:path+'/json/jsonToBean',
				type:'post',
				data:JSON.stringify(obj),
				dataType:'json',
				contentType:'application/json;charset=UTF-8',
				success:function(data){
					alert(data.code+":"+data.msg);
				}
			})
		}
	</script>
</body>
</html>
