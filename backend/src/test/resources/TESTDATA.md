# Minisign test data

## Create keys

```
minisign -G -s minisgn_secret_key.key -p minisign_public_key.pub
```

Password: test123

## Sign a payload file

```
minisign -Sm test_payload_file.txt -s minisign_secret_key.key 
```

Password: test123

## Verify a payload file

```
minisign -Vm test_payload_file.txt -p minisign_public_key.pub
```