'use strict';

angular.module('app').service('RegularExpression', function (Dao) {

    var Model = Dao.RegularExpression;

    Model.PROPERTY_KEYS = {
        CUSTOMER_CONFIGURATION: {
            NAME: 'customerConfiguration.name',
            NCAGE: 'customerConfiguration.ncage'
        }
    };

    Model.prototype.getCustomerConfiguration = function () {
        var model = this;
        return {
            getName: function () {
                return model[Model.PROPERTY_KEYS.CUSTOMER_CONFIGURATION.NAME];
            },
            getNcage: function () {
                return model[Model.PROPERTY_KEYS.CUSTOMER_CONFIGURATION.NCAGE];
            }
        };
    };

    return Model;
});