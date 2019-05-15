import { Component, OnInit } from '@angular/core';
import { User } from '../../User';
import { AuthenticationService } from '../../authentication.service';
import { MatSnackBar } from '@angular/material';
import {Router} from '@angular/router'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user :User;
  constructor(private authService: AuthenticationService, private snackBar: MatSnackBar, private router:Router) {
    this.user = new User();
   }

  ngOnInit() {
  }

  register(){
    console.log(this.user);
    this.authService.registerUser(this.user).subscribe(data=>{
      if(data.status === 201){
         this.snackBar.open("Successfully registered!", "",{
           duration: 1000
         })
      }
      this.authService.saveUser(this.user).subscribe(data=>{
        console.log("save user-->",data);
      })
      this.router.navigate(['/login'])
    },error=>{
      if(error.status === 409){
        const errormessage = error.error.message;
        this.snackBar.open(errormessage, "",{
          duration: 1000
        })
      }
    })
  }

}
