import { Component } from '@angular/core';
import { Feature } from './feature/feature';

@Component({
  selector: 'app-home',
  imports: [Feature],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}
