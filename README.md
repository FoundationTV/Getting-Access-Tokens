# Getting Access Tokens
To get access authentication tokens and implement the Junction TV API in your apps.

## Getting a token
Authentication tokens is obtained from the JunctionTV OAuth API. Along with access tokens, it is required to obtain client credentials (a client id and a client password) they are specific to the API and operations that are required to be accessed or performed.

Once credentials are obtained, access authentication token by making a POST request to:

## Access token URL
https://cloud.junctiontv.net/ums/2.0/oauth/

####Method : POST

## Description of headers
| Header                        | Description                                                            |
| ----------------------------- |:----------------------------------------------------------------------:|
| Content-Type (required )      | application/x-www-form-urlencoded                                      |
| Authorization (required)      | Basic {client_id}:{client_secret}                                      |

**NOTE:** {client_id}:{client_secret} string must be Base64-encoded (curl will automatically Base64-encode the string if it is passed as –user credentials; in other languages, user need to handle the Base64-encoding himself).   

####The response of a successful authorization access call ( Status #200) will look as follows:-
{
version: "1.1",
client_id: "XXXX",
access_token: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
expires_in: 3600
}
The expired_in value is the number of seconds that the access token is valid for.

####The response of a invalid authorization access call ( Status #401) will look as follows:-
{
    result: "error",
    message: "UNAUTHORIZED",
    reason: "No Authorization Token"
}
####The response of an ERROR in authorization access call ( Status #404) will look as follows:-
HTTP Status 404


##Implementation Strategies


App will only be making periodic calls to the JTV APIs. If the API call (i.e. HTTP response) is successful, with response Status code is 200, then continue with further process. Else, if the response Status code is 401, that means unauthorized call. Follow the flow loop to get Authentication Token. At last if the response Status code is not 401, that means invalid API call, continue the loop to make API calls.

The processing sequence will look like this:


