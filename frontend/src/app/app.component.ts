import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { BarraNavegacionComponent } from './componentes/barra-navegacion/barra-navegacion.component';
import { CabeceraComponent } from './componentes/cabecera/cabecera.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, BarraNavegacionComponent, CabeceraComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Polaflix';
}
