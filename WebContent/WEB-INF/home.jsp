<!DOCTYPE html>
<html ng-app="floatInvoiceListAppHomePg" >
<head>
  <title>Float Invoice - Accelerate Cashflow</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="css/bootstrap.min.css">
 <style>
    table, th , td  {
      border: 1px solid grey;
      border-collapse: collapse;
      padding: 5px;
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
    floatInvoiceListAppHomePg.controller('TabsStackedCtrl', function ($scope) {
      $scope.tabs = [
          { link : '#/invoices', label : 'Invoices' },
          { link : '#/profile', label : 'Profile' },
          { link : '#/reports', label : 'Reports' },
          { link : '#/customers', label : 'Customers' }
        ];     
      $scope.selectedTab = $scope.tabs[0];    
      $scope.setSelectedTab = function(tab) {
      $scope.selectedTab = tab;
      }
      $scope.tabClass = function(tab) {
        if ($scope.selectedTab == tab) {
          return "active";
        } else {
          return "";
        }
      }
    });
  </script>
</head>
<body>
<div class="container">
<!--     <div>  
      <img src = "img/logo.jpg" height=100/>
      <p> Welcome ${acronym}</p>
    </div> -->
    <ul class="nav nav-pills nav-stacked" ng-controller="TabsStackedCtrl">
      <li ng-class="tabClass(tab)" ng-repeat="tab in tabs" tab="tab"><a href="{{tab.link}}" ng-click="setSelectedTab(tab)">{{tab.label}}</a></li>
    </ul>
    <div ng-view></div>
  </div>
</div>    
</body>
</html>
