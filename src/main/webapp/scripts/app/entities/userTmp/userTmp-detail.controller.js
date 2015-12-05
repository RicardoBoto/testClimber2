'use strict';

angular.module('testClimber2App')
    .controller('UserTmpDetailController', function ($scope, $rootScope, $stateParams, entity, UserTmp, Hotel, User) {
        $scope.userTmp = entity;
        $scope.load = function (id) {
            UserTmp.get({id: id}, function(result) {
                $scope.userTmp = result;
            });
        };
        var unsubscribe = $rootScope.$on('testClimber2App:userTmpUpdate', function(event, result) {
            $scope.userTmp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
