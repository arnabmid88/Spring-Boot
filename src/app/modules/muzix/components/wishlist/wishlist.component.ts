import { Component, OnInit } from '@angular/core';
import { MuzixService } from '../../muzix.service';
import { Track } from '../../track';
import {MatSnackBar} from '@angular/material'

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {
  tracks: Array<Track>;
  wishData = true;
  statusCode: number;

  constructor(private muzixService: MuzixService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    const message = "WishList is empty!";
    this.muzixService.getAllTracksOfWishList().subscribe(data=>{
      this.tracks = data;

      if(data.length === 0){
        this.snackBar.open(message,"",{
          duration:1000
        })
      }
    })
  }

  deleteFromWishlist(track){
    this.muzixService.deleteTrackFromWishlist(track).subscribe(data=>{
      console.log("deleted!",data);
      const index = this.tracks.indexOf(track);
      this.tracks.splice(index,1);
      this.snackBar.open(data,"",{
        duration:1000
      })
    })
    return this.tracks;
  }

  updateComments(track) {
    this.muzixService.updateComments(track).subscribe(
      data=>{
      this.snackBar.open("successfully Updated!","",{
        duration:1000
      })
    },
    error=>{
      console.log(error);
    })
  }

}
