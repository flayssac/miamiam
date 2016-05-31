'use strict';

angular.module('app').service('Dao', function (Resource, settings) {

    return {
        Configuration: Resource('Configuration', settings.restEndpoint + 'configurations', {}),
        RegularExpression: Resource('RegularExpression', settings.restEndpoint + 'regular-expressions', {}),
        Me: Resource('Me', settings.restEndpoint + 'me', {}),
        Book: Resource('Book', settings.restEndpoint + 'books/:id', {id: '@id'}, {
            getPdf: {
                method: 'GET',
                params:{pdf:true}
            }
        }),
        Error: Resource('Error', settings.restEndpoint + 'errors', {}),

	init: function (resources) {
            var dao = this;
            resources.forEach(function (resource) {
                    switch (resource.name) {
                        case 'du':
                            dao.DM = Resource('DM', resource.uri + '/:id', {id: '@id'}, {
                                search: {
                                    method: 'POST'
                                }
                            });
                            break;
                        default:
                    }
            });
        }
    };
});
