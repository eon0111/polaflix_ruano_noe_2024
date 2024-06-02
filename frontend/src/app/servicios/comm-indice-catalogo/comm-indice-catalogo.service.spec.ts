import { TestBed } from '@angular/core/testing';

import { CommIndiceCatalogoService } from './comm-indice-catalogo.service';

describe('CommIndiceCatalogoService', () => {
  let service: CommIndiceCatalogoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommIndiceCatalogoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
