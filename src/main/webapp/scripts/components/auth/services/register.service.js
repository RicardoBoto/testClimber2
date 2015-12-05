'use strict';

angular.module('testClimber2App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


