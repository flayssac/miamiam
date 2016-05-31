'use strict';

angular.module('appStub', [
    'app.mock',
    'app.interceptor.stub',
    'app',
    'app.i18n.stub'
]).config(function ($httpProvider, $urlRouterProvider, $stateProvider) {
    /** Routes */
    $stateProvider
        .state('fake-auth', {
            url: '/auth',
            templateUrl: 'src/main/fake-auth/view.html',
            onEnter: function ($rootScope) {
                $rootScope.ready = true;
            },
            controller: function ($scope, $state, Me) {
                $scope.roles = Me.const.ROLES;
                $scope.loadMe = function (role) {
                    role = role.toLowerCase();
                    sessionStorage.setItem('role', role);
                    location.reload();
                };
                if (sessionStorage.getItem('role')) {
                    $state.go('main');
                }
            }
        });
    /** END Routes */

    /** Interceptor **/
    $httpProvider.interceptors.push('appInterceptorStub');
    /** END OF Interceptor **/

}).run(
    function ($rootScope, mock, $state, settings) {

        $rootScope.$on('$stateChangeSuccess', function (ev, to) {
            if (to.name) {
                sessionStorage.setItem('from', to.name);
            }
        });

        /** Mocked Resources */
        //User
        var role = sessionStorage.getItem('role');
        if (role) {
            mock.get(new RegExp(settings.restEndpoint + 'me$'), 'stub/app/me/GET-' + role + '.json');
            var $logout = $('<a href style="position: absolute;right: 0;"><span class="icon icon-logout icon-x3"></span></a>');
            $logout.click(function () {
                sessionStorage.removeItem('role');
                $state.go('fake-auth');
            });
            $('body').prepend($logout);
        }

        // Book
        mock.get(new RegExp(settings.restEndpoint + 'books$'), 'stub/app/book/GET.json');
        mock.get(new RegExp(settings.restEndpoint + 'books\/[0-9]+$'), 'stub/app/book/GET-1.json');
        mock.get(new RegExp(settings.restEndpoint + 'books\/[0-9]+\\?pdf\\=true$'), 'stub/app/book/GET-pdf.json');
        mock.delete(new RegExp(settings.restEndpoint + 'books\/[0-9]+$'), 200);
        mock.post(new RegExp(settings.restEndpoint + 'books$'), 'stub/app/book/POST.json');
        mock.put(new RegExp(settings.restEndpoint + 'books\/[0-9]+$'), 200);

        // Configuration
        mock.get(new RegExp(settings.restEndpoint + 'configurations\/?$'), 'stub/app/configuration/GET.json');

        // Error
        mock.post(new RegExp(settings.restEndpoint + 'errors$'), 'stub/app/error/POST.json');

        // Master API
        mock.get(new RegExp('/masterapi/ws/v1.1.0/$'), 'stub/app/masterAPI/GET.json');

        // Regular Expression
        mock.get(new RegExp(settings.restEndpoint + 'regular-expressions\/?$'), 'stub/app/regular-expression/GET.json');

        mock.else();
        /** END OF Mocked Resources */


    });
