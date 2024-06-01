import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contenedor-series',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './contenedor-series.component.html',
  styleUrl: './contenedor-series.component.css'
})
export class ContenedorSeriesComponent {

  @Input() tituloContenedor: string = '';
  @Input() listaSeries: any[] = [];

}
