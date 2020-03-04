# Introduction

This project is a demo springboot app with SSL HTTPs configuration

# Prerequisites

* Java 1.8
* Spring boot 2.x.x
* Java keytool utility

# Generate SSL certificate

We use OpenSSL Tools to generate self signed certificate and convert  into PKCS#12 or PFX format

dev.localhost.com.p12


# Application appliaction
````
spring:
  application:
    name: application
---
server:
  ssl:
    key-store: classpath:dev.localhost.com.p12
    key-store-password: password
    key-store-type: pkcs12
    key-password: password
  port: 8443
````

# API

https://127.0.0.1:8443/api/test/data


Access with domain : https://dev.localhost.com:8443

You need to append this line 127.0.0.1 dev.localhost.com in the /etc/hosts
````
echo '127.0.0.1 dev.localhost.com'>> /etc/hosts'
````