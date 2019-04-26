import { Component, OnInit } from '@angular/core';
import { Track } from '../../track';
import { Artist } from '../../artist';
import { Image } from '../../image';
import { MuzixService } from '../../muzix.service';
import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-card-container',
  templateUrl: './card-container.component.html',
  styleUrls: ['./card-container.component.css']
})
export class CardContainerComponent implements OnInit {

  tracks: Array<Track>
  trackObj: Track
  artistObj: Artist
  imageObj: Image
  countryName: string
  id: number
  statusCode: number
  errorStatus: string
  artistName: string
  searchTracks: Array<Track>
  constructor(private muzixService:MuzixService, private route:ActivatedRoute, private matSnackBar: MatSnackBar) {
    this.tracks = []
   }

  ngOnInit() {
    const tempData = this.route.data.subscribe(newData=>{
      this.countryName = newData.country;
      console.log("country name-->",this.countryName);
    })
  


    this.muzixService.getTrackDetails(this.countryName).subscribe(tracks=>{
      console.log(tracks);
      this.tracks = [];
      const data = tracks['tracks']['track']
      this.id = 0;
      data.forEach(element => {
        this.id++
        this.trackObj = new Track()
        this.artistObj = new Artist()
        this.artistObj = element["artist"]
        this.imageObj = new Image()
        this.imageObj.text = element["image"][2]["#text"]
        this.imageObj.size = element["image"][2]["size"]
        this.trackObj = element
        this.trackObj.artist = this.artistObj
        this.artistObj.image = this.imageObj
        this.trackObj.trackId = this.countryName.slice(0,3)+this.id;
        this.tracks.push(this.trackObj)
        this.searchTracks = this.tracks
        
      });
  })
  }

  onKey(event:any){
    this.artistName = event.target.value;
    console.log('artist name',this.artistName);

    const result = this.searchTracks.filter(track=>{
      return track.artist.name.match(this.artistName);
    })
    this.tracks = result;
  }
  addTrackToWishList(track){
    console.log('card container--->',track)
    this.muzixService.addTrackToWishList(track).subscribe(data=>{
      console.log('data from muzix service-->',data);
      this.statusCode = data.status;

      if(this.statusCode ===201){
        console.log('track added successfully',this.statusCode)
        this.matSnackBar.open("Track Successfully Added!","",{
          duration:1000
        })
      }
    },
    error=>{
      this.errorStatus = `${error.status}`;
      const errorMsg = `${error.error.message}`;
      this.statusCode = parseInt(this.errorStatus, 10);
      if(this.statusCode === 409){
        this.matSnackBar.open(errorMsg,"",{
          duration:1000
        });
        this.statusCode = 0;
      }
    })
  }
  

}
