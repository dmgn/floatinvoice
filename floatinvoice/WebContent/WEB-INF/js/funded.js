 angular.module('floatInvoiceListApp')
 .controller('FundedCtrl', ['$scope', '$http', '$routeParams', '$window',
  'fiService', '$timeout', '$mdSidenav', '$log', function($scope, $http, $routeParams, 
    $window, fiService, $timeout, $mdSidenav, $log){
      var acro = fiService.getAcronym();  
    $http.get('/floatinvoice/invoice/funded?acro='+acro)
       .success(function(data){
          $scope.invoices = data.list;
          $scope.sortField = 'totPoolAmt';
           console.log(data);
          });


  /*  function buildToggler(navID, poolRefId) {
      console.log(navID);
      return function() {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
            $log.debug("toggle " + navID + " is done");
          });
      }
    }*/

		 	/*$scope.displayInvoices = function (poolRefId){
        console.log(poolRefId);

        ModalService.showModal({
            templateUrl: '/floatinvoice/html/invoicePopUp.html',
            controller: 'InvoicePopUpCtrl'
        }).then(function(modal) {
          console.log(modal);
            modal.element.modal();
            modal.close.then(function(result) {
                //$scope.message = "You said " + result;
                console.log("closed");
            });
        });
				//var newWindowRef = $window.open('/floatinvoice/html/invoicePopUp.html', 'name', 'width=600,height=400');
        //var newWindowRootScope = newWindowRef.angular.element("#main").scope();

        //newWindowRootScope.$broadcast("INTER_WINDOW_DATA_TRANSFER", {"poolRefId":poolRefId});  
/*        var modalInstance = $modal.open({
          templateUrl: '/floatinvoice/html/invoicePopUp.html',
          controller: ModalInstanceCtrl,
          resolve: {
            mydata: function() {
                return "Loading...";
            }
          }
        });

        modalInstance.opened.then(
          function() {
            $scope.loadData(modalInstance);
          },
          function() {
            $log.info('Modal dismissed at: ' + new Date());
          }
        );

        $scope.loadData = function(aModalInstance) {
          $log.info("starts loading");
          $timeout(function() {
            $log.info("data loaded");
            aModalInstance.setMyData("data loaded...");
          }, 3000);
        };


			};*/

 /*     var ModalInstanceCtrl = function($scope, $modalInstance, mydata) {
        $scope.mydata = mydata;

        $modalInstance.setMyData = function(theData) {
          $scope.mydata = theData;
        };

        $scope.ok = function() {
          $modalInstance.close('close');
        };
        $scope.cancel = function() {
          $modalInstance.dismiss('cancel');
        };
      };*/



}])
  
.controller('RightCtrl', ['$http', '$scope', '$window', '$timeout', '$mdSidenav', '$log', 'fiService', 
  function ($http, $scope, $window, $timeout, $mdSidenav, $log, fiService) {
    var smeAcro = fiService.getAcronym();  

  $scope.toggleRight = function(poolRefId, navID) {
        $scope.user = "";
        $scope.userForm.$setPristine();
        $scope.showId = true;
        $scope.poolRefId = poolRefId;
        console.log("poolRefId: " + poolRefId + " navID: "+ navID + ", " + $scope.showId);
        $mdSidenav(navID)
          .toggle()
          .then(function () {
            $log.debug("toggle " + navID + " is done");
          });
      }   



  $scope.isOpenRight = function(){
      return $mdSidenav('right').isOpen();
   };

    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait, context) {
      var timer;
      return function debounced() {
        var context = $scope,
            args = Array.prototype.slice.call(arguments);
        $timeout.cancel(timer);
        timer = $timeout(function() {
          timer = undefined;
          func.apply(context, args);
        }, wait || 10);
      };
    }

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
    function buildDelayedToggler(navID) {
      return debounce(function() {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
            $log.debug("toggle " + navID + " is done");
          });
      }, 200);
    }


    $scope.close = function () {
      console.log("close" + $scope.showId);
      $mdSidenav('right').close()
        .then(function () {
          $log.debug("close RIGHT is done");
        });
        $scope.userForm.$setPristine();
    };

    $scope.submitForm = function() {
      // check to make sure the form is completely valid
      if ($scope.userForm.$valid) {
          $scope.user.acro = smeAcro;
          console.log($scope.poolRefId);
          $http({
              method:'POST',
              url:'/floatinvoice/bank/saveAccount',
              data:$scope.user,
              xhrFields: {
                  withCredentials: true
              },
              headers:{'Content-Type':'application/json'}
              }).then(function successCallback(response) {
                  $scope.showId = false;
                  console.log(response);
                }, function errorCallback(response) {
                  console.log(response);
            });
      }
    };

  }]);



/* .config(function($routeProvider){
    $routeProvider.when('/:',{
        templateUrl:'/floatinvoice/html/upload.html',
        controller:'UploadCtrl'
      })

 });*/

