import  httplib, urllib, base64, json, sys

# get the oauth 2.0 token
def getAuthToken(client_id, client_secret):
    conn = httplib.HTTPConnection("cloud.junctiontv.net")
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
        print '[API_CALL_ERROR]' + "{error: " + str(response.status) + ",reason: "+ response.reason+" }"
    


# call jtv API 
def getJson(token):
    
    conn = httplib.HTTPConnection("metax.stage.junctiontv.net")
    url =  "/metax/1.1/feed/json/all"

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
        print '[API_CALL_ERROR]' + "{error: " + str(response.status) + ",reason: "+ response.reason+" }"
    
def main():
    
    client_id = "XXXX"
    client_secret = "XXXXXXXXX....XXXXXX"
    
    token=getAuthToken(client_id, client_secret)
    print "Authentication Token: ",token
    try:
        results = getJson(token)
    except e:
        # handle an auth error by re-fetching a auth token again
        token = getAuthToken(creds)
        results = getJson(token)

    # print the results
    print results
    

if __name__ == "__main__":
    main()
