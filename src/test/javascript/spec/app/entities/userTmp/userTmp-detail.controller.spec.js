'use strict';

describe('UserTmp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockUserTmp, MockHotel, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockUserTmp = jasmine.createSpy('MockUserTmp');
        MockHotel = jasmine.createSpy('MockHotel');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'UserTmp': MockUserTmp,
            'Hotel': MockHotel,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("UserTmpDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'testClimber2App:userTmpUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
