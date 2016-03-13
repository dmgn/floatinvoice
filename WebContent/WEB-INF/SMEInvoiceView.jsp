<!doctype html>
<html lang="en" ng-app="floatInvoiceListApp">
 <head>
  
  <script>
    var floatInvoiceListApp = angular.module('floatInvoiceListApp',[]);

    function loadData($scope, $http){
      $http.get('/floatinvoice/invoice/view?acro=COTIND').success(function(data){
        $scope.invoices = data.list;
        $scope.sortField = 'amount';
        console.log(data);
        $scope.collectNow = function (refId){
          console.log(" RefId is .." + refId);
          $scope.refId = refId;
          var data = JSON.stringify({
                refId : $scope.refId            
          });
          $http.post('/floatinvoice/invoice/credit', data);
        };
      });
    };

    
    floatInvoiceListApp.controller('InvoiceCtrl', loadData);
  </script>
 </head>
 <body>
  <table border="1" ng-controller="InvoiceCtrl" class="table table-striped">
    <tr>
      <th><a href="" ng-click="sortField = 'smeCtpy'">Buyer Name</td>
      <th><a href="" ng-click="sortField = 'amount'"> Amount</td>
      <th><a href="" ng-click="sortField = 'invoiceNo'">Invoice #</th>
      <th><a href="" ng-click="sortField = 'startDt'">Invoice Date</td>
      <th><a href="" ng-click="sortField = 'endDt'">Due Date</td>
      <th><a href="" ng-click="sortField = 'desc'">Description</td>
      <th><a href="" ng-click="sortField = 'action'">Action</td>    
    </tr>
    <tr>
        <th>
            <input type="text" ng-model="search.invoiceNo" placeholder="search for invoice number" class="input-sm form-control" />
        </th>
        <th>
            <input type="text" ng-model="search.amount" placeholder="search for amount" class="input-sm form-control"/>
        </th>
         <th>
            <input type="text" ng-model="search.desc" placeholder="search for description" class="input-sm form-control" />
        </th>
         <th>
            <input type="text" ng-model="search.startDt" placeholder="search for start date" class="input-sm form-control" />
        </th>
         <th>
            <input type="text" ng-model="search.endDt" placeholder="search for end date" class="input-sm form-control" />
        </th>
         <th>
            <input type="text" ng-model="search.smeCtpy" placeholder="search for smeCtpy" class="input-sm form-control"/>
        </th>
         <th width=>
        </th>
    </tr>
    <tr ng-repeat="item in invoices | filter:search | orderBy:sortField">
      <td>{{item.smeCtpy}}</td>
      <td>{{item.amount}}</td>
      <td>{{item.invoiceNo}}</td>
      <td>{{item.startDt}}</td>
      <td>{{item.endDt}}</td>
      <td>{{item.desc}}</td>
      <td >
      <button type="button" class="btn btn-primary" ng-click="collectNow(item.refId)">Collect Now</button>
      </td>
    </tr>
  </table>
 </body>
</html>
