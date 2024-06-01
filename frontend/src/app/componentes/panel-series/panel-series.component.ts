import { Component, Inject, Input, OnInit } from '@angular/core';
import { Usuario } from '../../interfaces/usuario';
import { UsuariosService } from '../../servicios/usuarios/usuarios.service';
import { SerieUsuario } from '../../interfaces/serieUsuario';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-panel-series',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './panel-series.component.html',
  styleUrl: './panel-series.component.css'
})
export class PanelSeriesComponent implements OnInit {

  @Input() nombreUsuario: string = '';

  usuario: Usuario | null = null;

  constructor (
    private usuariosService: UsuariosService
  ) {}

  ngOnInit(): void {
    if (this.nombreUsuario) {
      this.getUsuario()
    }
  }

  getUsuario(): void {
    this.usuariosService.getUsuario(this.nombreUsuario).subscribe(
      usuario => {
        this.usuario = usuario
      },
    )
  }

  getSeries(map: { [id: number]: SerieUsuario }): any[] {
    return Object.entries(map).map(([id, serie]) => ({ id, serie }));
  }

}
