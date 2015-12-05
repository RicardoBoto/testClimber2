'use strict';

angular.module('testClimber2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hotel', {
                parent: 'entity',
                url: '/hotels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'testClimber2App.hotel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotel/hotels.html',
                        controller: 'HotelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hotel.detail', {
                parent: 'entity',
                url: '/hotel/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'testClimber2App.hotel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hotel/hotel-detail.html',
                        controller: 'HotelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hotel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Hotel', function($stateParams, Hotel) {
                        return Hotel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hotel.new', {
                parent: 'hotel',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-dialog.html',
                        controller: 'HotelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    code: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('hotel');
                    })
                }]
            })
            .state('hotel.edit', {
                parent: 'hotel',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-dialog.html',
                        controller: 'HotelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Hotel', function(Hotel) {
                                return Hotel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hotel.delete', {
                parent: 'hotel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hotel/hotel-delete-dialog.html',
                        controller: 'HotelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Hotel', function(Hotel) {
                                return Hotel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hotel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
