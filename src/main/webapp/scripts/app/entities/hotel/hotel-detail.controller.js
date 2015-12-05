'use strict';

angular.module('testClimber2App')
    .controller('HotelDetailController', function ($scope, $rootScope, $stateParams, entity, Hotel, UserTmp) {
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
