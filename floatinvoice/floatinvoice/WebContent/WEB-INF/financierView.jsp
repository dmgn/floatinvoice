<!doctype html>
<html lang="en" ng-app="floatInvoiceFinView">
 <head>
   <title>Float Invoice - Accelerate Cashflow</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
   <style>
    table, th , td  {
      /*border: 1px solid grey;*/
      border-collapse: collapse;
      padding: 10px;
    }
    table tr:nth-child(odd) {
      background-color: #f1f1f1;
    }
    table tr:nth-child(even) {
      background-color: #ffffff;
    }
</style>
    <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/angular.min.js"></script>
  <script src="js/angular-route.min.js"></script>

  <script>
    var floatInvoiceFinView = angular.module('floatInvoiceFinView',[]);
    floatInvoiceFinView.controller('InvoiceFinViewCtrl', function ($scope, $http){
  	    $http.get('/floatinvoice/invoice/view?acro=${acronym}').success(function(data){
    		$scope.invoices = data.list;
        $scope.sortField = 'amount';


        $scope.refresh = function(refId){
            console.log(refId);
          var index = -1;
          var invoiceList =  $scope.invoices;
          for (var i=0; i<invoiceList.length; i++){
            if( invoiceList[i].refId == refId ) {
                index = i;
                break;
            }
          }
          $scope.invoices.splice(index, 1);
        };

        $scope.bidNow = function (refId){
          $scope.refId = refId;
          var data = JSON.stringify({
            refIds : [$scope.refId]           
          });
          $http.post('/floatinvoice/invoice/bid?acro=${acronym}', data).success($scope.refresh($scope.refId));
        };



	    });
     });
  </script>
 </head>
 <body>
 <div class="container">
<br/>

    <div class="row">  
      <div class="col-sm-7">
          <img src = "img/logo.jpg" height=70/>                 
      </div>

      <div class="col-sm-5">
          <span style="float:right">
          <label> Welcome ${acronym}</label>
            <a href="/floatinvoice/logout" class="btn btn-primary" title="Logout" data-toggle="tooltip" >
            <span class="glyphicon glyphicon-log-out"></span></a>
          </span>
          
      </div>
    </div>
  <table ng-controller="InvoiceFinViewCtrl" class="table table-striped">
		<tr>
		    <th><a href="" ng-click="sortField = 'amount'"> Amount</th>
			<!--<th><a href="" ng-click="sortField = 'desc'">Description</th>-->
			<th><a href="" ng-click="sortField = 'startDt'">Invoice Date</th>
			<th><a href="" ng-click="sortField = 'endDt'">Due Date</th>
            <th><a href="" ng-click="sortField = 'invoiceNo'">Invoice #</th>
            <th><a href="" ng-click="sortField = 'smeCtpy'">Supplier</th>
			<th><a href="" ng-click="sortField = 'smeCtpy'">Buyer</th>
			<th>Action</th>
		
		</tr>
    <tr>
        <th>
            <input type="text" ng-model="search.amount" placeholder="search for amount" class="input-sm form-control"/>
        </th>
<!--          <th>
            <input type="text" ng-model="search.desc" placeholder="search for description" class="input-sm form-control" />
        </th> -->
         <th>
            <input type="text" ng-model="search.startDt" placeholder="search for start date" class="input-sm form-control" />
        </th>
         <th>
            <input type="text" ng-model="search.endDt" placeholder="search for end date" class="input-sm form-control" />
        </th>
        <th>
            <input type="text" ng-model="search.invoiceNo" placeholder="search for invoice number" class="input-sm form-control" />
        </th>
        <th>
            <input type="text" ng-model="search.sme" placeholder="search for sme" class="input-sm form-control"/>
        </th>
         <th>
            <input type="text" ng-model="search.smeCtpy" placeholder="search for smeCtpy" class="input-sm form-control"/>
        </th>
         <th>
           
        </th>
    </tr>
		<tr ng-repeat="item in invoices | filter:search | orderBy:sortField">
			<td>{{item.amount}}</td>
			<td>{{item.startDt}}</td>
			<td>{{item.endDt}}</td>
      <td>{{item.invoiceNo}}</td>
      <td>{{item.sme}}</td>
			<td>{{item.smeCtpy}}</td>
			<td>  <button type="button" class="btn btn-primary" ng-click=bidNow(item.refId)>Bid Now</button>
</td>
		</tr>
	</table>
  </div>
 </body>
</html>
