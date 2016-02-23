<!DOCTYPE html>
<html ng-app="floatInvoiceListApp" >
<head>
  <title>Float Invoice - Accelerate Cashflow</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!--     <link rel="stylesheet" href="css/bootstrap.min.css" />
 -->
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

  <script src="js/dirPagination.js"></script>

  <script src="js/angular-route.min.js"></script>
  <script src="js/app.js"></script>
  <script src="js/funded.js"></script>
  <script src="js/paid.js"></script>
  <script src="js/pending.js"></script>
  <script src="js/unpaid.js"></script>
  <script src="js/upload.js"></script>
   <script>
    floatInvoiceListApp.service('fiService', function(){
      this.getAcronym = function(){
        return "${acronym}";
      };
    });
    floatInvoiceListApp.controller('TabsCtrl', ['$scope', '$location', 
      function ($scope, $location) {

      $scope.tabs = [
          { link : '#/upload', label : 'Upload' },
          { link : '#/unpaid', label : 'Unpaid' },
          { link : '#/pending', label : 'Pending' },
          { link : '#/funded', label : 'Funded' },
          { link : '#/paid', label : 'Paid' }
        ];     
      //console.log("hash " + $location.url());
      //$scope.selectedTab = $scope.tabs[0];   
      var index = -1;
      var tabList =  $scope.tabs;
      for (var i=0; i<tabList.length; i++){
        if( tabList[i].link == '#'+$location.url() ) {
            index = i;
            break;
        }
      }
      $scope.selectedTab = $scope.tabs[index];

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
    }]);

  </script>
</head>
<body>

<div class="container">
    <br/>
    <br/>
    <div class="row">  
      <div class="col-sm-3">
          <img src = "img/logo.jpg" height=65/>                 
      </div>
       <div class="col-sm-3">
        <div class="column well well-sm" >
          <div><strong>Most Recent Funding:</strong></div>
          <div>&#x20B9; 1000
          <span style="float:right" class="glyphicon glyphicon-calendar">21-02-2016</span>
          </div>
        </div>
      </div>
      <div class="col-sm-3">
        <div class="column  well well-sm">
          <div><strong>Next Invoice Due:</strong></div>
           <div>&#x20B9; 1000
          <span style="float:right" class="glyphicon glyphicon-calendar">21-02-2016</span>
          </div>
        </div>
      </div>
      <div class="col-sm-3 well well-sm">
          <label> Welcome ${acronym} </label>
          <span style="float:right">
            <a href="/floatinvoice/logout" class="btn btn-primary" title="Logout" data-toggle="tooltip" >
            <span class="glyphicon glyphicon-log-out"></span></a>
          </span>
          <div>
            <strong> Credit Rating: </strong>
            <!-- <span class="label label-danger">Poor</span>
            <span class="label label-warning">Good</span>
             -->
             <span id="rating" class="label label-success">Excellent</span>
          </div>
      </div>
    </div>
    <ul class="nav nav-tabs nav-pills nav-justified" ng-controller="TabsCtrl">
      <li ng-class="tabClass(tab)" ng-repeat="tab in tabs" tab="tab"><a href="{{tab.link}}" ng-click="setSelectedTab(tab)">{{tab.label}}</a></li>
    </ul>
    <br/>
    <div ng-view></div>
  </div>
</div>
    
</body>
</html>
