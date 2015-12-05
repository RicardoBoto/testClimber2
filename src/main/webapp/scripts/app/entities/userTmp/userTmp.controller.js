'use strict';

angular.module('testClimber2App')
    .controller('UserTmpController', function ($scope, $state, $modal, UserTmp) {
      
        $scope.userTmps = [];
        $scope.loadAll = function() {
            UserTmp.query(function(result) {
               $scope.userTmps = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userTmp = {
                id: null
            };
        };
    });
