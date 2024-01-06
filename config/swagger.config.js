// swagger.config.js

import SwaggerJsdoc from "swagger-jsdoc";

const options = {
    definition: {
        info: {
            title: 'UMC hackathon API',
            version: '1.0.0',
            description: 'UMC hackathon API with express, API ����'
        },
        host: 'abc.umctestserver:3000',
        basepath: '../'
    },
    apis: ['./src/routes/*.js', './swagger/*']
};

export const specs = SwaggerJsdoc(options);