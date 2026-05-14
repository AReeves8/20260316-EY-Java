/**
 * ENVIRONMENTS
 *      - initialize variable that can be used across your application
 *      - only information your application needs, not DATA that it will use
 *          - ex: endpoint URLs, feature flags, logging/monitoring options, etc.
 *              - NO SECRETS - file will be public
 * 
 * 
 *      - For Prod: create a environments.prod.ts file with a similar object structure
 */

export const environment = {
    production: false,
    baseApiUrl: "http://localhost:8080/api/v1"
}