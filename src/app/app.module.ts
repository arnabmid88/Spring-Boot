import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AuthenticationModule} from './modules/authentication/authentication.module'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MuzixModule } from './modules/muzix/muzix.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MuzixModule,
    HttpClientModule,
    AuthenticationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
