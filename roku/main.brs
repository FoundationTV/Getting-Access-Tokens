'To get the authentication token for Junction TV API calls

'For detailed description read: http://api.junctiontv.com/jtv/jtapi/getting-access-tokens-2
'Refer Github: https://github.com/JunctionTV/Getting-Access-Tokens 

'-----------------------------------------------------------------------------------------
' Author: Subhankar Ganguly <subhankar1981@gmail.com>
'
' License: The MIT License (MIT)
' Copyright (c) <2016> <Junction TV Inc.>
'------------------------------------------------------------------------------------------


Sub Main(args as Dynamic)
print "Main called "
respauth = ApiAuthentication()
APIAuthToken = respauth.gettoken(respauth)
print "APIAuthToken : "+APIAuthToken
End Sub