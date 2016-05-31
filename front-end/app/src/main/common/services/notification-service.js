'use strict';

angular.module('app').provider('NotificationService', function NotificationServiceProvider() {

    var alertDurationSeconds = 0;

    this.alertDurationSeconds = function (value) {
        alertDurationSeconds = value;
    };

    this.$get = function ($alert, $rootScope, $sce) {
        return {
            constant: {
                ERROR: 'danger',
                WARNING: 'warning',
                INFO: 'info',
                SUCCESS: 'success'
            },
            add: function (attr) {
                attr.scope = $rootScope.$new();
                if (attr.title) {
                    if (attr.title.then) {
                        attr.title.then(function (title) {
                            attr.scope.title = attr.type.toUpperCase() + '! ' + title;
                        });
                    } else {
                        attr.scope.title = attr.type.toUpperCase() + '! ' + attr.title;
                    }
                }
                if (attr.content) {
                    if (attr.content.then) {
                        attr.content.then(function (content) {
                            attr.scope.content = $sce.trustAsHtml(content);
                        });
                    } else {
                        attr.scope.content = $sce.trustAsHtml(attr.content);
                    }
                }
                delete attr.title;
                delete attr.content;
                attr.container = '#app-notification';
                attr.duration = alertDurationSeconds;
                $alert(attr);
            }
        };
    };
});