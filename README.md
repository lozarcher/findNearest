# findNearest
Example service for AWS Lambda to find the nearest pub from Google Places API for a user's location

## Prerequisite
Requires an environment variable 'googleKey' with permission to use the Google Places API

## Usage
Invoke com.loz.FindNearest::handleRequest with the parameters:
- latitude
- longitude

Get a Json object back with the details of the nearest pub:
- name
- address
- rating
- photo
