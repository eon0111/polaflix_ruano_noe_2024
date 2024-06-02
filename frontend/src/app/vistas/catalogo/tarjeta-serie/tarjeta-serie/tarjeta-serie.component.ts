import { Component, Input } from '@angular/core';
import { Serie } from '../../../../interfaces/serie';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tarjeta-serie',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tarjeta-serie.component.html',
  styleUrl: './tarjeta-serie.component.css'
})
export class TarjetaSerieComponent {

  @Input() serie: Serie | null = null;

}
