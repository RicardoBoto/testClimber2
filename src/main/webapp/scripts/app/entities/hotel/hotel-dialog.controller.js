'use strict';

angular.module('testClimber2App').controller('HotelDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Hotel', 'UserTmp',
        function($scope, $stateParams, $modalInstance, entity, Hotel, UserTmp) {

        $scope.hotel = entity;
        $scope.usertmps = UserTmp.query();
        $scope.load = function(id) {
            Hotel.get({id : id}, function(result) {
                $scope.hotel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('testClimber2App:hotelUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hotel.id != null) {
                Hotel.update($scope.hotel, onSaveSuccess, onSaveError);
            } else {
                Hotel.save($scope.hotel, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
