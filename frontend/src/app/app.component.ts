import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { BarraNavegacionComponent } from './barra-navegacion/barra-navegacion.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, BarraNavegacionComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Polaflix';
}
