'use strict';

angular.module('testClimber2App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userTmp', {
                parent: 'entity',
                url: '/userTmps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'testClimber2App.userTmp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userTmp/userTmps.html',
                        controller: 'UserTmpController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userTmp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userTmp.detail', {
                parent: 'entity',
                url: '/userTmp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'testClimber2App.userTmp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userTmp/userTmp-detail.html',
                        controller: 'UserTmpDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userTmp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'UserTmp', function($stateParams, UserTmp) {
                        return UserTmp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userTmp.new', {
                parent: 'userTmp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userTmp/userTmp-dialog.html',
                        controller: 'UserTmpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userTmp', null, { reload: true });
                    }, function() {
                        $state.go('userTmp');
                    })
                }]
            })
            .state('userTmp.edit', {
                parent: 'userTmp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userTmp/userTmp-dialog.html',
                        controller: 'UserTmpDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserTmp', function(UserTmp) {
                                return UserTmp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userTmp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userTmp.delete', {
                parent: 'userTmp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/userTmp/userTmp-delete-dialog.html',
                        controller: 'UserTmpDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserTmp', function(UserTmp) {
                                return UserTmp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userTmp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
