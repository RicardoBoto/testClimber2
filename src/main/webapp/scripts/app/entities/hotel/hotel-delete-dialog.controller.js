'use strict';

angular.module('testClimber2App')
	.controller('HotelDeleteController', function($scope, $modalInstance, entity, Hotel) {

        $scope.hotel = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Hotel.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });