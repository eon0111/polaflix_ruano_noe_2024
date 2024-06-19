import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndiceAlfabeticoComponent } from './indice-alfabetico/indice-alfabetico.component';
import { BarraBusquedaComponent } from './barra-busqueda/barra-busqueda.component';
import { Serie } from '../../interfaces/serie';
import { SeriesService } from '../../servicios/series/series.service';
import { CommIndiceCatalogo } from '../../servicios/comm-indice-catalogo/comm-indice-catalogo.service';
import { CommBusquedaCatalogo } from '../../servicios/comm-busqueda-catalogo/comm-busqueda-catalogo.service';
import { TarjetaSerieComponent } from './tarjeta-serie/tarjeta-serie/tarjeta-serie.component';
import { catchError } from 'rxjs';
import { of } from 'rxjs';

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
  mensajeError: string = '';

  constructor(
    private seriesService: SeriesService,
    private commIndice: CommIndiceCatalogo,
    private commBusqueda: CommBusquedaCatalogo
  ) { }

  ngOnInit() {
    this.commIndice.inicial$.subscribe(inicial => {
      this.seriesService.getSeriesByInicial(inicial).pipe(
        catchError(error => {
          if (error.status === 404) {
            this.mensajeError = 'El catálogo no dispone de series que comiencen por la inicial seleccionada';
            return of([]);
          } else {
            this.mensajeError = '';
            throw error;
          }
        })
      ).subscribe(
        series => {
          this.series = series;

          if (this.series.length != 0) {
            this.mensajeError = '';
          }
        },
      )
    })
    this.commBusqueda.titulo$.subscribe(titulo => {
      this.seriesService.getSeriesByTitulo(titulo).pipe(
        catchError(error => {
          if (error.status === 404) {
            this.mensajeError = 'El catálogo no dispone de series con el título indicado';
            return of([]);
          } else {
            this.mensajeError = '';
            throw error;
          }
        })
      ).subscribe(
        series => {
          this.series = series;

          if (this.series.length != 0) {
            this.mensajeError = '';
          }
        },
      )
    })
  }

}
