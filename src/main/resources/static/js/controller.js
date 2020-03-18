app.controller('ngSearch', function ($scope, $http) {
    $scope.searchString = null;
    $scope.response = null;
    $scope.obj = null;

    $scope.getDataLucene = function (searchString) {
        $scope.visible = 'Selected Index: Lucene'; //'//+searchString Show user what he last searched
        $http({
            method: 'GET',
            url: 'api/query',
            params: {index: "l", query: searchString}
         }).then(function(response) {
            $scope.data = (response.data);
        });
    };

    $scope.getDataHadoop = function (searchString) {
        $scope.visible = 'Selected Index: Hadoop MR'; //Show user what he last searched
        $http({
            method: 'GET',
            url: 'api/query',
            params: {index: "m", query: searchString}
         }).then(function(response){
             $scope.data = (response.data);
         });
    };
});