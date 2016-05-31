'use strict';

angular.module('app').service('Configuration', function(Dao) {

    var Model = Dao.Configuration;

    return Model;
});