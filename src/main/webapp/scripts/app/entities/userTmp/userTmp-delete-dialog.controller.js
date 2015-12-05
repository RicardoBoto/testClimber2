'use strict';

angular.module('testClimber2App')
	.controller('UserTmpDeleteController', function($scope, $modalInstance, entity, UserTmp) {

        $scope.userTmp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserTmp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });