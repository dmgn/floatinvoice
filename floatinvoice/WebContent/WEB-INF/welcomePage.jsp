<!DOCTYPE html>
<html ng-app="floatInvoiceListApp" id="main">
<head>
  <title>Float Invoice - Accelerate Cashflow</title>
  <meta charset="utf-8">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
       <link rel="stylesheet" href="css/angular-material.min.css">

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
  <script src="js/angular-animate.min.js"></script>
  <script src="js/angular-aria.min.js"></script>
  <script src="js/angular-messages.min.js"></script>
  <!-- Angular Material Library -->
  <script src="js/angular-material.min.js"></script>

<script src="https://rawgit.com/dwmkerr/angular-modal-service/master/dst/angular-modal-service.js"></script>

  <script src="js/dirPagination.js"></script>

  <script src="js/angular-route.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/angular-route-segment/1.4.0/angular-route-segment.min.js"></script>

  <script src="js/app.js"></script>
  <script src="js/funded.js"></script>
  <script src="js/paid.js"></script>
  <script src="js/pending.js"></script>
  <script src="js/unpaid.js"></script>
  <script src="js/upload.js"></script>
  <script src="js/homePage.js"></script>

  <script>
    floatInvoiceListApp.service('fiService', function(){
      this.getAcronym = function(){
        return "${acronym}";
      };
    });
    floatInvoiceListApp.controller('TabsCtrl', ['$scope', '$location', 
      function ($scope, $location) {

      /*$scope.tabs = [
          { link : '#/upload', label : 'Upload' },
          { link : '#/unpaid', label : 'Unpaid' },
          { link : '#/pending', label : 'Pending' },
          { link : '#/funded', label : 'Bids' },
          { link : '#/paid', label : 'Paid' }
        ]; */  
      $scope.vtabs = [
          { link : '#/homePg', label : 'Home' },
          { link : '#/payments', label : 'Manage Payments' },
          { link : '#/reports', label : 'Reports' },
          { link : '#/profile', label : 'Manage Profile' },
          
        ];

      //console.log("hash " + $location.url());
      $scope.vselectedTab = $scope.vtabs[0];   
      var vindex = -1;
      var vtabList =  $scope.vtabs;
      //var vtabList = $scope.vtabs;

      for (var i=0; i<vtabList.length; i++){
        if( vtabList[i].link == '#'+$location.url() ) {
            vindex = i;
            break;
        }
      }

    /*  for (var i=0; i<vtabList.length; i++){
        if( vtabList[i].link == '#'+$location.url() ) {
            vindex = i;
            break;
        }
      }*/
     /* if(index > 0){
        $scope.selectedTab = $scope.tabs[index];
      }*/
     
      $scope.vselectedTab = $scope.vtabs[vindex];
     

      $scope.vsetSelectedTab = function(tab, alignment) {
        $scope.vselectedTab = tab;
        
      }
      $scope.vtabClass = function(tab, alignment) {
       console.log("tabClass invoked");
        if ($scope.vselectedTab == tab) {
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
    <div class="row"> 
      <div class="col-sm-2">
        <ul class="nav nav-tabs nav-pills nav-stacked" ng-controller="TabsCtrl">
          <li ng-class="vtabClass(vtab, 'stacked')" ng-repeat="vtab in vtabs" tab="vtab"><a href="{{vtab.link}}" ng-click="vsetSelectedTab(vtab, 'stacked')">{{vtab.label}}</a></li>
        </ul>
      </div>
      <div class="col-sm-10">
       <!--  <ul class="nav nav-tabs nav-pills nav-justified" ng-controller="TabsCtrl">
          <li ng-class="tabClass(tab, 'justified')" ng-repeat="tab in tabs" tab="tab"><a href="{{tab.link}}" ng-click="setSelectedTab(tab, 'justified')">{{tab.label}}</a></li>
        </ul> -->
        <div app-view-segment="0"></div>
      </div>
    </div>
    <br/>
    
  </div>
</div>
    
</body>
</html>
