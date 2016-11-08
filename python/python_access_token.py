"""
To get the authentication token for Junction TV API calls

This code had two function:
- ``getAuthToken(client_id, client_secret)``: to get the authentication call from `http://cloud.junctiontv.net/ums/2.0/oauth/`
- ``getFeeds(token)``: to get the Feeds according to vairous Junction TV API   

For detailed description read: http://api.junctiontv.com/jtv/jtapi/getting-access-tokens-2
Refer Github: https://github.com/JunctionTV/Getting-Access-Tokens 

"""
# Author: Gourab Chowdhury <gourab@junctiotntv.com>
#
# License: The MIT License (MIT)
# Copyright (c) <2016> <Junction TV Inc.>

import  httplib, urllib, base64, json, sys

# get the oauth 2.0 token
def getAuthToken(client_id, client_secret):
    conn = httplib.HTTPSConnection("cloud.junctiontv.net")
    url =  "/ums/2.0/oauth/"
    
    authString = base64.encodestring('%s:%s' % (client_id, client_secret)).replace('\n', '')
    
    headersMap = {
       "Content-Type": "application/x-www-form-urlencoded",
       "Authorization": "Basic " + authString
    }
    conn.request("POST", url, headers=headersMap)
    response = conn.getresponse()
    
    if response.status == 200:
        data = response.read()
        result = json.loads(data)
        return result["access_token"]
    
    else:
        raise Exception('[API_CALL_ERROR]' + "{status code: " + str(response.status) + ",reason: "+ response.reason+" }")
    


# call jtv API 
def getFeeds(token):
    
    conn = httplib.HTTPConnection("www.examplejtvapi.com")
    url =  "/xyz/abc/def"

    headersMap = {
        "Authorization": "Bearer " + token
    }

    #The method will vary according to specific API. 
    conn.request("GET", url, headers=headersMap)
    response = conn.getresponse()

    
    if response.status == 200:
        data = response.read()
        result = json.loads( data )
        return result
    else:
        raise Exception('[API_CALL_ERROR]' + "{status code: " + str(response.status) + ",reason: "+ response.reason+" }")
    
def main():
    
    client_id = "XXXX"
    client_secret = "XXXXXXXXX....XXXXXX"
    
    token=getAuthToken(client_id, client_secret)
    print "Authentication Token: ",token
    try:
        results = getFeeds(token)
    except e:
        # handle an auth error by re-fetching a auth token again
        token = getAuthToken(client_id, client_secret)
        results = getFeeds(token)

    # print the results
    print results
    

if __name__ == "__main__":
    main()
