import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogoComponent } from '../catalogo.component';
import { CommIndiceCatalogo } from '../../../servicios/comm-indice-catalogo/comm-indice-catalogo.service';

@Component({
  selector: 'app-indice-alfabetico',
  standalone: true,
  imports: [CommonModule, CatalogoComponent],
  templateUrl: './indice-alfabetico.component.html',
  styleUrl: './indice-alfabetico.component.css'
})
export class IndiceAlfabeticoComponent {

  abecedario: string[] = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

  constructor(
    private commCatalogo: CommIndiceCatalogo
  ) { }

  clickInicial(inicial: string): void {
    this.commCatalogo.enviaInicial(inicial);
  }

}
