'use strict';

angular.module('testClimber2App').controller('UserTmpDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'UserTmp', 'Hotel', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, UserTmp, Hotel, User) {

        $scope.userTmp = entity;
        $scope.hotels = Hotel.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            UserTmp.get({id : id}, function(result) {
                $scope.userTmp = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('testClimber2App:userTmpUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userTmp.id != null) {
                UserTmp.update($scope.userTmp, onSaveSuccess, onSaveError);
            } else {
                UserTmp.save($scope.userTmp, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
