#  https://blog.invivoo.com/securiser-application-spring-boot-spring-security/


-----------
https://github.com/bezkoder/angular-8-jwt-authentication
https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication
-----------

https://bezkoder.com/angular-spring-boot-jwt-auth/


## JWT disponible à cette adresse: https://jwt.io/#libraries-io. Pour cet article, c’est jjwt qui a été choisi.

Passe en mode JWT ( stateless)
````java
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//.and() .formLogin()   
````

