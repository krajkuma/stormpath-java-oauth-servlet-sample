## Stormpath Servlet + Token Auth Example

This repository takes the [Stormpath Servlet Example](https://github.com/stormpath/stormpath-sdk-java/tree/master/examples/servlet) 
from the sdk and adds a standalone Java program that demonstrates accessing a protected page with an OAuth 2 Bearer token.

**Note**: This is addresses the particular case where one needs to interact with the REST API directly from Java. It is *not* the
recommended best practice for token auth and token management with the Java SDK. Refer to: http://docs.stormpath.com/java/apidocs/
and the `oauth` package for more information.


### Build

```
mvn clean package
mvn package -Pstandalone
```

The first command builds the web application.

The second command builds the standalone Java application.

### Run

Start the servlet example like so:

```
STORMPATH_API_KEY_FILE=<path the apiKey.properties> \
STORMPATH_APPLICATION_HREF=<Application URL from the Stormpath admin UI> \
mvn jetty:run
```

Note: 

* `STORMPATH_API_KEY_FILE` is only needed if your `apiKey.properties` file is not in the default location:
`~/.stormpath/apiKey.properties`.
* `STORMPATH_APPLICATION_HREF` is only needed if you have more than the default number of Stormpath Applications in your tenant.

In a separate terminal window, you can run the standalone Java application like so:

```
java \
-Dcommand=getToken -Dusername=<email address> -Dpassword=<password> \
-jar target/stormpath-sdk-examples-servlet-uber-1.0.RC5.1.jar
```

You will get output like this:

```
{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2Yjk5OWRiMC0zN2VmLTQ2MzQtYmIyMi1kMWE4ZTE1MWI1ZTAiLCJpYXQiOjE0NDcyNzY4MDIsInN1YiI6Imh0dHBzOi8vYXBpLnN0b3JtcGF0aC5jb20vdjEvYWNjb3VudHMvdFFlVFg1V0k3blNXaHdPazZ3bmZCIiwiZXhwIjoxNDQ3MjgwNDAyfQ.vBlWuzhyMTYl5eeihmHH7LxgIkGwzWPB7VW4yR7iOz8",
    "expires_in": 3600,
    "token_type": "Bearer"
}
```

Next, you can run the Java application to hit a protected resource using the token from the previous step:

```
java \
-Dcommand=getPage \
-Dtoken=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2Yjk5OWRiMC0zN2VmLTQ2MzQtYmIyMi1kMWE4ZTE1MWI1ZTAiLCJpYXQiOjE0NDcyNzY4MDIsInN1YiI6Imh0dHBzOi8vYXBpLnN0b3JtcGF0aC5jb20vdjEvYWNjb3VudHMvdFFlVFg1V0k3blNXaHdPazZ3bmZCIiwiZXhwIjoxNDQ3MjgwNDAyfQ.vBlWuzhyMTYl5eeihmHH7LxgIkGwzWPB7VW4yR7iOz8 \
-jar target/stormpath-sdk-examples-servlet-uber-1.0.RC5.1.jar
```

You will get output like this:

```
Attempting to retrieve /dashboard without token...
return code: 401

Attempting to retrieve /dashboard with token...
return code: 200
```
