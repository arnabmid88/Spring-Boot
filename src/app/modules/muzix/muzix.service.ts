import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from './track';

@Injectable({
  providedIn: 'root'
})
export class MuzixService {

  thirdPartyApi: string;
  apiKey: string;
  springEndpoint: string;

  constructor(private httpClient: HttpClient) { 
    this.thirdPartyApi = 'http://ws.audioscrobbler.com/2.0?method=geo.gettoptracks&country=';
    this.apiKey='&api_key=50b658c9de8b98675c03658a72978873&format=json';
    this.springEndpoint = 'http://localhost:8083/api/v1/muzixservice/';
  }

  getTrackDetails(country:string) : Observable<any>{

    const url = `${this.thirdPartyApi}${country}${this.apiKey}`;

    return this.httpClient.get(url);

  }

  addTrackToWishList(track: Track){
    const url = this.springEndpoint+"track";
    return this.httpClient.post(url,track,{
      observe: "response"
    });
  }

  getAllTracksOfWishList() : Observable<Track[]>{
    const url = this.springEndpoint+"tracks";
    return this.httpClient.get<Track[]>(url);
  }

  deleteTrackFromWishlist(track:Track){
    const url = this.springEndpoint+"track/"+track.trackId;
    return this.httpClient.delete(url,{responseType:"text"});
  }

  updateComments(track){
    const id = track.trackId;
    const url = this.springEndpoint+"track/"+track.trackId;
    return this.httpClient.put(url, track, {observe:"response"});
  }
}
