import { Component, Input, OnInit } from '@angular/core';
import { Usuario } from '../../interfaces/usuario';
import { UsuariosService } from '../../servicios/usuarios/usuarios.service';

@Component({
  selector: 'app-panel-series',
  standalone: true,
  imports: [],
  templateUrl: './panel-series.component.html',
  styleUrl: './panel-series.component.css'
})
export class PanelSeriesComponent implements OnInit {

  @Input() nombreUsuario = '';

  constructor (
    private usuariosService: UsuariosService,
    private usuario: Usuario
  ) {}

  ngOnInit(): void {
      this.getUsuario(this.nombreUsuario);
  }

  getUsuario(nombre: string): void {
    this.usuariosService.getUsuario(this.nombreUsuario).subscribe(usuario => this.usuario = usuario);
  }

}
