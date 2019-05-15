import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CardContainerComponent} from './modules/muzix/components/card-container/card-container.component'
import {WishlistComponent} from './modules/muzix/components/wishlist/wishlist.component'
import { RegisterComponent } from './modules/authentication/components/register/register.component';
import { LoginComponent } from './modules/authentication/components/login/login.component';
import { AuthGuardService } from './modules/muzix/auth-guard.service';

const routes: Routes = [
  {
    path:"Australia",
    component: CardContainerComponent,
    data: {country:"Australia"}

  },
  {
    path:"",
    component: LoginComponent
  },
  {
    path:"login",
    component: LoginComponent
  },
  {
    path:"register",
    component: RegisterComponent
  },
  {
    path:"India",
    component: CardContainerComponent,
    data: {country:"India"}

  },
  {
    path:"Spain",
    component: CardContainerComponent,
    data: {country:"Spain"}

  },
  {
    path:"WishList",
    component: WishlistComponent,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
