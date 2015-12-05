'use strict';

describe('Hotel Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockHotel, MockUserTmp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockHotel = jasmine.createSpy('MockHotel');
        MockUserTmp = jasmine.createSpy('MockUserTmp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Hotel': MockHotel,
            'UserTmp': MockUserTmp
        };
        createController = function() {
            $injector.get('$controller')("HotelDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'testClimber2App:hotelUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
