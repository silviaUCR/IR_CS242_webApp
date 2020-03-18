app.controller('ngSearch', function ($scope, $http) {
    $scope.searchString = null;
    $scope.response = null;
    $scope.obj = null;

    //$scope.json = '{"index":"m","query":"Kobe Basketball","result":"goes into here"}';
    //obj = JSON.parse($scope.json);
    //$scope.response = obj.result;

    $scope.getDataLucene = function (searchString) {

        $scope.visible = 'Lucene: '+searchString; //Show user what he last searched
        console.log(searchString);

        $http({
            url: '/api/query',
            method: "GET",
            params: {index: "l", query: searchString}
         }).then(function success(response) {
            myResponse = response.data;
            $scope.response = myResponse.result;
         });
    };

    $scope.getDataHadoop = function (searchString) {
        $scope.visible = 'Hadoop: '+searchString; //Show user what he last searched
        console.log(searchString);

        $http({
            url: '/api/query',
            method: "GET",
            params: {index: "m", query: searchString}
         }).then(function success(response) {
            myResponse = response.data;
            $scope.response = myResponse.urllist;
         });
    };
});
