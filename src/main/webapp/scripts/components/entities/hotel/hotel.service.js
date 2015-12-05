'use strict';

angular.module('testClimber2App')
    .factory('Hotel', function ($resource, DateUtils) {
        return $resource('api/hotels/:id', {}, {
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
