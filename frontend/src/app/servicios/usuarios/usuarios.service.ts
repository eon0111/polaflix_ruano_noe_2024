import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';
import { Usuario } from '../../interfaces/usuario';
import { Serie } from '../../interfaces/serie';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private usuariosUrl = 'http://localhost:8080/usuarios';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  getUsuario(nombre: string): Observable<Usuario> {
    const url = `${this.usuariosUrl}/${nombre}`;
    return this.http.get<Usuario>(url).pipe();
  }

  anhadeSeriePendiente(usuario: string, idSerie: number): Observable<Serie> {
    const url = `${this.usuariosUrl}/${usuario}/series-pendientes/${idSerie}`;
    return this.http.put<Serie>(url, null).pipe();
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
