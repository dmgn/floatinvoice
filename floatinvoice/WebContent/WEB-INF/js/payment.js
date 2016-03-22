 angular.module('floatInvoiceListApp')
 .controller('PaymentCtrl', ['$scope', '$http', '$routeParams','fiService',
 			function($scope, $http, $routeParams, fiService){
 				var acro = fiService.getAcronym();	
				$http.get('/floatinvoice/bank/viewActiveLoans?acro='+acro)
		 		.success(function(data){
    			$scope.loans = data.list;
    			$scope.sortField = 'loanId';
   				$scope.refresh = function(refId){
   					console.log(refId);
					var index = -1;
					var loansList =  $scope.loans;
					for (var i=0; i<loansList.length; i++){
						if( loansList[i].refId == refId ) {
								index = i;
								break;
						}
					}
					$scope.loansList.splice(index, 1);
				};
   				

    			});
		}]);