!/bin/env

# Start with Rancher Desktop
nerdctl run -it -p 8080:8082 --rm --name minisign-web-poc ddd-journey/minisign-web-poc
