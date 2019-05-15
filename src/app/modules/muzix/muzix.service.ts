import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from './track';
import { USER_NAME } from '../authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class MuzixService {

  thirdPartyApi: string;
  apiKey: string;
  springEndpoint: string;
  username: string;

  constructor(private httpClient: HttpClient) { 
    this.thirdPartyApi = 'http://ws.audioscrobbler.com/2.0?method=geo.gettoptracks&country=';
    this.apiKey='&api_key=50b658c9de8b98675c03658a72978873&format=json';
    //this.springEndpoint = 'http://localhost:8083/api/v1/muzixservice/';
    this.springEndpoint = 'http://localhost:8083/api/v1/usertrackservice/';
  }

  getTrackDetails(country:string) : Observable<any>{

    const url = `${this.thirdPartyApi}${country}${this.apiKey}`;

    return this.httpClient.get(url);

  }

  addTrackToWishList(track: Track){
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndpoint+ "user/" +this.username+ "/track";
    return this.httpClient.post(url,track,{
      observe: "response"
    });
  }

  getAllTracksOfWishList() : Observable<Track[]>{
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndpoint+ "user/" +this.username+ "/tracks";
    return this.httpClient.get<Track[]>(url);
  }

  deleteTrackFromWishlist(track:Track){
    this.username = sessionStorage.getItem(USER_NAME);
    const url = this.springEndpoint+ "user/" +this.username+ "/track/"+track.trackId;

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: track
    }

    return this.httpClient.delete(url,options );
  }

  updateComments(track){
    this.username = sessionStorage.getItem(USER_NAME);
    const id = track.trackId;
    const url = this.springEndpoint+ "user/" +this.username+ "/track/"+track.trackId;
    return this.httpClient.put(url, track, {observe:"response"});
  }
}
