 angular.module('finfloatInvoiceListApp')
 .controller('FinProfileTabsCtrl', ['$scope', '$location', 
      function ($scope, $location) {

      $scope.tabs = [
          { link : '#/t2/dashbd', label : 'Dashboard' },
          { link : '#/t2/directorInfo', label : 'Directors' },
          { link : '#/t2/financing', label : 'Metrics' },
          { link : '#/t2/compInfo', label : 'Cash Flows' },
          { link : '#/t2/trends', label : 'Industry Trends' }
        ];  
      var index = -1;
      var tabList =  $scope.tabs;
      for (var i=0; i<tabList.length; i++){
        if( tabList[i].link == '#'+$location.url() ) {
            index = i;
            break;
        }
      }
      $scope.selectedTab = $scope.tabs[index];
      $scope.setSelectedTab = function(tab, alignment) {
        $scope.selectedTab = tab;
      }
      $scope.tabClass = function(tab, alignment) {
        if ($scope.selectedTab == tab) {
          return "active";
        } else {
          return "";
        }
      }
    }]);