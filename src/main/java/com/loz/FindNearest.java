package com.loz;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.loz.domain.GoogleSearchResponse;
import com.loz.domain.Photo;
import com.loz.domain.SearchResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class FindNearest implements RequestHandler<RequestObject, ResponseObject> {

    // Set an environment variable 'googleKey' with permission to use the Places API
    private static String GOOGLE_KEY = System.getenv("googleKey");

    private static String URL_GET_PLACES = "https://maps.googleapis.com/maps/api/place/search/json?location=%s,%s&types=bar&rankby=distance&fields=photos&sensor=false&key=%s";
    private static String URL_GET_PICTURE = "https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=%s&maxwidth=400";

    public ResponseObject handleRequest(RequestObject request, Context context){
        RestTemplate restTemplate = new RestTemplate();

        String getPlacesUrl = String.format(URL_GET_PLACES, request.getLatitude(), request.getLongitude(), GOOGLE_KEY);

        ResponseEntity<GoogleSearchResponse> response = null;
        try {
            response = restTemplate.getForEntity(URLDecoder.decode(getPlacesUrl, "UTF-8"), GoogleSearchResponse.class);
        } catch (UnsupportedEncodingException e) {
            return getErrorObject("Can't get place data from Google");
        }

        ResponseObject responseObject = new ResponseObject();
        if (response.getBody().getResults().size() > 0) {
            SearchResult result = response.getBody().getResults().get(0);

            responseObject.setName(result.getName());
            responseObject.setAddress(result.getVicinity());
            responseObject.setRating(result.getRating().toString());

            List<Photo> photos = result.getPhotos();
            if (photos.size()>0) {
                String photoUrl = String.format(URL_GET_PICTURE, photos.get(0).getPhoto_reference(), GOOGLE_KEY);
                responseObject.setPicture(photoUrl);
            } else {
                responseObject.setPicture("");
            }
        } else {
            return getErrorObject("No results");
        }
        return responseObject;
    }

    private ResponseObject getErrorObject(String error) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setName(error);
        responseObject.setAddress("");
        responseObject.setRating("0.0");
        return responseObject;
    }

}
