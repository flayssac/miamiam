'use strict';

angular.module('appDev', [
    'app.interceptor.dev',
    'app',
    'app.mock',
    'app.settings'
]).config(function ($httpProvider, $stateProvider, settings) {
    settings.restEndpoint = 'http://localhost:8080/miamiam-back/ws/v1/';

	var devUser = 'miamiam:sqlitlse';

    /** Routes */
    $stateProvider
        .state('fake-auth', {
            url: '/auth',
            templateUrl: 'src/main/fake-auth/view.html',
            onEnter: function ($rootScope) {
                $rootScope.ready = true;
            },
            controller: function ($scope, $state, $httpBackend, $resource, Me) {
                $scope.roles = Me.const.ROLES;
                $scope.loadMe = function () {
                    sessionStorage.setItem('authorization', 'Basic ' + btoa(devUser));
                    if ($('body').find('icon-logout').size() < 1) {
                        var $logout = $('<a href><span class="icon-logout icon-x3 pull-right"></span></a>');
                        $logout.click(function () {
                            sessionStorage.removeItem('authorization');
                            $state.go('fake-auth');
                        });
                        $('body').prepend($logout);
                    }
                    $state.go('main');
                };
            }
        });
    /** END Routes */

    /** Interceptor **/
    $httpProvider.interceptors.push('appInterceptorDev');
    /** END OF Interceptor **/
}).run(
    function (mock) {

        mock.else();
        /** END OF Mocked Resources */


    });

