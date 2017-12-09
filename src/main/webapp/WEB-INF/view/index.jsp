<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    	<% response.sendRedirect("app/"); %>
        <h1>Hello World!</h1>
    </body>
</html> --%>
<html lang="en-US">
<style>
div {
  transition: all linear 0.5s;
  background-color: lightblue;
  height: 100px;
}

.ng-hide {
  height: 0;
}

</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular-animate.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/vaadinproject/resources/js/test.js"></script>
<link href="/vaadinproject/resources/css/bootstrap.css" rel="stylesheet" />	
</head>
<body ng-app="vaadinproject" ng-controller="ctrl">
	<div>
		<p class="test">
			Name : <input type="text" ng-model="name">
		</p>
		<h1>Hello {{name}}</h1>
		<ul>
			<li ng-repeat="item in items | orderBy:'username'">{{
				item.username + ', ' + item.role.roleName }}</li>
		</ul>
	</div>
	<h1>Mobile Hello World!</h1>
	<ul>
		<li ng-repeat="x in names | orderBy:'country'">{{ x.name + ', ' +
			x.country }}</li>
	</ul>
	<h1 class="test">name-country.....</h1>
	<div>
		<p>
			Select User : <select ng-model="user">
				<option ng-repeat="item in items | orderBy:'username'"
					value="{{item}}">{{item.username}}</option>
			</select>
		</p>
		<h1>{{user}}</h1>
	</div>
	<div>
		<h1 ng-mousemove="mousemove($event)">mouse move</h1>
		<p>Coordinates: {{x + ', ' + y}}</p>
		<p>Screen WH: {{width + ', ' + height}}</p>
	</div>
	<div>
		<h1>Post/Save: {{postCust}}</h1>
	</div>
	<div>
		<h1>Posts/Saves: {{postsCust}}</h1>
	</div>
	
	<h1>Hide the DIV: <input type="checkbox" ng-model="myCheck"></h1>
	<div ng-hide="myCheck"></div>
</body>

<script type="text/javascript">
	var app = angular.module("vaadinproject", ['ngAnimate']);
	
	app.controller("ctrl", function($scope, $location, $http, $window) {

		$scope.name = "Mongheng";
		var url = $location.absUrl();

		$http.get(url + "items").then(function(response) {
			$scope.items = response.data;
		}, function(reason) {
			$scope.postResultMessage = "Error Status: " + reason.statusText;
		}, function(value) {

		});
		$scope.width = $window.innerWidth;
		$scope.height = $window.innerHeight;

		$scope.mousemove = function(cor) {
			$scope.x = cor.clientX;
			$scope.y = cor.clientY;
		};

		var user = {
			"userid" : "27abfc1c-a909-4df7-b83f-e8c80559c9b2",
			"username" : "sochenda",
			"password" : "12345",
			"telephone" : "012345678",
			"email" : "sochenda@gmail.com",
			"image" : null,
			"role" : {
				"roleID" : "eb8cb55c-604f-4408-8bc7-c2d173586189",
				"roleName" : "User"
			}
		};
		var config = {
			headers : {
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}
		$http.post(url + "save", user, config).then(function(value) {
			$scope.postDivAvailable = true;
			$scope.postCust = value.data;
		}, function(reason) {

		}, function(value) {

		});

		var users = [ {
			"userid" : "27abfc1c-a909-4df7-b83f-e8c80559c9b2",
			"username" : "sochenda",
			"password" : "12345",
			"telephone" : "012345678",
			"email" : "sochenda@gmail.com",
			"image" : null,
			"role" : {
				"roleID" : "eb8cb55c-604f-4408-8bc7-c2d173586189",
				"roleName" : "User"
			}
		}, {
			"userid" : "d500eb10-f01e-4a6c-afaf-03805cbed406",
			"username" : "hellen",
			"password" : "12345",
			"telephone" : "092345678",
			"email" : "hellen@gmail.com",
			"image" : null,
			"role" : {
				"roleID" : "c24266d6-28ee-4a3d-859a-2cb514c46115",
				"roleName" : "Product Manager"
			}
		}, {
			"userid" : "989d0da5-e7f8-479f-92a0-190365b1f719",
			"username" : "sok",
			"password" : "12345",
			"telephone" : "015345678",
			"email" : "sok@gmail.com",
			"image" : null,
			"role" : {
				"roleID" : "603d38c3-a902-45d0-a640-ceba6f1cfc88",
				"roleName" : "Technical"
			}
		} ];
		
		$http.post(url + "saves", users, config).then(function(value) {
			$scope.postDivAvailable = true;
			$scope.postsCust = value.data;
		}, function(reason) {

		}, function(value) {

		});
	});
</script>
</html>
