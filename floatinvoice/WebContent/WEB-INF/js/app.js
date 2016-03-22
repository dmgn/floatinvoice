var floatInvoiceListApp = angular.module('floatInvoiceListApp',
  ['angularUtils.directives.dirPagination','ngRoute', 'route-segment', 'view-segment', 'angularModalService',
  'ngMaterial', 'ngMessages']);
floatInvoiceListApp.config(function ($locationProvider, $routeProvider, $routeSegmentProvider) {
    $routeSegmentProvider.
    when('/homePg','s1').
    when('/reports','s2').
    when('/payments','s3').
    when('/s1/upload','s1.upload').
    when('/s1/unpaid','s1.unpaid').
    when('/s1/pending','s1.pending').
    when('/s1/funded','s1.funded').
    when('/s1/paid','s1.paid');

    $routeSegmentProvider
    .segment('s1', {
        templateUrl: '/floatinvoice/html/homePage.html',
        controller: 'JustifiedTabsCtrl'
    });

    $routeSegmentProvider
    .segment('s2', {
        templateUrl: '/floatinvoice/html/paid.html',
        controller: 'JustifiedTabsCtrl'
    });

    $routeSegmentProvider
    .segment('s3', {
        templateUrl: '/floatinvoice/html/paid.html',
        controller: 'JustifiedTabsCtrl'
    });

    $routeSegmentProvider
      .within('s1')
      .segment('upload', {
        templateUrl:'/floatinvoice/html/upload.html',
        controller:'UploadCtrl'
    });

    $routeSegmentProvider
      .within('s1')
      .segment('unpaid', {
        templateUrl:'/floatinvoice/html/unpaid.html',
        controller:'UnpaidCtrl'
    });  
     
    $routeSegmentProvider
      .within('s1')
      .segment('pending', {
        templateUrl:'/floatinvoice/html/pending.html',
        controller:'PendingCtrl'
    });

    $routeSegmentProvider
      .within('s1')
      .segment('funded', {
        templateUrl:'/floatinvoice/html/funded.html',
        controller:'FundedCtrl'
    });

    $routeSegmentProvider
      .within('s1')
      .segment('paid', {
        templateUrl:'/floatinvoice/html/paid.html',
        controller:'PaidCtrl'
    });
});


/* var floatInvoiceListApp = angular.module('floatInvoiceListApp',
  ['angularUtils.directives.dirPagination','ngRoute', 'angularModalService',
  'ngMaterial', 'ngMessages']);

 /*       floatInvoiceListApp.config(function($mdThemingProvider) {
          $mdThemingProvider.theme('default')
            .primaryPalette('light-blue')
            .accentPalette('green');
        });
 
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
      .when('/homePg',{
        templateUrl:'/floatinvoice/html/homePage.html',
        controller:'JustifiedTabsCtrl'
      })
    


    });*/