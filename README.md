# findNearest
Example service for AWS Lambda to find the nearest pub from Google Places API for a user's location

## Prerequisite
Requires an environment variable 'googleKey' with permission to use the Google Places API

## Usage
Set up API Gateway to invoke *com.loz.FindNearest::handleRequest* with the parameters:
- latitude
- longitude

Receive a Json object back with the details of the nearest pub:
- name
- address
- rating
- photo