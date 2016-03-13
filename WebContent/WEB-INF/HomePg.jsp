<!DOCTYPE html>
<html ng-app="floatInvoiceListApp">
<head>
<style>
table, th , td  {
  border: 1px solid grey;
  border-collapse: collapse;
  padding: 5px;
}
table tr:nth-child(odd)	{
  background-color: #f1f1f1;
}
table tr:nth-child(even) {
  background-color: #ffffff;
}
</style>
<meta charset="ISO-8859-1">
<title>Float Invoice</title>
	<script src="js/angular.min.js"></script>
	<script src="js/angular-route.min.js"></script>
	<script src="js/fileUpload.js"></script>
	<script src="js/funded.js"></script>
	<script src="js/paid.js"></script>
	<script src="js/pending.js"></script>
	<script src="js/unpaid.js"></script>
  <script>
  	var floatInvoiceListApp = angular.module('floatInvoiceListApp',['ngRoute']);
    floatInvoiceListApp.config( function($routeProvider){
    	$routeProvider.when('/upload',{
    		templateUrl:'html/upload.html'
    		controller:'UploadCtrl'
    	})
    	.when('/unpaid',{
    		templateUrl:'html/unpaid.html'
    		controller:'UnpaidCtrl'
    	})
    	.when('/pending',{
    		templateUrl:'html/pending.html'
    		controller:'PendingCtrl'
    	})
    	.when('/funded',{
    		templateUrl:'html/funded.html'
    		controller:'FundedCtrl'
    	})
    	.when('/paid',{
    		templateUrl:'html/paid.html'
    		controller:'PaidCtrl'
    	})
    });

  </script>
</head>
<body ng-controller="InvoiceCtrl">
	<div ng-view></div>
	<!-- <table border="1">
		<tr>
			<td>Amount</td>
			<td>Description</td>
			<td>Start Date</td>
			<td>End Date</td>
			<td>Counterparty Name</td>
			<td>Status</td>		
		</tr>
		<tr ng-repeat="item in invoices">
			<td>{{item.amount}}</td>
			<td>{{item.desc}}</td>
			<td>{{item.startDt}}</td>
			<td>{{item.endDt}}</td>
			<td>{{item.smeCtpy}}</td>
			<td>{{item.status}}</td>
		</tr>
	</table> -->


</body>
</html>