import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndiceAlfabeticoComponent } from './indice-alfabetico/indice-alfabetico.component';
import { BarraBusquedaComponent } from './barra-busqueda/barra-busqueda.component';
import { Serie } from '../../interfaces/serie';
import { SeriesService } from '../../servicios/series/series.service';
import { CommIndiceCatalogo } from '../../servicios/comm-indice-catalogo/comm-indice-catalogo.service';
import { CommBusquedaCatalogo } from '../../servicios/comm-busqueda-catalogo/comm-busqueda-catalogo.service';
import { TarjetaSerieComponent } from './tarjeta-serie/tarjeta-serie/tarjeta-serie.component';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, IndiceAlfabeticoComponent, BarraBusquedaComponent, TarjetaSerieComponent],
  templateUrl: './catalogo.component.html',
  styleUrl: './catalogo.component.css'
})
export class CatalogoComponent {

  @Output() eventoInvocarMetodo = new EventEmitter<void>();
  
  series: Serie[] = [];

  constructor(
    private seriesService: SeriesService,
    private commIndice: CommIndiceCatalogo,
    private commBusqueda: CommBusquedaCatalogo
  ) { }

  ngOnInit() {
    this.commIndice.inicial$.subscribe(inicial => {
      this.seriesService.getSeriesByInicial(inicial).subscribe(
        series => {
          this.series = series
        },
      )
    })
    this.commBusqueda.titulo$.subscribe(titulo => {
      this.seriesService.getSeriesByTitulo(titulo).subscribe(
        series => {
          this.series = series
        },
      )
    })
  }

}
