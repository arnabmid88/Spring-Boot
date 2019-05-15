import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatToolbarModule,
    MatMenuModule,
    MatSnackBarModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule
  ],
  exports:[
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatToolbarModule,
    MatMenuModule,
    MatSnackBarModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule
  ]
})
export class AngularmaterialModule { }
