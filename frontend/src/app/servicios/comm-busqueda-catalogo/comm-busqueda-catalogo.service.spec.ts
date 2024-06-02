import { TestBed } from '@angular/core/testing';

import { CommBusquedaCatalogoService } from './comm-busqueda-catalogo.service';

describe('CommBusquedaCatalogoService', () => {
  let service: CommBusquedaCatalogoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommBusquedaCatalogoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
