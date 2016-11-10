Sub Main(args as Dynamic)
print "Main called "
respauth = ApiAuthentication()
APIAuthToken = respauth.gettoken(respauth)
print "APIAuthToken : "+APIAuthToken
End Sub