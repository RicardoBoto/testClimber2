 'use strict';

angular.module('testClimber2App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-testClimber2App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-testClimber2App-params')});
                }
                return response;
            }
        };
    });
