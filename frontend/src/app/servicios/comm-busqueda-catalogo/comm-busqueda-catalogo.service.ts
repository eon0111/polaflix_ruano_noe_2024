import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommBusquedaCatalogo {

  private tituloSubject = new Subject<string>();

  titulo$ = this.tituloSubject.asObservable();

  enviaTitulo(titulo: string) {
    this.tituloSubject.next(titulo);
  }

}
