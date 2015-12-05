'use strict';

angular.module('testClimber2App')
    .controller('HotelController', function ($scope, $state, $modal, Hotel) {
      
        $scope.hotels = [];
        $scope.loadAll = function() {
            Hotel.query(function(result) {
               $scope.hotels = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hotel = {
                name: null,
                code: null,
                id: null
            };
        };
    });
