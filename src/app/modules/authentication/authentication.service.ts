import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
export const USER_NAME = 'username';
export const TOKEN_NAME = 'token';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private SpringRegisterEndpoint :String;
  private SpringSaveEndpoint :String;

  constructor(private httpClient :HttpClient) {
    this.SpringRegisterEndpoint = "http://localhost:8083/api/v1/usertrackservice/";
    this.SpringSaveEndpoint = "http://localhost:8080/api/v1/userservice/";
   }

   registerUser(newUser){
     const url = this.SpringRegisterEndpoint+"register";
     return this.httpClient.post(url, newUser, {observe: "response"});
   }

   saveUser(newUser){
    const saveUserUrl = this.SpringSaveEndpoint+"save";
    return this.httpClient.post(saveUserUrl, newUser);
   }

   loginUser(newUser){
     const loginUserUrl = this.SpringSaveEndpoint+"login";
     sessionStorage.setItem(USER_NAME, newUser.username);
     return this.httpClient.post(loginUserUrl, newUser,{observe: 'response'});
   }

   getToken(){
     return localStorage.getItem(TOKEN_NAME);
   }

   isTokenExpired(token?:string): boolean{
    if(localStorage.getItem(TOKEN_NAME)){
      return true;
    }else{
      return false;
    }
   }

   logout(){
     sessionStorage.removeItem(USER_NAME);
     sessionStorage.clear();
     localStorage.removeItem(TOKEN_NAME);
     localStorage.clear();
     console.log("logged out!");
   }
}
