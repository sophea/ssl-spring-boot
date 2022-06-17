# Introduction

This project is a demo springboot app with SSL HTTPs configuration

# Prerequisites

* Java 11 or later
* Spring boot 2.7.x
* openssl Tool
* Java keytool utility

# Generate SSL certificate

We use OpenSSL Tools to generate self signed certificate and convert  into PKCS#12 or PFX format (dev.localhost.com.p12)

Here are the step to generate SSL certificate using OpenSSL

````
1# Create CA Root

openssl genrsa -des3 -out myCARoot.key 2048

You will be prompted for a passphrase, which I recommend not skipping and keeping safe. The pass phrase will prevent anyone who gets your private key from generating a root certificate of their own. Output should look like this

==========

Generating RSA private key, 2048 bit long modulus
.................................................................+++
.....................................+++
e is 65537 (0x10001)
Enter pass phrase for myCARoot.key: password
Verifying - Enter pass phrase for myCARoot.key:password
==========


2# Create CA Root Certificate

openssl req -x509 -new -nodes -key myCARoot.key -sha256 -days 3650 -out myCARoot.pem


You should now have two files: myCARoot.key (your private key) and myCARoot.pem (your root certificate).


3# Creating CA-Signed Certificates for Your springobot app

Now that you have a CA on all our devices, we can sign certificates for any new dev sites that need HTTPS. First, we create a private key:

openssl genrsa -out dev.localhost.com.key 2048

Then a CSR file:

openssl req -new -key dev.localhost.com.key -out dev.localhost.com.csr


Create public certificate file using csr file

we need to create config file csr file ( csr.config) : The config file is needed to define the Subject Alternative Name (SAN) extension. the new file named csr.config and added the following contents:
===========
[req]
days                   = 3650
serial                 = 1
distinguished_name     = req_distinguished_name
x509_extensions        = v3_ca

[req_distinguished_name]
countryName            = Country
stateOrProvinceName    = State
localityName           = Locality
organizationName       = Organizational Name
organizationalUnitName = Organizational Unit Name
commonName             = Common Name (Domain Name)
emailAddress           = Email Address


[ v3_ca ]
# The extentions to add to a self-signed cert
subjectKeyIdentifier   = hash
authorityKeyIdentifier = keyid:always,issuer:always

# THIS IS VERY IMPORTANT IF YOU WANT TO USE THIS CERTIFICATION AS AN AUTHORITY!!!
basicConstraints       = CA:TRUE

keyUsage               = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment, keyAgreement, keyCertSign
subjectAltName         = @alt_names
issuerAltName          = issuer:copy

[alt_names]
DNS.1 = dev.localhost.com
DNS.2 = dev.localhost.com.127.0.0.1.io
IP.1= 127.0.0.1

===========

Now we run the command to create the public certificate:

openssl x509 -req -in dev.localhost.com.csr -CA myCARoot.pem -CAkey myCARoot.key -CAcreateserial -out dev.localhost.com.crt -days 3650-sha256 -extfile csr.config  -extensions v3_ca

You should be prompted for your CA private key passphrase.
You should have three files: dev.localhost.com.key (the private key), dev.localhost.com.csr (the certificate signing request), and dev.localhost.com.crt (the signed certificate).

Convert PKCS#12 or PFX format

openssl pkcs12 -export -out dev.localhost.com.p12 -inkey dev.localhost.com.key -in dev.localhost.com.crt

````
# Enable SSL HTTPS in Springboot java application 
To enable HTTPS for the Spring Boot 2 application, copy the dev.localhost.com.p12 into springboot resource folder and  we just config on application properties.
# Application properties
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
