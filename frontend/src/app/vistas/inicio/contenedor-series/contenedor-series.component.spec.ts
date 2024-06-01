import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContenedorSeriesComponent } from './contenedor-series.component';

describe('ContenedorSeriesComponent', () => {
  let component: ContenedorSeriesComponent;
  let fixture: ComponentFixture<ContenedorSeriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContenedorSeriesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ContenedorSeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
