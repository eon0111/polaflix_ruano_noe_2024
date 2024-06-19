import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommIndiceCatalogo {

  private inicialSubject = new Subject<string>();
  private errorInicialSubject = new Subject<boolean>();

  inicial$ = this.inicialSubject.asObservable();

  enviaInicial(inicial: string) {
    this.inicialSubject.next(inicial);
  }

}
