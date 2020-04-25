# Angular JWT 

angular version 9.1.1
https://bezkoder.com/angular-jwt-authentication/
https://www.javainuse.com/spring/ang7-crud
https://www.toptal.com/angular/angular-6-jwt-authentication
https://jasonwatmore.com/post/2019/06/22/angular-8-jwt-authentication-example-tutorial
https://github.com/cornflourblue/angular-8-jwt-authentication-example

https://howtodoinjava.com/angular/rxjs-observable-httpclient/

## installtation de bootstrap  et jquerydans le projet
```shell script
npm install --save bootstrap@3.3.7  
npm install --save jquery@latest  
```

## install librairie jwt dans le projet
 ```shell script
 npm install @auth0/angular-jwt
```
## generation composant
 ```shell script
ng g c login
ng g c task
ng g c new-task
ng g c registration
ng g s services/authentification
```

## service
**observe: reponse** signifie renvoi la requete complete avec l'entete et le body.
par defaut **observe: body** et **media-type: json**
````typescript
  login(user: IUser) {
    return this.http.post(this.hostEndPoint + '/login', user, { observe: 'response' }) ;
  }
````
