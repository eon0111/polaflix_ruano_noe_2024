import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommBusquedaCatalogo } from '../../../servicios/comm-busqueda-catalogo/comm-busqueda-catalogo.service';

@Component({
  selector: 'app-barra-busqueda',
  standalone: true,
  imports: [],
  templateUrl: './barra-busqueda.component.html',
  styleUrl: './barra-busqueda.component.css'
})
export class BarraBusquedaComponent {

  @ViewChild('titulo') tituloRef!: ElementRef;

  constructor(
    private commCatalogo: CommBusquedaCatalogo
  ) { }

  clickBuscar() {
    const titulo = this.tituloRef.nativeElement.value;
    this.commCatalogo.enviaTitulo(titulo);
  }

}
