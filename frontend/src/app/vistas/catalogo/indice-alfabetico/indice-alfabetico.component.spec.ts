import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndiceAlfabeticoComponent } from './indice-alfabetico.component';

describe('IndiceAlfabeticoComponent', () => {
  let component: IndiceAlfabeticoComponent;
  let fixture: ComponentFixture<IndiceAlfabeticoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndiceAlfabeticoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(IndiceAlfabeticoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
