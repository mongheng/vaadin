<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" ng-app="vaadinproject" ng-controller="ctrl">
    <head>
    	<spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    	<link href="${bootstrap}" rel="stylesheet"/>
    	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
    	<script src="<c:url value="/resources/script/country.js"/>"></script>
    	<script src="/resources/script/country.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    	<div>
	    	<p>Name : <input type="text" ng-model="name"></p>
	  		<h1>Hello {{name}}</h1>
	  		<script type="text/javascript">
        		var app = angular.module("vaadinproject",[]);
        		app.controller("ctrl", function($scope, $location, $http) {
        			
        			$scope.name = "Mongheng";
        			var url = $location.absUrl() + "items";
        		
        			$http.get(url).then(function(response) {
        				$scope.items = response.data;
        			}, function(reason) {
        				$scope.postResultMessage = "Error Status: " +  reason.statusText;
        			}, function(value) {
        				
        			});
        		});
        	</script>
        	<ul>
	  			<li ng-repeat="item in items | orderBy:'username'">
	    			{{ item.username + ', ' + item.role.roleName }}
	  			</li>
			</ul>
    	</div>
    	<%-- <%response.sendRedirect("app/"); %> --%>
        <h1>Mobile Hello World!</h1>
        <ul>
	  		<li ng-repeat="x in names | orderBy:'country'">
	    		{{ x.name + ', ' + x.country }}
	  		</li>
		</ul>
		<h1>name-country.....</h1>
    </body>
</html>
