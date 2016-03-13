 var floatInvoiceListApp = angular.module('floatInvoiceListApp',
  ['angularUtils.directives.dirPagination','ngRoute', 'angularModalService',
  'ngMaterial', 'ngMessages']);

 /*       floatInvoiceListApp.config(function($mdThemingProvider) {
          $mdThemingProvider.theme('default')
            .primaryPalette('light-blue')
            .accentPalette('green');
        });*/
 
    floatInvoiceListApp.config( function($locationProvider, $routeProvider){
      $routeProvider.when('/upload',{
        templateUrl:'/floatinvoice/html/upload.html',
        controller:'UploadCtrl'
      })
      .when('/unpaid',{
        templateUrl:'/floatinvoice/html/unpaid.html',
        controller:'UnpaidCtrl'
      })
      .when('/pending',{
        templateUrl:'/floatinvoice/html/pending.html',
        controller:'PendingCtrl'
      })
      .when('/funded',{
        templateUrl:'/floatinvoice/html/funded.html',
        controller:'FundedCtrl'
      })
      .when('/paid',{
        templateUrl:'/floatinvoice/html/paid.html',
        controller:'PaidCtrl'
      })
    
     /* .when('/popup/:poolRefId',{
        templateUrl:'/floatinvoice/html/invoicePopUp.html',
        controller:'PaidCtrl'
      })*/

    });