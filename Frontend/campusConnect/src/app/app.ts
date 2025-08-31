import { Component } from '@angular/core';
import { Header } from './header/header';
import { Home } from './home/home';

@Component({
  selector: 'app-root',
  imports: [Header,Home],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'campusConnect';
}
