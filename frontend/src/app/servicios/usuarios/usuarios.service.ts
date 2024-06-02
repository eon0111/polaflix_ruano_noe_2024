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

  //anhadeSeriePendiente(idSerie: number): Observable<Serie> {
  //  const url = `${this.usuariosUrl}/series-pendientes/${idSerie}`;
  //  return TODO
  //}

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
