Function ApiAuthentication() as object
    Auth = {
                ByteArray : CreateObject("roByteArray")
                AuthTokenURL : "https://cloud.junctiontv.net/ums/2.0/oauth/"
                GetToken : GetTokenFromServer
                ClientID : "Client_ID"
                Client_secret : "Client_Secret"
                Auth_String : ""
            } 
    return Auth
End Function

Function GetTokenFromServer(param as object) as String
        timeout% = 1000 * 3600
        str = param.ClientID+":"+param.Client_secret        
        param.ByteArray.FromAsciiString(str)
        param.Auth_String = param.ByteArray.ToBase64String()       
        Http = CreateObject("roUrlTransfer")
        Http.SetPort(CreateObject("roMessagePort"))
        Http.SetUrl(param.AuthTokenURL)
        Http.AddHeader("Content-Type", "application/x-www-form-urlencoded")
        Http.AddHeader("Authorization", "Basic "+param.Auth_String.Trim())        
        Http.EnableEncodings(true)        
        Http.SetCertificatesFile("common:/certs/ca-bundle.crt")
        Http.InitClientCertificates()        
        Http.EnableFreshConnection(true) 'Don't reuse existing connections
        Http.AsyncPostFromString("")
        event = wait(timeout%, Http.GetPort())
        if type(event) = "roUrlEvent"           
            retstr = event.GetString()            
            json = ParseJSON(retstr)            
            return json.access_token
        end if 
End Function
