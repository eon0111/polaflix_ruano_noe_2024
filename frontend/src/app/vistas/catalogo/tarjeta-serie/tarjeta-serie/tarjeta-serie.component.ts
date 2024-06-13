import { Component, Input } from '@angular/core';
import { Serie } from '../../../../interfaces/serie';
import { CommonModule } from '@angular/common';
import { PersonalSerie } from '../../../../interfaces/personalSerie';
import { UsuariosService } from '../../../../servicios/usuarios/usuarios.service';

@Component({
  selector: 'app-tarjeta-serie',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tarjeta-serie.component.html',
  styleUrl: './tarjeta-serie.component.css'
})
export class TarjetaSerieComponent {

  @Input() serie: Serie | null = null;

  mostrarDetalles: boolean = false;

  constructor(
    private usuariosService: UsuariosService
  ) {}

  formateaNombrePersona(persona: PersonalSerie): string {
    const nombres = [persona.nombre, persona.apellido1, persona.apellido2].filter(Boolean);
    return nombres.join(' ');
  }

  toggleDetalles(): void {
    this.mostrarDetalles = !this.mostrarDetalles;
  }

  anhadePendiente(idSerie: number): void {
    if (idSerie != -1) {
      this.usuariosService.anhadeSeriePendiente("pacoloco", idSerie).subscribe();
    }
  }

}
