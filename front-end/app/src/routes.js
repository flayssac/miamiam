'use strict';

angular.module('app').config(function($stateProvider, $urlRouterProvider){

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('main', {
            url: '',
            templateUrl: 'src/main/view.html',
            resolve: {
                me: function (Dao) {
                    return Dao.Me.get().$promise;
                },
				configuration: function (Configuration) {
                    return Configuration.get().$promise;
                },
                regularExpressions: function (RegularExpression) {
                    return RegularExpression.get().$promise;
                },
                chameleon: function($interval, $q){
                    var deferred = $q.defer();
                    var chameleonIsLoaded = function(){
                        return $('body').css('color') === 'rgb(51, 51, 51)';
                    };
                    var iterations = 0;
                    var iterationsMax = 10;
                    var interval = yepnope.errorTimeout / iterationsMax;
                    var stop = $interval(function(){
                        if(chameleonIsLoaded() || iterations >= iterationsMax){
                            $interval.cancel(stop);
                            deferred.resolve();
                        }
                        iterations ++;
                    }, interval);
                    return deferred.promise;
                },
                i18n: function($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('common');
                    $translatePartialLoader.addPart('teamcopter-menu');
                    return $translate.refresh();
                },
                initResources: function($http, settings, Dao, $q, configuration) {
                    var deferred = $q.defer();
                    $http.get(configuration['master.endpoint']).then(function(resources) {
                        Dao.init(resources.data);
                        deferred.resolve();
                    });
                    return deferred.promise;
                }
            },
            controller: function($state, me, Me) {
                if ($state.$current.name === 'main') {
                    if (me.hasRight(Me.const.RIGHTS.ACCESS_KEYCOPTER)) {
                        $state.go('keycopter-home');
                    } else if (me.hasRight(Me.const.RIGHTS.ACCESS_TEAMCOPTER)) {
                        $state.go('teamcopter-home');
                    } else {
                        $state.go('app-security-forbidden');
                    }
                }
            },
            onEnter: function ($rootScope, me, $state, $checkBrowser) {
                $rootScope.ready = true;
                $rootScope.state = $state;
                $rootScope.me = me;

                if(!$checkBrowser.isCompatible()){
                    $state.go('app-browser-not-compatible');
                    return;
                }
            }
        });

});