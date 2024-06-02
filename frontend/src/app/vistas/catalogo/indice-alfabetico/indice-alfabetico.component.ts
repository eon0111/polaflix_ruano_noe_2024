import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogoComponent } from '../catalogo.component';

@Component({
  selector: 'app-indice-alfabetico',
  standalone: true,
  imports: [CommonModule, CatalogoComponent],
  templateUrl: './indice-alfabetico.component.html',
  styleUrl: './indice-alfabetico.component.css'
})
export class IndiceAlfabeticoComponent {

  @ViewChild(CatalogoComponent) catalogo!: CatalogoComponent;

  abecedario: string[] = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

  getSeriesByInicial(inicial: string): void {
    this.catalogo.getSeriesByInicial(inicial);
  }

}
