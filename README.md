# Registratie-verplaatsing

## Support for Artifactory Repository
To connect to the Artifactory repository, where for example the common library is hosted, you need to provide login credentials.
This can be done by adding a server entry to your local maven settings.

### Maven settings.xml location
Mac: ```~/.m2/settings.xml```

Windows: ```${user.home}/.m2/settings.xml```


### Adding authorization:
``` 
<servers>
        <server>
            <username>admin</username>
            <password>APBALjaZVr9YFcHPuBgypPagyHY</password>
            <id>central</id>
        </server>
        <server>
            <username>admin</username>
            <password>APBALjaZVr9YFcHPuBgypPagyHY</password>
            <id>snapshots</id>
        </server>
    </servers>
```
This should be added withing the ```settings```-tag in your settings.xml
