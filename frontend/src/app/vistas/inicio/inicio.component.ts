import { Component } from '@angular/core';
import { PanelSeriesComponent } from '../../componentes/panel-series/panel-series.component';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [PanelSeriesComponent],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

}
