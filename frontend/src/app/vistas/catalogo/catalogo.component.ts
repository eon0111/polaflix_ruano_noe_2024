import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndiceAlfabeticoComponent } from './indice-alfabetico/indice-alfabetico.component';
import { BarraBusquedaComponent } from './barra-busqueda/barra-busqueda.component';
import { Serie } from '../../interfaces/serie';
import { SeriesService } from '../../servicios/series/series.service';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, IndiceAlfabeticoComponent, BarraBusquedaComponent],
  templateUrl: './catalogo.component.html',
  styleUrl: './catalogo.component.css'
})
export class CatalogoComponent {

  @Output() eventoInvocarMetodo = new EventEmitter<void>();
  
  series: Serie[] = [];

  constructor(
    private seriesService: SeriesService
  ) { }

  getSeriesByInicial(inicial: string) {
    this.seriesService.getSeriesByInicial(inicial).subscribe(
      series => {
        this.series = series
      },
    )
  }

  getSeriesByTitulo(titulo: string) {
    this.seriesService.getSeriesByTitulo(titulo).subscribe(
      series => {
        this.series = series
      },
    )
  }

}
