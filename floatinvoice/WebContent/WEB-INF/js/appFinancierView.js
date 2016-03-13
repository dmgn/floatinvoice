 var finfloatInvoiceListApp = angular.module('finfloatInvoiceListApp',
  ['angularUtils.directives.dirPagination','ngRoute', 'angularModalService',
  'ngMaterial', 'ngMessages']);

 /*       floatInvoiceListApp.config(function($mdThemingProvider) {
          $mdThemingProvider.theme('default')
            .primaryPalette('light-blue')
            .accentPalette('green');
        });*/
 
    finfloatInvoiceListApp.config( function($locationProvider, $routeProvider){
      $routeProvider.when('/list',{
        templateUrl:'/floatinvoice/html/finInvoiceListView.html',
        controller:'InvoiceFinViewCtrl'
      })
       .when('/approve',{
        templateUrl:'/floatinvoice/html/finApprovalQueue.html',
        controller:'FinApprovalViewCtrl'
      })
      .when('/funded',{
        templateUrl:'/floatinvoice/html/finFundedInvoiceView.html',
        controller:'InvoiceLoanViewCtrl'
      })
      .when('/repaid',{
        templateUrl:'/floatinvoice/html/finRePaidInvoiceView.html',
        controller:'InvoiceFinViewCtrl'
      })
      
    });