# Minisign Backend Proof of Concept

## TODO

- Parallel processes / Isolation
- Exception Handling / Error Responses
- API Documentation

## Test data

### Create keys

```
minisign -G -s minisgn_secret_key.key -p minisign_public_key.pub
```

Password: test123

### Sign a payload file

```
minisign -Sm test_payload_file.txt -s minisign_secret_key.key 
```

Password: test123

### Verify a payload file

```
minisign -Vm test_payload_file.txt -p minisign_public_key.pub
```

## Hyperlinks

- [Guide to java.lang.ProcessBuilder API](https://www.baeldung.com/java-lang-processbuilder-api)
- [Minisign](https://jedisct1.github.io/minisign/)
- [File sample project](https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem)
- [Spring Boot Docker](https://spring.io/guides/topicals/spring-boot-docker)