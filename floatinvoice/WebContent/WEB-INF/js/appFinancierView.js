 var finfloatInvoiceListApp = angular.module('finfloatInvoiceListApp',
  ['angularUtils.directives.dirPagination','ngRoute', 'route-segment', 'view-segment', 'angularModalService',
  'ngMaterial', 'ngMessages']);

 /*       floatInvoiceListApp.config(function($mdThemingProvider) {
          $mdThemingProvider.theme('default')
            .primaryPalette('light-blue')
            .accentPalette('green');
        });*/
 
    finfloatInvoiceListApp.config(
      function($locationProvider, $routeProvider, $routeSegmentProvider){
      $routeSegmentProvider.
        when('/finhomePg','t1').
        when('/t1/list','t1.list').
        when('/t1/approve','t1.approve').        
        when('/t1/funded','t1.funded').        
        when('/t1/repaid','t1.repaid').        
        when('/finprofile','t2').
        when('/t2/dashbd','t2.dashbd').        
        when('/t2/compInfo','t2.compInfo').        
        when('/t2/directorInfo','t2.directorInfo');

      $routeSegmentProvider
      .segment('t1', {
        templateUrl: '/floatinvoice/html/financierHomePg.html',
        controller: 'FinJustifiedTabsCtrl'
      });

      $routeSegmentProvider
      .segment('t2', {
        templateUrl: '/floatinvoice/html/finProfileView.html',
        controller: 'FinProfileTabsCtrl'
      });


    $routeSegmentProvider
      .within('t1')
      .segment('list', {
        templateUrl:'/floatinvoice/html/finInvoiceListView.html',
        controller:'InvoiceFinViewCtrl'
    });

    $routeSegmentProvider
      .within('t1')
      .segment('approve', {
        templateUrl:'/floatinvoice/html/finApprovalQueue.html',
        controller:'FinApprovalViewCtrl'
    });

    $routeSegmentProvider
      .within('t1')
      .segment('funded', {
        templateUrl:'/floatinvoice/html/finFundedInvoiceView.html',
        controller:'InvoiceLoanViewCtrl'
    });

    $routeSegmentProvider
      .within('t1')
      .segment('repaid', {
        templateUrl:'/floatinvoice/html/finRePaidInvoiceView.html',
        controller:'InvoiceFinViewCtrl'
    });    

    $routeSegmentProvider
      .within('t2')
      .segment('dashbd', {
        templateUrl:'/floatinvoice/html/borrowerDashboard.html',
        controller:'PaidCtrl'
    });

    $routeSegmentProvider
      .within('t2')
      .segment('compInfo', {
        templateUrl:'/floatinvoice/html/companyInfo.html',
        controller:'PaidCtrl'
    });
    
    $routeSegmentProvider
      .within('t2')
      .segment('directorInfo', {
        templateUrl:'/floatinvoice/html/directorInfo.html',
        controller:'PaidCtrl'
    }); 

      
    });