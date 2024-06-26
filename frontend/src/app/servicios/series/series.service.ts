import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable} from 'rxjs';
import { Serie } from '../../interfaces/serie';

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private seriesUrl = 'http://localhost:8080/series';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  getSeriesByInicial(inicial: string): Observable<Serie[]> {
    const url = `${this.seriesUrl}?inicial=${inicial}`;
    return this.http.get<Serie[]>(url).pipe();
  }

  getSeriesByTitulo(titulo: string): Observable<Serie[]> {
    const url = `${this.seriesUrl}?titulo=${titulo}`;
    return this.http.get<Serie[]>(url).pipe();
  }

}
