'use strict';

angular.module('testClimber2App')
    .factory('UserTmp', function ($resource, DateUtils) {
        return $resource('api/userTmps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
