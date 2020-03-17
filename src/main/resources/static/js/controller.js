app.controller('ngSearch', function ($scope, $http) {
    $scope.searchString = null;

    $scope.getDataLucene = function (searchString) {

        $scope.visible = 'Lucene: '+searchString; //Show user what he last searched
        console.log(searchString);
        var data = {
            index: 'l',
            name: searchString
        };

        //Convert to json object
//        var myJSON = JSON.stringify(data);

        $http({
            url: '/api/query',
            method: "GET",
            params: {index: "l", query: searchString}
         });
    };

    $scope.getDataHadoop = function (searchString) {
        $scope.visible = 'Hadoop: '+searchString; //Show user what he last searched
        console.log(searchString);
        var data = {
            index: 'm',
            name: searchString
        };

        $http({
            url: '/api/query',
            method: "GET",
            params: {index: "m", query: searchString}
         });
    };

});

//app.controller('ngSearch', function ($scope) {
//    $scope.clkcount = null;
//
//    $scope.getDetails = function (searchString) {
//        //console.log(searchString);
//        $scope.clkcount = searchString;
//
//        $http.put(url, data, config).then(function (response) {
//            // This function handles success
//            }, function (response) {
//                // this function handles error
//            });
//
//    }
//});

//var app = angular.module('putserviceApp', []);
//app.controller('putserviceCtrl', function ($scope, $http) {
//    // Simple Put request example:
//    var url = 'puturl', data = 'parameters',config='contenttype';
//    $http.put(url, data, config).then(function (response) {
//        // This function handles success
//    }, function (response) {
//        // this function handles error
//    });
//});


// Create the search filter
app.filter('searchFor', function(){
    // All filters must return a function. The first parameter
    // is the data that is to be filtered, and the second is an
    // argument that may be passed with a colon (searchFor:searchString)
    return function(dataArr, searchString){

        if(!searchString){
            return dataArr;
        }

        var result = [];
        searchString = searchString.toLowerCase();

        // Using the forEach helper method to loop through the array
        angular.forEach(dataArr, function(item){
            if(item.name.toLowerCase().indexOf(searchString) !== -1){
                result.push(item);
            }
        });
        return result;
    };
});

// The controller
function searchController($scope){
    // The data model. These items would normally be requested via AJAX,
    // but are hardcoded here for simplicity. See the next example for
    // tips on using AJAX.

    $scope.movies = [
        {name:"The Godfather",releaseYr:"1972"},
        {name:"Inception",releaseYr:"2010"},
        {name:"Titanic",releaseYr:"1997"},
        {name:"Thor:Ragnarok",releaseYr:"2017"},
        {name:"Forest Gump",releaseYr:"1994"},
        {name:"Star Wars",releaseYr:"1977"},
        {name:"Justice League",releaseYr:"2017"},
        {name:"The Dark Knight",releaseYr:"2008"},
        {name:"Avatar",releaseYr:"2009"},
        {name:"The Avengers",releaseYr:"2012"}
    ];}