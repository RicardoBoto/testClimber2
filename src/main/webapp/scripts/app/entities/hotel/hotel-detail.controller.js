'use strict';

angular.module('testClimber2App')
    .controller('HotelDetailController', function ($scope, $rootScope, $stateParams, entity, Hotel, UserDetail) {
        $scope.hotel = entity;
        $scope.load = function (id) {
            Hotel.get({id: id}, function(result) {
                $scope.hotel = result;
            });
        };
        var unsubscribe = $rootScope.$on('testClimber2App:hotelUpdate', function(event, result) {
            $scope.hotel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
