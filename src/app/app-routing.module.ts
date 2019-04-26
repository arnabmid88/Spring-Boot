import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CardContainerComponent} from './modules/muzix/components/card-container/card-container.component'
import {WishlistComponent} from './modules/muzix/components/wishlist/wishlist.component'

const routes: Routes = [
  {
    path:"",
    component: CardContainerComponent,
    data: {country:"Australia"}

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
    component: WishlistComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
