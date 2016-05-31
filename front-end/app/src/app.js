'use strict';

angular.module('app.settings', []).constant('settings', {
    restEndpoint : '/miamiam-back/ws/<VERSION>/'
});

angular.module('app', [
    'ui.router',
    'pascalprecht.translate',
    'ngAnimate',
    'angular-loading-bar',
    'ui.select',
    'app.cache',
    'app.i18n',
    'app.security',
    'app.notification',
    'app.dialog',
    'app.resource',
    'app.resource.hal',
    'app.teamcopter',
    'app.keycopter',
    'app.check.browser',
    'app.error.handling',
    'app.settings',
    'ui.tinymce',
    'app.table',
    'app.download.file',
    'app.htmlunsafe'
]).config(function ($translateProvider, $httpProvider, $locationProvider, $notificationProvider, NotificationServiceProvider) {
        $locationProvider.html5Mode(false);

        /** Interceptor **/
        $httpProvider.interceptors.push('appErrorInterceptor');
        /** END OF Interceptor **/

        NotificationServiceProvider.alertDurationSeconds(10);
        $notificationProvider.$get = NotificationServiceProvider.$get;
    }
);
