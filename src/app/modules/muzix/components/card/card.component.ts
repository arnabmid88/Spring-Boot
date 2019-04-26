import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Track } from '../../track';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  @Input()
  track:Track

  @Input()
  wishData:boolean

  @Output()
  addTrackToWishList = new EventEmitter();

  @Output()
  deleteFromWishlist = new EventEmitter();

  @Output()
  updateComments = new EventEmitter();

  constructor(private matDialog:MatDialog) { }

  addButtonClick(track){
    console.log('card component-->',track);
    this.addTrackToWishList.emit(track);
  }

  deleteButtonClick(track){
    console.log('delete card--->',track)
    this.deleteFromWishlist.emit(track);
  }

  ngOnInit() {
  }

  addComments(){
    const dialogRef = this.matDialog.open(DialogComponent, {
      width:"250px",
      data: {comments: this.track.comments}
    });
    dialogRef.afterClosed().subscribe(result=>{
      console.log('dialog result---',result);
      this.track.comments = result;
      this.updateComments.emit(this.track);
    })
  }

}
